package xyzlex.gui;

import xyzlex.node.Token;

public class TokenDiscriber {
	private Class<? extends Token> tokenClass;
	private String name;
	private String color;
	
	public TokenDiscriber(){
		
	}
	
	public TokenDiscriber(
			Class<? extends Token> tokenClass,
			String name,
			String color){
		this.tokenClass=tokenClass;
		this.name=name;
		this.color=color;
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
