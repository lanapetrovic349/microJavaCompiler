// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class ConstVarDeclListList extends ConstVarDeclList {

    private ConstVarDecl ConstVarDecl;
    private ConstVarDeclList ConstVarDeclList;

    public ConstVarDeclListList (ConstVarDecl ConstVarDecl, ConstVarDeclList ConstVarDeclList) {
        this.ConstVarDecl=ConstVarDecl;
        if(ConstVarDecl!=null) ConstVarDecl.setParent(this);
        this.ConstVarDeclList=ConstVarDeclList;
        if(ConstVarDeclList!=null) ConstVarDeclList.setParent(this);
    }

    public ConstVarDecl getConstVarDecl() {
        return ConstVarDecl;
    }

    public void setConstVarDecl(ConstVarDecl ConstVarDecl) {
        this.ConstVarDecl=ConstVarDecl;
    }

    public ConstVarDeclList getConstVarDeclList() {
        return ConstVarDeclList;
    }

    public void setConstVarDeclList(ConstVarDeclList ConstVarDeclList) {
        this.ConstVarDeclList=ConstVarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstVarDecl!=null) ConstVarDecl.accept(visitor);
        if(ConstVarDeclList!=null) ConstVarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstVarDecl!=null) ConstVarDecl.traverseTopDown(visitor);
        if(ConstVarDeclList!=null) ConstVarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstVarDecl!=null) ConstVarDecl.traverseBottomUp(visitor);
        if(ConstVarDeclList!=null) ConstVarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstVarDeclListList(\n");

        if(ConstVarDecl!=null)
            buffer.append(ConstVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstVarDeclList!=null)
            buffer.append(ConstVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstVarDeclListList]");
        return buffer.toString();
    }
}
