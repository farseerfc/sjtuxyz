package xyzlex.interpt;

import java.util.*;

import xyzlex.analysis.DepthFirstAdapter;
import xyzlex.node.*;

public class Interpt extends DepthFirstAdapter {

	private Map<String, Value> symbol;
	private List<SemanticError> errors;
	private PTypeDefaultValue dv;

	public Interpt() {
		symbol = new HashMap<String, Value>();
		errors = new ArrayList<SemanticError>();
		dv = new PTypeDefaultValue();
	}

	@Override
	public void outAVarDecl(AVarDecl node) {
		String name = node.getId().getText();
		PType typeDecl = node.getType();
		if (symbol.containsKey(name)) {
			errors.add(new SemanticError("Variable already declared! ", node,
					node.getId()));
		} else {
			Value v = new Value();
			v.setType(typeDecl);
			v.setValue(dv.defaultValue(typeDecl));
			symbol.put(name, v);
		}
	}

	@Override
	public void outAIntLtExp(AIntLtExp node) {
		Value v = new Value();
		v.setType(new AIntType());
		v.setValue(Integer.valueOf(node.getIntLt().getText()));
		setOut(node, v);
	}

	@Override
	public void outARealLtExp(ARealLtExp node) {
		Value v = new Value();
		v.setType(new ARealType());
		v.setValue(Double.valueOf(node.getRealLt().getText()));
		setOut(node, v);
	}

	@Override
	public void outATrueLtExp(ATrueLtExp node) {
		Value v = new Value();
		v.setType(new ABooleanType());
		v.setValue(true);
		setOut(node, v);
	}

	@Override
	public void outAFalseLtExp(AFalseLtExp node) {
		Value v = new Value();
		v.setType(new ABooleanType());
		v.setValue(false);
		setOut(node, v);
	}

	@Override
	public void outAVarExp(AVarExp node) {
		String name = node.getVar().getText();
		if (symbol.containsKey(name)) {
			setOut(node, symbol.get(name));
		} else {
			errors.add(new SemanticError("Variable not defined!", node, node
					.getVar()));
		}
	}
	
	@Override
	public void outASubExpExp(ASubExpExp node) {
		setOut(node,getOut(node.getExp()));
	}

	@Override
	public void outANotOprExp(ANotOprExp node) {
		Value v=(Value)getOut(node.getFirst());
		if(v.type instanceof ABooleanType){
			Value my=new Value();
			my.type=new ABooleanType();
			my.value=!((Boolean)(v.getValue()));
		}
	}
}
