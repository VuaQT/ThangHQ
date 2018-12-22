package ProblemConflict;

import java.util.ArrayList;


public class Employee {
    public float salary;
    public float ability;
    public ArrayList<Integer> skills;
    public Employee(float salary, float ability){
        this.salary = salary;
        this.ability = ability;
        this.skills = new ArrayList<Integer>();        
    }
    public void addSkill(String str){
        this.skills.add(Config.enCodeStr(str));
    }
    public float getValue() {
    	return ability * 0.5f - salary * 0.1f;
    }
}

