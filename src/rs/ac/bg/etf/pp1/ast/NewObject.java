// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:16


package rs.ac.bg.etf.pp1.ast;

public class NewObject implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private ArraySpecifier ArraySpecifier;

    public NewObject (Type Type, ArraySpecifier ArraySpecifier) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ArraySpecifier=ArraySpecifier;
        if(ArraySpecifier!=null) ArraySpecifier.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ArraySpecifier getArraySpecifier() {
        return ArraySpecifier;
    }

    public void setArraySpecifier(ArraySpecifier ArraySpecifier) {
        this.ArraySpecifier=ArraySpecifier;
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
        if(Type!=null) Type.accept(visitor);
        if(ArraySpecifier!=null) ArraySpecifier.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ArraySpecifier!=null) ArraySpecifier.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ArraySpecifier!=null) ArraySpecifier.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NewObject(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ArraySpecifier!=null)
            buffer.append(ArraySpecifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NewObject]");
        return buffer.toString();
    }
}
