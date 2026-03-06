// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class CaseClause implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Integer N1;
    private CaseMarker CaseMarker;
    private CaseStart CaseStart;
    private StatementList StatementList;

    public CaseClause (Integer N1, CaseMarker CaseMarker, CaseStart CaseStart, StatementList StatementList) {
        this.N1=N1;
        this.CaseMarker=CaseMarker;
        if(CaseMarker!=null) CaseMarker.setParent(this);
        this.CaseStart=CaseStart;
        if(CaseStart!=null) CaseStart.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public Integer getN1() {
        return N1;
    }

    public void setN1(Integer N1) {
        this.N1=N1;
    }

    public CaseMarker getCaseMarker() {
        return CaseMarker;
    }

    public void setCaseMarker(CaseMarker CaseMarker) {
        this.CaseMarker=CaseMarker;
    }

    public CaseStart getCaseStart() {
        return CaseStart;
    }

    public void setCaseStart(CaseStart CaseStart) {
        this.CaseStart=CaseStart;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
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
        if(CaseMarker!=null) CaseMarker.accept(visitor);
        if(CaseStart!=null) CaseStart.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CaseMarker!=null) CaseMarker.traverseTopDown(visitor);
        if(CaseStart!=null) CaseStart.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CaseMarker!=null) CaseMarker.traverseBottomUp(visitor);
        if(CaseStart!=null) CaseStart.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CaseClause(\n");

        buffer.append(" "+tab+N1);
        buffer.append("\n");

        if(CaseMarker!=null)
            buffer.append(CaseMarker.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CaseStart!=null)
            buffer.append(CaseStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CaseClause]");
        return buffer.toString();
    }
}
