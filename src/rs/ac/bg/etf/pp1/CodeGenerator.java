package rs.ac.bg.etf.pp1;

import static rs.ac.bg.etf.pp1.SemanticPass.*;
import static rs.etf.pp1.symboltable.Tab.*;

import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.AddTerm;
import rs.ac.bg.etf.pp1.ast.Argumentss;
import rs.ac.bg.etf.pp1.ast.AssignopExpr;
import rs.ac.bg.etf.pp1.ast.Asterisk;
import rs.ac.bg.etf.pp1.ast.BoolConst;
import rs.ac.bg.etf.pp1.ast.BreakStmt;
import rs.ac.bg.etf.pp1.ast.CondFact;
import rs.ac.bg.etf.pp1.ast.CondOR;
import rs.ac.bg.etf.pp1.ast.CondTerm;
import rs.ac.bg.etf.pp1.ast.Condition;
import rs.ac.bg.etf.pp1.ast.ContinueStmt;
import rs.ac.bg.etf.pp1.ast.DecOp;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.DesignatorName;
import rs.ac.bg.etf.pp1.ast.DesignatorStatement;
import rs.ac.bg.etf.pp1.ast.ElseCodeBlock;
import rs.ac.bg.etf.pp1.ast.ElseKeyWord;
import rs.ac.bg.etf.pp1.ast.Equal;
import rs.ac.bg.etf.pp1.ast.Expr;
import rs.ac.bg.etf.pp1.ast.FactBoolConst;
import rs.ac.bg.etf.pp1.ast.FactCharConst;
import rs.ac.bg.etf.pp1.ast.FactDesignatorAccesor;
import rs.ac.bg.etf.pp1.ast.FactNumConst;
import rs.ac.bg.etf.pp1.ast.ForCodeBlock;
import rs.ac.bg.etf.pp1.ast.ForConditionalStatement;
import rs.ac.bg.etf.pp1.ast.ForFirstStatement;
import rs.ac.bg.etf.pp1.ast.ForLoop;
import rs.ac.bg.etf.pp1.ast.ForSecondStatement;
import rs.ac.bg.etf.pp1.ast.FunctionExpr;
import rs.ac.bg.etf.pp1.ast.Greater;
import rs.ac.bg.etf.pp1.ast.GreaterEqual;
import rs.ac.bg.etf.pp1.ast.IfKeyWord;
import rs.ac.bg.etf.pp1.ast.IncOp;
import rs.ac.bg.etf.pp1.ast.InitExpr;
import rs.ac.bg.etf.pp1.ast.InitStarted;
import rs.ac.bg.etf.pp1.ast.LValueDesignator;
import rs.ac.bg.etf.pp1.ast.Less;
import rs.ac.bg.etf.pp1.ast.LessEqual;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodName;
import rs.ac.bg.etf.pp1.ast.MinusSign;
import rs.ac.bg.etf.pp1.ast.MulopFactors;
import rs.ac.bg.etf.pp1.ast.NewArrayEnd;
import rs.ac.bg.etf.pp1.ast.NoElseBranch;
import rs.ac.bg.etf.pp1.ast.NoRelopExprs;
import rs.ac.bg.etf.pp1.ast.NotEqual;
import rs.ac.bg.etf.pp1.ast.OptInit;
import rs.ac.bg.etf.pp1.ast.PlusAddop;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.ReadStmt;
import rs.ac.bg.etf.pp1.ast.Relop;
import rs.ac.bg.etf.pp1.ast.RelopExprs;
import rs.ac.bg.etf.pp1.ast.ReturnStmt;
import rs.ac.bg.etf.pp1.ast.Slash;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.TrueConst;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	private Obj incDec = new Obj(Obj.Con, "KonstantaJedan", intType);
	private Stack<Stack<Integer> > stackOfFixupAddrStacks = new Stack<Stack<Integer>>();
	private Stack<Integer> currentFixupAddrStack = null;
	private Stack<Integer> trueJumpFixupAddrStack = new Stack<Integer>();
	private Stack<Integer> breakFixupAddrStack = new Stack<Integer>();
	private int currentOp;
	private int currentIndex;
	
	public CodeGenerator() {
		chrObj.setAdr(Code.pc);
		ordObj.setAdr(Code.pc);
		createOrdChrCode();
		lenObj.setAdr(Code.pc);
		createLenCode();
		incDec.setAdr(1);
	}
	
	private void createOrdChrCode() {
		enterEmbeddedFunction();
		Code.put(Code.load_n + 0);
		returnFromFunction();
	}

	private void createLenCode() {
		enterEmbeddedFunction();
		Code.put(Code.load_n + 0);
		Code.put(Code.arraylength);
		returnFromFunction();
	}
	
	private void enterEmbeddedFunction() {
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
	}

	private void returnFromFunction() {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(MethodName methodName) {
		Obj currentMethod = methodName.obj;
		if (methodName.getName().equals("main")) {
			Code.mainPc = Code.pc;
		}
		currentMethod.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(currentMethod.getLevel());
		Code.put(currentMethod.getLocalSymbols().size());
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		returnFromFunction();
	}
	
	@Override
	public void visit(AssignopExpr assignopExpr) {
		if (!(lValue.getType().getKind() == Struct.Array)) {
			Code.store(lValue);
		}
	}
	
	@Override
	public void visit(IfKeyWord ifKeyWord) {
		createFixupAddrStack();
	}
	
	@Override
	public void visit(Condition Condition) {
		while (!trueJumpFixupAddrStack.empty()) {
			Code.fixup(trueJumpFixupAddrStack.pop());
		}
	}
	
	@Override
	public void visit(CondTerm condTerm) {
//		CondTermList condTermList = ((Condition) condTerm.getParent()).getCondTermList();
//		if (condTermList instanceof CondTermsList) {
//			System.out.println("Lol");
//		}
		Code.putJump(0);
		trueJumpFixupAddrStack.push(Code.pc - 2);
	}

	@Override
	public void visit(CondOR condOR) {
		fixupPreviousCode();
	}
	
	@Override
	public void visit(CondFact condFact) {
		if (condFact.getRelopExpr() instanceof NoRelopExprs) {
			currentOp = Code.ne;
			Code.put(Code.const_n + 0);
		}
		Code.putFalseJump(currentOp, 0);
		addFixupAddrToStack();
	}
	
	@Override
	public void visit(RelopExprs relopExprs) {
		Relop relop = relopExprs.getRelop();
		if (relop instanceof Equal) {
			currentOp = Code.eq;
		} else if (relop instanceof NotEqual) {
			currentOp = Code.ne;
		} else if (relop instanceof Greater) {
			currentOp = Code.gt;
		} else if (relop instanceof GreaterEqual) {
			currentOp = Code.ge;
		} else if (relop instanceof Less) {
			currentOp = Code.lt;
		} else if (relop instanceof LessEqual) {
			currentOp = Code.le;
		} 
	}
	
	@Override
	public void visit(ElseKeyWord goElseBranch) {
		Code.putJump(0);
		fixupCodeAndCloseScopeStack();
		openScopeStackAndAddFixupCodeAddr(); 
	}
	
	@Override
	public void visit(ElseCodeBlock elseCodeBlock) {
		fixupCodeAndCloseScopeStack();
	}
	
	@Override
	public void visit(NoElseBranch noElseBranch) {
		fixupCodeAndCloseScopeStack();
	}
	
	@Override
	public void visit(ForFirstStatement forFirstStatement) {
		forFirstStatement.integer = Code.pc;
		createFixupAddrStack();
		//forLoopTopAddr.push(Code.pc);
	}
	
	@Override
	public void visit(ForConditionalStatement forConditionalStatement) {
		Code.putJump(0);
		addFixupAddrToStack();
		forConditionalStatement.integer = Code.pc;
		//forLoopTopAddr.push(Code.pc);
	}
	
	@Override
	public void visit(ForSecondStatement forSecondStatement) {
		ForFirstStatement forFirstStatement = ((ForLoop) forSecondStatement.getParent()).getForFirstStatement();
		int fixupAddr = forFirstStatement.integer;
		Code.putJump(fixupAddr);
		Code.fixup(currentFixupAddrStack.pop());
	}
	
	@Override
	public void visit(ForCodeBlock forCodeBlock) {
		ForConditionalStatement forConditionalStatement = ((ForLoop)forCodeBlock.getParent()).getForConditionalStatement();
		int fixupAddr = forConditionalStatement.integer;
		Code.putJump(fixupAddr);
		fixupCodeAndCloseScopeStack();
		fixupBreakJumps();
	}

	@Override
	public void visit(BreakStmt breakStmt) {
		Code.putJump(0);
		breakFixupAddrStack.push(Code.pc - 2);
	}
	
	@Override
	public void visit(ContinueStmt continueStmt) {
		SyntaxNode forLoop = continueStmt.getParent();
		while (!(forLoop instanceof ForLoop)) {
			forLoop = forLoop.getParent();
		}
		int fixupAddr = ((ForLoop) forLoop).getForConditionalStatement().integer;
		Code.putJump(fixupAddr);
	}
	
	private void fixupBreakJumps() {
		while (!breakFixupAddrStack.empty()) {
			Code.fixup(breakFixupAddrStack.pop());
		}
	}
	
	private void openScopeStackAndAddFixupCodeAddr() {
		createFixupAddrStack();
		addFixupAddrToStack();
	}
	
	private void createFixupAddrStack() {
		currentFixupAddrStack = new Stack<Integer>();
		stackOfFixupAddrStacks.push(currentFixupAddrStack);
	}
	
	private void addFixupAddrToStack() {
		currentFixupAddrStack.push(Code.pc - 2);
	}

	private void fixupCodeAndCloseScopeStack() {
		fixupPreviousCode();
		removeCurrentFixupAddrStack();
	}
	
	private void fixupPreviousCode() {
		while (!currentFixupAddrStack.empty()) {
			Code.fixup(currentFixupAddrStack.pop());
		}
	}
	
	private void removeCurrentFixupAddrStack() {
		stackOfFixupAddrStacks.pop();
		currentFixupAddrStack = stackOfFixupAddrStacks.empty() ? null :stackOfFixupAddrStacks.peek();
	}
	
	@Override
	public void visit(ReturnStmt ReturnStmt) {
		returnFromFunction();
	}
	
	@Override
	public void visit(ReadStmt readStmt) {
		Obj desigObj = readStmt.getDesignator().obj;
		if (desigObj.getType().compatibleWith(charType)) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.put(Code.bread);
		Code.put(Code.pop);
		Code.store(desigObj);
	}

	@Override
	public void visit(PrintStmt printStmt) {
		Struct exprStruct = printStmt.getExpr().struct;
		if (assignableTo(charType, exprStruct)) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else {
			Code.loadConst(4);
			Code.put(Code.print);
		}
	}
	
	@Override
	public void visit(LValueDesignator lValueDesignator) {
		lValue = lValueDesignator.obj;
		if (lValue.getType().getKind() == Struct.Array) {
			currentIndex = 0;
		}
	}
	
	@Override
	public void visit(FunctionExpr FunctionExpr) {
		callMethod(lValue);
		Code.put(Code.pop);
	}
	
	@Override
	public void visit(IncOp incOp) {
		if (isOperandElem(incOp)) {
			Code.put(Code.dup2);
		}
		Code.load(lValue);
		Code.load(incDec);
		Code.put(Code.add);
		Code.store(lValue);
	}
	
	@Override
	public void visit(DecOp decOp) {
		if (isOperandElem(decOp)) {
			Code.put(Code.dup2);
		}
		Code.load(lValue);
		Code.load(incDec);
		Code.put(Code.sub);
		Code.store(lValue);
	}
	
	private boolean isOperandElem(SyntaxNode incDecOP) {
		Obj lValueDesig = ((DesignatorStatement) incDecOP.getParent()).getLValueDesignator().obj;
		boolean isOperandElem = lValueDesig.getKind() == Obj.Elem;
		return isOperandElem;
	}
	
	@Override
	public void visit(Expr expr) {
		boolean minusUsed = expr.getMinus() instanceof MinusSign;
		if (minusUsed) {
			Code.put(Code.neg);
		}
	}

	@Override
	public void visit(AddTerm addTerm) {
		boolean addition = addTerm.getAddop() instanceof PlusAddop;
		if (addition) {
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}
	
	@Override
	public void visit(MulopFactors mulopFactors) {
		SyntaxNode operation = mulopFactors.getMulop();
		if (operation instanceof Asterisk) {
			Code.put(Code.mul);
		} else if (operation instanceof Slash) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}

	@Override
	public void visit(FactDesignatorAccesor factDesignatorAccesor) {
		Obj desigObj = factDesignatorAccesor.getDesignator().obj;
		if (!isMethod(desigObj)) {
			Code.load(desigObj);
		}
	}
	
	@Override
	public void visit(Argumentss argumentss) {
		Obj methodObj = ((FactDesignatorAccesor) argumentss.getParent()).getDesignator().obj;
		callMethod(methodObj);
		if (methodObj.getType() == noType) {
			Code.put(Code.pop);
		}
	}

	private void callMethod(Obj methodObj) {
		int dest_adr = methodObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(dest_adr);
	}

	@Override
	public void visit(FactNumConst factNumConst) {
		Obj obj = new Obj(Obj.Con, "", intType);
		obj.setAdr(factNumConst.getN1());
		Code.load(obj);
	}
	
	@Override
	public void visit(FactCharConst factCharConst) {
		Obj obj = new Obj(Obj.Con, "", charType);
		obj.setAdr(factCharConst.getC1());
		Code.load(obj);
	}
	
	@Override
	public void visit(FactBoolConst factBoolConst) {
		Obj obj;
		BoolConst boolConst = factBoolConst.getBoolConst();
		if (boolConst instanceof TrueConst) {
			obj = new Obj(Obj.Con, "", boolType);
			obj.setAdr(1);
		} else {
			obj = new Obj(Obj.Con, "", boolType);
			obj.setAdr(0);
		}
		Code.load(obj);
	}

	@Override
	public void visit(NewArrayEnd newArrayEnd) {
		Code.put(Code.newarray);
		if (currentType.compatibleWith(charType)) {
			Code.put(0);
		} else {
			Code.put(1);
		}
		Code.store(lValue);
	}
	
	@Override
	public void visit(InitStarted initStarted) {
		loadArrayAndIndex();
	}
	
	@Override
	public void visit(InitExpr initExpr) {
		if (lValue.getType().getElemType() == charType) {
			Code.put(Code.bastore);
		} else {
			Code.put(Code.astore);
		}
		loadArrayAndIndex();
	}
	
	@Override
	public void visit(OptInit optInit) {
		popLastArrayAndIndex();
	}

	private void popLastArrayAndIndex() {
		Code.put(Code.pop);
		Code.put(Code.pop);
	}
	
	private void loadArrayAndIndex() {
		Code.load(lValue);
		Code.loadConst(currentIndex);
		currentIndex++;
	}

	@Override
	public void visit(DesignatorName designatorName) {
		if (((Designator) designatorName.getParent()).obj.getKind() == Obj.Elem) {
			Code.load(designatorName.obj);
		}
	}
	
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		currentType = typeNode.getType();
	}
}
