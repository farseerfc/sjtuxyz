package xyzlex.interpt.test;

import java.io.IOException;
import java.util.List;

import xyzlex.interpt.Interpt;
import xyzlex.lexer.LexerException;
import xyzlex.parser.ParserException;


import junit.framework.TestCase;

public class InterptTest extends TestCase {
	public void testPrint001() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("System.out.println(1);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1");
	}
	
	public void testPrint002() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("System.out.println(1+2);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "3");
	}
	
	public void testPrint003() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("System.out.println(3*2);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "6");
	}
	
	public void testPrint004() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("System.out.println(1+3*2);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "7");
	}
	
	public void testInt001() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("int a;a=1+3*2-2;System.out.println(a);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "5");
	}
}
