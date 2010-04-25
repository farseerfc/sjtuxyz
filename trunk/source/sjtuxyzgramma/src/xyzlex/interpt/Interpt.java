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

	private HashMap<String, AClassDecl> classDecls;

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
		classDecls = new HashMap<String, AClassDecl>();
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
		return symbol.get(symbol.size() - 1).getSymbol();
	}

	@Override
	public void outAVarDecl(AVarDecl node) {
		String name = node.getId().getText().trim();
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
	public void outAVarLeftValue(AVarLeftValue node) {
		String name = node.getId().getText().trim();
		if (currentScope().containsKey(name)) {
			setOut(node, currentScope().get(name));
		} else {
			errors.add(new SemanticError("Variable not defined!", node, node
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
	public void outALeftValueExp(ALeftValueExp node) {
		setOut(node,getOut(node.getLeftValue()));
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
		Value old=(Value)getOut(node.getTarget());
		Value value=(Value)(getOut(node.getValue()));
		if(WhatType.getInstance().is(old.getType(),TypeNodes.aIntType)){
			old.setValue(Convert2Int.getInstance().convert(value));
		}else if(WhatType.getInstance().is(old.getType(),TypeNodes.aRealType)){
			old.setValue(Convert2Real.getInstance().convert(value));
		}else if(WhatType.getInstance().is(old.getType(),TypeNodes.aBooleanType)){
			old.setValue(Convert2Boolean.getInstance().convert(value));
		}else{
			old.setValue(value.getValue());
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
		String className = node.getId().getText().trim();
		classDecls.put(className, node);
		outAClassDecl(node);
	}

}
