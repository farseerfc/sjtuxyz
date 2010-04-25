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

public class Convert2Int extends AnalysisAdapter {
	public static Convert2Int instance = null;

	public static Convert2Int getInstance() {
		if (instance == null) {
			instance = new Convert2Int();
		}
		return instance;
	}

	private Convert2Int() {
	}

	int returnValue;
	Value value;

	public int convert(Value value) {
		this.value = value;
		value.getType().apply(this);
		return returnValue;
	}

	@Override
	public void caseABooleanType(ABooleanType node) {
		throw new TypeCastException(node, TypeNodes.aIntType);
	}

	@Override
	public void caseAIntArrayType(AIntArrayType node) {
		throw new TypeCastException(node, TypeNodes.aIntType);
	}

	@Override
	public void caseARealArrayType(ARealArrayType node) {
		throw new TypeCastException(node, TypeNodes.aIntType);
	}

	@Override
	public void caseAIntType(AIntType node) {
		returnValue = ((Integer) value.getValue());
	}

	@Override
	public void caseARealType(ARealType node) {
		returnValue = (int) (double) ((Double) value.getValue());
	}

	@Override
	public void caseAClassType(AClassType node) {
		throw new TypeCastException(node, TypeNodes.aIntType);
	}
}