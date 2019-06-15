// generated with ast extension for cup
// version 0.8
// 15/5/2019 11:53:16


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(FormPars FormPars);
    public void visit(Factor Factor);
    public void visit(Statement Statement);
    public void visit(ElseBranch ElseBranch);
    public void visit(ConstList ConstList);
    public void visit(DesignatorAccess DesignatorAccess);
    public void visit(Minus Minus);
    public void visit(Relop Relop);
    public void visit(FormalParamList FormalParamList);
    public void visit(DeclList DeclList);
    public void visit(CondFactList CondFactList);
    public void visit(ForLoop ForLoop);
    public void visit(OptionalDesignatorStatement OptionalDesignatorStatement);
    public void visit(ConstType ConstType);
    public void visit(BoolConst BoolConst);
    public void visit(AddopTerm AddopTerm);
    public void visit(ArraySpecifier ArraySpecifier);
    public void visit(ActualParamList ActualParamList);
    public void visit(TypeList TypeList);
    public void visit(DesignatorAccessList DesignatorAccessList);
    public void visit(Condition Condition);
    public void visit(Mulop Mulop);
    public void visit(NumConstList NumConstList);
    public void visit(Addop Addop);
    public void visit(StatementList StatementList);
    public void visit(AssignEnumValue AssignEnumValue);
    public void visit(Assignop Assignop);
    public void visit(EnumList EnumList);
    public void visit(ClassMethodList ClassMethodList);
    public void visit(MulopFactor MulopFactor);
    public void visit(CondTermList CondTermList);
    public void visit(RelopExpr RelopExpr);
    public void visit(CondTerm CondTerm);
    public void visit(VarList VarList);
    public void visit(ClassDecl ClassDecl);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(MethodType MethodType);
    public void visit(Arguments Arguments);
    public void visit(SingleDecl SingleDecl);
    public void visit(Extends Extends);
    public void visit(OptionalCondition OptionalCondition);
    public void visit(ActPars ActPars);
    public void visit(InterfaceMethodDecl InterfaceMethodDecl);
    public void visit(VarDeclList VarDeclList);
    public void visit(CondFact CondFact);
    public void visit(InterfaceMethodDeclList InterfaceMethodDeclList);
    public void visit(IfElseCondition IfElseCondition);
    public void visit(OptionalExpr OptionalExpr);
    public void visit(Operation Operation);
    public void visit(Percent Percent);
    public void visit(Slash Slash);
    public void visit(Asterisk Asterisk);
    public void visit(MinusAddop MinusAddop);
    public void visit(PlusAddop PlusAddop);
    public void visit(LessEqual LessEqual);
    public void visit(Less Less);
    public void visit(GreaterEqual GreaterEqual);
    public void visit(Greater Greater);
    public void visit(NotEqual NotEqual);
    public void visit(Equal Equal);
    public void visit(AssignopDerived1 AssignopDerived1);
    public void visit(ActualParam ActualParam);
    public void visit(ActualParams ActualParams);
    public void visit(NoActuals NoActuals);
    public void visit(Actuals Actuals);
    public void visit(ExprDecl ExprDecl);
    public void visit(NoArraySpecifiers NoArraySpecifiers);
    public void visit(ArraySpecifiers ArraySpecifiers);
    public void visit(NewObject NewObject);
    public void visit(NoArgumentss NoArgumentss);
    public void visit(Argumentss Argumentss);
    public void visit(DesignatorElementAccess DesignatorElementAccess);
    public void visit(DesignatorFieldAccess DesignatorFieldAccess);
    public void visit(NoDesignatorsList NoDesignatorsList);
    public void visit(DesignatorsAccessList DesignatorsAccessList);
    public void visit(DesignatorName DesignatorName);
    public void visit(Designator Designator);
    public void visit(FactExprDecl FactExprDecl);
    public void visit(FactNewObject FactNewObject);
    public void visit(FactBoolConst FactBoolConst);
    public void visit(FactCharConst FactCharConst);
    public void visit(FactNumConst FactNumConst);
    public void visit(FactDesignatorAccesor FactDesignatorAccesor);
    public void visit(NoMulopFactors NoMulopFactors);
    public void visit(MulopFactors MulopFactors);
    public void visit(FirstFactor FirstFactor);
    public void visit(Term Term);
    public void visit(NoAddTerm NoAddTerm);
    public void visit(AddTerm AddTerm);
    public void visit(NoMinusSign NoMinusSign);
    public void visit(MinusSign MinusSign);
    public void visit(FirstTerm FirstTerm);
    public void visit(Expr Expr);
    public void visit(NoExpression NoExpression);
    public void visit(Expression Expression);
    public void visit(NoNumConstsList NoNumConstsList);
    public void visit(NumConstsList NumConstsList);
    public void visit(ForLoopDerived1 ForLoopDerived1);
    public void visit(NoGoElseBranch NoGoElseBranch);
    public void visit(GoElseBranch GoElseBranch);
    public void visit(NoRelopExprs NoRelopExprs);
    public void visit(RelopExprs RelopExprs);
    public void visit(CondFactDerived1 CondFactDerived1);
    public void visit(NoCondFactsList NoCondFactsList);
    public void visit(CondFactsList CondFactsList);
    public void visit(CondTermDerived1 CondTermDerived1);
    public void visit(NoCondTermsList NoCondTermsList);
    public void visit(CondTermsList CondTermsList);
    public void visit(NoOptCond NoOptCond);
    public void visit(OptCond OptCond);
    public void visit(ConditionDerived1 ConditionDerived1);
    public void visit(IfBranch IfBranch);
    public void visit(NoOptDesignatorStatement NoOptDesignatorStatement);
    public void visit(OptDesignatorStatement OptDesignatorStatement);
    public void visit(DecOp DecOp);
    public void visit(IncOp IncOp);
    public void visit(FunctionExpr FunctionExpr);
    public void visit(AssignopExpr AssignopExpr);
    public void visit(LValueDesignator LValueDesignator);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(Stmt Stmt);
    public void visit(PrintStmt PrintStmt);
    public void visit(ReadStmt ReadStmt);
    public void visit(ReturnStmt ReturnStmt);
    public void visit(ContinueStmt ContinueStmt);
    public void visit(BreakStmt BreakStmt);
    public void visit(ForStmt ForStmt);
    public void visit(IfElseStmt IfElseStmt);
    public void visit(DesignatorStmt DesignatorStmt);
    public void visit(NoStmt NoStmt);
    public void visit(Statements Statements);
    public void visit(FormalParamDecl FormalParamDecl);
    public void visit(SingleFormalParamDecl SingleFormalParamDecl);
    public void visit(FormalParamDecls FormalParamDecls);
    public void visit(NoFormParam NoFormParam);
    public void visit(FormParams FormParams);
    public void visit(VoidType VoidType);
    public void visit(MethodsType MethodsType);
    public void visit(MethodName MethodName);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoMethodDecl NoMethodDecl);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(Type Type);
    public void visit(IntefaceMethod IntefaceMethod);
    public void visit(SingleInterfaceMethod SingleInterfaceMethod);
    public void visit(InterfaceMethodList InterfaceMethodList);
    public void visit(InterfaceDecl InterfaceDecl);
    public void visit(NoAssignEnumValues NoAssignEnumValues);
    public void visit(AssignEnumValues AssignEnumValues);
    public void visit(SingleEnumValue SingleEnumValue);
    public void visit(SingleEnum SingleEnum);
    public void visit(EnumerationList EnumerationList);
    public void visit(EnumName EnumName);
    public void visit(EnumDecl EnumDecl);
    public void visit(NoClassMethodDecl NoClassMethodDecl);
    public void visit(ClassMethodDecl ClassMethodDecl);
    public void visit(SingleType SingleType);
    public void visit(TypesList TypesList);
    public void visit(Implements Implements);
    public void visit(NoExtend NoExtend);
    public void visit(Extend Extend);
    public void visit(ClassDeclaration ClassDeclaration);
    public void visit(NoBrackets NoBrackets);
    public void visit(Brackets Brackets);
    public void visit(SingleVar SingleVar);
    public void visit(SingleVariable SingleVariable);
    public void visit(VariableList VariableList);
    public void visit(VarDecl VarDecl);
    public void visit(NoVarDecl NoVarDecl);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(FalseConst FalseConst);
    public void visit(TrueConst TrueConst);
    public void visit(BooleanType BooleanType);
    public void visit(NumberType NumberType);
    public void visit(CharacterType CharacterType);
    public void visit(SingleConst SingleConst);
    public void visit(SingleConstant SingleConstant);
    public void visit(ConstantList ConstantList);
    public void visit(ConstDecl ConstDecl);
    public void visit(SingleInterfaceDecl SingleInterfaceDecl);
    public void visit(SingleEnumDecl SingleEnumDecl);
    public void visit(SingleClassDecl SingleClassDecl);
    public void visit(SingleVarDecl SingleVarDecl);
    public void visit(SingleConstDecl SingleConstDecl);
    public void visit(NoDeclarationList NoDeclarationList);
    public void visit(DeclarationList DeclarationList);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
