// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:16


package rs.ac.bg.etf.pp1.ast;

public class FactNewObject extends Factor {

    private NewObject NewObject;

    public FactNewObject (NewObject NewObject) {
        this.NewObject=NewObject;
        if(NewObject!=null) NewObject.setParent(this);
    }

    public NewObject getNewObject() {
        return NewObject;
    }

    public void setNewObject(NewObject NewObject) {
        this.NewObject=NewObject;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NewObject!=null) NewObject.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NewObject!=null) NewObject.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NewObject!=null) NewObject.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactNewObject(\n");

        if(NewObject!=null)
            buffer.append(NewObject.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactNewObject]");
        return buffer.toString();
    }
}
