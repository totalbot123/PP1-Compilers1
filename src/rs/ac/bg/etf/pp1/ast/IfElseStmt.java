// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:16


package rs.ac.bg.etf.pp1.ast;

public class IfElseStmt extends Statement {

    private IfElseCondition IfElseCondition;

    public IfElseStmt (IfElseCondition IfElseCondition) {
        this.IfElseCondition=IfElseCondition;
        if(IfElseCondition!=null) IfElseCondition.setParent(this);
    }

    public IfElseCondition getIfElseCondition() {
        return IfElseCondition;
    }

    public void setIfElseCondition(IfElseCondition IfElseCondition) {
        this.IfElseCondition=IfElseCondition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfElseCondition!=null) IfElseCondition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfElseCondition!=null) IfElseCondition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfElseCondition!=null) IfElseCondition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfElseStmt(\n");

        if(IfElseCondition!=null)
            buffer.append(IfElseCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfElseStmt]");
        return buffer.toString();
    }
}
