package xyzlex.weight;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import xyzlex.analysis.DepthFirstAdapter;
import xyzlex.lexer.Lexer;
import xyzlex.lexer.LexerException;
import xyzlex.node.AAssignState;
import xyzlex.node.ABlockState;
import xyzlex.node.ABody;
import xyzlex.node.ABodyProgram;
import xyzlex.node.AClassDecl;
import xyzlex.node.AClassProgram;
import xyzlex.node.AIfState;
import xyzlex.node.AMainClass;
import xyzlex.node.AMethodDecl;
import xyzlex.node.APrintState;
import xyzlex.node.AWhileState;
import xyzlex.node.PClassDecl;
import xyzlex.node.PMethodDecl;
import xyzlex.node.PState;
import xyzlex.node.Start;
import xyzlex.parser.Parser;
import xyzlex.parser.ParserException;
import xyzlex.utils.Consts;

public class Weight extends DepthFirstAdapter {
	private int weight;

	public Weight() {
		weight = 0;
	}

	public Weight(PushbackReader in) {
		
		Start s;
		try {
			Parser p=new Parser(new Lexer(in));
			s = p.parse();
			s.apply(this);
			weight=(Integer)getOut(s);
		} catch (ParserException e) {
			
		} catch (LexerException e) {
			
		} catch (IOException e) {
			
		}
		
	}

	public Weight(String input) throws ParserException, LexerException,
			IOException {
		this(new PushbackReader(new StringReader(input), Consts
				.getConst(Consts.InputStreamBufferSize)));
	}

	@Override
	public void outAAssignState(AAssignState node) {
		setOut(node, 1);
	}

	@Override
	public void outAPrintState(APrintState node) {
		setOut(node, 1);
	}

	@Override
	public void outABlockState(ABlockState node) {
		int count = 0;
		for (PState s : node.getList()) {
			count += (Integer) getOut(s);
		}
		setOut(node, count);
	}

	@Override
	public void outAIfState(AIfState node) {
		int count = 0;
		count += (Integer) getOut(node.getElse());
		count += (Integer) getOut(node.getThen());
		setOut(node, count + count);
	}

	@Override
	public void outAWhileState(AWhileState node) {
		int count = (Integer) getOut(node.getClause());
		setOut(node, count * 4);
	}

	@Override
	public void outAClassDecl(AClassDecl node) {
		int count = 0;
		for (PMethodDecl m : node.getMethodDecl()) {
			count += (Integer) getOut(m);
		}
		setOut(node, count);
	}

	@Override
	public void outAMethodDecl(AMethodDecl node) {
		setOut(node, ((Integer) getOut(node.getBody())) + 1);
	}

	@Override
	public void outABody(ABody node) {
		int count = 0;
		for (PState s : node.getState()) {
			count += (Integer) getOut(s);
		}
		setOut(node, count);
	}

	@Override
	public void outAClassProgram(AClassProgram node) {
		int count = (Integer) getOut(node.getMainClass());
		for (PClassDecl s : node.getClassDecl()) {
			count += (Integer) getOut(s);
		}
		setOut(node, count);
	}
	
	@Override
	public void outAMainClass(AMainClass node) {
		setOut(node,getOut(node.getBody()));
	}

	@Override
	public void outABodyProgram(ABodyProgram node) {
		setOut(node, getOut(node.getBody()));
	}

	@Override
	public void outStart(Start node) {
		setOut(node, getOut(node.getPProgram()));
	}

	public int getWeight() {
		return weight;
	}
}
