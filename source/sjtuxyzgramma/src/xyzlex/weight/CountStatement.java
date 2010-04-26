package xyzlex.weight;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import xyzlex.analysis.DepthFirstAdapter;
import xyzlex.lexer.Lexer;
import xyzlex.lexer.LexerException;
import xyzlex.node.AAssignState;
import xyzlex.node.AMethodDecl;
import xyzlex.node.APrintState;
import xyzlex.node.Start;
import xyzlex.parser.Parser;
import xyzlex.parser.ParserException;
import xyzlex.utils.Consts;

public class CountStatement extends DepthFirstAdapter {
	private int count=0;
	

	public CountStatement() {
		count=0;
	}

	public CountStatement(PushbackReader in){
		Start s;
		try {
			Parser p=new Parser(new Lexer(in));
			s = p.parse();
			s.apply(this);
		} catch (ParserException e) {
			
		} catch (LexerException e) {
			
		} catch (IOException e) {
			
		}
		//count=(Integer)getOut(s);
	}

	public CountStatement(String input) throws ParserException, LexerException,
			IOException {
		this(new PushbackReader(new StringReader(input), Consts
				.getConst(Consts.InputStreamBufferSize)));
	}
	
	@Override
	public void outAAssignState(AAssignState node) {
		count++;
	}
	
	@Override
	public void outAPrintState(APrintState node) {
		count++;
	}
	
	@Override
	public void outAMethodDecl(AMethodDecl node) {
		count++;
	}

	public int getCount() {
		return count;
	}
	
}
