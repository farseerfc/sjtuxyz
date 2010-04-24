package xyzlex.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

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

		add(new TokenDescriber(TBlanks.class,				"Blank               ", normal));
		add(new TokenDescriber(TBooleanKey.class,			"Keyword(boolean)    ", keyword));
		add(new TokenDescriber(TClassKey.class,				"Keyword(class)      ", keyword));
		add(new TokenDescriber(TColon.class,				"Colon(:)            ", normal));
		add(new TokenDescriber(TComma.class,				"Comma(,)            ", normal));
		add(new TokenDescriber(TCommentAnyN.class,			"Comment(new body)   ", comment));
		add(new TokenDescriber(TCommentAnyO.class,			"Comment(old body)   ", comment));
		add(new TokenDescriber(TCommentNewStyle.class,		"Comment(new begin)  ", comment));
		add(new TokenDescriber(TCommentOldStyle.class,		"Comment(old begin)  ", comment));
		add(new TokenDescriber(TCommentNewStyleEnd.class,	"Comment(new end)    ", comment));
		add(new TokenDescriber(TCommentOldStyleEnd.class,	"Comment(old end)    ", comment));
		add(new TokenDescriber(TElseKey.class,				"Keyword(else)       ", keyword));
		add(new TokenDescriber(TExtendsKey.class,			"Keyword(extends)    ", keyword));
		add(new TokenDescriber(TId.class,					"Identifier          ", id));
		add(new TokenDescriber(TIfKey.class,				"Keyword(if)         ", keyword));
		add(new TokenDescriber(TIntegerLiteral.class,		"Literal(integer)    ", literal));
		add(new TokenDescriber(TIntKey.class,				"Keyword(int)        ", keyword));
		add(new TokenDescriber(TLeftBrace.class,			"LeftBrace({)        ", normal));
		add(new TokenDescriber(TLeftBracket.class,			"LeftBracket([)      ", normal));
		add(new TokenDescriber(TLeftP.class,				"LeftParentheses(()  ", normal));
		add(new TokenDescriber(TMainKey.class,				"Keyword(main)       ", keyword));
		add(new TokenDescriber(TOperator.class,				"Operator            ", normal));
		add(new TokenDescriber(TPoint.class,				"Point(.)            ", normal));
		add(new TokenDescriber(TPostKey.class,				"Keyword(post)       ", keyword));
		add(new TokenDescriber(TPreKey.class,				"Keyword(pre)        ", keyword));
		add(new TokenDescriber(TPublicKey.class,			"Keyword(public)     ", keyword));
		add(new TokenDescriber(TRealKey.class,				"Keyword(real)       ", keyword));
		add(new TokenDescriber(TRealLiteral.class,			"Literal(real)       ", literal));
		add(new TokenDescriber(TReturnKey.class,			"Keyword(return)     ", keyword));
		add(new TokenDescriber(TRightBrace.class,			"RightBrace(})       ", normal));
		add(new TokenDescriber(TRightBracket.class,			"RightBracket(])     ", normal));
		add(new TokenDescriber(TRightP.class,				"RightParentheses()) ", normal));
		add(new TokenDescriber(TSemicolon.class,			"Semicolon(;)        ", normal));
		add(new TokenDescriber(TSlashN.class,				"Comment(new slash)  ", comment));
		add(new TokenDescriber(TSlashO.class,				"Comment(old slash)  ", comment));
		add(new TokenDescriber(TStar.class,					"Comment(old star)   ", comment));
		add(new TokenDescriber(TStaticKey.class,			"Keyword(static)     ", keyword));
		add(new TokenDescriber(TVoidKey.class,				"Keyword(void)       ", keyword));
		add(new TokenDescriber(TWhileKey.class,				"Keyword(while)      ", keyword));
		add(new TokenDescriber(TNewKey.class,				"Keyword(new)        ", keyword));
		add(new TokenDescriber(TThisKey.class,				"Keyword(this)       ", keyword));
		add(new TokenDescriber(null,						"Error!              ", error));
	}
}
