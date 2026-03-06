// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class EnumValueListSingle extends EnumValueList {

    private EnumValue EnumValue;

    public EnumValueListSingle (EnumValue EnumValue) {
        this.EnumValue=EnumValue;
        if(EnumValue!=null) EnumValue.setParent(this);
    }

    public EnumValue getEnumValue() {
        return EnumValue;
    }

    public void setEnumValue(EnumValue EnumValue) {
        this.EnumValue=EnumValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumValue!=null) EnumValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumValue!=null) EnumValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumValue!=null) EnumValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumValueListSingle(\n");

        if(EnumValue!=null)
            buffer.append(EnumValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumValueListSingle]");
        return buffer.toString();
    }
}
