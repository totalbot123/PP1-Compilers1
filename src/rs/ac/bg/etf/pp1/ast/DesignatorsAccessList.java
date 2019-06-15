// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:16


package rs.ac.bg.etf.pp1.ast;

public class DesignatorsAccessList extends DesignatorAccessList {

    private DesignatorAccessList DesignatorAccessList;
    private DesignatorAccess DesignatorAccess;

    public DesignatorsAccessList (DesignatorAccessList DesignatorAccessList, DesignatorAccess DesignatorAccess) {
        this.DesignatorAccessList=DesignatorAccessList;
        if(DesignatorAccessList!=null) DesignatorAccessList.setParent(this);
        this.DesignatorAccess=DesignatorAccess;
        if(DesignatorAccess!=null) DesignatorAccess.setParent(this);
    }

    public DesignatorAccessList getDesignatorAccessList() {
        return DesignatorAccessList;
    }

    public void setDesignatorAccessList(DesignatorAccessList DesignatorAccessList) {
        this.DesignatorAccessList=DesignatorAccessList;
    }

    public DesignatorAccess getDesignatorAccess() {
        return DesignatorAccess;
    }

    public void setDesignatorAccess(DesignatorAccess DesignatorAccess) {
        this.DesignatorAccess=DesignatorAccess;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorAccessList!=null) DesignatorAccessList.accept(visitor);
        if(DesignatorAccess!=null) DesignatorAccess.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorAccessList!=null) DesignatorAccessList.traverseTopDown(visitor);
        if(DesignatorAccess!=null) DesignatorAccess.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorAccessList!=null) DesignatorAccessList.traverseBottomUp(visitor);
        if(DesignatorAccess!=null) DesignatorAccess.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorsAccessList(\n");

        if(DesignatorAccessList!=null)
            buffer.append(DesignatorAccessList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorAccess!=null)
            buffer.append(DesignatorAccess.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorsAccessList]");
        return buffer.toString();
    }
}
