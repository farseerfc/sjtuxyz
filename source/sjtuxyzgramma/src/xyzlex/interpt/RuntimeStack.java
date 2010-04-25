/**
 * 
 */
package xyzlex.interpt;

import java.util.HashMap;

public class RuntimeStack{
	private Value thisValue;
	private HashMap<String, Value> symbol;
	private Value returnValue;
	
	public RuntimeStack(){
		symbol=new HashMap<String, Value>();
	}
	
	public Value getThisValue() {
		return thisValue;
	}
	public void setThisValue(Value thisValue) {
		this.thisValue = thisValue;
	}
	public HashMap<String, Value> getSymbol() {
		return symbol;
	}
	public void setSymbol(HashMap<String, Value> symbol) {
		this.symbol = symbol;
	}
	public Value getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(Value returnValue) {
		this.returnValue = returnValue;
	}
}