// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:15


package rs.ac.bg.etf.pp1.ast;

public class SingleEnum extends EnumList {

    private SingleEnumValue SingleEnumValue;

    public SingleEnum (SingleEnumValue SingleEnumValue) {
        this.SingleEnumValue=SingleEnumValue;
        if(SingleEnumValue!=null) SingleEnumValue.setParent(this);
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
        if(SingleEnumValue!=null) SingleEnumValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleEnumValue!=null) SingleEnumValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleEnumValue!=null) SingleEnumValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleEnum(\n");

        if(SingleEnumValue!=null)
            buffer.append(SingleEnumValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleEnum]");
        return buffer.toString();
    }
}
