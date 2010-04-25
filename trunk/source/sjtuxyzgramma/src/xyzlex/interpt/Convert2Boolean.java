/**
 * 
 */
package xyzlex.interpt;

import xyzlex.analysis.AnalysisAdapter;
import xyzlex.node.ABooleanType;
import xyzlex.node.AClassType;
import xyzlex.node.AIntArrayType;
import xyzlex.node.AIntType;
import xyzlex.node.ARealArrayType;
import xyzlex.node.ARealType;

public class Convert2Boolean extends AnalysisAdapter {
	public static Convert2Boolean instance = null;

	public static Convert2Boolean getInstance() {
		if (instance == null) {
			instance = new Convert2Boolean();
		}
		return instance;
	}

	private Convert2Boolean() {
	}

	boolean returnValue;
	Value value;

	public boolean convert(Value value) {
		this.value = value;
		value.getType().apply(this);
		return returnValue;
	}

	@Override
	public void caseABooleanType(ABooleanType node) {
		returnValue = (Boolean)( value.getValue());
	}

	@Override
	public void caseAIntArrayType(AIntArrayType node) {
		throw new TypeCastException(node, TypeNodes.aBooleanType);
	}

	@Override
	public void caseARealArrayType(ARealArrayType node) {
		throw new TypeCastException(node, TypeNodes.aBooleanType);
	}

	@Override
	public void caseAIntType(AIntType node) {
		returnValue = ((Integer) value.getValue()) != 0;
	}

	@Override
	public void caseARealType(ARealType node) {
		returnValue = ((Double) value.getValue()) != 0;
	}

	@Override
	public void caseAClassType(AClassType node) {
		throw new TypeCastException(node, TypeNodes.aBooleanType);
	}
}