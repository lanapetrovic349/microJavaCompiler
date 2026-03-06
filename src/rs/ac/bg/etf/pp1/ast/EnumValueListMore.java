// generated with ast extension for cup
// version 0.8
// 5/2/2026 12:15:32


package rs.ac.bg.etf.pp1.ast;

public class EnumValueListMore extends EnumValueList {

    private EnumValue EnumValue;
    private EnumValueList EnumValueList;

    public EnumValueListMore (EnumValue EnumValue, EnumValueList EnumValueList) {
        this.EnumValue=EnumValue;
        if(EnumValue!=null) EnumValue.setParent(this);
        this.EnumValueList=EnumValueList;
        if(EnumValueList!=null) EnumValueList.setParent(this);
    }

    public EnumValue getEnumValue() {
        return EnumValue;
    }

    public void setEnumValue(EnumValue EnumValue) {
        this.EnumValue=EnumValue;
    }

    public EnumValueList getEnumValueList() {
        return EnumValueList;
    }

    public void setEnumValueList(EnumValueList EnumValueList) {
        this.EnumValueList=EnumValueList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumValue!=null) EnumValue.accept(visitor);
        if(EnumValueList!=null) EnumValueList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumValue!=null) EnumValue.traverseTopDown(visitor);
        if(EnumValueList!=null) EnumValueList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumValue!=null) EnumValue.traverseBottomUp(visitor);
        if(EnumValueList!=null) EnumValueList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumValueListMore(\n");

        if(EnumValue!=null)
            buffer.append(EnumValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumValueList!=null)
            buffer.append(EnumValueList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumValueListMore]");
        return buffer.toString();
    }
}
