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
import xyzlex.node.Switchable;

public class PTypeDefaultValue extends AnalysisAdapter {
	public static PTypeDefaultValue instance = null;

	public static PTypeDefaultValue getInstance() {
		if (instance == null) {
			instance = new PTypeDefaultValue();
		}
		return instance;
	}

	private PTypeDefaultValue() {
	}

	Object returnValue;

	Object defaultValue(Switchable s) {
		s.apply(this);
		return returnValue;
	}

	@Override
	public void caseAIntType(AIntType node) {
		returnValue = 0;
	}

	@Override
	public void caseAIntArrayType(AIntArrayType node) {
		returnValue = new int[0];
	}

	@Override
	public void caseARealType(ARealType node) {
		returnValue = 0.0;
	}

	@Override
	public void caseARealArrayType(ARealArrayType node) {
		returnValue = new double[0];
	}

	@Override
	public void caseABooleanType(ABooleanType node) {
		returnValue = false;
	}

	@Override
	public void caseAClassType(AClassType node) {
		returnValue = null;
	}
}