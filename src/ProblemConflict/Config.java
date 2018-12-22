package ProblemConflict;
import java.util.HashMap;
public class Config {
	public static float A = 1;
	public static float B = 1;	
	public static int nVariable = 68;
	public static int nObjective = 2;
	public static int nConstraint = 1;
	public static int nEmployee = 13+14;
	public static int nTask = 11;
	public static HashMap<String, Integer> mapStrToInt = new HashMap<String,Integer>();
	public static int counter = 0;
	public static int enCodeStr(String str){
		if(mapStrToInt.containsKey(str)==true) {
			return mapStrToInt.get(str);
		}
		counter ++ ;
		mapStrToInt.put(str, counter);
		return counter;
	}
}
