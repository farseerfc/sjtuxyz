package xyzlex.interpt;

import xyzlex.analysis.AnalysisAdapter;
import xyzlex.node.*;

public class WhatType extends AnalysisAdapter {
	public static WhatType instance=null;
	
	public static WhatType getInstance(){
		if(instance == null){
			instance=new WhatType();
		}
		return instance;
	}
	
	private WhatType(){}
	
	private Types result;

	public boolean is(Node type, Node compareTo) {
		result = Types.Unknown;
		type.apply(this);
		Types middle = result;
		result = Types.Unknown;
		compareTo.apply(this);
		if (middle == Types.Class && result == Types.Class) {
			return ((AClassType) type).getId().getText().trim().equals(
					((AClassType) compareTo).getId().getText().trim());
		}

		return middle != Types.Unknown && result != Types.Unknown
				&& middle == result;
	}
	
	public boolean isClass(Node type){
		result = Types.Unknown;
		type.apply(this);
		return result==Types.Class;		
	}

	@Override
	public void caseAIntType(AIntType node) {
		result = Types.Int;
	}

	@Override
	public void caseAIntArrayType(AIntArrayType node) {
		result = Types.IntAr;
	}

	@Override
	public void caseARealType(ARealType node) {
		result = Types.Real;
	}

	@Override
	public void caseARealArrayType(ARealArrayType node) {
		result = Types.RealAr;
	}

	@Override
	public void caseABooleanType(ABooleanType node) {
		result = Types.Boolean;
	}

	@Override
	public void caseAClassType(AClassType node) {
		result = Types.Class;
	}
}
