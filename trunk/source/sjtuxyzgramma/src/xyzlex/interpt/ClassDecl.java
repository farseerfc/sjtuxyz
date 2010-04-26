/**
 * 
 */
package xyzlex.interpt;

import java.util.HashMap;

import xyzlex.node.AClassDecl;
import xyzlex.node.AMethodDecl;
import xyzlex.node.PMethodDecl;

public class ClassDecl{
	private AClassDecl aClassDecl;
	private HashMap<String,AMethodDecl> methods;
	private HashMap<String,Value> vars;
	private String superClass;
	
	public AClassDecl getAClassDecl() {
		return aClassDecl;
	}
	public void setAClassDecl(AClassDecl classDecl) {
		aClassDecl = classDecl;
	}
	public HashMap<String, AMethodDecl> getMethods() {
		return methods;
	}
	public void setMethods(HashMap<String, AMethodDecl> methods) {
		this.methods = methods;
	}
	public HashMap<String, Value> getVars() {
		return vars;
	}
	public void setVars(HashMap<String, Value> vars) {
		this.vars = vars;
	}
	public String getSuperClass() {
		return superClass;
	}
	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}
}