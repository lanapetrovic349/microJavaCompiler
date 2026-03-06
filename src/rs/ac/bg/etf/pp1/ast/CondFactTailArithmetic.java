// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class CondFactTailArithmetic extends CondFactTail {

    private Relop Relop;
    private ArithmeticExpr ArithmeticExpr;

    public CondFactTailArithmetic (Relop Relop, ArithmeticExpr ArithmeticExpr) {
        this.Relop=Relop;
        if(Relop!=null) Relop.setParent(this);
        this.ArithmeticExpr=ArithmeticExpr;
        if(ArithmeticExpr!=null) ArithmeticExpr.setParent(this);
    }

    public Relop getRelop() {
        return Relop;
    }

    public void setRelop(Relop Relop) {
        this.Relop=Relop;
    }

    public ArithmeticExpr getArithmeticExpr() {
        return ArithmeticExpr;
    }

    public void setArithmeticExpr(ArithmeticExpr ArithmeticExpr) {
        this.ArithmeticExpr=ArithmeticExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Relop!=null) Relop.accept(visitor);
        if(ArithmeticExpr!=null) ArithmeticExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Relop!=null) Relop.traverseTopDown(visitor);
        if(ArithmeticExpr!=null) ArithmeticExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Relop!=null) Relop.traverseBottomUp(visitor);
        if(ArithmeticExpr!=null) ArithmeticExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFactTailArithmetic(\n");

        if(Relop!=null)
            buffer.append(Relop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ArithmeticExpr!=null)
            buffer.append(ArithmeticExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFactTailArithmetic]");
        return buffer.toString();
    }
}
