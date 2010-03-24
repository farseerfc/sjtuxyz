package xyzlex.counter;

import xyzlex.node.*;
import xyzlex.utils.Consts;
import xyzlex.lexer.*;
import java.io.*;
import java.util.*;

public class Counter {
	private PushbackReader in;
	private Lexer lexer;
	private Map<Class<? extends Token>, Integer> tokensCount;
	private Exception exception;
	private List<Token> tokenStream;

	public Counter(PushbackReader in) {
		this.in = in;
		lexer = new Lexer(this.in);
		tokensCount = new HashMap<Class<? extends Token>, Integer>();
		count();
	}

	public Counter(String input) {
		in = new PushbackReader(new StringReader(input),Consts.getConst(Consts.InputStreamBufferSize));
		lexer = new Lexer(this.in);
		tokensCount = new HashMap<Class<? extends Token>, Integer>();
		tokenStream=new ArrayList<Token>();
		count();
	}

	private void count() {
		Token token;
		try {
			while (!((token = lexer.next()) instanceof EOF)) {
				if (tokensCount.containsKey(token.getClass())) {
					int c = tokensCount.get(token.getClass());
					c++;
					tokensCount.put(token.getClass(), c);
				} else {
					tokensCount.put(token.getClass(), 1);
				}
				tokenStream.add(token);
			}
		} catch (Exception e) {
			exception=e;
		}
	}
	
	public int getCount(Class<? extends Token> tokenClass){
		if (!tokensCount.containsKey(tokenClass))
			return 0;
		return tokensCount.get(tokenClass);
	}
	
	public Set<Class<? extends Token>> getTokenKinds(){
		return tokensCount.keySet();
	}
	
	public Exception getException(){
		return exception;
	}

	
	
///////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) throws FileNotFoundException {
		//Map<Class<? extends Token>, Integer> tokensCount = new HashMap<Class<? extends Token>, Integer>();
		if (args.length > 0) {
			Counter counter=new Counter(new PushbackReader(new FileReader(
					args[0]), 1024));
			
			if(counter.getException()!=null){
				counter.getException().printStackTrace();
				System.exit(1);
			}
			
			for (Class<? extends Token> c : counter.getTokenKinds()) {
				System.out.printf("All %s : %d tokens \n", c
						.getSimpleName(), counter.getCount(c));
			}
		} else {
			System.err.println("usage: java simpleAdder inputFile");
			System.exit(1);
		}
		
		
	}

}
