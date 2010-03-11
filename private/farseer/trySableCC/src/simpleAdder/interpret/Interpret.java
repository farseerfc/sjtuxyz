package simpleAdder.interpret;

import simpleAdder.node.*;
import simpleAdder.analysis.*;
import simpleAdder.lexer.*;
import simpleAdder.parser.*;

import java.io.*;

public class Interpret extends DepthFirstAdapter {
	public void caseAStatement(AStatement node) {
		String lhs = node.getLeft().getText().trim();
		String rhs = node.getRight().getText().trim();
		int result = (new Integer(lhs)).intValue()
				+ (new Integer(rhs)).intValue();
		System.out.println(lhs + "+" + rhs + "=" + result);
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			try {
				/* Form our AST */
				Lexer lexer = new Lexer(new PushbackReader(new FileReader(
						args[0]), 1024));
				Parser parser = new Parser(lexer);
				Start ast = parser.parse();

				/* Get our Interpreter going. */
				Interpret interp = new Interpret();
				ast.apply(interp);
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			System.err.println("usage: java simpleAdder inputFile");
			System.exit(1);
		}
	}
}
