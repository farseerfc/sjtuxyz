package xyzlex.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.sun.corba.se.impl.naming.cosnaming.TransientBindingIterator;

import xyzlex.node.*;

public class TokenRegister {
	private Map<Class<? extends Token>,TokenDescriber> map;
	
	public TokenRegister(){
		map=new HashMap<Class<? extends Token>,TokenDescriber>();
		addAllTokens();
	}
	
	public void add(TokenDescriber dis)
	{
		if(map.containsKey(dis.getTokenClass()))
			throw new RuntimeException("Token Discriber already exist!");
		map.put(dis.getTokenClass(), dis);
	}
	
	public TokenDescriber get(Class<? extends Token> tokenClass)
	{
		return map.get(tokenClass);
	}
	
	private void addAllTokens()
	{
		SimpleAttributeSet keyword=new SimpleAttributeSet();
		StyleConstants.setFontFamily(keyword, "Courier New");
		StyleConstants.setForeground(keyword,new Color(0x8b,0,0));
		StyleConstants.setBold(keyword, true);
		
		SimpleAttributeSet normal=new SimpleAttributeSet();
		StyleConstants.setFontFamily(normal, "Courier New");
		StyleConstants.setForeground(normal,Color.black);
		
		SimpleAttributeSet comment=new SimpleAttributeSet();
		StyleConstants.setFontFamily(comment, "Courier New");
		StyleConstants.setForeground(comment,Color.green);
		
		SimpleAttributeSet id=new SimpleAttributeSet();
		StyleConstants.setFontFamily(id, "Courier New");
		StyleConstants.setForeground(id,Color.blue);
		StyleConstants.setItalic(id, true);
		
		SimpleAttributeSet literal=new SimpleAttributeSet();
		StyleConstants.setFontFamily(literal, "Courier New");
		StyleConstants.setForeground(literal,Color.black);
		
		SimpleAttributeSet error=new SimpleAttributeSet();
		StyleConstants.setFontFamily(error, "Courier New");
		StyleConstants.setForeground(error,Color.red);

		add(new TokenDescriber(TAndOpr.class,			"AndOpr(&&)          ", normal));
		add(new TokenDescriber(TAssignOpr.class,		"AssignOpr(=)        ", normal));
		add(new TokenDescriber(TBlanks.class,			"Blank               ", normal));
		add(new TokenDescriber(TBoolean.class,			"Keyword(boolean)    ", keyword));
		add(new TokenDescriber(TClass.class,			"Keyword(class)      ", keyword));
		add(new TokenDescriber(TColon.class,			"Colon(:)            ", normal));
		add(new TokenDescriber(TComma.class,			"Comma(,)            ", normal));
		add(new TokenDescriber(TComment.class,			"Comment(new body)   ", comment));
		add(new TokenDescriber(TDivideOpr.class,		"DivideOpr(/)        ", normal));
		add(new TokenDescriber(TElse.class,				"Keyword(else)       ", keyword));
		add(new TokenDescriber(TExtends.class,			"Keyword(extends)    ", keyword));
		add(new TokenDescriber(TFalse.class,			"Keyword(false)      ", keyword));
		add(new TokenDescriber(TGreaterOpr.class,		"GreaterOpr(>)       ", normal));
		add(new TokenDescriber(TId.class,				"Identifier          ", id));
		add(new TokenDescriber(TIf.class,				"Keyword(if)         ", keyword));
		add(new TokenDescriber(TInt.class,				"Keyword(int)        ", keyword));
		add(new TokenDescriber(TIntLt.class,		    "Literal(integer)    ", literal));
		add(new TokenDescriber(TLB.class,			    "LeftBrace({)        ", normal));
		add(new TokenDescriber(TLength.class,			"Keyword(length)     ", keyword));
		add(new TokenDescriber(TLessOpr.class,			"LessOpr(<)          ", normal));
		add(new TokenDescriber(TLP.class,				"LeftParentheses(()  ", normal));
		add(new TokenDescriber(TLSq.class,				"LeftBracket([)      ", normal));
		add(new TokenDescriber(TMain.class,				"Keyword(main)       ", keyword));
		add(new TokenDescriber(TMinusOpr.class,			"MinusOpr(-)         ", normal));
		add(new TokenDescriber(TMultiplyOpr.class,		"MultiplyOpr(*)      ", normal));
		add(new TokenDescriber(TNew.class,				"Keyword(new)        ", keyword));
		add(new TokenDescriber(TNotOpr.class,			"NotOpr(!)           ", normal));
		add(new TokenDescriber(TOrOpr.class,			"OrOpr(||)           ", normal));
		add(new TokenDescriber(TPlusOpr.class,			"PlusOpr(+)          ", normal));
		add(new TokenDescriber(TPoint.class,			"Point(.)            ", normal));
		add(new TokenDescriber(TPost.class,				"Keyword(post)       ", keyword));
		add(new TokenDescriber(TPre.class,				"Keyword(pre)        ", keyword));
		add(new TokenDescriber(TPrint.class,			"Keyword(print)      ", keyword));
		add(new TokenDescriber(TPublic.class,			"Keyword(public)     ", keyword));
		add(new TokenDescriber(TRB.class,				"RightBrace(})       ", normal));
		add(new TokenDescriber(TReal.class,				"Keyword(real)       ", keyword));
		add(new TokenDescriber(TRealLt.class,			"Literal(real)       ", literal));
		add(new TokenDescriber(TReturn.class,			"Keyword(return)     ", keyword));
		add(new TokenDescriber(TRSq.class,				"RightBracket(])     ", normal));
		add(new TokenDescriber(TRP.class,				"RightParentheses()) ", normal));
		add(new TokenDescriber(TSemi.class,				"Semicolon(;)        ", normal));
		add(new TokenDescriber(TStatic.class,			"Keyword(static)     ", keyword));
		add(new TokenDescriber(TTrue.class,				"Keyword(true)       ", keyword));
		add(new TokenDescriber(TVoid.class,				"Keyword(void)       ", keyword));
		add(new TokenDescriber(TWhile.class,			"Keyword(while)      ", keyword));
		add(new TokenDescriber(TThis.class,				"Keyword(this)       ", keyword));
		add(new TokenDescriber(null,					"Error!              ", error));
	}
}
