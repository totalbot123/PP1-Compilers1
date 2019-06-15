package rs.ac.bg.etf.pp1;

import static rs.etf.pp1.symboltable.Tab.charType;
import static rs.etf.pp1.symboltable.Tab.intType;
import static rs.etf.pp1.symboltable.Tab.noObj;
import static rs.etf.pp1.symboltable.Tab.noType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.ActualParam;
import rs.ac.bg.etf.pp1.ast.ActualParams;
import rs.ac.bg.etf.pp1.ast.Actuals;
import rs.ac.bg.etf.pp1.ast.AddExpr;
import rs.ac.bg.etf.pp1.ast.AddTerm;
import rs.ac.bg.etf.pp1.ast.AddopTerm;
import rs.ac.bg.etf.pp1.ast.Argumentss;
import rs.ac.bg.etf.pp1.ast.ArraySpecifiers;
import rs.ac.bg.etf.pp1.ast.AssignEnumValues;
import rs.ac.bg.etf.pp1.ast.AssignopExpr;
import rs.ac.bg.etf.pp1.ast.BooleanType;
import rs.ac.bg.etf.pp1.ast.CharacterType;
import rs.ac.bg.etf.pp1.ast.DecOp;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.DesignatorElementAccess;
import rs.ac.bg.etf.pp1.ast.DesignatorFieldAccess;
import rs.ac.bg.etf.pp1.ast.DesignatorName;
import rs.ac.bg.etf.pp1.ast.DesignatorStatement;
import rs.ac.bg.etf.pp1.ast.EnumDecl;
import rs.ac.bg.etf.pp1.ast.EnumName;
import rs.ac.bg.etf.pp1.ast.Expr;
import rs.ac.bg.etf.pp1.ast.FactBoolConst;
import rs.ac.bg.etf.pp1.ast.FactCharConst;
import rs.ac.bg.etf.pp1.ast.FactExprDecl;
import rs.ac.bg.etf.pp1.ast.FactDesignatorAccesor;
import rs.ac.bg.etf.pp1.ast.FactNewObject;
import rs.ac.bg.etf.pp1.ast.FactNumConst;
import rs.ac.bg.etf.pp1.ast.FalseConst;
import rs.ac.bg.etf.pp1.ast.FirstFactor;
import rs.ac.bg.etf.pp1.ast.FirstTerm;
import rs.ac.bg.etf.pp1.ast.FormalParamDecl;
import rs.ac.bg.etf.pp1.ast.FunctionExpr;
import rs.ac.bg.etf.pp1.ast.IncOp;
import rs.ac.bg.etf.pp1.ast.LValueDesignator;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodName;
import rs.ac.bg.etf.pp1.ast.MulopFactors;
import rs.ac.bg.etf.pp1.ast.NoBrackets;
import rs.ac.bg.etf.pp1.ast.NoExpression;
import rs.ac.bg.etf.pp1.ast.NoMinusSign;
import rs.ac.bg.etf.pp1.ast.NumberType;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.ReadStmt;
import rs.ac.bg.etf.pp1.ast.ReturnStmt;
import rs.ac.bg.etf.pp1.ast.SingleConst;
import rs.ac.bg.etf.pp1.ast.SingleEnumValue;
import rs.ac.bg.etf.pp1.ast.SingleVar;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.Term;
import rs.ac.bg.etf.pp1.ast.TrueConst;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.VoidType;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticPass extends VisitorAdaptor {
	
	public static Struct boolType = new Struct(Struct.Bool);

	public static boolean errorDetected = false;
	int printCallCount = 0;
	protected static Obj currentMethod = noObj;
	protected static boolean returnFound = false;
	protected static int nVars;
	protected static int currentLevel = -1;
	
	protected static Struct currentType;
	protected static Obj currentDesignator = noObj;
	
	protected static HashSet<Integer> currentEnumSet = null;
	protected static int currentEnumInt = 0;
	
	protected static Obj accessedDesignator = noObj;
	protected static Obj lValue = noObj;
	
	protected static Stack<Struct> argumentsTypes = new Stack<Struct>();
	
	protected static Struct previousFactor = noType;
	protected static Struct previousTerm = noType;

	static Logger log = Logger.getLogger(SemanticPass.class);
	
	private String typeToString(Struct struct) {
		String result = "";
		
		switch (struct.getKind()) {
			case 1:
				result = "Int";
				break;
			case 2:
				result = "Char";
				break;
			case 5:
				result = "Bool";
				break;
			case 6:
				result = "Enum";
				break;
			default:
				break;
		}
		
		return result;
	}
	
	protected static void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder("Greska! " + message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji: ").append(line);
		log.error(msg.toString());
	}

	protected static void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji: ").append(line);
		log.info(msg.toString());
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	@Override
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode.equals(noObj)) {
			report_error("Nepostojeci tip " + type.getTypeName(), type);
			type.struct = noType;
		} else {
			if (typeNode.getKind() == Obj.Type) {
				type.struct = typeNode.getType();
				currentType = typeNode.getType();
			} else {
				report_error("" + typeNode.getName() + " ne predstavlja tip", type);
				type.struct = noType;
			}
		}
	}
	
	@Override
	public void visit(Program program) {		
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
		currentLevel--;
	}

	@Override
	public void visit(ProgName progName) {
		report_info("Pocetak programa: " + progName.getPName(), progName);
		progName.obj = Tab.insert(Obj.Prog, progName.getPName(), noType);
		Tab.openScope();
		currentLevel++;
	}
	
	@Override
	public void visit(SingleConst singleConst) {
		Obj varNode = Tab.find(singleConst.getName());
		if (varNode != noObj) {
			int varLevel = varNode.getLevel();
			if (varLevel == currentLevel) {
				report_error("Konstanta sa imenom " + singleConst.getName() + " vec postoji u trenutnom scope-u",
						singleConst);
			}
		} else {
			boolean isBasicType = isBasicType(currentType);
			if (!isBasicType) {
				report_error("Konstante moraju biti prostog tipa", singleConst);
			} else {
				Obj valueObj = singleConst.getConstType().obj;
				boolean kindCheck = assignableTo(currentType, valueObj.getType());
				if (kindCheck) {
					Tab.insert(Obj.Con, singleConst.getName(), currentType);
					Obj sameObj = Tab.find(singleConst.getName());
					sameObj.setAdr(valueObj.getAdr());
					report_info("Definasana konstanta " + singleConst.getName() + " tipa: " + typeToString(currentType)
							+ ", na liniji: " + singleConst.getLine(), null);
				} else {
					report_error("Dodeljena vrednost nije istog tipa kao i konstanta", singleConst);
				}
			}
		}
	}
	
	@Override
	public void visit(CharacterType characterType) {
		Obj charValue = new Obj(Obj.Con, "", charType);
		charValue.setAdr(characterType.getC1());
		characterType.obj = charValue;
	}
	
	@Override
	public void visit(NumberType numberType) {
		Obj intValue = new Obj(Obj.Con, "", intType);
		intValue.setAdr(numberType.getN1());
		numberType.obj = intValue;
	}
	
	@Override
	public void visit(BooleanType booleanType) {
		Obj boolValue = new Obj(Obj.Con, "", boolType);
		boolValue.setAdr(1);
		booleanType.obj = booleanType.getBoolConst().obj;
	}
	
	@Override
	public void visit(TrueConst trueConst) {
		Obj trueValue = new Obj(Obj.Con, "", boolType);
		trueValue.setAdr(1);
		trueConst.obj = trueValue;
	}
	
	@Override
	public void visit(FalseConst falseConst) {
		Obj falseValue = new Obj(Obj.Con, "", boolType);
		falseValue.setAdr(0);;
		falseConst.obj = falseValue;
	}
	
	
	@Override
	public void visit(SingleVar singleVar) {
		Obj varNode = Tab.find(singleVar.getName());
		boolean objectExistsInCurrentScope = varNode != noObj && varNode.getLevel() == currentLevel;
		if (objectExistsInCurrentScope) {
			int varLevel = varNode.getLevel();
			if (varLevel == currentLevel) {
				report_error("Varijabla sa imenom " + singleVar.getName() + " vec postoji u trenutnom scope-u ",
						singleVar);
			}
		} else {
			if (!(singleVar.getBrackets() instanceof NoBrackets)) {
				Tab.insert(Obj.Var, singleVar.getName(), new Struct(Struct.Array, currentType));
				report_info("Deklarisana niz " + singleVar.getName() + " tipa: " + typeToString(currentType),
						singleVar);
			} else {
				Tab.insert(Obj.Var, singleVar.getName(), currentType);
				report_info("Deklarisana promenljiva " + singleVar.getName() + " tipa: " + typeToString(currentType),
						singleVar);
			}
		}
	}
	
	@Override
	public void visit(EnumDecl enumDecl) {
		Obj enumObj = enumDecl.getEnumName().obj;
		Tab.chainLocalSymbols(enumObj);
		report_info("Kraj definicije enuma: " + enumDecl.getEnumName().getName(), enumDecl);
		Tab.closeScope();
		--currentLevel;
	}
	
	@Override
	public void visit(EnumName enumName) {
		Obj enumNode = Tab.find(enumName.getName());
		if (enumNode != noObj) {
			report_error("Enum sa imenom " + enumName.getName() + " vec postoji", enumName);
		} else {
			currentEnumSet = new HashSet<Integer>();
			enumName.obj = Tab.insert(Obj.Type, enumName.getName(), new Struct(Struct.Enum));
			report_info("Definisananje enuma " + enumName.getName(), enumName);
			Tab.openScope();
			++currentLevel;
		}
	}
	
	@Override
	public void visit(SingleEnumValue singleEnumValue) {
		Obj enumVal = new Obj(Obj.Con, singleEnumValue.getName(), intType);
		if (Tab.currentScope().addToLocals(enumVal)) {
			report_info("Dodato novo nabrajanje " + singleEnumValue.getName() + " kojem je dodeljena vrednost: "
					+ currentEnumInt + " u trenutni enum", singleEnumValue);
			enumVal.setAdr(currentEnumInt);
			currentEnumSet.add(currentEnumInt);
			currentEnumInt++;
		} else {
			report_error("Vrednost " + singleEnumValue.getName() + " vec postoji u trentnom enumu", singleEnumValue);
		}
	}
	
	@Override
	public void visit(AssignEnumValues assignEnumValue) {
		if (currentEnumSet.add(assignEnumValue.getN())) {
			currentEnumInt = assignEnumValue.getN();
		} else {
			report_error("Vrednost kojoj je dodeljen integer: " + assignEnumValue.getN() + " u trentnom enumu",
					assignEnumValue);
		}
	}

	private String returnEdgeCaseCheck(MethodDecl methodDecl) {
		boolean voidMethod = methodDecl.getMethodType() instanceof VoidType;
		Struct methodType = currentMethod.getType();
		Struct returnType = currentType;
		if (voidMethod && returnFound && !returnType.equals(noType)) {
			return "Return void metoda ne sme vracati izraz";
		} else if (!voidMethod && !returnFound) {
			return "Metod mora imati return izraz";
		} else if (!voidMethod && returnFound && returnType.equals(noType)) {
			return "Return ne-void metoda mora vracati povratni tip";
		} else if (!voidMethod && returnFound && !assignableTo(methodType, returnType)) {
			return "Tip metoda mora biti isti kao i tip povratnog izraza";
		}
		return "";
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		if (!returnEdgeCaseCheck(methodDecl).equals("")) {
			report_error(returnEdgeCaseCheck(methodDecl), methodDecl.getMethodName());
		}
		Tab.chainLocalSymbols(methodDecl.getMethodName().obj);
		Tab.closeScope();
		returnFound = false;
		currentLevel--;
	}
	
	@Override
	public void visit(VoidType voidType) {
		currentType = noType;
	}
	
	@Override
	public void visit(MethodName methodName) {
		report_info("Pocetak funkcije: " + methodName.getName(), methodName);
		methodName.obj = Tab.insert(Obj.Meth, methodName.getName(), currentType);
		currentMethod = methodName.obj;
		Tab.openScope();
		currentLevel++;
	}
	
	@Override
	public void visit(FormalParamDecl formalParamDecl) {
		Obj argNode = new Obj(Obj.Var, formalParamDecl.getId(), currentType);
		if (Tab.currentScope().addToLocals(argNode)) {
			currentMethod.setLevel(currentMethod.getLevel() + 1);
			argNode.setFpPos(currentMethod.getLevel());
			report_info("Novo parametar " + formalParamDecl.getId() + " trenutne funkcije", formalParamDecl);
		} else {
			report_error("Parametar " + formalParamDecl.getId() + " vec postoji u trentnom enumu", formalParamDecl);
		}
	}

	@Override
	public void visit(ReturnStmt returnStmt) {
		if (returnStmt.getOptionalExpr() instanceof NoExpression) {
			currentType = noType;
		}
		returnFound = true;
	}

	@Override
	public void visit(Expr expr) {
		if (expr.getAddopTerm() instanceof AddTerm) {
			Struct firstTermStruct = expr.getFirstTerm().struct;
			if (!firstTermStruct.compatibleWith(intType)) {
				report_error("Sabiranje se moze vrsiti samo sa integer-ima!", expr.getFirstTerm());
			}
		}
		expr.struct = expr.getFirstTerm().struct;
		currentType = expr.struct;
		report_info("Izracunavanje izraza", expr.getFirstTerm());
	}

	@Override
	public void visit(FirstTerm firstTerm) {
		Struct termNode = firstTerm.getTerm().struct;
		boolean minusUsed = !(((Expr) firstTerm.getParent()).getMinus() instanceof NoMinusSign);
		if (!termNode.compatibleWith(intType) && minusUsed) {
			report_error("Neodgovarajuci tip izraza uz znak minus!", firstTerm);
		}

		firstTerm.struct = termNode;
		previousTerm = firstTerm.struct;
	}

	@Override
	public void visit(AddTerm addTerm) {
		Struct termNode = addTerm.getTerm().struct;
//		if (termNode.getKind() != Struct.Int && termNode.getKind() != Struct.Enum) {
//			report_error("Sabiranje se moze vrsiti samo sa integer-ima!", addTerm.getTerm());
//		}
		if (!assignableTo(previousTerm, termNode)) {
			report_error("Operandi moraju biti istog tipa za operaciju sabiranja/oduzimanja", addTerm);
		}
		previousTerm = termNode;
	}
	
	@Override
	public void visit(Term term) {
		if (term.getMulopFactor() instanceof MulopFactors) {
			Struct firstFactorStruct = term.getFirstFactor().struct;
			if (!assignableTo(intType, firstFactorStruct)) {
				report_error("Mnozenje se moze vrsiti samo sa integer-ima", term.getFirstFactor());
			}
		} 
		term.struct = term.getFirstFactor().struct;
	}
	
	@Override
	public void visit(FirstFactor firstFactor) {
		firstFactor.struct = firstFactor.getFactor().struct;
		previousFactor = firstFactor.struct;
	}
	
	@Override
	public void visit(MulopFactors mulopFactor) {
		Struct mulopFactNode = mulopFactor.getFactor().struct;
//		if (!assignableTo(intType, mulopFactNode)) {
//			report_error("Mnozenje se moze vrsiti samo sa integer-ima", mulopFactor);
//		}
		if (!assignableTo(previousFactor, mulopFactNode)) {
			report_error("Operandi moraju biti istog tipa za operaciju mnozenja/deljenja/mod-a", mulopFactor);
		}
		previousFactor = mulopFactNode;
	}
	
	@Override
	public void visit(FactDesignatorAccesor factDesignatorAccesor) {		
		Obj obj = Tab.find(currentDesignator.getName());
		if (accessedDesignator != noObj) {
			if (enumContains(accessedDesignator, currentDesignator) || isMethod(currentDesignator)) {
				factDesignatorAccesor.struct = accessedDesignator.getType();
			} else {
				factDesignatorAccesor.struct = currentDesignator.getType();
			}
			accessedDesignator = noObj;
		} else if (obj == noObj) {
			report_error("Nepostojeci simbol", factDesignatorAccesor);
		} else {
			factDesignatorAccesor.struct = currentDesignator.getType();
		}
	}

	protected static boolean isMethod(Obj designator) {
		return designator.getKind() == Obj.Meth;
	}

	private boolean enumContains(Obj enumObj, Obj designator) {
		Optional<Obj> result = enumObj.getLocalSymbols().stream()
				.filter(value -> value.getName().equals(designator.getName()))
				.findFirst();
		if (result.isEmpty()) {
			return false;			
		}
		int foundObjValue = result.get().getAdr();
		designator.setAdr(foundObjValue);
		return true;
	}
	
	@Override
	public void visit(Argumentss argumentss) {
		Obj currentMethod = ((FactDesignatorAccesor)argumentss.getParent()).getDesignator().obj;
		currentDesignator = currentMethod;
		if (currentMethod.getKind() != Obj.Meth) {
			report_error(currentMethod.getName() + " ne predstavlja metod", argumentss);
		} else {
			if (!checkArgumentTypes(currentMethod).equals("")) {
				report_error(checkArgumentTypes(currentMethod), ((FactDesignatorAccesor)argumentss.getParent()).getDesignator());
			}
		}
	}
	
	@Override
	public void visit(FactNumConst factNumConst) {
		factNumConst.struct = intType;
	}
	
	@Override
	public void visit(FactCharConst factCharConst) {
		factCharConst.struct = charType;
	}
	
	@Override
	public void visit(FactBoolConst factBoolConst) {
		factBoolConst.struct = boolType;
	}
	
	@Override
	public void visit(FactNewObject factNewObject) {
		Struct elementStruct = factNewObject.getNewObject().getType().struct;
		factNewObject.struct = new Struct(Struct.Array, elementStruct);
	}
	
	@Override
	public void visit(ArraySpecifiers arraySpecifier) {
		//arraySpecifier.getExpr().struct != intType
		if (!assignableTo(intType, arraySpecifier.getExpr().struct)) {
			report_error("Izraz za specifiranje broja elemenata niza mora biti tipa Integer", arraySpecifier);
		}
	}
	
	@Override
	public void visit(FactExprDecl factExprDecl) {
		factExprDecl.struct = factExprDecl.getExprDecl().getExpr().struct;
	}
	
	@Override
	public void visit(LValueDesignator lValueDesignator) {
		lValueDesignator.obj = currentDesignator;
		lValue = lValueDesignator.obj;
	}
	
	@Override
	public void visit(Designator designator) {
		designator.obj = currentDesignator;
	}
	
	@Override
	public void visit(DesignatorName designatorName) {
		Obj objNode = Tab.find(designatorName.getName());
		if (objNode.equals(noObj)) {
			report_error("" + designatorName.getName() + " nije deklarisan", designatorName);
		} else {
			designatorName.obj = objNode;
			currentDesignator = objNode;
			
			if (designatorName.getParent().getParent() instanceof LValueDesignator) {
				lValue = objNode;
			}
		}
		
	}
	
	@Override
	public void visit(DesignatorFieldAccess designatorFieldAccess) {
		if ((currentDesignator.getKind() != Obj.Type && currentDesignator.getType().getKind() != Struct.Enum)) {
			report_error("" + currentDesignator.getName() + " nije enum", designatorFieldAccess);
		} else {
			Obj valueNode = new Obj(Obj.Con, designatorFieldAccess.getId(), intType);
			if (enumContains(currentDesignator, valueNode)) {
				accessedDesignator = currentDesignator;
				currentDesignator = valueNode;
			} else {
				report_error("Vrednost " + designatorFieldAccess.getId() + " ne pripada enumu: "
						+ currentDesignator.getName(), designatorFieldAccess);
			}
		}
	}
	
	@Override
	public void visit(DesignatorElementAccess designatorElementAccess) {
		accessedDesignator = ((Designator) designatorElementAccess.getParent().getParent()).getDesignatorName().obj;
		if (accessedDesignator.getKind() != Obj.Var || accessedDesignator.getType().getKind() != Struct.Array) {
			report_error("" + accessedDesignator.getName() + " nije niz", null);
		} else {
			int arrayIndexStructKind = designatorElementAccess.getExpr().struct.getKind();
			if (arrayIndexStructKind != Struct.Int && arrayIndexStructKind != Struct.Enum) {
				report_error("Indeksiranje niza moze vrsiti samo tipom integer", designatorElementAccess.getExpr());
			} else {
				currentDesignator = new Obj(Obj.Elem, "", accessedDesignator.getType().getElemType());
			}
		}
	}
	
	@Override
	public void visit(AssignopExpr assignopExpr) {	
		if (lValue.getKind() != Obj.Var && lValue.getKind() != Obj.Elem) {
			report_error("Nemoguce dodavalje konstanti", assignopExpr);
		}
		
		Struct lValueType = lValue.getType();
		Struct assignedExprType = assignopExpr.getExpr().struct;
		if (!assignableTo(lValueType, assignedExprType)) {
			report_error("Tipovi se ne poklapaju za operaciju dodele", assignopExpr);
		}
	}

	@Override
	public void visit(FunctionExpr functionExpr) {
		Obj currentMethod = ((DesignatorStatement) functionExpr.getParent()).getLValueDesignator().obj;
		if (currentMethod.getKind() != Obj.Meth) {
			report_error(currentMethod.getName() + " ne predstavlja metod", functionExpr);
		} else {
			if (!checkArgumentTypes(currentMethod).equals("")) {
				report_error(checkArgumentTypes(currentMethod), functionExpr);
			}
		}
	}
	
	private String checkArgumentTypes(Obj currentMethod) {
		if (currentMethod.getLevel() != argumentsTypes.size()) {
			return "Broj argumenata se mora poklapati sa brojem parametara u definiciji funkcije";
		}
		Iterator<Obj> methodLocalsIterator = currentMethod.getLocalSymbols().iterator();
		while (methodLocalsIterator.hasNext() && !argumentsTypes.empty()) {
			Struct parameterType = methodLocalsIterator.next().getType();
			Struct argumentType = argumentsTypes.pop();
			if (!assignableTo(parameterType, argumentType)) {
				return "Argumenti se ne poklapaju sa parametrima funkcije";
			}
		}
		return "";
	}

	@Override
	public void visit(Actuals actuals) {
		argumentsTypes = new Stack<Struct>();
		SyntaxNode current = actuals.getActualParamList();
		Struct exprType;
		while (current instanceof ActualParams) {
			exprType = ((ActualParams) current).getExpr().struct;
			argumentsTypes.push(exprType);
			current = ((ActualParams) current).getActualParamList();
		}
		exprType = ((ActualParam) current).getExpr().struct;
		argumentsTypes.push(exprType);
	}
	
	@Override
	public void visit(IncOp incOp) {
		boolean kindCheckFails = currentDesignator.getKind() != Obj.Var && currentDesignator.getKind() != Obj.Elem;
		boolean typeCheckFails = currentDesignator.getType() != intType;
		if (kindCheckFails || typeCheckFails) {
			report_error("Inkrementiranje je moguce samo za varijable tipa integer", incOp);
		} else {
			report_info("Inkrementiranje vrednosti", incOp);
		}
	}
	
	@Override
	public void visit(DecOp decOp) {
		boolean kindCheckFails = currentDesignator.getKind() != Obj.Var && currentDesignator.getKind() != Obj.Elem;
		boolean typeCheckFails = currentDesignator.getType() != intType;
		if (kindCheckFails || typeCheckFails) {
			report_error("Dekrementiranje je moguce samo za varijable tipa integer", decOp);
		}
	}
	
	@Override
	public void visit(ReadStmt readStmt) {
		int designatorKind = readStmt.getDesignator().obj.getKind();
		boolean kindCheck = designatorKind == Obj.Var || designatorKind == Obj.Elem;
		Struct designatorType = readStmt.getDesignator().obj.getType();
		boolean isBasicType = isBasicType(designatorType);
		if (kindCheck && isBasicType) {

		} else {
			report_error("Neodgovarajuci tip za operaciju read", readStmt.getDesignator());
		}
	}

	private boolean isBasicType(Struct designatorType) {
		return designatorType == intType || designatorType == charType || designatorType == boolType;
	}
	
	@Override
	public void visit(PrintStmt printStmt) {
		Struct exprType = printStmt.getExpr().struct;	
		boolean isBasicType = isBasicType(exprType);
		if (!isBasicType) {
			report_error("Neodgovarajuci tip izraza uz operaciju print", printStmt.getExpr());
		}
	}
	
	protected static boolean assignableTo(Struct destination, Struct source) {
		return source.assignableTo(destination) || (destination == intType && source.getKind() == Struct.Enum);
	}
}

