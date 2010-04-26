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
	public Value clone() throws CloneNotSupportedException {
		Value result = new Value();
		result.type = (PType) type.clone();
		if (value instanceof Node) {
			result.value = ((Node)value).clone();
		}else if (value instanceof Value) {
			result.value = ((Value)value).clone();
		}else{
			result.value=value;
		}
		return result;
	}
}