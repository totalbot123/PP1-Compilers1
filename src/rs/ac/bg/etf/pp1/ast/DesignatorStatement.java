// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:16


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatement implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private LValueDesignator LValueDesignator;
    private Operation Operation;

    public DesignatorStatement (LValueDesignator LValueDesignator, Operation Operation) {
        this.LValueDesignator=LValueDesignator;
        if(LValueDesignator!=null) LValueDesignator.setParent(this);
        this.Operation=Operation;
        if(Operation!=null) Operation.setParent(this);
    }

    public LValueDesignator getLValueDesignator() {
        return LValueDesignator;
    }

    public void setLValueDesignator(LValueDesignator LValueDesignator) {
        this.LValueDesignator=LValueDesignator;
    }

    public Operation getOperation() {
        return Operation;
    }

    public void setOperation(Operation Operation) {
        this.Operation=Operation;
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
        if(LValueDesignator!=null) LValueDesignator.accept(visitor);
        if(Operation!=null) Operation.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LValueDesignator!=null) LValueDesignator.traverseTopDown(visitor);
        if(Operation!=null) Operation.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LValueDesignator!=null) LValueDesignator.traverseBottomUp(visitor);
        if(Operation!=null) Operation.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatement(\n");

        if(LValueDesignator!=null)
            buffer.append(LValueDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Operation!=null)
            buffer.append(Operation.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatement]");
        return buffer.toString();
    }
}
