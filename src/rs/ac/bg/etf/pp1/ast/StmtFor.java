// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class StmtFor extends Statement {

    private ForInitOpt ForInitOpt;
    private ForCondMarker ForCondMarker;
    private ForCondOpt ForCondOpt;
    private ForStepMarker ForStepMarker;
    private ForStepOpt ForStepOpt;
    private ForStepEndMarker ForStepEndMarker;
    private ForBodyMarker ForBodyMarker;
    private Statement Statement;

    public StmtFor (ForInitOpt ForInitOpt, ForCondMarker ForCondMarker, ForCondOpt ForCondOpt, ForStepMarker ForStepMarker, ForStepOpt ForStepOpt, ForStepEndMarker ForStepEndMarker, ForBodyMarker ForBodyMarker, Statement Statement) {
        this.ForInitOpt=ForInitOpt;
        if(ForInitOpt!=null) ForInitOpt.setParent(this);
        this.ForCondMarker=ForCondMarker;
        if(ForCondMarker!=null) ForCondMarker.setParent(this);
        this.ForCondOpt=ForCondOpt;
        if(ForCondOpt!=null) ForCondOpt.setParent(this);
        this.ForStepMarker=ForStepMarker;
        if(ForStepMarker!=null) ForStepMarker.setParent(this);
        this.ForStepOpt=ForStepOpt;
        if(ForStepOpt!=null) ForStepOpt.setParent(this);
        this.ForStepEndMarker=ForStepEndMarker;
        if(ForStepEndMarker!=null) ForStepEndMarker.setParent(this);
        this.ForBodyMarker=ForBodyMarker;
        if(ForBodyMarker!=null) ForBodyMarker.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForInitOpt getForInitOpt() {
        return ForInitOpt;
    }

    public void setForInitOpt(ForInitOpt ForInitOpt) {
        this.ForInitOpt=ForInitOpt;
    }

    public ForCondMarker getForCondMarker() {
        return ForCondMarker;
    }

    public void setForCondMarker(ForCondMarker ForCondMarker) {
        this.ForCondMarker=ForCondMarker;
    }

    public ForCondOpt getForCondOpt() {
        return ForCondOpt;
    }

    public void setForCondOpt(ForCondOpt ForCondOpt) {
        this.ForCondOpt=ForCondOpt;
    }

    public ForStepMarker getForStepMarker() {
        return ForStepMarker;
    }

    public void setForStepMarker(ForStepMarker ForStepMarker) {
        this.ForStepMarker=ForStepMarker;
    }

    public ForStepOpt getForStepOpt() {
        return ForStepOpt;
    }

    public void setForStepOpt(ForStepOpt ForStepOpt) {
        this.ForStepOpt=ForStepOpt;
    }

    public ForStepEndMarker getForStepEndMarker() {
        return ForStepEndMarker;
    }

    public void setForStepEndMarker(ForStepEndMarker ForStepEndMarker) {
        this.ForStepEndMarker=ForStepEndMarker;
    }

    public ForBodyMarker getForBodyMarker() {
        return ForBodyMarker;
    }

    public void setForBodyMarker(ForBodyMarker ForBodyMarker) {
        this.ForBodyMarker=ForBodyMarker;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForInitOpt!=null) ForInitOpt.accept(visitor);
        if(ForCondMarker!=null) ForCondMarker.accept(visitor);
        if(ForCondOpt!=null) ForCondOpt.accept(visitor);
        if(ForStepMarker!=null) ForStepMarker.accept(visitor);
        if(ForStepOpt!=null) ForStepOpt.accept(visitor);
        if(ForStepEndMarker!=null) ForStepEndMarker.accept(visitor);
        if(ForBodyMarker!=null) ForBodyMarker.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForInitOpt!=null) ForInitOpt.traverseTopDown(visitor);
        if(ForCondMarker!=null) ForCondMarker.traverseTopDown(visitor);
        if(ForCondOpt!=null) ForCondOpt.traverseTopDown(visitor);
        if(ForStepMarker!=null) ForStepMarker.traverseTopDown(visitor);
        if(ForStepOpt!=null) ForStepOpt.traverseTopDown(visitor);
        if(ForStepEndMarker!=null) ForStepEndMarker.traverseTopDown(visitor);
        if(ForBodyMarker!=null) ForBodyMarker.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForInitOpt!=null) ForInitOpt.traverseBottomUp(visitor);
        if(ForCondMarker!=null) ForCondMarker.traverseBottomUp(visitor);
        if(ForCondOpt!=null) ForCondOpt.traverseBottomUp(visitor);
        if(ForStepMarker!=null) ForStepMarker.traverseBottomUp(visitor);
        if(ForStepOpt!=null) ForStepOpt.traverseBottomUp(visitor);
        if(ForStepEndMarker!=null) ForStepEndMarker.traverseBottomUp(visitor);
        if(ForBodyMarker!=null) ForBodyMarker.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtFor(\n");

        if(ForInitOpt!=null)
            buffer.append(ForInitOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCondMarker!=null)
            buffer.append(ForCondMarker.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCondOpt!=null)
            buffer.append(ForCondOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForStepMarker!=null)
            buffer.append(ForStepMarker.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForStepOpt!=null)
            buffer.append(ForStepOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForStepEndMarker!=null)
            buffer.append(ForStepEndMarker.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForBodyMarker!=null)
            buffer.append(ForBodyMarker.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtFor]");
        return buffer.toString();
    }
}
