package xyzlex.filter;

import java.io.FileReader;
import java.io.PushbackReader;

import xyzlex.lexer.*;
import xyzlex.node.*;

import java.util.*;

public class Filter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<Class<? extends Token>, Integer> tokensCount = new HashMap<Class<? extends Token>, Integer>();
		if (args.length > 0) {
			try {
				/* Form our AST */
				Lexer lexer = new Lexer(new PushbackReader(new FileReader(
						args[0]), 1024));
				Token token;
				while (!((token = lexer.next()) instanceof EOF)) {
					System.out.println(token.getClass().getName()+" "+token.getText());
					if (tokensCount.containsKey(token.getClass())) {
						int c = tokensCount.get(token.getClass());
						c++;
						tokensCount.put(token.getClass(), c);
					}else{
						tokensCount.put(token.getClass(), 1);
					}

				}
				
				for(Class<? extends Token> c : tokensCount.keySet()){
					System.out.printf("All %s : %d tokens \n",c.getSimpleName(),tokensCount.get(c));
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			System.err.println("usage: java simpleAdder inputFile");
			System.exit(1);
		}
	}

}
