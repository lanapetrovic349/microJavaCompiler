// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class Factor implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private Unary Unary;
    private FactorNeg FactorNeg;

    public Factor (Unary Unary, FactorNeg FactorNeg) {
        this.Unary=Unary;
        if(Unary!=null) Unary.setParent(this);
        this.FactorNeg=FactorNeg;
        if(FactorNeg!=null) FactorNeg.setParent(this);
    }

    public Unary getUnary() {
        return Unary;
    }

    public void setUnary(Unary Unary) {
        this.Unary=Unary;
    }

    public FactorNeg getFactorNeg() {
        return FactorNeg;
    }

    public void setFactorNeg(FactorNeg FactorNeg) {
        this.FactorNeg=FactorNeg;
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
        if(Unary!=null) Unary.accept(visitor);
        if(FactorNeg!=null) FactorNeg.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Unary!=null) Unary.traverseTopDown(visitor);
        if(FactorNeg!=null) FactorNeg.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Unary!=null) Unary.traverseBottomUp(visitor);
        if(FactorNeg!=null) FactorNeg.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor(\n");

        if(Unary!=null)
            buffer.append(Unary.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FactorNeg!=null)
            buffer.append(FactorNeg.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor]");
        return buffer.toString();
    }
}
