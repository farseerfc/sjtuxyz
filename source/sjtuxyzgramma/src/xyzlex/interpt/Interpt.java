package xyzlex.interpt;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.*;

import xyzlex.analysis.DepthFirstAdapter;
import xyzlex.lexer.Lexer;
import xyzlex.lexer.LexerException;
import xyzlex.node.*;
import xyzlex.parser.Parser;
import xyzlex.parser.ParserException;
import xyzlex.utils.Consts;

public class Interpt extends DepthFirstAdapter {
	private List<RuntimeStack> symbol;
	private List<SemanticError> errors;
	private List<String> outputs;
	private HashMap<String, ClassDecl> classDecls;
	private Start start;

	public List<RuntimeStack> getSymbol() {
		return symbol;
	}

	public List<SemanticError> getErrors() {
		return errors;
	}

	public List<String> getOutputs() {
		return outputs;
	}

	private void init() {
		symbol = new ArrayList<RuntimeStack>();
		symbol.add(new RuntimeStack());
		errors = new ArrayList<SemanticError>();
		outputs = new ArrayList<String>();
		classDecls = new HashMap<String, ClassDecl>();
	}

	public Interpt() {
		init();
	}

	public Interpt(PushbackReader in) throws ParserException, LexerException,
			IOException {
		init();
		start=new Parser(new Lexer(in)).parse();
		start.apply(this);
	}

	public Interpt(String input) throws ParserException, LexerException,
			IOException {
		this(new PushbackReader(new StringReader(input), Consts
				.getConst(Consts.InputStreamBufferSize)));
	}

	public RuntimeStack currentScope() {
		return symbol.get(symbol.size() - 1);
	}

	@Override
	public void outAVarDecl(AVarDecl node) {
		String name = node.getId().getText().trim();
		PType typeDecl = node.getType();
		if (currentScope().getSymbol().containsKey(name)) {
			errors.add(new SemanticError("Variable already declared! ", node,
					node.getId()));
			return;
		} else {
			Value v = new Value();
			v.setType(typeDecl);
			v.setValue(PTypeDefaultValue.getInstance().defaultValue(typeDecl));
			currentScope().getSymbol().put(name, v);
		}
	}

	@Override
	public void outAVarLeftValue(AVarLeftValue node) {
		String name = node.getId().getText().trim();
		HashMap<String, Value> thisScope = currentScope().getThisValue() != null ? (HashMap<String, Value>) currentScope()
				.getThisValue().getValue()
				: null;
		if (currentScope().getSymbol().containsKey(name)) {
			setOut(node, currentScope().getSymbol().get(name));
		} else if (thisScope != null && thisScope.containsKey(name)) {
			setOut(node, thisScope.get(name));
		} else {
			errors.add(new SemanticError("Variable not found!", node, node
					.getId()));
		}
	}

	@Override
	public void outAArrSubLeftValue(AArrSubLeftValue node) {
		Value array = (Value) getOut(node.getArray());
		Value result = ((Value[]) array.getValue())[Convert2Int.getInstance()
				.convert((Value) getOut(node.getIndex()))];
		setOut(node, result);
	}

	@Override
	public void outAFieldLeftValue(AFieldLeftValue node) {
		Value obj = (Value) getOut(node.getObject());
		String fieldName = node.getField().getText().trim();
		HashMap<String, Value> fields = (HashMap<String, Value>) obj.getValue();
		if (fields.containsKey(fieldName)) {
			setOut(node, fields.get(fieldName));
		} else {
			errors.add(new SemanticError("object '" + obj.getValue()
					+ "' don't contain field '" + node.getField() + "'", node,
					node.getField()));
		}
	}

	@Override
	public void outAIntLtExp(AIntLtExp node) {
		Value v = new Value();
		v.setType(TypeNodes.aIntType);
		v.setValue(Integer.valueOf(node.getIntLt().getText().trim()));
		setOut(node, v);
	}

	@Override
	public void outARealLtExp(ARealLtExp node) {
		Value v = new Value();
		v.setType(TypeNodes.aRealType);
		v.setValue(Double.valueOf(node.getRealLt().getText().trim()));
		setOut(node, v);
	}

	@Override
	public void outATrueLtExp(ATrueLtExp node) {
		Value v = new Value();
		v.setType(TypeNodes.aBooleanType);
		v.setValue(true);
		setOut(node, v);
	}

	@Override
	public void outAFalseLtExp(AFalseLtExp node) {
		Value v = new Value();
		v.setType(TypeNodes.aBooleanType);
		v.setValue(false);
		setOut(node, v);
	}

	@Override
	public void outALeftValueExp(ALeftValueExp node) {
		setOut(node, getOut(node.getLeftValue()));
	}

	@Override
	public void outASubExpExp(ASubExpExp node) {
		setOut(node, getOut(node.getExp()));
	}

	@Override
	public void outANotOprExp(ANotOprExp node) {
		Value v = (Value) getOut(node.getFirst());
		Value my = new Value();
		my.setType(TypeNodes.aBooleanType);
		try {
			my.setValue(!(Convert2Boolean.getInstance().convert(v)));
			setOut(node, my);
		} catch (TypeCastException tce) {
			errors.add(new SemanticError(tce.getMessage(), node, node
					.getNotOpr()));
		}
	}

	@Override
	public void outAThisExp(AThisExp node) {
		if (symbol.get(symbol.size() - 1).getThisValue() == null) {
			errors.add(new SemanticError(
					"'this' keyword not supported in main method! ", node, node
							.getThis()));
		} else {
			setOut(node, currentScope().getThisValue());
		}
	}

	@Override
	public void outAMultiplyOprExp(AMultiplyOprExp node) {
		Value l = (Value) getOut(node.getFirst());
		Value r = (Value) getOut(node.getRest());
		Value result = new Value();
		try {
			if (WhatType.getInstance().is(l.getType(), TypeNodes.aRealType)) {
				if (WhatType.getInstance().is(r.getType(), TypeNodes.aRealType)) {
					result.setType(TypeNodes.aRealType);
					result.setValue(((Double) l.getValue())
							* ((Double) r.getValue()));
				} else if (WhatType.getInstance().is(r.getType(),
						TypeNodes.aIntType)) {
					result.setType(TypeNodes.aRealType);
					result.setValue(((Double) l.getValue())
							* ((double) ((Integer) r.getValue())));
				} else
					throw new TypeCastException(r.getType(),
							TypeNodes.aRealType);
			} else if (WhatType.getInstance().is(l.getType(),
					TypeNodes.aIntType)) {
				if (WhatType.getInstance().is(r.getType(), TypeNodes.aRealType)) {
					result.setType(TypeNodes.aRealType);
					result.setValue(((double) ((Integer) l.getValue()))
							* ((Double) r.getValue()));
				} else if (WhatType.getInstance().is(r.getType(),
						TypeNodes.aIntType)) {
					result.setType(TypeNodes.aIntType);
					result.setValue(((Integer) l.getValue())
							* (((Integer) r.getValue())));
				} else
					throw new TypeCastException(r.getType(), TypeNodes.aIntType);
			} else
				throw new TypeCastException(l.getType(), TypeNodes.aIntType);
			setOut(node, result);
		} catch (TypeCastException tc) {
			errors.add(new SemanticError(tc.getMessage(), tc.getFrom(), node
					.getMultiplyOpr()));
		}
	}

	@Override
	public void outADivideOprExp(ADivideOprExp node) {
		Value l = (Value) getOut(node.getFirst());
		Value r = (Value) getOut(node.getRest());
		Value result = new Value();
		try {
			if (WhatType.getInstance().is(l.getType(), TypeNodes.aRealType)) {
				if (WhatType.getInstance().is(r.getType(), TypeNodes.aRealType)) {
					result.setType(TypeNodes.aRealType);
					result.setValue(((Double) l.getValue())
							/ ((Double) r.getValue()));
				} else if (WhatType.getInstance().is(r.getType(),
						TypeNodes.aIntType)) {
					result.setType(TypeNodes.aRealType);
					result.setValue(((Double) l.getValue())
							/ ((double) ((Integer) r.getValue())));
				} else
					throw new TypeCastException(r.getType(),
							TypeNodes.aRealType);
			} else if (WhatType.getInstance().is(l.getType(),
					TypeNodes.aIntType)) {
				if (WhatType.getInstance().is(r.getType(), TypeNodes.aRealType)) {
					result.setType(TypeNodes.aRealType);
					result.setValue(((double) ((Integer) l.getValue()))
							/ ((Double) r.getValue()));
				} else if (WhatType.getInstance().is(r.getType(),
						TypeNodes.aIntType)) {
					result.setType(TypeNodes.aIntType);
					result.setValue(((Integer) l.getValue())
							/ (((Integer) r.getValue())));
				} else
					throw new TypeCastException(r.getType(), TypeNodes.aIntType);
			} else
				throw new TypeCastException(l.getType(), TypeNodes.aIntType);

			setOut(node, result);
		} catch (TypeCastException tc) {
			errors.add(new SemanticError(tc.getMessage(), tc.getFrom(), node
					.getDivideOpr()));
		}
	}

	@Override
	public void outAPlusOprExp(APlusOprExp node) {
		Value l = (Value) getOut(node.getFirst());
		Value r = (Value) getOut(node.getRest());
		Value result = new Value();
		try {
			if (WhatType.getInstance().is(l.getType(), TypeNodes.aRealType)) {
				if (WhatType.getInstance().is(r.getType(), TypeNodes.aRealType)) {
					result.setType(TypeNodes.aRealType);
					result.setValue(((Double) l.getValue())
							+ ((Double) r.getValue()));
				} else if (WhatType.getInstance().is(r.getType(),
						TypeNodes.aIntType)) {
					result.setType(TypeNodes.aRealType);
					result.setValue(((Double) l.getValue())
							+ ((double) ((Integer) r.getValue())));
				} else
					throw new TypeCastException(r.getType(),
							TypeNodes.aRealType);
			} else if (WhatType.getInstance().is(l.getType(),
					TypeNodes.aIntType)) {
				if (WhatType.getInstance().is(r.getType(), TypeNodes.aRealType)) {
					result.setType(TypeNodes.aRealType);
					result.setValue(((double) ((Integer) l.getValue()))
							+ ((Double) r.getValue()));
				} else if (WhatType.getInstance().is(r.getType(),
						TypeNodes.aIntType)) {
					result.setType(TypeNodes.aIntType);
					result.setValue(((Integer) l.getValue())
							+ (((Integer) r.getValue())));
				} else
					throw new TypeCastException(r.getType(), TypeNodes.aIntType);
			} else
				throw new TypeCastException(l.getType(), TypeNodes.aIntType);

			setOut(node, result);
		} catch (TypeCastException tc) {
			errors.add(new SemanticError(tc.getMessage(), tc.getFrom(), node
					.getPlusOpr()));
		}
	}

	@Override
	public void outAMinusOprExp(AMinusOprExp node) {
		Value l = (Value) getOut(node.getFirst());
		Value r = (Value) getOut(node.getRest());
		Value result = new Value();
		try {
			if (WhatType.getInstance().is(l.getType(), TypeNodes.aRealType)) {
				if (WhatType.getInstance().is(r.getType(), TypeNodes.aRealType)) {
					result.setType(TypeNodes.aRealType);
					result.setValue(((Double) l.getValue())
							- ((Double) r.getValue()));
				} else if (WhatType.getInstance().is(r.getType(),
						TypeNodes.aIntType)) {
					result.setType(TypeNodes.aRealType);
					result.setValue(((Double) l.getValue())
							- ((double) ((Integer) r.getValue())));
				} else
					throw new TypeCastException(r.getType(),
							TypeNodes.aRealType);
			} else if (WhatType.getInstance().is(l.getType(),
					TypeNodes.aIntType)) {
				if (WhatType.getInstance().is(r.getType(), TypeNodes.aRealType)) {
					result.setType(TypeNodes.aRealType);
					result.setValue(((double) ((Integer) l.getValue()))
							- ((Double) r.getValue()));
				} else if (WhatType.getInstance().is(r.getType(),
						TypeNodes.aIntType)) {
					result.setType(TypeNodes.aIntType);
					result.setValue(((Integer) l.getValue())
							- (((Integer) r.getValue())));
				} else
					throw new TypeCastException(r.getType(), TypeNodes.aIntType);
			} else
				throw new TypeCastException(l.getType(), TypeNodes.aIntType);

			setOut(node, result);
		} catch (TypeCastException tc) {
			errors.add(new SemanticError(tc.getMessage(), tc.getFrom(), node
					.getMinusOpr()));
		}
	}

	@Override
	public void outAGreaterOprExp(AGreaterOprExp node) {
		Value l = (Value) getOut(node.getFirst());
		Value r = (Value) getOut(node.getRest());
		Value result = new Value();
		result.setType(TypeNodes.aBooleanType);
		try {
			result
					.setValue(Convert2Real.getInstance().convert(l) > Convert2Real
							.getInstance().convert(r));
			setOut(node, result);
		} catch (TypeCastException tc) {
			errors.add(new SemanticError(tc.getMessage(), tc.getFrom(), node
					.getGreaterOpr()));
		}
	}

	@Override
	public void outALessOprExp(ALessOprExp node) {
		Value l = (Value) getOut(node.getFirst());
		Value r = (Value) getOut(node.getRest());
		Value result = new Value();
		result.setType(TypeNodes.aBooleanType);
		try {
			result
					.setValue(Convert2Real.getInstance().convert(l) < Convert2Real
							.getInstance().convert(r));
			setOut(node, result);
		} catch (TypeCastException tc) {
			errors.add(new SemanticError(tc.getMessage(), tc.getFrom(), node
					.getLessOpr()));
		}
	}

	@Override
	public void outANewIntArExp(ANewIntArExp node) {
		Value exp = (Value) getOut(node.getSize());
		Value result = new Value();
		result.setType(TypeNodes.aIntArrayType);
		try {
			Value[] array = new Value[Convert2Int.getInstance().convert(exp)];
			for (int i = 0; i < array.length; ++i) {
				array[i] = new Value();
				array[i].setType(TypeNodes.aIntType);
				array[i].setValue(0);
			}
			result.setValue(array);
			setOut(node, result);
		} catch (TypeCastException tc) {
			errors.add(new SemanticError(tc.getMessage(), tc.getFrom(), node
					.getNew()));
		}
	}

	@Override
	public void outANewRealArExp(ANewRealArExp node) {
		Value exp = (Value) getOut(node.getSize());
		Value result = new Value();
		result.setType(TypeNodes.aRealArrayType);
		try {
			Value[] array = new Value[Convert2Int.getInstance().convert(exp)];
			for (int i = 0; i < array.length; ++i) {
				array[i] = new Value();
				array[i].setType(TypeNodes.aRealType);
				array[i].setValue(0.0);
			}
			result.setValue(array);
			setOut(node, result);
		} catch (TypeCastException tc) {
			errors.add(new SemanticError(tc.getMessage(), tc.getFrom(), node
					.getNew()));
		}
	}

	@Override
	public void outANewObjectExp(ANewObjectExp node) {
		String className = node.getType().getText().trim();
		Value result = new Value();
		result.setType(new AClassType((TId)classDecls.get(className).getAClassDecl()
				.getId().clone()));
		HashMap<String, Value> obj = new HashMap<String, Value>();
		try {
			List<String> supers = new ArrayList<String>();
			do {
				supers.add(className);
				ClassDecl cd = classDecls.get(className);
				className = cd.getSuperClass();
			} while (className != null);
			Collections.reverse(supers);

			for (String name : supers) {
				ClassDecl cd = classDecls.get(name);
				for (String var : cd.getVars().keySet()) {
					Value v = new Value();
					PType type = cd.getVars().get(var).getType();
					v.setType(type);
					v.setValue(PTypeDefaultValue.getInstance().defaultValue(
							type));
					obj.put(var, v);
				}
			}
		} catch (NullPointerException npe) {
			errors.add(new SemanticError("Class or superclass '" + className
					+ "' not found!", node, node.getType()));
		}
		result.setValue(obj);
		setOut(node,result);
	}

	@Override
	public void outAArrayLengthExp(AArrayLengthExp node) {
		Value array = (Value) getOut(node.getArray());
		Value result = new Value();
		if (WhatType.getInstance().is(array.getType(), TypeNodes.aIntArrayType)
				|| (WhatType.getInstance().is(array.getType(),
						TypeNodes.aRealArrayType))) {
			result.setType(TypeNodes.aIntType);
			result.setValue(((Value[]) array.getValue()).length);
		} else {
			errors.add(new SemanticError("Value is not array", node.getArray(),
					node.getLength()));
			return;
		}
		setOut(node, result);
	}

	@Override
	public void outAMemFuncExp(AMemFuncExp node) {
		Value thisObject = (Value) getOut(node.getObject());
		symbol.add(new RuntimeStack());
		currentScope().setThisValue(thisObject);

		AClassType thisType = (AClassType) thisObject.getType();
		String className = thisType.getId().getText().trim();
		ClassDecl cd = classDecls.get(className);
		AMethodDecl method = cd.getMethods().get(
				node.getFunc().getText().trim());

		Value returnValue = new Value();
		returnValue.setType(method.getType());
		returnValue.setValue(PTypeDefaultValue.getInstance().defaultValue(
				method.getType()));
		currentScope().setReturnValue(returnValue);
		
		//compare args
		int formArgs=method.getArg()==null?0:method.getArg().size();
		int realArgs=node.getArgs()==null?0:node.getArgs().size();
		
		if(formArgs!=realArgs){
			errors.add(new SemanticError("Args count not equal!",node,node.getFunc()));
			symbol.remove(symbol.size()-1);
			setOut(node,returnValue);
			return;
		}
		
		//assign args
		for(int i=0;i<formArgs;++i){
			AArg formal=(AArg)method.getArg().get(i);
			Value arg=(Value)getOut(node.getArgs().get(i));
			if(!WhatType.getInstance().is(formal.getType(),arg.getType())){
				errors.add(new SemanticError("Args type not equal!",node,node.getFunc()));
				symbol.remove(symbol.size()-1);
				setOut(node,returnValue);
				return;
			}
			
			currentScope().getSymbol().put(formal.getId().getText().trim(), arg.clone());
							
		}
		
		if(method.getPreDecl()!=null){
			method.getPreDecl().apply(this);
			Value v=(Value)getOut(method.getPreDecl());
			boolean cond=Convert2Boolean.getInstance().convert(v);
			if(!cond){
				errors.add(new SemanticError("Pre condition failed!",method,method.getId()));
				symbol.remove(symbol.size()-1);
				setOut(node,returnValue);
				return;
			}
		}
		
		if(method.getBody()!=null){
			method.getBody().apply(this);
		}
		
		if(method.getPostDecl()!=null){
			method.getPostDecl().apply(this);
			Value v=(Value)getOut(method.getPostDecl());
			boolean cond=Convert2Boolean.getInstance().convert(v);
			if(!cond){
				errors.add(new SemanticError("Post condition failed!",method,method.getId()));
				symbol.remove(symbol.size()-1);
				setOut(node,returnValue);
				return;
			}
		}
		
		if(method.getExp()!=null){
			method.getExp().apply(this);
			returnValue=(Value)getOut(method.getExp());			
		}
		
		symbol.remove(symbol.size()-1);
		setOut(node,returnValue);
	}

	
	@Override
	public void outAPreDecl(APreDecl node) {
		setOut(node,getOut(node.getExp()));
	}
	
	@Override
	public void outAPostDecl(APostDecl node) {
		setOut(node,getOut(node.getExp()));
	}
	
	@Override
	public void caseAAndOprExp(AAndOprExp node) {
		inAAndOprExp(node);
		if (node.getAndOpr() != null) {
			node.getAndOpr().apply(this);
		}
		if (node.getFirst() != null) {
			node.getFirst().apply(this);
			Value v = (Value) getOut(node.getFirst());
			if (Convert2Boolean.getInstance().convert(v)) {
				if (node.getRest() != null) {
					node.getRest().apply(this);
					setOut(node, getOut(node.getRest()));
				}
			} else {
				setOut(node, getOut(node.getFirst()));
			}
		}
		outAAndOprExp(node);
	}

	@Override
	public void caseAOrOprExp(AOrOprExp node) {
		inAOrOprExp(node);
		if (node.getOrOpr() != null) {
			node.getOrOpr().apply(this);
		}
		if (node.getFirst() != null) {
			node.getFirst().apply(this);
			Value v = (Value) getOut(node.getFirst());
			if (!Convert2Boolean.getInstance().convert(v)) {
				if (node.getRest() != null) {
					node.getRest().apply(this);
					setOut(node, getOut(node.getRest()));
				}
			} else {
				setOut(node, getOut(node.getFirst()));
			}
		}
		outAOrOprExp(node);
	}

	@Override
	public void outAPrintState(APrintState node) {
		Value v = (Value) getOut(node.getExp());
		PrintValue.getInstance().print(v, outputs);
	}

	@Override
	public void outAAssignState(AAssignState node) {
		Value old = (Value) getOut(node.getTarget());
		Value value = (Value) (getOut(node.getValue()));
		if (WhatType.getInstance().is(old.getType(), TypeNodes.aIntType)) {
			old.setValue(Convert2Int.getInstance().convert(value));
		} else if (WhatType.getInstance()
				.is(old.getType(), TypeNodes.aRealType)) {
			old.setValue(Convert2Real.getInstance().convert(value));
		} else if (WhatType.getInstance().is(old.getType(),
				TypeNodes.aBooleanType)) {
			old.setValue(Convert2Boolean.getInstance().convert(value));
		} else {
			PType type;
			type = value.getType();
			while (true) {
				if (WhatType.getInstance().is(old.getType(), type)) {
					old.setValue(value.getValue());
					return;
				}
				if (WhatType.getInstance().isClass(old.getType())
						&& WhatType.getInstance().isClass(type)) {
					AClassDecl aClass = classDecls.get(
							((AClassType) type).getId().getText().trim())
							.getAClassDecl();
					if (aClass.getExtendsClause() != null) {
						aClass.getExtendsClause().apply(this);
						String superClassName = (String) getOut(aClass
								.getExtendsClause());
						type = new AClassType((TId)classDecls.get(superClassName)
								.getAClassDecl().getId().clone());
						if (type == null) {
							errors.add(new SemanticError(
									"Can't find superclass '" + superClassName
											+ "'", node, (TId)aClass.getId().clone()));
							return;
						}
					} else {
						errors.add(new SemanticError("Type '" + value.getType()
								+ "' can not conver to Type" + old.getType(),
								node, null));
						return;
					}
				} else {
					errors.add(new SemanticError("Type '" + value.getType()
							+ "' can not conver to Type" + old.getType(), node,
							null));
					return;
				}
			}

		}
	}

	@Override
	public void caseAIfState(AIfState node) {
		inAIfState(node);
		if (node.getCond() != null) {
			node.getCond().apply(this);
			Value v = (Value) getOut(node.getCond());
			boolean cond = Convert2Boolean.getInstance().convert(v);
			if (cond) {
				if (node.getThen() != null) {
					node.getThen().apply(this);
				}
			} else {
				if (node.getElse() != null) {
					node.getElse().apply(this);
				}
			}
		}
		outAIfState(node);
	}

	@Override
	public void caseAWhileState(AWhileState node) {
		inAWhileState(node);
		if (node.getCond() != null) {
			while (true) {
				node.getCond().apply(this);
				Value v = (Value) getOut(node.getCond());
				boolean cond = Convert2Boolean.getInstance().convert(v);
				if (cond) {
					if (node.getClause() != null) {
						node.getClause().apply(this);
					}
				} else {
					break;
				}
			}
		}
		outAWhileState(node);
	}

	@Override
	public void caseABodyProgram(ABodyProgram node) {
		super.caseABodyProgram(node);
	}

	@Override
	public void caseAClassProgram(AClassProgram node) {
		inAClassProgram(node);
		for (PClassDecl classDecl : node.getClassDecl()) {
			classDecl.apply(this);
		}

		if (node.getMainClass() != null) {
			node.getMainClass().apply(this);
		}
		outAClassProgram(node);
	}

	@Override
	public void caseAClassDecl(AClassDecl node) {
		inAClassDecl(node);
		String className = ((TId)node.getId().clone()).getText().trim();
		ClassDecl cd = new ClassDecl();
		cd.setAClassDecl(node);

		symbol.add(new RuntimeStack());
		if (node.getVarDecl() != null) {
			for (PVarDecl vd : node.getVarDecl()) {
				vd.apply(this);
			}
		}
		HashMap<String, Value> vars = symbol.get(symbol.size() - 1).getSymbol();
		symbol.remove(symbol.size() - 1);
		cd.setVars(vars);

		cd.setMethods(new HashMap<String, AMethodDecl>());
		if (node.getMethodDecl() != null) {
			for (PMethodDecl md : node.getMethodDecl()) {
				md.apply(this);
				AMethodDecl aMethod = (AMethodDecl) getOut(md);
				cd.getMethods().put(((TId)aMethod.getId().clone()).getText().trim(), aMethod);
			}
		}

		if (node.getExtendsClause() != null) {
			String superClass = (String) getOut(node.getExtendsClause());
			cd.setSuperClass(superClass);
		}

		classDecls.put(className, cd);
		outAClassDecl(node);
	}

	@Override
	public void caseAMethodDecl(AMethodDecl node) {
		setOut(node, node);
	}

	@Override
	public void outAExtendsClause(AExtendsClause node) {
		setOut(node, node.getId().getText().trim());
	}

	public HashMap<String, ClassDecl> getClassDecls() {
		return classDecls;
	}

	public Start getStart() {
		return start;
	}
}
