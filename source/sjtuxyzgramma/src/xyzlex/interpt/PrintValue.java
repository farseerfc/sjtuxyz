package xyzlex.interpt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyzlex.analysis.AnalysisAdapter;
import xyzlex.node.ABooleanType;
import xyzlex.node.AIntArrayType;
import xyzlex.node.AIntType;
import xyzlex.node.ARealArrayType;
import xyzlex.node.ARealType;

public class PrintValue extends AnalysisAdapter {
	public static PrintValue instance=null;
	
	public static PrintValue getInstance(){
		if(instance == null){
			instance=new PrintValue();
		}
		return instance;
	}
	
	private PrintValue(){}
	
	private Value v;
	private List<String> o;
	public void print(Value value,List<String> output){
		v=value;
		o=output;
		v.getType().apply(this);
	}
	
	@Override
	public void caseAIntType(AIntType node) {
		o.add(((Integer)v.getValue()).toString());
	}
	
	@Override
	public void caseAIntArrayType(AIntArrayType node) {
		Value [] array=(Value[])v.getValue();
		List<String> result=new ArrayList<String>();
		for(Value v :array){
			print(v,result);
		}
		o.add(Arrays.toString(result.toArray()));
	}
	
	@Override
	public void caseARealType(ARealType node) {
		o.add(((Double)v.getValue()).toString());
	}
	
	@Override
	public void caseARealArrayType(ARealArrayType node) {
		Value [] array=(Value[])v.getValue();
		List<String> result=new ArrayList<String>();
		for(Value v :array){
			print(v,result);
		}
		o.add(Arrays.toString(result.toArray()));
	}
	
	@Override
	public void caseABooleanType(ABooleanType node) {
		o.add(((Boolean)v.getValue()).toString());
	}
}
