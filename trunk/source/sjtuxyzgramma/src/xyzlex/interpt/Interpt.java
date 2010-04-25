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

	private List<HashMap<String, Value>> symbol;
	private List<SemanticError> errors;
	private List<String> outputs;
	
	private HashMap<String,AClassDecl> classDecls;
	

	public List<HashMap<String, Value>> getSymbol() {
		return symbol;
	}

	public List<SemanticError> getErrors() {
		return errors;
	}

	public List<String> getOutputs() {
		return outputs;
	}

	
	private void init(){
		symbol = new ArrayList<HashMap<String, Value>>();
		symbol.add(new HashMap<String, Value>());
		errors = new ArrayList<SemanticError>();
		outputs = new ArrayList<String>();
		classDecls=new HashMap<String,AClassDecl>();
	}

	public Interpt() {
		init();
	}

	public Interpt(PushbackReader in) throws ParserException, LexerException,
			IOException {
		init();
		new Parser(new Lexer(in)).parse().apply(this);
	}

	public Interpt(String input) throws ParserException, LexerException,
			IOException {
		this(new PushbackReader(new StringReader(input), Consts
				.getConst(Consts.InputStreamBufferSize)));
	}

	public Map<String, Value> currentScope() {
		return symbol.get(symbol.size() - 1);
	}

	@Override
	public void outAVarDecl(AVarDecl node) {
		String name = node.getId().getText();
		PType typeDecl = node.getType();
		if (currentScope().containsKey(name)) {
			errors.add(new SemanticError("Variable already declared! ", node,
					node.getId()));
			return;
		} else {
			Value v = new Value();
			v.setType(typeDecl);
			v.setValue(PTypeDefaultValue.getInstance().defaultValue(typeDecl));
			currentScope().put(name, v);
		}
	}

	@Override
	public void outAIntLtExp(AIntLtExp node) {
		Value v = new Value();
		v.setType(TypeNodes.aIntType);
		v.setValue(Integer.valueOf(node.getIntLt().getText()));
		setOut(node, v);
	}

	@Override
	public void outARealLtExp(ARealLtExp node) {
		Value v = new Value();
		v.setType(TypeNodes.aRealType);
		v.setValue(Double.valueOf(node.getRealLt().getText()));
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
	public void outAVarExp(AVarExp node) {
		String name = node.getVar().getText();
		if (currentScope().containsKey(name)) {
			setOut(node, currentScope().get(name));
		} else {
			errors.add(new SemanticError("Variable not defined!", node, node
					.getVar()));
		}
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
			setOut(node,my);
		} catch (TypeCastException tce) {
			errors.add(new SemanticError(tce.getMessage(), node, node
					.getNotOpr()));
		}
	}

	@Override
	public void outAThisExp(AThisExp node) {
		errors.add(new SemanticError("'this' keyword not supported yet! ",
				node, node.getThis()));
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
			setOut(node,result);
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
			
			setOut(node,result);
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
			
			setOut(node,result);
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
			
			setOut(node,result);
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
			setOut(node,result);
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
			setOut(node,result);
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
			result.setValue(new int[Convert2Int.getInstance().convert(exp)]);
			setOut(node,result);
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
			result.setValue(new double[Convert2Int.getInstance().convert(exp)]);
			setOut(node,result);
		} catch (TypeCastException tc) {
			errors.add(new SemanticError(tc.getMessage(), tc.getFrom(), node
					.getNew()));
		}
	}

	@Override
	public void outAArraySubExp(AArraySubExp node) {
		Value array = (Value) getOut(node.getArray());
		Value index = (Value) getOut(node.getIndex());
		Value result = new Value();
		int indexInt = 0;
		try {
			indexInt = Convert2Int.getInstance().convert(index);
		} catch (TypeCastException tc) {
			errors.add(new SemanticError(tc.getMessage(), tc.getFrom(), node
					.getLSq()));
			return;
		}
		if (WhatType.getInstance()
				.is(array.getType(), TypeNodes.aIntArrayType)) {
			result.setType(TypeNodes.aIntType);
			result.setValue(((int[]) array.getValue())[indexInt]);
		} else if (WhatType.getInstance().is(array.getType(),
				TypeNodes.aRealArrayType)) {
			result.setType(TypeNodes.aRealType);
			result.setValue(((double[]) array.getValue())[indexInt]);
		} else {
			errors.add(new SemanticError("Value is not array", node.getArray(),
					node.getLSq()));
			return;
		}
		setOut(node,result);
	}

	@Override
	public void outAArrayLengthExp(AArrayLengthExp node) {
		Value array = (Value) getOut(node.getArray());
		Value result = new Value();
		if (WhatType.getInstance()
				.is(array.getType(), TypeNodes.aIntArrayType)) {
			result.setType(TypeNodes.aIntType);
			result.setValue(((int[]) array.getValue()).length);
		} else if (WhatType.getInstance().is(array.getType(),
				TypeNodes.aRealArrayType)) {
			result.setType(TypeNodes.aIntType);
			result.setValue(((double[]) array.getValue()).length);
		} else {
			errors.add(new SemanticError("Value is not array", node.getArray(),
					node.getLength()));
			return;
		}
		setOut(node,result);
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
		String name = node.getId().getText();
		if (!currentScope().containsKey(name)) {
			errors.add(new SemanticError(
					"Current Scope don't contain variable of name '" + name
							+ "'", node, node.getId()));
			return;
		}
		Value old = currentScope().get(name);
		if (node.getIndex() != null) {
			if (WhatType.getInstance().is(old.getType(),
					TypeNodes.aIntArrayType)) {
				((int[]) (old.getValue()))[Convert2Int.getInstance().convert(
						(Value) getOut(node.getIndex()))] = Convert2Int
						.getInstance().convert((Value) getOut(node.getValue()));
			} else if (WhatType.getInstance().is(old.getType(),
					TypeNodes.aRealArrayType)) {
				((double[]) (old.getValue()))[Convert2Int.getInstance().convert(
						(Value) getOut(node.getIndex()))] = Convert2Real
						.getInstance().convert((Value) getOut(node.getValue()));
			} else {
				errors.add(new SemanticError("Variable '" + name
						+ "' is not an array!", node, node.getId()));
				return;
			}
		} else {
			if (WhatType.getInstance().is(old.getType(), TypeNodes.aIntType)) {
				old.setValue(Convert2Int.getInstance().convert(
						(Value) getOut(node.getValue())));
			} else if (WhatType.getInstance().is(old.getType(),
					TypeNodes.aRealType)) {
				old.setValue(Convert2Real.getInstance().convert(
						(Value) getOut(node.getValue())));
			} else if (WhatType.getInstance().is(old.getType(),
					TypeNodes.aBooleanType)) {
				old.setValue(Convert2Boolean.getInstance().convert(
						(Value) getOut(node.getValue())));
			} else if (WhatType.getInstance().is(old.getType(),
					((Value) getOut(node.getValue())).getType())) {
				old.setValue(((Value) getOut(node.getValue())).getValue());
			} else {
				errors.add(new SemanticError("Variable '"
						+ name
						+ "' can not assign type"
						+ ((Value) getOut(node.getValue())).getType()
								.toString() + "!", node, node.getId()));
				return;
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
	public void caseAClassProgram(AClassProgram node)
	{
		List<PClassDecl> list=node.getClassDecl();
		for(PClassDecl classDecl : list){
			AClassDecl aClass = (AClassDecl)classDecl;
			
		}
	}
}
