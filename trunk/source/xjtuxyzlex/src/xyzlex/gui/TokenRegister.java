package xyzlex.gui;

import java.util.HashMap;
import java.util.Map;

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
		add(new TokenDiscriber(TBlanks.class,				"Blank",				"#&000000"));
		add(new TokenDiscriber(TClassKey.class,				"Keyword(class)",		"#&003300"));
		add(new TokenDiscriber(TColon.class,				"Colon(:)",				"#&000000"));
		add(new TokenDiscriber(TCommentAnyN.class,			"Comment(new body)",	"#&000000"));
		add(new TokenDiscriber(TCommentAnyO.class,			"Comment(old body)",	"#&000000"));
		add(new TokenDiscriber(TCommentNewStyle.class,		"Comment(new begin)",	"#&000000"));
		add(new TokenDiscriber(TCommentOldStyle.class,		"Comment(old begin)",	"#&000000"));
		add(new TokenDiscriber(TCommentNewStyleEnd.class,	"Comment(new end)",		"#&000000"));
		add(new TokenDiscriber(TCommentOldStyleEnd.class,	"Comment(old end)",		"#&000000"));
		add(new TokenDiscriber(TElseKey.class,				"Keyword(else)",		"#&003300"));
		add(new TokenDiscriber(TExtendsKey.class,			"Keyword(extends)",		"#&003300"));
		add(new TokenDiscriber(TId.class,					"Identifier",			"#&000000"));
		add(new TokenDiscriber(TIfKey.class,				"Keyword(if)",			"#&003300"));
		add(new TokenDiscriber(TIntegerLiteral.class,		"Literal(integer)",		"#&000000"));
		add(new TokenDiscriber(TIntKey.class,				"Keyword(int)",			"#&003300"));
		add(new TokenDiscriber(TLeftBrace.class,			"LeftBrace({)",			"#&000000"));
		add(new TokenDiscriber(TLeftP.class,				"LeftParentheses(()",	"#&000000"));
		add(new TokenDiscriber(TMainKey.class,				"Keyword(main)",		"#&003300"));
		add(new TokenDiscriber(TOperator.class,				"Operator",				"#&000000"));
		add(new TokenDiscriber(TPoint.class,				"Point(.)",				"#&000000"));
		add(new TokenDiscriber(TPostKey.class,				"Keyword(post)",		"#&003300"));
		add(new TokenDiscriber(TPreKey.class,				"Keyword(pre)",			"#&003300"));
		add(new TokenDiscriber(TPublicKey.class,			"Keyword(public)",		"#&003300"));
		add(new TokenDiscriber(TRealKey.class,				"Keyword(real)",		"#&003300"));
		add(new TokenDiscriber(TRealLiteral.class,			"Literal(real)",		"#&000000"));
		add(new TokenDiscriber(TRightBrace.class,			"RightBrace(})",		"#&000000"));
		add(new TokenDiscriber(TRightP.class,				"RightParentheses())",	"#&000000"));
		add(new TokenDiscriber(TSemicolon.class,			"Semicolon(;))",		"#&000000"));
		add(new TokenDiscriber(TSlashN.class,				"Comment(new slash)",	"#&000000"));
		add(new TokenDiscriber(TSlashO.class,				"Comment(old slash)",	"#&000000"));
		add(new TokenDiscriber(TStar.class,				"Comment(old star)",	"#&000000"));
	}
}
