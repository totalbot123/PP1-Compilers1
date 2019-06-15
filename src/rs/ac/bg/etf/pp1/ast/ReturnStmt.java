// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:16


package rs.ac.bg.etf.pp1.ast;

public class ReturnStmt extends Statement {

    private OptionalExpr OptionalExpr;

    public ReturnStmt (OptionalExpr OptionalExpr) {
        this.OptionalExpr=OptionalExpr;
        if(OptionalExpr!=null) OptionalExpr.setParent(this);
    }

    public OptionalExpr getOptionalExpr() {
        return OptionalExpr;
    }

    public void setOptionalExpr(OptionalExpr OptionalExpr) {
        this.OptionalExpr=OptionalExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptionalExpr!=null) OptionalExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptionalExpr!=null) OptionalExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptionalExpr!=null) OptionalExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ReturnStmt(\n");

        if(OptionalExpr!=null)
            buffer.append(OptionalExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ReturnStmt]");
        return buffer.toString();
    }
}
