package xyzlex.interpt;

import xyzlex.node.PType;

public class TypeCastException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6601590027858842103L;
	
	private PType from,to;
	
	public TypeCastException(PType from,PType to){
		super("Cannot convert from '"+from.toString()+"' to"+to.toString());
		this.from=from;
		this.to=to;
	}

	public PType getFrom() {
		return from;
	}

	public void setFrom(PType from) {
		this.from = from;
	}

	public PType getTo() {
		return to;
	}

	public void setTo(PType to) {
		this.to = to;
	}

}
