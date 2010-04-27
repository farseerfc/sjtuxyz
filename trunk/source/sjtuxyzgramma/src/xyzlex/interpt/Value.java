/**
 * 
 */
package xyzlex.interpt;

import xyzlex.node.*;

public class Value implements Cloneable {
	private PType type;
	private Object value;

	public PType getType() {
		return type;
	}

	public void setType(PType type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public Value clone()  {
		Value result=null;
		try {
			result = (Value)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		result.type = (PType) type.clone();
		if (value instanceof Node) {
			result.value = ((Node)result.value).clone();
		}else if (value instanceof Value) {
			result.value = ((Value)result.value).clone();
		}
		return result;
	}
	
	@Override
	public String toString() {		
		return type.toString()+":"+value.toString();
	}
}