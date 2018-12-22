package ProblemConflict;

public class IntervalTime {
	public int startTime;
	public int finishTime;
	public IntervalTime(int sDay, int sMonth, int eDay, int eMonth) {
		this.startTime = sMonth*12 + sDay;
		this.finishTime = eMonth*12 + eDay;
	}
	public static boolean isConfict(IntervalTime it1, IntervalTime it2){
		if((it1.finishTime-it2.startTime)*(it1.finishTime-it2.finishTime) <=0)
			return true;
		if((it2.finishTime-it1.startTime)*(it2.finishTime-it1.finishTime) <=0)
			return true;
		return false;
	}
}
