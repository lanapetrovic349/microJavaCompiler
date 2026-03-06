// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class FactorDesOrFuncCall extends FactorNeg {

    private Designator Designator;
    private CallOpt CallOpt;

    public FactorDesOrFuncCall (Designator Designator, CallOpt CallOpt) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.CallOpt=CallOpt;
        if(CallOpt!=null) CallOpt.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public CallOpt getCallOpt() {
        return CallOpt;
    }

    public void setCallOpt(CallOpt CallOpt) {
        this.CallOpt=CallOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(CallOpt!=null) CallOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(CallOpt!=null) CallOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(CallOpt!=null) CallOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorDesOrFuncCall(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CallOpt!=null)
            buffer.append(CallOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorDesOrFuncCall]");
        return buffer.toString();
    }
}
