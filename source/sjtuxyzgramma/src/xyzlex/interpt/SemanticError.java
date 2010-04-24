/**
 * 
 */
package xyzlex.interpt;

import xyzlex.node.Node;
import xyzlex.node.Token;

public class SemanticError {
	String discription;
	Node source;
	Token token;
	
	public SemanticError(){
		discription="";
		token=null;
		source=null;
	}
	
	public SemanticError(String dis,Node src,Token token)
	{
		discription=dis;
		source=src;
		this.token=token;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

}