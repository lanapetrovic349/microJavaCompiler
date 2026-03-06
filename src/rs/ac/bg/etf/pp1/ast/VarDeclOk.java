// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class VarDeclOk extends VarDecl {

    private Type Type;
    private VarDeclItem VarDeclItem;
    private VarDeclList VarDeclList;

    public VarDeclOk (Type Type, VarDeclItem VarDeclItem, VarDeclList VarDeclList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarDeclItem=VarDeclItem;
        if(VarDeclItem!=null) VarDeclItem.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarDeclItem getVarDeclItem() {
        return VarDeclItem;
    }

    public void setVarDeclItem(VarDeclItem VarDeclItem) {
        this.VarDeclItem=VarDeclItem;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarDeclItem!=null) VarDeclItem.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarDeclItem!=null) VarDeclItem.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarDeclItem!=null) VarDeclItem.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclOk(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclItem!=null)
            buffer.append(VarDeclItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclOk]");
        return buffer.toString();
    }
}
