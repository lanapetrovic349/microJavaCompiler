// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class CondFact implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private ArithmeticExpr ArithmeticExpr;
    private CondFactTail CondFactTail;

    public CondFact (ArithmeticExpr ArithmeticExpr, CondFactTail CondFactTail) {
        this.ArithmeticExpr=ArithmeticExpr;
        if(ArithmeticExpr!=null) ArithmeticExpr.setParent(this);
        this.CondFactTail=CondFactTail;
        if(CondFactTail!=null) CondFactTail.setParent(this);
    }

    public ArithmeticExpr getArithmeticExpr() {
        return ArithmeticExpr;
    }

    public void setArithmeticExpr(ArithmeticExpr ArithmeticExpr) {
        this.ArithmeticExpr=ArithmeticExpr;
    }

    public CondFactTail getCondFactTail() {
        return CondFactTail;
    }

    public void setCondFactTail(CondFactTail CondFactTail) {
        this.CondFactTail=CondFactTail;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArithmeticExpr!=null) ArithmeticExpr.accept(visitor);
        if(CondFactTail!=null) CondFactTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArithmeticExpr!=null) ArithmeticExpr.traverseTopDown(visitor);
        if(CondFactTail!=null) CondFactTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArithmeticExpr!=null) ArithmeticExpr.traverseBottomUp(visitor);
        if(CondFactTail!=null) CondFactTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFact(\n");

        if(ArithmeticExpr!=null)
            buffer.append(ArithmeticExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondFactTail!=null)
            buffer.append(CondFactTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFact]");
        return buffer.toString();
    }
}
