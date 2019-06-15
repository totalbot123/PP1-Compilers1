// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:15


package rs.ac.bg.etf.pp1.ast;

public class SingleInterfaceDecl extends SingleDecl {

    private InterfaceDecl InterfaceDecl;

    public SingleInterfaceDecl (InterfaceDecl InterfaceDecl) {
        this.InterfaceDecl=InterfaceDecl;
        if(InterfaceDecl!=null) InterfaceDecl.setParent(this);
    }

    public InterfaceDecl getInterfaceDecl() {
        return InterfaceDecl;
    }

    public void setInterfaceDecl(InterfaceDecl InterfaceDecl) {
        this.InterfaceDecl=InterfaceDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InterfaceDecl!=null) InterfaceDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InterfaceDecl!=null) InterfaceDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InterfaceDecl!=null) InterfaceDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleInterfaceDecl(\n");

        if(InterfaceDecl!=null)
            buffer.append(InterfaceDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleInterfaceDecl]");
        return buffer.toString();
    }
}
