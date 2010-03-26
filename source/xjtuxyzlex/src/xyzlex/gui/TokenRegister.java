package xyzlex.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import xyzlex.node.*;

public class TokenRegister {
	private Map<Class<? extends Token>,TokenDiscriber> map;
	
	public TokenRegister(){
		map=new HashMap<Class<? extends Token>,TokenDiscriber>();
		addAllTokens();
	}
	
	public void add(TokenDiscriber dis)
	{
		if(map.containsKey(dis.getTokenClass()))
			throw new RuntimeException("Token Discriber already exist!");
		map.put(dis.getTokenClass(), dis);
	}
	
	public TokenDiscriber get(Class<? extends Token> tokenClass)
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

		add(new TokenDiscriber(TBlanks.class,				"Blank               ", normal));
		add(new TokenDiscriber(TClassKey.class,				"Keyword(class)      ", keyword));
		add(new TokenDiscriber(TColon.class,				"Colon(:)            ", normal));
		add(new TokenDiscriber(TCommentAnyN.class,			"Comment(new body)   ", comment));
		add(new TokenDiscriber(TCommentAnyO.class,			"Comment(old body)   ", comment));
		add(new TokenDiscriber(TCommentNewStyle.class,		"Comment(new begin)  ", comment));
		add(new TokenDiscriber(TCommentOldStyle.class,		"Comment(old begin)  ", comment));
		add(new TokenDiscriber(TCommentNewStyleEnd.class,	"Comment(new end)    ", comment));
		add(new TokenDiscriber(TCommentOldStyleEnd.class,	"Comment(old end)    ", comment));
		add(new TokenDiscriber(TElseKey.class,				"Keyword(else)       ", keyword));
		add(new TokenDiscriber(TExtendsKey.class,			"Keyword(extends)    ", keyword));
		add(new TokenDiscriber(TId.class,					"Identifier          ", id));
		add(new TokenDiscriber(TIfKey.class,				"Keyword(if)         ", keyword));
		add(new TokenDiscriber(TIntegerLiteral.class,		"Literal(integer)    ", literal));
		add(new TokenDiscriber(TIntKey.class,				"Keyword(int)        ", keyword));
		add(new TokenDiscriber(TLeftBrace.class,			"LeftBrace({)        ", normal));
		add(new TokenDiscriber(TLeftP.class,				"LeftParentheses(()  ", normal));
		add(new TokenDiscriber(TMainKey.class,				"Keyword(main)       ", keyword));
		add(new TokenDiscriber(TOperator.class,				"Operator            ", normal));
		add(new TokenDiscriber(TPoint.class,				"Point(.)            ", normal));
		add(new TokenDiscriber(TPostKey.class,				"Keyword(post)       ", keyword));
		add(new TokenDiscriber(TPreKey.class,				"Keyword(pre)        ", keyword));
		add(new TokenDiscriber(TPublicKey.class,			"Keyword(public)     ", keyword));
		add(new TokenDiscriber(TRealKey.class,				"Keyword(real)       ", keyword));
		add(new TokenDiscriber(TRealLiteral.class,			"Literal(real)       ", literal));
		add(new TokenDiscriber(TRightBrace.class,			"RightBrace(})       ", normal));
		add(new TokenDiscriber(TRightP.class,				"RightParentheses()) ", normal));
		add(new TokenDiscriber(TSemicolon.class,			"Semicolon(;)        ", normal));
		add(new TokenDiscriber(TSlashN.class,				"Comment(new slash)  ", comment));
		add(new TokenDiscriber(TSlashO.class,				"Comment(old slash)  ", comment));
		add(new TokenDiscriber(TStar.class,					"Comment(old star)   ", comment));
		add(new TokenDiscriber(TStaticKey.class,			"Keyword(static)     ", keyword));
		add(new TokenDiscriber(TVoidKey.class,				"Keyword(void)       ", keyword));
		add(new TokenDiscriber(TWhileKey.class,				"Keyword(while)      ", keyword));
		add(new TokenDiscriber(TNewKey.class,				"Keyword(new)        ", keyword));
		add(new TokenDiscriber(TThisKey.class,				"Keyword(this)       ", keyword));
		add(new TokenDiscriber(null,						"Error!              ", error));
	}
}
