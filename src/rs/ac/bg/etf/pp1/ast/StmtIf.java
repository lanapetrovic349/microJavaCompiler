// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class StmtIf extends Statement {

    private IfCondMarker IfCondMarker;
    private Condition Condition;
    private CondEndMarker CondEndMarker;
    private Statement Statement;
    private ElseStatement ElseStatement;

    public StmtIf (IfCondMarker IfCondMarker, Condition Condition, CondEndMarker CondEndMarker, Statement Statement, ElseStatement ElseStatement) {
        this.IfCondMarker=IfCondMarker;
        if(IfCondMarker!=null) IfCondMarker.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.CondEndMarker=CondEndMarker;
        if(CondEndMarker!=null) CondEndMarker.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ElseStatement=ElseStatement;
        if(ElseStatement!=null) ElseStatement.setParent(this);
    }

    public IfCondMarker getIfCondMarker() {
        return IfCondMarker;
    }

    public void setIfCondMarker(IfCondMarker IfCondMarker) {
        this.IfCondMarker=IfCondMarker;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public CondEndMarker getCondEndMarker() {
        return CondEndMarker;
    }

    public void setCondEndMarker(CondEndMarker CondEndMarker) {
        this.CondEndMarker=CondEndMarker;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public ElseStatement getElseStatement() {
        return ElseStatement;
    }

    public void setElseStatement(ElseStatement ElseStatement) {
        this.ElseStatement=ElseStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfCondMarker!=null) IfCondMarker.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(CondEndMarker!=null) CondEndMarker.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ElseStatement!=null) ElseStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfCondMarker!=null) IfCondMarker.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(CondEndMarker!=null) CondEndMarker.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ElseStatement!=null) ElseStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfCondMarker!=null) IfCondMarker.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(CondEndMarker!=null) CondEndMarker.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ElseStatement!=null) ElseStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtIf(\n");

        if(IfCondMarker!=null)
            buffer.append(IfCondMarker.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondEndMarker!=null)
            buffer.append(CondEndMarker.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElseStatement!=null)
            buffer.append(ElseStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtIf]");
        return buffer.toString();
    }
}
