package ProblemConflict;

import java.util.ArrayList;

public class Task {
    public int employeeNeeded;
    public ArrayList<Integer> skillsNeeded;
    public IntervalTime intervalTime;
    public Task(int sDay, int sMonth, int eDay, int eMonth){
        this.intervalTime = new IntervalTime(sDay,sMonth,eDay,eMonth);
        skillsNeeded = new ArrayList<Integer>();
        employeeNeeded = 0;
    }
    public void addSkillNeeded(int number, String skillName){
        for(int i=0;i<number;i++){
            skillsNeeded.add(Config.enCodeStr(skillName));
        }
        employeeNeeded += number;
    }
    public boolean acceptEmployee(Employee e, int ind) {
    	for(Integer skillEmployee : e.skills) {    		    
			if(this.skillsNeeded.get(ind) == skillEmployee) {
				return true;
			}    		
    	}
    	return false;
    }
    public boolean conflictTime(Task task2) {
    	return IntervalTime.isConfict(this.intervalTime, task2.intervalTime);
    }
}
