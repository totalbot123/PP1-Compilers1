// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:15


package rs.ac.bg.etf.pp1.ast;

public class SingleEnumValue implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String name;
    private AssignEnumValue AssignEnumValue;

    public SingleEnumValue (String name, AssignEnumValue AssignEnumValue) {
        this.name=name;
        this.AssignEnumValue=AssignEnumValue;
        if(AssignEnumValue!=null) AssignEnumValue.setParent(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public AssignEnumValue getAssignEnumValue() {
        return AssignEnumValue;
    }

    public void setAssignEnumValue(AssignEnumValue AssignEnumValue) {
        this.AssignEnumValue=AssignEnumValue;
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
        if(AssignEnumValue!=null) AssignEnumValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AssignEnumValue!=null) AssignEnumValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AssignEnumValue!=null) AssignEnumValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleEnumValue(\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        if(AssignEnumValue!=null)
            buffer.append(AssignEnumValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleEnumValue]");
        return buffer.toString();
    }
}
