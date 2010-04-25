package xyzlex.interpt;

import xyzlex.node.*;

public class TypeNodes {
	public final static PType aRealType = new ARealType(new TReal());
	public final static PType aIntType = new AIntType(new TInt());
	public final static PType aBooleanType = new ABooleanType(new TBoolean());
	public final static PType aRealArrayType = new ARealArrayType(new TReal(),
			new TLSq(), new TRSq());
	public final static PType aIntArrayType = new AIntArrayType(new TInt(),
			new TLSq(), new TRSq());
}
