/* This file was generated by SableCC (http://www.sablecc.org/). */

package xyzlex.analysis;

import xyzlex.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseAClassProgram(AClassProgram node);
    void caseABodyProgram(ABodyProgram node);
    void caseAMainClass(AMainClass node);
    void caseAClassDecl(AClassDecl node);
    void caseAExtendsClause(AExtendsClause node);
    void caseAMethodDecl(AMethodDecl node);
    void caseAArg(AArg node);
    void caseAPreDecl(APreDecl node);
    void caseAPostDecl(APostDecl node);
    void caseABody(ABody node);
    void caseAVarDecl(AVarDecl node);
    void caseAIntArrayType(AIntArrayType node);
    void caseARealArrayType(ARealArrayType node);
    void caseABooleanType(ABooleanType node);
    void caseAIntType(AIntType node);
    void caseARealType(ARealType node);
    void caseAClassType(AClassType node);
    void caseABlockState(ABlockState node);
    void caseAIfState(AIfState node);
    void caseAWhileState(AWhileState node);
    void caseAPrintState(APrintState node);
    void caseAAssignState(AAssignState node);
    void caseAOrOprExp(AOrOprExp node);
    void caseAAndOprExp(AAndOprExp node);
    void caseANotOprExp(ANotOprExp node);
    void caseAGreaterOprExp(AGreaterOprExp node);
    void caseALessOprExp(ALessOprExp node);
    void caseAPlusOprExp(APlusOprExp node);
    void caseAMinusOprExp(AMinusOprExp node);
    void caseAMultiplyOprExp(AMultiplyOprExp node);
    void caseADivideOprExp(ADivideOprExp node);
    void caseAIntLtExp(AIntLtExp node);
    void caseARealLtExp(ARealLtExp node);
    void caseATrueLtExp(ATrueLtExp node);
    void caseAFalseLtExp(AFalseLtExp node);
    void caseAVarExp(AVarExp node);
    void caseAThisExp(AThisExp node);
    void caseASubExpExp(ASubExpExp node);
    void caseAArraySubExp(AArraySubExp node);
    void caseAArrayLengthExp(AArrayLengthExp node);
    void caseAMemFuncExp(AMemFuncExp node);
    void caseAFieldExp(AFieldExp node);
    void caseANewIntArExp(ANewIntArExp node);
    void caseANewRealArExp(ANewRealArExp node);
    void caseANewObjectExp(ANewObjectExp node);

    void caseTIf(TIf node);
    void caseTElse(TElse node);
    void caseTWhile(TWhile node);
    void caseTInt(TInt node);
    void caseTReal(TReal node);
    void caseTPre(TPre node);
    void caseTPost(TPost node);
    void caseTClassKey(TClassKey node);
    void caseTExtends(TExtends node);
    void caseTPublic(TPublic node);
    void caseTStatic(TStatic node);
    void caseTVoid(TVoid node);
    void caseTMain(TMain node);
    void caseTNew(TNew node);
    void caseTThis(TThis node);
    void caseTBoolean(TBoolean node);
    void caseTReturn(TReturn node);
    void caseTLength(TLength node);
    void caseTTrue(TTrue node);
    void caseTFalse(TFalse node);
    void caseTPrint(TPrint node);
    void caseTId(TId node);
    void caseTPoint(TPoint node);
    void caseTRealLt(TRealLt node);
    void caseTIntLt(TIntLt node);
    void caseTMultiplyOpr(TMultiplyOpr node);
    void caseTDivideOpr(TDivideOpr node);
    void caseTPlusOpr(TPlusOpr node);
    void caseTMinusOpr(TMinusOpr node);
    void caseTGreaterOpr(TGreaterOpr node);
    void caseTLessOpr(TLessOpr node);
    void caseTAndOpr(TAndOpr node);
    void caseTOrOpr(TOrOpr node);
    void caseTNotOpr(TNotOpr node);
    void caseTAssignOpr(TAssignOpr node);
    void caseTLB(TLB node);
    void caseTRB(TRB node);
    void caseTLSq(TLSq node);
    void caseTRSq(TRSq node);
    void caseTLP(TLP node);
    void caseTRP(TRP node);
    void caseTSemi(TSemi node);
    void caseTColon(TColon node);
    void caseTComma(TComma node);
    void caseTBlanks(TBlanks node);
    void caseTComment(TComment node);
    void caseEOF(EOF node);
}
