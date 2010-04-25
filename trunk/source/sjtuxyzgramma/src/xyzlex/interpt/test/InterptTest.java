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
	
	public void testPrint005() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("System.out.println(3/3+3*3+3-2);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "11");
	}
	
	public void testInt001() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("int a;a=1+3*2-2;System.out.println(a);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "5");
	}
	
	public void testIfElse001() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("int a;a=1;if(a>0){ a=0;}else{ a=2;}System.out.println(a);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "0");
	}
	
	public void testIfElse002() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("int a;a=1;if(a<0){ a=0;}else{ a=2;}System.out.println(a);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "2");
	}
	
	public void testReal001() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("real a;a=0.0;System.out.println(a);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "0.0");
	}
	
	public void testReal002() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("real a;real b;a=1.1;b=1.1;System.out.println(a+b);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "2.2");
	}
	
	public void testReal003() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("real a;real b;a=1.1;b=2;System.out.println(a*b);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "2.2");
	}
	
	public void testReal004() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("real a;int b;int c;int d;a=1.1;b=2;c=3;d=4;System.out.println(a*b+a*c+c-a+d/b);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "9.4");
	}
	
	public void testReal005() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("real a;real b;a=1.1;b=1.1;System.out.println(a/b);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1.0");
	}
	
	public void testReal006() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("real a;real b;int c;int d;a=1.1;b=1.0;c=2;d=3;System.out.println(a/b+a/c+d/b-c/b+a+c+d+b-a+c-b+a-b);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "9.75");
	}
	
	public void testBoolean001() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("boolean a;int b;a=true;if(a){b=0;}else{b=1;}System.out.println(b);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "0");
	}
	
	public void testBoolean002() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("boolean a;int b;a=false;if(a){b=0;}else{b=1;}System.out.println(b);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1");
	}
	
	public void testNotOpr001() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("boolean a;int b;a=false;if(!a){b=0;}else{b=1;}System.out.println(b);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "0");
	}
	
	public void testNotOpr002() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("boolean a;int b;a=true;if(!a){b=0;}else{b=1;}System.out.println(b);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1");
	}
	
	public void testNew001() throws ParserException, LexerException, IOException{
		Interpt interpt=new Interpt("int [] a;a=new int[10];a[0]=1;System.out.println(a[0]);");
		List<String> output=interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1");
	}
	
}
