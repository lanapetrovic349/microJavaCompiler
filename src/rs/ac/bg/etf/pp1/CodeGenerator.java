package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	private int relop = -1;
	private int mainPc;
	private int currentSwitchLevel = 0;
	
	private Stack<Integer> skipCondFact = new Stack<>(); // for False Jumps
	private Stack<Integer> endIfStack = new Stack<>();   // jump over the ELSE to the end
	private Stack<Integer> trueJumpsStack = new Stack<>(); // for short-circuit logic in OR - if one condition is true, skip all others

	private Stack<Integer> adrCond = new Stack<>(); // cond start
	private Stack<Integer> adrStep = new Stack<>(); // increment start
	private Stack<Integer> jmpToBody = new Stack<>(); // skip increment - first for iteration
	private Stack<List<Integer>> breakStack = new Stack<>(); // break stack - every loop,switch opens a new List
	
	private Stack<Integer> condFactCountStack = new Stack<>(); // for cond stack - keeps the size of skipCondFact stack at the beginning of the loop
	private Stack<Integer> ifCondCountStack = new Stack<>(); // if cond stack - keeps the size of the skipCondFact stack at the beginning of the IF stmt
	
	private Stack<Integer> ternaryEndJumps = new Stack<>();
	private Stack<List<Integer>> ternaryFalseJumpsStack = new Stack<>(); // false jumps in ternary structure, enables embedded structures
	private Stack<Integer> lastFalseJumpAddr = new Stack<>(); // keeps the address of the jump if case is false
	private Stack<Integer> fallthroughJumpAddr = new Stack<>(); // skip checking the next case if the last one doesn't have break stmt
	
	
	public int getmainPc() {
		return this.mainPc;
	}

	@Override
	public void visit(MethodName methodName) {
		methodName.obj.setAdr(Code.pc);

		if (methodName.getI1().equals("main"))
			this.mainPc = Code.pc;

		Code.put(Code.enter);
		Code.put(methodName.obj.getLevel());
		Code.put(methodName.obj.getLocalSymbols().size());
	}

	@Override
	public void visit(MethodDecl methodDecl) {
		Obj methodObj = methodDecl.getMethodName().obj;

		if (methodObj.getType() != Tab.noType) { // method not void
			Code.put(Code.trap);				 // return not found
			Code.put(1);
		}

		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(StmtReturnExpr stmt) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(StmtReturnNoExpr stmt) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(StmtPrint stmt) {
		int width = 0;

		if (stmt.getPrintOpt() instanceof PrintOptYes) {
			width = ((PrintOptYes) stmt.getPrintOpt()).getN1();
		}

		Code.loadConst(width);

		if (stmt.getExpr().struct == Tab.charType)
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
	}

	@Override
	public void visit(FactorNumber factor) {
		Code.loadConst(factor.getN1());
	}

	@Override
	public void visit(FactorChar factor) {
		Code.loadConst(factor.getC1());
	}

	@Override
	public void visit(FactorBool factor) {
		Code.loadConst(factor.getB1());
	}

	@Override
	public void visit(ArithmeticExprAdd expr) {
		if (expr.getAddop() instanceof AddopPlus) {
			Code.put(Code.add);
		} else
			Code.put(Code.sub);
	}

	@Override
	public void visit(TermMul term) {
		if (term.getMulop() instanceof MulopMul) {
			Code.put(Code.mul);
		} else if (term.getMulop() instanceof MulopDiv) {
			Code.put(Code.div);
		} else
			Code.put(Code.rem);
	}

	@Override
	public void visit(FactorDesOrFuncCall node) {
	    Obj o = node.getDesignator().obj;

	    if (node.getCallOpt() instanceof CallOptEpsilon) {
	        if (o != null && (o.getKind() == Obj.Var || o.getKind() == Obj.Con || 
	                          o.getKind() == Obj.Elem || o.getKind() == Obj.Fld)) {
	            Code.load(o);
	        }
	        return;
	    }

	    String funcName = o.getName();
	    
	    if (funcName.equals("ord") || funcName.equals("chr")) {
	        return;
	    } 
	    
	    if (funcName.equals("len")) {
	        Code.put(Code.arraylength);
	        return;
	    }

	    int offset = o.getAdr() - Code.pc;
	    Code.put(Code.call);
	    Code.put2(offset);
	}

	@Override
	public void visit(DesignatorStmtCall stmt) {
		Obj o = stmt.getDesignator().obj;

		int offset = o.getAdr() - Code.pc;

		Code.put(Code.call);
		Code.put2(offset);

		if (o.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}

	@Override
	public void visit(DesignatorStmtAssign stmt) {
		Obj obj = stmt.getDesignator().obj;
		Code.store(obj);
	}

	@Override
	public void visit(Factor factor) {
		if (factor.getUnary() instanceof UnaryMinus) {
			Code.put(Code.neg);
		}
	}

	@Override
	public void visit(FactorNew factor) {
		Code.put(Code.newarray);

		if (factor.getType().struct.equals(Tab.charType)) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}

	@Override
	public void visit(DesignatorTailMore t) {
		Obj base;

		if (t.getDesignatorTail() instanceof DesignatorTailEpsilon) {

			Designator parent = (Designator) t.getParent();
			base = parent.getDesignatorName().obj;

		} else {
			base = t.getDesignatorTail().obj;
		}

		if (base == null || base == Tab.noObj) {
			t.obj = Tab.noObj;
			return;
		}

		DesignatorSuffix suf = t.getDesignatorSuffix();

		if (suf instanceof DesignatorSuffixField) {
			String fieldName = ((DesignatorSuffixField) suf).getI1();

			if (base.getKind() == Obj.Type && base.getType().getKind() == rs.etf.pp1.symboltable.concepts.Struct.Enum) {
				for (Obj o : base.getLocalSymbols()) {
					if (o.getName().equals(fieldName)) {
						t.obj = o;
						return;
					}
				}
			}

			t.obj = Tab.noObj;
			return;
		}

		// array
		if (suf instanceof DesignatorSuffixArray) {
			Code.load(base);
			Code.put(Code.dup_x1);
			Code.put(Code.pop);

			t.obj = new Obj(Obj.Elem, base.getName(), base.getType().getElemType());
			return;
		}

		// len
		if (suf instanceof DesignatorSuffixLength) {
			Code.load(base);
			Code.put(Code.arraylength);
			t.obj = null;
			return;
		}

		t.obj = base;
	}

	@Override
	public void visit(Designator d) {
		d.obj = d.getDesignatorTail().obj;
	}

	@Override
	public void visit(DesignatorStmtInc stmt) {
		Obj o = stmt.getDesignator().obj;

		if (o.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		else if (o.getKind() == Obj.Fld)
			Code.put(Code.dup);

		Code.load(o);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(o);
	}

	@Override
	public void visit(DesignatorStmtDec stmt) {
		Obj o = stmt.getDesignator().obj;

		if (o.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		else if (o.getKind() == Obj.Fld)
			Code.put(Code.dup);

		Code.load(o);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(o);
	}

	@Override
	public void visit(StmtRead stmt) {
		Obj obj = stmt.getDesignator().obj;

		if (obj.getType().equals(Tab.charType))
			Code.put(Code.bread);
		else
			Code.put(Code.read);

		Code.store(obj);
	}

	@Override
	public void visit(RelopEQ relop) {
		this.relop = Code.eq;
	}

	@Override
	public void visit(RelopNEQ relop) {
		this.relop = Code.ne;
	}

	@Override
	public void visit(RelopGT relop) {
		this.relop = Code.gt;
	}

	@Override
	public void visit(RelopGTE relop) {
		this.relop = Code.ge;
	}

	@Override
	public void visit(RelopLT relop) {
		this.relop = Code.lt;
	}

	@Override
	public void visit(RelopLTE relop) {
		this.relop = Code.le;
	}

	@Override
	public void visit(CondFact node) {
	    if (relop == -1) {					// if (a) or if (verify) where a/verify return boolean
	        Code.loadConst(0);				// put 0 on stack to cmp it to the boolean value
	        Code.putFalseJump(Code.ne, 0);  // if the boolean value == 0, we should jump
	    } else {
	        Code.putFalseJump(relop, 0);    // both values already on stack, just jump
	    }
	    skipCondFact.push(Code.pc - 2);	    // remember the address of the 0 address to fix-up later
	    relop = -1;
	}
	

	@Override
	public void visit(IfCondMarker node) {
		ifCondCountStack.push(skipCondFact.size());	// capture current state of stack
	}

	@Override
	public void visit(ThenEndMarker node) {
		Code.putJump(0);								// we need to skip the else if we come from then 
		endIfStack.push(Code.pc - 2);

		int previousSize = ifCondCountStack.peek();

		while (skipCondFact.size() > previousSize) {	// the threads that failed the condition should come here
			Code.fixup(skipCondFact.pop());
		}
	}

	@Override
	public void visit(StmtIf node) {
		int previousSize = ifCondCountStack.pop();

		if (node.getElseStatement() instanceof ElseStmtYes) {	// the successful if jumps come here, they skipped else
			if (!endIfStack.isEmpty()) {
				Code.fixup(endIfStack.pop());
			}
		} else {
			while (skipCondFact.size() > previousSize) {		// if there was no else, unsuccessful jumps also come here
				Code.fixup(skipCondFact.pop());
			}
		}
	}

	@Override
	public void visit(OrMarker orMarker) {
	    Code.putJump(0);																			// condition was estimated true, do not check other ones
	    trueJumpsStack.push(Code.pc - 2);

	    int limit = 0;
	    if (!ifCondCountStack.isEmpty()) limit = Math.max(limit, ifCondCountStack.peek());
	    if (!condFactCountStack.isEmpty()) limit = Math.max(limit, condFactCountStack.peek());
	    

	    while (skipCondFact.size() > limit) {														// if there were false jumps, they should come here to check the other ones
	        Code.fixup(skipCondFact.pop());
	    }
	}

	@Override
	public void visit(CondEndMarker node) {
		while (!trueJumpsStack.isEmpty()) {			// start of then part
			Code.fixup(trueJumpsStack.pop());		// all threads that passed the condition come here
		}
	}

	@Override
	public void visit(ForCondMarker marker) {
		adrCond.push(Code.pc);							// push current PC to return here
		condFactCountStack.push(skipCondFact.size());	
		breakStack.push(new ArrayList<Integer>());
	}

	@Override
	public void visit(ForStepMarker marker) {
	    Code.putJump(0);								// jump to body when we first encounter this we skip increment
	    jmpToBody.push(Code.pc - 2);
	    adrStep.push(Code.pc);							// address where continue will jump
	    
	    condFactCountStack.push(skipCondFact.size()); 
	}

	@Override
	public void visit(ForStepEndMarker marker) {
		if (!adrCond.isEmpty())							// to jump to condition check again
			Code.putJump(adrCond.peek());
	}

	@Override
	public void visit(ForBodyMarker marker) {
		if (!jmpToBody.isEmpty()) {						// start of the body
			Code.fixup(jmpToBody.pop());
		}
	}
	
	@Override
	public void visit(StmtFor stmt) {
	    if (!adrStep.isEmpty()) {						// jmp to increment again
	        Code.putJump(adrStep.pop());
	    }

	    Code.putJump(Code.pc + 3);						
	    int safeAddr = Code.pc - 2;						

	    if (!condFactCountStack.isEmpty()) {			
	        condFactCountStack.pop(); 
	        
	        int originalLimit = condFactCountStack.pop(); 

	        while (skipCondFact.size() > originalLimit) { // fix up for the false jumps that come from current loop
	            Code.fixup(skipCondFact.pop());
	        }
	    }
	    
	    
	    Code.fixup(safeAddr);

	    if (!breakStack.isEmpty()) {
	        List<Integer> currentBreaks = breakStack.pop();		// all breaks from current loop should jump here, at the end of the loop
	        for (int address : currentBreaks) {
	            Code.fixup(address);
	        }
	    }

	    if (!adrCond.isEmpty()) adrCond.pop();					// at the end, pop the cond address of current loop, if it was an inner loop, now the 
	}															// top of the stack will be the outer loop cond addr

	@Override
	public void visit(StmtContinue stmt) {
	    for (int i = 0; i < currentSwitchLevel; i++) {			// if in switch, clean the stack from the values
	        Code.put(Code.pop);
	    }
	    Code.putJump(adrStep.peek());							// jump to increment
	}
	
	@Override
	public void visit(TernaryQuestionMarker marker) {											 // the condition is already calculated here
	    int limit = 0;
	    if (!condFactCountStack.isEmpty()) limit = Math.max(limit, condFactCountStack.peek());
	    if (!ifCondCountStack.isEmpty()) limit = Math.max(limit, ifCondCountStack.peek());

	    
	    List<Integer> myFalseJumps = new ArrayList<>(); 										// if the cond is false, we skip the first expr
	    
	    while (skipCondFact.size() > limit) {
	        myFalseJumps.add(skipCondFact.pop());
	    }
	    
	    ternaryFalseJumpsStack.push(myFalseJumps);

	    
	    while (!trueJumpsStack.isEmpty()) {														// if the cond is true, we fix up the true jumps here
	        Code.fixup(trueJumpsStack.pop());
	    }
	}

	@Override
	public void visit(TernaryColonMarker marker) {												// after :, at the end of expr1 and start of expr2
	    Code.putJump(0);																		// if we evaluated expr1, we must not evaluate expr2 
	    ternaryEndJumps.push(Code.pc - 2);														// save the address to fix up later

	    if (!ternaryFalseJumpsStack.isEmpty()) {
	        List<Integer> falseJumps = ternaryFalseJumpsStack.pop();
	        for (int addr : falseJumps) {
	            Code.fixup(addr);																// fix up the jumps for all false jumps that should evaluate expr2
	        }
	    }
	}

	@Override
	public void visit(ExprTernary node) {
	    Code.fixup(ternaryEndJumps.pop());														// fix up those that skipped expr2 
	}

	@Override
	public void visit(SwitchExprEndMarker marker) {												// we have a value on stack, switch(x) , x is on stack
	    breakStack.push(new ArrayList<Integer>());
	    lastFalseJumpAddr.push(-1); 															// to mark that this is the first case
	    fallthroughJumpAddr.push(-1);
	    
	    currentSwitchLevel++; 
	}

	@Override
	public void visit(CaseMarker marker) {
	    int currentLastFalse = lastFalseJumpAddr.pop();											// if first case - currentLastFalse == -1
	    
	    if (currentLastFalse != -1) {
	        Code.putJump(0);																				
	        int myFallthrough = Code.pc - 2;													// skip checking the case, the last case didn't have break
	        
	        Code.fixup(currentLastFalse);														// if previous case num != x, try this case now
	        
	        Code.put(Code.dup);																	// same logic
	        CaseClause parent = (CaseClause) marker.getParent();
	        Code.loadConst(parent.getN1()); 
	        Code.putFalseJump(Code.eq, 0); 
	        lastFalseJumpAddr.push(Code.pc - 2);

	        Code.fixup(myFallthrough);
	    } else {
	        Code.put(Code.dup);																	// duplicate x on stack, one to check condition and another one for the next case
	        CaseClause parent = (CaseClause) marker.getParent();								
	        Code.loadConst(parent.getN1()); 
	        Code.putFalseJump(Code.eq, 0); 														// if x and case num are not equal, jump somewhere
	        lastFalseJumpAddr.push(Code.pc - 2);												// remember this as the last case
	    }
	}

	@Override
	public void visit(StmtSwitch stmt) {
	    int lastJump = lastFalseJumpAddr.pop();
	    
	    if (lastJump != -1) {																   // last jump should come here
	        Code.fixup(lastJump);
	    }
	    
	    List<Integer> breaks = breakStack.pop();
	    for (int addr : breaks) {
	        Code.fixup(addr);																  // all jumps from break should come here
	    }

	    Code.put(Code.pop);																	  // pop the original x from stack
	    currentSwitchLevel--;

	    if (!fallthroughJumpAddr.isEmpty()) {												  // clean the stack of current switch
	        fallthroughJumpAddr.pop();
	    }
	}
	
	@Override
	public void visit(StmtBreak stmt) {
	    Code.putJump(0);
	    if (!breakStack.isEmpty()) {														 // add this break to the list of breaks for current loop / switch
	        breakStack.peek().add(Code.pc - 2);												
	    }
	}
	
} 