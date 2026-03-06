// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class StmtSwitch extends Statement {

    private Expr Expr;
    private SwitchExprEndMarker SwitchExprEndMarker;
    private CaseList CaseList;

    public StmtSwitch (Expr Expr, SwitchExprEndMarker SwitchExprEndMarker, CaseList CaseList) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.SwitchExprEndMarker=SwitchExprEndMarker;
        if(SwitchExprEndMarker!=null) SwitchExprEndMarker.setParent(this);
        this.CaseList=CaseList;
        if(CaseList!=null) CaseList.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public SwitchExprEndMarker getSwitchExprEndMarker() {
        return SwitchExprEndMarker;
    }

    public void setSwitchExprEndMarker(SwitchExprEndMarker SwitchExprEndMarker) {
        this.SwitchExprEndMarker=SwitchExprEndMarker;
    }

    public CaseList getCaseList() {
        return CaseList;
    }

    public void setCaseList(CaseList CaseList) {
        this.CaseList=CaseList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(SwitchExprEndMarker!=null) SwitchExprEndMarker.accept(visitor);
        if(CaseList!=null) CaseList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(SwitchExprEndMarker!=null) SwitchExprEndMarker.traverseTopDown(visitor);
        if(CaseList!=null) CaseList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(SwitchExprEndMarker!=null) SwitchExprEndMarker.traverseBottomUp(visitor);
        if(CaseList!=null) CaseList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtSwitch(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SwitchExprEndMarker!=null)
            buffer.append(SwitchExprEndMarker.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseList!=null)
            buffer.append(CaseList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtSwitch]");
        return buffer.toString();
    }
}
