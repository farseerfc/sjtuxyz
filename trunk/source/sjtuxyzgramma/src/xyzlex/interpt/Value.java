/**
 * 
 */
package xyzlex.interpt;

import xyzlex.node.*;


public class Value{
	PType type;
	Object value;
	
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
}