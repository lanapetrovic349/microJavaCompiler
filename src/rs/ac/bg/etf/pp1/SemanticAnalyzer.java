package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {

	private boolean isError = false;
	private Logger log = Logger.getLogger(getClass());
	private Obj myProgramName;
	private Struct currentType;
	private int constant;
	private Struct constantType;
	private Struct boolType = Tab.find("bool").getType();
	private Obj currentMethod;
	private Obj mainMethod = null;
	private Obj currentEnumObj;
	private int enumValueCounter;
	private Set<Integer> enumUsedValues = new HashSet<>();
	private int loopLevel = 0;
	private int switchLevel = 0;
	int nVars;

	public void report_error(String message, SyntaxNode info) {
		isError = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0) {
			msg.append(" na liniji ").append(line);
		}
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0) {
			msg.append(" na liniji ").append(line);
		}
		log.info(msg.toString());
	}

	public boolean isError() {
		return isError;
	}

	@Override
	public void visit(ProgramName programName) {
		myProgramName = Tab.insert(Obj.Prog, programName.getI1(), Tab.noType);
		Tab.openScope();
	}

	@Override
	public void visit(Program program) {
		if (mainMethod == null) {
			report_error("Ne postoji main metoda!", program);
		} else {
			if (mainMethod.getType() != Tab.noType) {
				report_error("main mora biti void!", program);
			}

			if (mainMethod.getLevel() != 0) {
				report_error("main ne sme imati parametre!", program);
			}
		}
		nVars = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(myProgramName);
		Tab.closeScope();
		myProgramName = null;
	}

	@Override
	public void visit(ConstInit constInit) {
		Obj conObject = Tab.find(constInit.getI1());
		if (conObject != Tab.noObj) {
			report_error("Dvostruka definicija konstante: " + constInit.getI1(), constInit);
		} else {
			if (constantType.equals(currentType)) {
				conObject = Tab.insert(Obj.Con, constInit.getI1(), currentType);
				conObject.setAdr(constant);
			} else {
				report_error("Neadekvatna dodela konstanti: " + constInit.getI1(), constInit);
			}
		}
	}

	@Override
	public void visit(Type type) {
		Obj typeObject = Tab.find(type.getI1());

		if (typeObject == Tab.noObj) {
			report_error("Nepostojeci tip podatka: " + type.getI1(), type);
			type.struct = currentType = Tab.noType;
			return;
		}

		if (typeObject.getKind() != Obj.Type) {
			report_error("Neadekvatan tip podatka: " + type.getI1(), type);
			type.struct = currentType = Tab.noType;
			return;
		}

		if (typeObject.getType().getKind() == Struct.Enum) {
			type.struct = currentType = Tab.intType;
		} else {
			type.struct = currentType = typeObject.getType();
		}
	}

	@Override
	public void visit(ConstNum constNum) {
		constant = constNum.getN1();
		constantType = Tab.intType;
	}

	@Override
	public void visit(ConstChar constChar) {
		constant = constChar.getC1();
		constantType = Tab.charType;
	}

	@Override
	public void visit(ConstBool constBool) {
		constant = constBool.getB1();
		constantType = boolType;
	}

	@Override
	public void visit(VarDeclItemSingle varDeclItem) {
		Obj varObject = null;

		if (currentMethod == null) {
			varObject = Tab.find(varDeclItem.getI1());
		} else {
			varObject = Tab.currentScope().findSymbol(varDeclItem.getI1());
		}
		if (varObject != null && varObject != Tab.noObj) {
			report_error("Dvostruka definicija promenljive: " + varDeclItem.getI1(), varDeclItem);
		} else {
			varObject = Tab.insert(Obj.Var, varDeclItem.getI1(), currentType);
		}
	}

	@Override
	public void visit(VarDeclItemArray varArray) {
		Obj varObject = null;

		if (currentMethod == null) {
			varObject = Tab.find(varArray.getI1());
		} else {
			varObject = Tab.currentScope().findSymbol(varArray.getI1());
		}
		if (varObject != null && varObject != Tab.noObj) {
			report_error("Dvostruka definicija promenljive: " + varArray.getI1(), varArray);
		} else {
			varObject = Tab.insert(Obj.Var, varArray.getI1(), new Struct(Struct.Array, currentType));
		}
	}

	@Override
	public void visit(MethodName methodName) {
		Obj methodObj = Tab.find(methodName.getI1());

		if (methodObj != Tab.noObj) {
			report_error("Metoda vec postoji: " + methodName.getI1(), methodName);
			currentMethod = null;
		} else {
			methodName.obj = currentMethod = Tab.insert(Obj.Meth, methodName.getI1(), currentType);
			Tab.openScope();

			if (methodName.getI1().equals("main")) {
				mainMethod = currentMethod;
			}

			report_info("Obradjuje se metoda: " + methodName.getI1(), methodName);
		}
	}

	@Override
	public void visit(MethodTypeVoid methodTypeVoid) {
		currentType = Tab.noType;
	}

	@Override
	public void visit(MethodDecl methodDecl) {
		if (currentMethod != null) {

			Tab.chainLocalSymbols(currentMethod);
			Tab.closeScope();
		}
		currentMethod = null;
	}

	@Override
	public void visit(FormParSingle formPar) {
		String paramName = formPar.getI2();

		if (Tab.currentScope().findSymbol(paramName) != null) {
			report_error("Parametar vec postoji: " + paramName, formPar);
		} else {
			Obj paramObj = Tab.insert(Obj.Var, paramName, currentType);

			int newLevel = currentMethod.getLevel() + 1;
			currentMethod.setLevel(newLevel);

			paramObj.setFpPos(newLevel);

		}
	}

	@Override
	public void visit(FormParArray formPar) {
		String paramName = formPar.getI2();

		if (Tab.currentScope().findSymbol(paramName) != null) {
			report_error("Parametar vec postoji: " + paramName, formPar);
		} else {
			Struct arrayType = new Struct(Struct.Array, currentType);

			Obj paramObj = Tab.insert(Obj.Var, paramName, arrayType);

			int newLevel = currentMethod.getLevel() + 1;
			currentMethod.setLevel(newLevel);

			paramObj.setFpPos(newLevel);
		}
	}

	@Override
	public void visit(EnumName enumName) {
		String name = enumName.getI1();

		if (Tab.currentScope().findSymbol(name) != null) {
			report_error("Enum vec postoji: " + name, enumName);
			return;
		}

		Struct enumStruct = new Struct(Struct.Enum);
		currentEnumObj = Tab.insert(Obj.Type, name, enumStruct);
		currentEnumObj.setLevel(0);

		enumValueCounter = 0;
		enumUsedValues.clear();

		Tab.openScope();
	}

	@Override
	public void visit(EnumValueDecl enumValue) {
		String name = enumValue.getI1();

		if (Tab.currentScope().findSymbol(name) != null) {
			report_error("Duplikat enum konstante: " + name, enumValue);
			return;
		}

		if (enumUsedValues.contains(enumValueCounter)) {
			report_error("Enum vrednost mora biti jedinstvena", enumValue);
			return;
		}

		Obj obj = Tab.insert(Obj.Con, name, Tab.intType);
		obj.setAdr(enumValueCounter);

		enumUsedValues.add(enumValueCounter);
		enumValueCounter++;
	}

	@Override
	public void visit(EnumValueDef enumValue) {
		String name = enumValue.getI1();
		int value = enumValue.getN2();

		if (Tab.currentScope().findSymbol(name) != null) {
			report_error("Duplikat enum konstante: " + name, enumValue);
			return;
		}

		if (enumUsedValues.contains(value)) {
			report_error("Enum vrednost mora biti jedinstvena", enumValue);
			return;
		}

		Obj obj = Tab.insert(Obj.Con, name, Tab.intType);
		obj.setAdr(value);

		enumUsedValues.add(value);
		enumValueCounter = value + 1;
	}

	@Override
	public void visit(EnumDecl enumDecl) {
		if (currentEnumObj != null) {
			Tab.chainLocalSymbols(currentEnumObj);
			Tab.closeScope();
		}

		currentEnumObj = null;
	}

	@Override
	public void visit(FactorChar factorChar) {
		factorChar.struct = Tab.charType;
	}

	@Override
	public void visit(FactorNumber factorNumber) {
		factorNumber.struct = Tab.intType;
	}

	@Override
	public void visit(FactorBool factorBool) {
		factorBool.struct = boolType;
	}

	@Override
	public void visit(Factor factor) {
		Struct baseType = factor.getFactorNeg().struct;

		if (factor.getUnary() instanceof UnaryMinus) {
			if (baseType == Tab.intType)
				factor.struct = Tab.intType;
			else {
				report_error("Ne moze se negirati dati tip", factor);
				factor.struct = Tab.noType;
			}
		} else {
			factor.struct = baseType;
		}
	}

	@Override
	public void visit(FactorNew factorNew) {
		if (factorNew.getExpr() == null || factorNew.getExpr().struct == null)
			return;

		if (factorNew.getExpr().struct != Tab.intType) {
			report_error("Velicina niza mora biti int", factorNew);
			factorNew.struct = Tab.noType;
			return;
		}

		factorNew.struct = new Struct(Struct.Array, currentType);
	}

	@Override
	public void visit(FactorParen factorParen) {
		factorParen.struct = factorParen.getExpr().struct;
	}

	@Override
	public void visit(FactorDesOrFuncCall node) {
		Obj obj = node.getDesignator().obj;

		if (obj == null || obj == Tab.noObj) {
			node.struct = Tab.noType;
			return;
		}

		if (node.getCallOpt() instanceof CallOptEpsilon) {
			node.struct = obj.getType();
			return;
		}

		if (obj.getKind() != Obj.Meth) {
			report_error("Poziv se moze vrsiti samo nad metodom", node);
			node.struct = Tab.noType;
			return;
		}

		List<Struct> actualTypes = new ArrayList<>();

		CallOpt call = (CallOpt) node.getCallOpt();
		if (call instanceof CallOptYes) {
			ActParsOpt apo = ((CallOptYes) call).getActParsOpt();
			if (apo instanceof ActParsYes) {
				actualTypes = collectActParsTypes(((ActParsYes) apo).getActPars());
			}
		}

		int expected = obj.getLevel();
		int actual = actualTypes.size();

		if (expected != actual) {
			report_error("Pogresan broj argumenata", node);
		} else {
			int i = 0;
			for (Obj fp : obj.getLocalSymbols()) {
				if (i >= actual)
					break;

				if (!actualTypes.get(i).assignableTo(fp.getType())) {
					report_error("Neispravan tip argumenta", node);
					break;
				}
				i++;
			}
		}

		node.struct = obj.getType();
	}

	private List<Struct> collectActParsTypes(ActPars actPars) {
		List<Struct> list = new ArrayList<>();

		if (actPars instanceof ActParsSingle) {
			list.add(((ActParsSingle) actPars).getExpr().struct);
		} else if (actPars instanceof ActParsMore) {
			ActParsMore more = (ActParsMore) actPars;
			list.addAll(collectActParsTypes(more.getActPars()));
			list.add(more.getExpr().struct);
		}

		return list;
	}

	@Override
	public void visit(TermFactor term) {
		term.struct = term.getFactor().struct;
	}

	@Override
	public void visit(ExprRegular expr) {
	    expr.struct = expr.getArithmeticExpr().struct;
	}

	@Override
	public void visit(ExprTernary expr) {
	    // 1. Provera uslova (Condition)
	    Struct condType = expr.getCondition().struct;
	    if (condType == null || !condType.assignableTo(boolType)) {
	        report_error("Uslov ternarnog operatora mora biti tipa bool", expr);
	    }

	    // 2. Provera grana (Sada su one tipa ArithmeticExpr)
	    // CUP će ih verovatno nazvati getArithmeticExpr (leva) i getArithmeticExpr1 (desna)
	    Struct trueBranchType = expr.getExpr().struct;
	    Struct falseBranchType = expr.getExpr1().struct;

	    if (trueBranchType != null && falseBranchType != null) {
	        if (trueBranchType.compatibleWith(falseBranchType)) {
	            // Rezultat celog Expr je tipa njegovih grana
	            expr.struct = trueBranchType;
	        } else {
	            report_error("Tipovi grana ternarnog operatora nisu kompatibilni", expr);
	            expr.struct = Tab.noType;
	        }
	    } else {
	        expr.struct = Tab.noType;
	    }
	}

	@Override
	public void visit(DesignatorName designatorName) {
		Obj obj = Tab.find(designatorName.getI1());

		if (obj == Tab.noObj) {
			report_error("Simbol nije nadjen: " + designatorName.getI1(), designatorName);
			designatorName.obj = Tab.noObj;
		} else if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Con && obj.getKind() != Obj.Elem
				&& obj.getKind() != Obj.Type && obj.getKind() != Obj.Meth) {
			report_error("Neadekvatna promenljiva " + designatorName.getI1(), designatorName);
			designatorName.obj = Tab.noObj;
		} else {
			designatorName.obj = obj;
		}
	}

	@Override
	public void visit(Designator designator) {
		Obj base = designator.getDesignatorName().obj;

		if (designator.getDesignatorTail() instanceof DesignatorTailEpsilon) {
			designator.obj = base;
		} else {
			designator.obj = designator.getDesignatorTail().obj;
		}
	}

	@Override
	public void visit(DesignatorTailEpsilon tail) {
		SyntaxNode p = tail.getParent();
		while (p != null && !(p instanceof Designator)) {
			p = p.getParent();
		}

		if (p != null) {
			Designator d = (Designator) p;
			tail.obj = d.getDesignatorName().obj;
		} else {
			tail.obj = Tab.noObj;
		}
	}

	@Override
	public void visit(DesignatorTailMore tail) {

		Obj base;

		if (tail.getDesignatorTail() instanceof DesignatorTailEpsilon) {
			Designator parent = (Designator) tail.getParent();
			base = parent.getDesignatorName().obj;
		} else {
			base = tail.getDesignatorTail().obj;
		}

		if (base == null || base == Tab.noObj) {
			tail.obj = Tab.noObj;
			return;
		}

		DesignatorSuffix suffix = tail.getDesignatorSuffix();

		if (suffix instanceof DesignatorSuffixArray) {

			if (base.getType().getKind() != Struct.Array) {
				report_error("Objekat nije niz", tail);
				tail.obj = Tab.noObj;
				return;
			}

			if (((DesignatorSuffixArray) suffix).getExpr().struct != Tab.intType) {
				report_error("Indeks niza mora biti tipa int", tail);
				tail.obj = Tab.noObj;
				return;
			}

			tail.obj = new Obj(Obj.Elem, base.getName(), base.getType().getElemType());
		}

		else if (suffix instanceof DesignatorSuffixLength) {

			if (base.getType().getKind() != Struct.Array) {
				report_error("length se moze koristiti samo nad nizom", tail);
				tail.obj = Tab.noObj;
				return;
			}

			tail.obj = new Obj(Obj.Var, "length", Tab.intType);
		}

		else if (suffix instanceof DesignatorSuffixField) {

			String fieldName = ((DesignatorSuffixField) suffix).getI1();

			if (base.getKind() == Obj.Type && base.getType().getKind() == Struct.Enum) {

				Obj enumConst = null;

				for (Obj o : base.getLocalSymbols()) {
					if (o.getName().equals(fieldName)) {
						enumConst = o;
						break;
					}
				}

				if (enumConst == null) {
					report_error("Enum nema vrednost " + fieldName, tail);
					tail.obj = Tab.noObj;
				} else {
					tail.obj = enumConst;
				}
			} else {
				report_error("Polja klasa nisu implementirana", tail);
				tail.obj = Tab.noObj;
			}
		}
	}

	@Override
	public void visit(TermMul term) {
		Struct left = term.getTerm().struct;
		Struct right = term.getFactor().struct;

		if (left != Tab.intType || right != Tab.intType) {
			report_error("Operatori *, / i %  mogu samo nad int tipovima", term);
			term.struct = Tab.noType;
			return;
		}

		term.struct = Tab.intType;
	}

	@Override
	public void visit(ArithmeticExprAdd expr) {
		Struct left = expr.getArithmeticExpr().struct;
		Struct right = expr.getTerm().struct;

		if (left != Tab.intType || right != Tab.intType) {
			report_error("Operatori + i - mogu samo nad int tipovima", expr);
			expr.struct = Tab.noType;
			return;
		}

		expr.struct = Tab.intType;
	}

	@Override
	public void visit(ArithmeticExprTerm expr) {
		expr.struct = expr.getTerm().struct;
	}

	@Override
	public void visit(StmtReturnNoExpr stmt) {
		if (currentMethod == null) {
			report_error("return van metode", stmt);
			return;
		}

		if (currentMethod.getType() != Tab.noType) {
			report_error("Metoda mora vratiti vrednost", stmt);
		}

	}

	@Override
	public void visit(StmtReturnExpr stmt) {
		if (stmt.getExpr() == null || stmt.getExpr().struct == null)
			return;

		if (currentMethod == null) {
			report_error("return van metode", stmt);
			return;
		}

		Struct exprType = stmt.getExpr().struct;

		if (currentMethod.getType() == Tab.noType) {
			report_error("Void metoda ne sme vracati vrednost", stmt);
		} else if (!exprType.assignableTo(currentMethod.getType())) {
			report_error("Tip return izraza nije kompatibilan sa tipom metode", stmt);
		}
	}

	@Override
	public void visit(DesignatorStmtAssign stmt) {
		if (stmt.getDesignator() == null || stmt.getDesignator().obj == null)
			return;

		if (stmt.getExpr() == null || stmt.getExpr().struct == null)
			return;

		Obj dest = stmt.getDesignator().obj;
		Struct src = stmt.getExpr().struct;

		if (dest.getKind() != Obj.Var && dest.getKind() != Obj.Elem) {
			report_error("Leva strana dodele mora biti promenljiva ili element niza", stmt);
			return;
		}

		if (!src.assignableTo(dest.getType())) {
			report_error("Nekompatibilni tipovi u dodeli", stmt);
		}
	}

	@Override
	public void visit(DesignatorStmtCall stmt) {
		Obj obj = stmt.getDesignator().obj;

		if (obj == null || obj == Tab.noObj)
			return;

		if (obj.getKind() != Obj.Meth) {
			report_error("Poziv se moze vrsiti samo nad metodom", stmt);
			return;
		}

		List<Struct> actualTypes = new ArrayList<>();

		ActParsOpt apo = stmt.getActParsOpt();
		if (apo instanceof ActParsYes) {
			actualTypes = collectActParsTypes(((ActParsYes) apo).getActPars());
		}

		int expected = obj.getLevel();
		int actual = actualTypes.size();

		if (expected != actual) {
			report_error("Pogresan broj argumenata", stmt);
		} else {
			int i = 0;
			for (Obj fp : obj.getLocalSymbols()) {
				if (i >= actual)
					break;

				if (!actualTypes.get(i).assignableTo(fp.getType())) {
					report_error("Neispravan tip argumenta", stmt);
					break;
				}
				i++;
			}
		}

	}

	@Override
	public void visit(DesignatorStmtInc stmt) {
		Obj obj = stmt.getDesignator().obj;

		if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) {
			report_error("++ moze samo nad promenljivom ili elementom niza", stmt);
			return;
		}

		if (!obj.getType().equals(Tab.intType)) {
			report_error("++ zahteva int tip", stmt);
		}
	}

	@Override
	public void visit(DesignatorStmtDec stmt) {

		Obj obj = stmt.getDesignator().obj;

		if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) {
			report_error("-- moze samo nad promenljivom ili elementom niza", stmt);
			return;
		}

		if (!obj.getType().equals(Tab.intType)) {
			report_error("-- zahteva int tip", stmt);
		}
	}

	@Override
	public void visit(StmtRead stmt) {
		Obj obj = stmt.getDesignator().obj;

		if (obj == null || obj == Tab.noObj) {
			report_error("Neispravan designator u read", stmt);
			return;
		}

		if (obj.getKind() != Obj.Var && obj.getKind() != Obj.Elem) {
			report_error("Argument read mora biti promenljiva ili element niza", stmt);
			return;
		}

		Struct type = obj.getType();

		if (type != Tab.intType && type != Tab.charType && type != boolType) {
			report_error("read podrzava samo int, char i bool", stmt);
		}
	}

	@Override
	public void visit(StmtPrint stmt) {
		if (stmt.getExpr() == null || stmt.getExpr().struct == null)
			return;

		Struct exprType = stmt.getExpr().struct;

		if (exprType != Tab.intType && exprType != Tab.charType && exprType != boolType) {
			report_error("print podrzava samo int, char i bool", stmt);
		}

		if (stmt.getPrintOpt() instanceof PrintOptYes) {
			int width = ((PrintOptYes) stmt.getPrintOpt()).getN1();

			if (width < 0) {
				report_error("Sirina ispisa mora biti >= 0", stmt);
			}
		}

	}

	@Override
	public void visit(StmtFor stmt) {
		if (stmt.getForCondOpt() instanceof ForCondYes) {
			Condition cond = ((ForCondYes) stmt.getForCondOpt()).getCondition();
			if (cond.struct == null) {
				report_error("Uslov u for petlji nema definisan tip", stmt);
			} else if (!cond.struct.assignableTo(boolType)) {
				report_error("Uslov u for petlji mora biti tipa bool", stmt);
			}
		}
		loopLevel--;
	}

	@Override
	public void visit(ForCondMarker marker) {
		loopLevel++;
	}

	@Override
	public void visit(SwitchExprEndMarker marker) {
	    StmtSwitch parent = (StmtSwitch) marker.getParent();
	    if (parent.getExpr().struct != Tab.intType) {
	        report_error("Izraz u switch naredbi mora biti tipa int", marker);
	    }
	    switchLevel++;
	}

	@Override
	public void visit(StmtSwitch stmt) {
	    Set<Integer> caseValues = new HashSet<>();
	    collectCases(stmt.getCaseList(), caseValues);

	    switchLevel--;
	}

	private void collectCases(CaseList caseList, Set<Integer> set) {
		if (caseList instanceof CaseListMore) {
			CaseListMore more = (CaseListMore) caseList;
			CaseClause clause = more.getCaseClause();

			int value = clause.getN1();

			if (set.contains(value)) {
				report_error("Duplikat case vrednosti: " + value, clause);
			} else {
				set.add(value);
			}

			collectCases(more.getCaseList(), set);
		}
	}

	@Override
	public void visit(StmtBreak stmt) {
		if (loopLevel == 0 && switchLevel == 0)
			report_error("break van petlje", stmt);
	}

	@Override
	public void visit(StmtContinue stmt) {
		if (loopLevel == 0)
			report_error("continue van petlje", stmt);
	}

	@Override
	public void visit(CondFact condFact) {
		if (condFact.getArithmeticExpr() == null) {
			condFact.struct = Tab.noType;
			return;
		}

		Struct left = condFact.getArithmeticExpr().struct;
		CondFactTail tail = condFact.getCondFactTail();

		if (tail instanceof CondFactTailEpsilon) {
			// Ako je samo 'if (a)', struct je tipa promenljive a
			condFact.struct = left;
		} else if (tail instanceof CondFactTailArithmetic) {
			// ... tvoj postojeći kod za relacije ...
			// Ovde obavezno ostavi:
			condFact.struct = boolType;
		}
	}

	@Override
	public void visit(CondTermSingle term) {
		term.struct = term.getCondFact().struct;
	}

	@Override
	public void visit(CondTermAnd term) {
		if (term.getCondTerm() == null || term.getCondFact() == null)
			return;

		if (term.getCondTerm().struct == null || term.getCondFact().struct == null)
			return;

		Struct left = term.getCondTerm().struct;
		Struct right = term.getCondFact().struct;

		if (!left.equals(boolType) || !right.equals(boolType)) {
			report_error("AND zahteva bool operande", term);
			term.struct = Tab.noType;
			return;
		}

		term.struct = boolType;
	}

	@Override
	public void visit(ConditionSingle cond) {
		cond.struct = cond.getCondTerm().struct;
	}

	@Override
	public void visit(ConditionOr cond) {
		Struct left = cond.getCondition().struct;
		Struct right = cond.getCondTerm().struct;

		if (left != boolType || right != boolType) {
			report_error("OR zahteva bool operande", cond);
			cond.struct = Tab.noType;
			return;
		}

		cond.struct = boolType;
	}

	@Override
	public void visit(StmtIf stmt) {
		if (stmt.getCondition().struct != boolType) {
			report_error("Uslov u if mora biti bool", stmt);
		}
	}
	

}
