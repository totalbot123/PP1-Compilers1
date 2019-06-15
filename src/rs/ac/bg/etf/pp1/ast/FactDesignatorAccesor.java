// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:16


package rs.ac.bg.etf.pp1.ast;

public class FactDesignatorAccesor extends Factor {

    private Designator Designator;
    private Arguments Arguments;

    public FactDesignatorAccesor (Designator Designator, Arguments Arguments) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.Arguments=Arguments;
        if(Arguments!=null) Arguments.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public Arguments getArguments() {
        return Arguments;
    }

    public void setArguments(Arguments Arguments) {
        this.Arguments=Arguments;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(Arguments!=null) Arguments.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(Arguments!=null) Arguments.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(Arguments!=null) Arguments.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactDesignatorAccesor(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Arguments!=null)
            buffer.append(Arguments.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactDesignatorAccesor]");
        return buffer.toString();
    }
}
