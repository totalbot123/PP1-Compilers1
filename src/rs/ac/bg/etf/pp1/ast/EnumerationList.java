// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:15


package rs.ac.bg.etf.pp1.ast;

public class EnumerationList extends EnumList {

    private EnumList EnumList;
    private SingleEnumValue SingleEnumValue;

    public EnumerationList (EnumList EnumList, SingleEnumValue SingleEnumValue) {
        this.EnumList=EnumList;
        if(EnumList!=null) EnumList.setParent(this);
        this.SingleEnumValue=SingleEnumValue;
        if(SingleEnumValue!=null) SingleEnumValue.setParent(this);
    }

    public EnumList getEnumList() {
        return EnumList;
    }

    public void setEnumList(EnumList EnumList) {
        this.EnumList=EnumList;
    }

    public SingleEnumValue getSingleEnumValue() {
        return SingleEnumValue;
    }

    public void setSingleEnumValue(SingleEnumValue SingleEnumValue) {
        this.SingleEnumValue=SingleEnumValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumList!=null) EnumList.accept(visitor);
        if(SingleEnumValue!=null) SingleEnumValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumList!=null) EnumList.traverseTopDown(visitor);
        if(SingleEnumValue!=null) SingleEnumValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumList!=null) EnumList.traverseBottomUp(visitor);
        if(SingleEnumValue!=null) SingleEnumValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumerationList(\n");

        if(EnumList!=null)
            buffer.append(EnumList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SingleEnumValue!=null)
            buffer.append(SingleEnumValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumerationList]");
        return buffer.toString();
    }
}
