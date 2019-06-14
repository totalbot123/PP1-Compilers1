package rs.ac.bg.etf.pp1;

import static rs.ac.bg.etf.pp1.SemanticPass.*;
import static rs.etf.pp1.symboltable.Tab.*;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.AddExpr;
import rs.ac.bg.etf.pp1.ast.Argumentss;
import rs.ac.bg.etf.pp1.ast.AssignopExpr;
import rs.ac.bg.etf.pp1.ast.Asterisk;
import rs.ac.bg.etf.pp1.ast.DecOp;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.DesignatorName;
import rs.ac.bg.etf.pp1.ast.DesignatorStatement;
import rs.ac.bg.etf.pp1.ast.Expr;
import rs.ac.bg.etf.pp1.ast.FactCharConst;
import rs.ac.bg.etf.pp1.ast.FactDesignatorAccesor;
import rs.ac.bg.etf.pp1.ast.FactNewObject;
import rs.ac.bg.etf.pp1.ast.FactNumConst;
import rs.ac.bg.etf.pp1.ast.IncOp;
import rs.ac.bg.etf.pp1.ast.LValueDesignator;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodName;
import rs.ac.bg.etf.pp1.ast.MinusSign;
import rs.ac.bg.etf.pp1.ast.MulopFactors;
import rs.ac.bg.etf.pp1.ast.PlusAddop;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.ReadStmt;
import rs.ac.bg.etf.pp1.ast.Slash;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	private Obj incDec = new Obj(Obj.Con, "KonstantaJedan", intType);
	
	public CodeGenerator() {
		incDec.setAdr(1);
		chrObj.setAdr(Code.pc);
		ordObj.setAdr(Code.pc);
		createOrdChrCode();
		lenObj.setAdr(Code.pc);
		createLenCode();
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
	
	Logger log = Logger.getLogger(getClass());
	private boolean printUsed = false;
	
	@Override
	public void visit(MethodName methodName) {
		Obj currentMethod = methodName.obj;
		if (methodName.getName().equals("main")) {
			Code.mainPc = Code.pc;
		}
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
		Code.store(lValue);
	}
	
	public void visit(ReadStmt readStmt) {
		Obj desigObj = readStmt.getDesignator().obj;
		if (desigObj.getType().compatibleWith(charType)) {
			if (printUsed) {
				Code.put(Code.bread);
				Code.put(Code.pop);
			}
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(desigObj);
		printUsed = true;
	}

	@Override
	public void visit(PrintStmt printStmt) {
		Struct exprStruct = printStmt.getExpr().struct;
		if (assignableTo(charType, exprStruct)) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else if (assignableTo(intType, exprStruct)) {
			Code.loadConst(4);
			Code.put(Code.print);
		} else {
			report_error("Printovanje se moze raditi samo sa char ili int tipom", printStmt);
		}
	}
	
	@Override
	public void visit(LValueDesignator lValueDesignator) {
		lValue = lValueDesignator.obj;
		boolean isArray = lValue.getKind() == Obj.Elem;
		if (!isArray) {
			Code.load(lValue);
		}
	}
	
	@Override
	public void visit(DecOp decOp) {
		Obj lValueDesig = ((DesignatorStatement) decOp.getParent()).getLValueDesignator().obj;
		if (isOperandElem(decOp)) {
			Code.put(Code.dup2);
			Code.load(lValueDesig);
		}
		Code.load(incDec);
		Code.put(Code.sub);
		Code.store(lValue);
	}
	
	@Override
	public void visit(IncOp incOp) {
		Obj lValueDesig = ((DesignatorStatement) incOp.getParent()).getLValueDesignator().obj;
		if (isOperandElem(incOp)) {
			Code.put(Code.dup2);
			Code.load(lValueDesig);
		}
		Code.load(incDec);
		Code.put(Code.add);
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
	public void visit(AddExpr addExpr) {
		boolean addition = addExpr.getAddop() instanceof PlusAddop;
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
	
	public void visit(Argumentss argumentss) {
		Obj methodObj = ((FactDesignatorAccesor) argumentss.getParent()).getDesignator().obj;
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
	public void visit(FactNewObject factNewObject) {
		Code.put(Code.newarray);
		Code.put(1);
	}

	@Override
	public void visit(DesignatorName designatorName) {
		if (((Designator) designatorName.getParent()).obj.getKind() == Obj.Elem) {
			Code.load(designatorName.obj);
		}
	}
}
