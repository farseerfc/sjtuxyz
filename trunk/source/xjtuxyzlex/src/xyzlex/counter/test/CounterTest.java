package xyzlex.counter.test;

import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.StringBufferInputStream;
import java.io.StringReader;

import junit.framework.TestCase;
import xyzlex.counter.*;
import xyzlex.node.*;

public class CounterTest extends TestCase {

	public void testCounterString() {
		Counter counter=new Counter("");
		assertTrue(counter.getException()==null);
		assertTrue(counter.getTokenKinds().size()==0);
		
	}

	public void testGetCount() {
		Counter counter=new Counter("int");
		assertTrue(counter.getCount(TIntKey.class)==1);
	}

	public void testGetTokenKinds() {
		Counter counter=new Counter("int int");
		assertTrue(counter.getTokenKinds().contains(TIntKey.class));
	}
	
	public void testCountCommentOldStyle000(){
		Counter counter = new Counter(" /* comment /* aa */ int");
		assertTrue(counter.getCount(TCommentOldStyle.class)==1);
		assertTrue(counter.getCount(TIntKey.class)==1);
	}
	
	public void testCountReal000(){
		Counter counter = new Counter("2.131e-12 i");
		assertTrue(counter.getCount(TRealLiteral.class)==1);
		assertTrue(counter.getCount(TId.class)==1);
		assertTrue(counter.getCount(TBlanks.class)==1);
	}
	
	public void testCountReal001(){
		Counter counter = new Counter("2.131e-12i");
		assertTrue(counter.getCount(TRealLiteral.class)==1);
		assertTrue(counter.getCount(TId.class)==1);
		
	}
	
	public void testCountReal002(){
		Counter counter = new Counter("2.131e i");
		assertTrue(counter.getCount(TRealLiteral.class)==1);
		assertTrue(counter.getCount(TId.class)==2);
	}
	
	public void testCountReal003(){
		Counter counter = new Counter("2.131E-12");
		assertTrue(counter.getCount(TRealLiteral.class)==1);
	}

	public void testCountPoint000(){
		Counter counter = new Counter(". e22");
		assertTrue(counter.getCount(TPoint.class)==1);
		assertTrue(counter.getCount(TId.class)==1);
	}
	
	public void testCountId000(){
		Counter counter = new Counter("a.e");
		assertTrue(counter.getCount(TId.class) == 2);
		assertTrue(counter.getCount(TPoint.class) == 1);
	}
	
	public void testCountId001(){
		Counter counter = new Counter("ifelse");
		assertTrue(counter.getCount(TId.class) == 1);
//		assertTrue(counter.getCount(TPoint.class) == 1);
	}
	
	public void testCountCommentNewStyle000(){
		Counter counter = new Counter("//dfsdgds int click\n");
		assertTrue(counter.getCount(TCommentNewStyle.class)==1);
//		assertTrue(counter.getCount())
	}
	
	public void testCountCommentNewStyle001(){
		Counter counter = new Counter("//dfsdgds int click\n int");
		assertTrue(counter.getCount(TCommentNewStyle.class)==1);
		assertTrue(counter.getCount(TIntKey.class)==1);
//		assertTrue(counter.getCount())
	}
	
	public void testCountCommentNewStyle002(){
		Counter counter = new Counter("//dfsdgds int /* */click\n int");
		assertTrue(counter.getCount(TCommentOldStyle.class) == 0);
		assertTrue(counter.getCount(TCommentNewStyle.class)==1);
		assertTrue(counter.getCount(TIntKey.class)==1);
//		assertTrue(counter.getCount())
	}
	
	public void testCountIfKey(){
		Counter counter = new Counter("if if");
		assertTrue(counter.getCount(TIfKey.class) == 2);
	}
}
