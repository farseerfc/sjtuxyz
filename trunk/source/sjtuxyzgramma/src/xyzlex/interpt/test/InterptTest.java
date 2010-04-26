package xyzlex.interpt.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import xyzlex.interpt.ClassDecl;
import xyzlex.interpt.Interpt;
import xyzlex.lexer.LexerException;
import xyzlex.parser.ParserException;

import junit.framework.TestCase;

public class InterptTest extends TestCase {
	public void testPrint001() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt("System.out.println(1);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1");
	}

	public void testPrint002() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt("System.out.println(1+2);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "3");
	}

	public void testPrint003() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt("System.out.println(3*2);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "6");
	}

	public void testPrint004() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt("System.out.println(1+3*2);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "7");
	}

	public void testPrint005() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt("System.out.println(3/3+3*3+3-2);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "11");
	}

	public void testInt001() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt("int a;a=1+3*2-2;System.out.println(a);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "5");
	}

	public void testIfElse001() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"int a;a=1;if(a>0){ a=0;}else{ a=2;}System.out.println(a);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "0");
	}

	public void testIfElse002() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"int a;a=1;if(a<0){ a=0;}else{ a=2;}System.out.println(a);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "2");
	}

	public void testReal001() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt("real a;a=0.0;System.out.println(a);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "0.0");
	}

	public void testReal002() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"real a;real b;a=1.1;b=1.1;System.out.println(a+b);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "2.2");
	}

	public void testReal003() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"real a;real b;a=1.1;b=1.1;System.out.println(a-b);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "0.0");
	}

	public void testReal004() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"real a;real b;a=1.1;b=2;System.out.println(a*b);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "2.2");
	}

	public void testReal005() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"real a;int b;int c;int d;a=1.1;b=2;c=3;d=4;"
						+ "System.out.println(a*b+a*c+c-a+d/b);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "9.4");
	}

	public void testReal006() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"real a;real b;a=1.1;b=1.1;System.out.println(a/b);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1.0");
	}

	public void testReal007() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"real a;real b;int c;int d;a=1.1;b=1.0;c=2;d=3;"
						+ "System.out.println(a/b+a/c+d/b-c/b+a+c+d+b-a+c-b+a-b);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "9.75");
	}

	public void testBoolean001() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"boolean a;int b;a=true;if(a){b=0;}else{b=1;}"
						+ "System.out.println(b);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "0");
	}

	public void testBoolean002() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"boolean a;int b;a=false;if(a){b=0;}else{b=1;}"
						+ "System.out.println(b);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1");
	}

	public void testNotOpr001() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"boolean a;int b;a=false;if(!a){b=0;}else{b=1;}"
						+ "System.out.println(b);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "0");
	}

	public void testNotOpr002() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"boolean a;int b;a=true;if(!a){b=0;}else{b=1;}"
						+ "System.out.println(b);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1");
	}

	public void testNew001() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"int [] a;a=new int[10];a[0]=1;System.out.println(a[0]);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1");
	}

	public void testNew002() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"real [] a;a=new real[10];a[0]=1.1;System.out.println(a[0]);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1.1");
	}

	public void testArrLength001() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"int [] a;int b;a=new int[10];b=a.length;System.out.println(b);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "10");
	}

	public void testArrLength002() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"real [] a;a=new real[10];System.out.println(a.length);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "10");
	}

	public void testAndOpr001() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"boolean a;boolean b;int c;a=true;b=true;if(a&&b){c=1;}else{c=2;}System.out.println(c);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1");
	}

	public void testAndOpr002() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"boolean a;boolean b;int c;a=true;b=false;if(a&&b){c=1;}else{c=2;}System.out.println(c);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "2");
	}

	public void testOrOpr001() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"boolean a;boolean b;int c;a=true;b=false;if(a||b){c=1;}else{c=2;}System.out.println(c);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "1");
	}

	public void testOrOpr002() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"boolean a;boolean b;int c;a=false;b=false;if(a||b){c=1;}else{c=2;}System.out.println(c);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "2");
	}

	public void testWhile001() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"int a;a=0;while(a<5){a=a+1;}System.out.println(a);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "5");
	}

	public void testWhile002() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"boolean a;int c;a=false;c=2;while(a){c=1;}System.out.println(c);");
		List<String> output = interpt.getOutputs();
		assertEquals(output.size(), 1);
		assertEquals(output.get(0), "2");
	}

	public void testClassDecl001() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"class Factorial {"
						+ "public static void main() {"
						+ " System.out.println(10); "
						+ "}}"
						+ "class F{int c;public int Fun(){pre:true;post:true;return 0;}}"
						+ "class Fac {" + "int a;int b;"
						+ "public int ComputeFac(int num) {"
						+ "   pre: num > 0;   post: num_aux>0;"
						+ "       int num_aux; num_aux = 1; "
						+ " return num_aux;   }}");
		HashMap<String, ClassDecl> map = interpt.getClassDecls();
		assertTrue(map.containsKey("Fac"));
		ClassDecl cd = map.get("Fac");
		assertTrue(cd.getVars().containsKey("a"));
		assertTrue(cd.getVars().containsKey("b"));
		assertTrue(cd.getMethods().containsKey("ComputeFac"));
		assertTrue(map.containsKey("F"));
		cd = map.get("F");
		assertTrue(cd.getVars().containsKey("c"));
		assertTrue(cd.getMethods().containsKey("Fun"));
		assertEquals(interpt.getOutputs().size(), 1);
		assertEquals(interpt.getOutputs().get(0), "10");
	}

	public void testClassDecl002() throws ParserException, LexerException,
			IOException {
		Interpt interpt = new Interpt(
				"class F {"
						+ "public static void main() {"
						+ " System.out.println((new A()).f(1)); "
						+ "}}"
						+ "class A{public int f(int a){pre:true;post:true;return a+1;}}"
						);
		
		assertEquals(interpt.getOutputs().size(), 1);
		assertEquals(interpt.getOutputs().get(0), "2");
	}
}
