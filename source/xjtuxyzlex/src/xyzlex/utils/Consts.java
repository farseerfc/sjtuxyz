package xyzlex.utils;

import java.util.HashMap;
import java.util.Map;


public class Consts {

	public static int InputStreamBufferSize=0;
	
	private static Map<Integer,Integer> consts;
	static{
		consts=new HashMap<Integer,Integer>();
		consts.put(InputStreamBufferSize, 65536);
	}
	
	
	public static int getConst(Integer constEnum){
		return consts.get(constEnum);
	}
}
