package xyzlex.gui;

import javax.swing.text.AttributeSet;

import xyzlex.node.Token;

public class TokenDescriber {
	private Class<? extends Token> tokenClass;
	private String name;
	private AttributeSet attribute;
	
	public TokenDescriber(){
		
	}
	
	public TokenDescriber(
			Class<? extends Token> tokenClass,
			String name,
			AttributeSet attribute){
		this.tokenClass=tokenClass;
		this.name=name;
		this.attribute=attribute;
	}
	
	
	public Class<? extends Token> getTokenClass() {
		return tokenClass;
	}
	public void setTokenClass(Class<? extends Token> tokenClass) {
		this.tokenClass = tokenClass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AttributeSet getColor() {
		return attribute;
	}
	public void setColor(AttributeSet attribute) {
		this.attribute = attribute;
	}
}
