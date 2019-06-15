// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:16


package rs.ac.bg.etf.pp1.ast;

public class ForStmt extends Statement {

    private ForLoop ForLoop;

    public ForStmt (ForLoop ForLoop) {
        this.ForLoop=ForLoop;
        if(ForLoop!=null) ForLoop.setParent(this);
    }

    public ForLoop getForLoop() {
        return ForLoop;
    }

    public void setForLoop(ForLoop ForLoop) {
        this.ForLoop=ForLoop;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForLoop!=null) ForLoop.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForLoop!=null) ForLoop.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForLoop!=null) ForLoop.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForStmt(\n");

        if(ForLoop!=null)
            buffer.append(ForLoop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForStmt]");
        return buffer.toString();
    }
}
