/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.analysis;

import xyzlex.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseTIfKey(TIfKey node);
    void caseTElseKey(TElseKey node);
    void caseTWhileKey(TWhileKey node);
    void caseTIntKey(TIntKey node);
    void caseTRealKey(TRealKey node);
    void caseTPreKey(TPreKey node);
    void caseTPostKey(TPostKey node);
    void caseTClassKey(TClassKey node);
    void caseTExtendsKey(TExtendsKey node);
    void caseTPublicKey(TPublicKey node);
    void caseTStaticKey(TStaticKey node);
    void caseTVoidKey(TVoidKey node);
    void caseTMainKey(TMainKey node);
    void caseTNewKey(TNewKey node);
    void caseTThisKey(TThisKey node);
    void caseTBooleanKey(TBooleanKey node);
    void caseTReturnKey(TReturnKey node);
    void caseTId(TId node);
    void caseTPoint(TPoint node);
    void caseTRealLiteral(TRealLiteral node);
    void caseTIntegerLiteral(TIntegerLiteral node);
    void caseTBiOpr(TBiOpr node);
    void caseTLeftBrace(TLeftBrace node);
    void caseTRightBrace(TRightBrace node);
    void caseTLeftBracket(TLeftBracket node);
    void caseTRightBracket(TRightBracket node);
    void caseTLeftP(TLeftP node);
    void caseTRightP(TRightP node);
    void caseTSemicolon(TSemicolon node);
    void caseTColon(TColon node);
    void caseTComma(TComma node);
    void caseTBlanks(TBlanks node);
    void caseTComment(TComment node);
    void caseEOF(EOF node);
}
