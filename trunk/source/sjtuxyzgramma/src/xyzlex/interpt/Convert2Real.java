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

public class Convert2Real extends  AnalysisAdapter {
	public static Convert2Real instance=null;
	
	public static Convert2Real getInstance(){
		if(instance == null){
			instance=new Convert2Real();
		}
		return instance;
	}
	
	private Convert2Real(){}
	
	double returnValue;
	Value value;

	public double convert(Value value) {
		this.value = value;
		value.getType().apply(this);
		return returnValue; 
	}

	@Override
	public void caseABooleanType(ABooleanType node) {
		throw new TypeCastException(node, TypeNodes.aRealType);
	}

	@Override
	public void caseAIntArrayType(AIntArrayType node) {
		throw new TypeCastException(node, TypeNodes.aRealType);
	}

	@Override
	public void caseARealArrayType(ARealArrayType node) {
		throw new TypeCastException(node, TypeNodes.aRealType);
	}

	@Override
	public void caseAIntType(AIntType node) {
		returnValue = ((Integer) value.getValue()) ;
	}

	@Override
	public void caseARealType(ARealType node) {
		returnValue = ((Double) value.getValue());
	}

	@Override
	public void caseAClassType(AClassType node) {
		throw new TypeCastException(node, TypeNodes.aRealType);
	}
}