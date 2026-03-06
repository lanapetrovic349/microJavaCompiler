// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class ExprRegular extends Expr {

    private ArithmeticExpr ArithmeticExpr;

    public ExprRegular (ArithmeticExpr ArithmeticExpr) {
        this.ArithmeticExpr=ArithmeticExpr;
        if(ArithmeticExpr!=null) ArithmeticExpr.setParent(this);
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
        if(ArithmeticExpr!=null) ArithmeticExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArithmeticExpr!=null) ArithmeticExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArithmeticExpr!=null) ArithmeticExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprRegular(\n");

        if(ArithmeticExpr!=null)
            buffer.append(ArithmeticExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprRegular]");
        return buffer.toString();
    }
}
