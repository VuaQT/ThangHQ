package ProblemConflict;
import java.util.ArrayList;
import java.util.Random;

import org.moeaframework.problem.AbstractProblem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.BinaryVariable;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;

public class Problem extends AbstractProblem {
    ArrayList<Task> tasks;
    ArrayList<Employee> employees;    
	boolean[][] accept = new boolean[Config.nVariable][Config.nEmployee];
	ArrayList<Integer> acceptList[] = new ArrayList[Config.nVariable];
	int[] mapPosToTask = new int[Config.nVariable];
	public Problem() {
		super(Config.nVariable, Config.nObjective, Config.nConstraint);	
		this.initEmployee();
		this.initTasks();		
		if(this.tasks.size() < Config.nTask || this.employees.size()<Config.nEmployee) {
			System.out.println("WARNING : constants dont match input data!!");
		}
		int ind = 0;
        for(int i=0;i<Config.nTask;i++) {
        	Task task  = tasks.get(i);        	
        	for(int k=0;k<task.employeeNeeded;k++) {
        		String s = "";
        		acceptList[ind] = new ArrayList<Integer>();
        		for(int j=0;j<Config.nEmployee;j++) {        		
        			if(task.acceptEmployee(employees.get(j),k)) {        				
	        			s += "1";
	        			accept[ind][j] = true;	 
	        			acceptList[ind].add(j);
	        		}	else	{
	        			s+= "0";
	        			accept[ind][j] = false;
	        		}        			
        		}
        		ind++;
        		System.out.println(s);
        	}   
        	System.out.println();
        }        
        System.out.println("sum " +ind);
        ind = 0;
        for(int i=0;i<Config.nTask;i++) {
        	for(int j=0;j<tasks.get(i).employeeNeeded;j++) {
        		this.mapPosToTask[ind] = i;
        		ind++;
        	}
        }
        if(ind != Config.nVariable) {
        	System.out.println("WARNING : constants dont match input data??");
        }
	}

	@Override
	public void evaluate(Solution solution) {				
		int constraint = 0;	
		float objective0 = 0; // total value
		float objective1 = 0; // variance
		
		float value[] = new float[Config.nTask];
		int indices[] = new int[Config.nVariable];			
		
		for(int i=0;i<Config.nVariable;i++) {
			indices[i] = (int) EncodingUtils.getReal(solution.getVariable(i));			
			indices[i] = indices[i]%acceptList[i].size();
			//System.out.println(indices[i]);
			//System.out.println(EncodingUtils.getReal(solution.getVariable(i)));
			float employeeValue = employees.get(acceptList[i].get(indices[i])).getValue();
			objective0 +=  employeeValue;
			int indTask = this.mapPosToTask[i];
			value[indTask] += employeeValue;
		}				
		for(int i=0;i<Config.nVariable;i++) {
			int indTaski = this.mapPosToTask[i];
			//System.out.println("indTaski : " + indTaski);
			for(int j=0;j<i;j++){	
				if(acceptList[i].get(indices[i])==acceptList[j].get(indices[j])) {
					// two task use the same employee
					int indTaskj = this.mapPosToTask[j];
					//System.out.println("indTaskj : " + indTaskj);					
					if(tasks.get(indTaski).conflictTime(tasks.get(indTaskj))) {						
						constraint++;
					}	
				}				
			}
		}
		solution.setObjective(0, -objective0);
		objective1 = variance(value, Config.nTask);
		solution.setObjective(1, objective1);
		
		solution.setConstraint(0, constraint);						
	}

	@Override
	public Solution newSolution() {
		Random rand = new Random();			
		Solution solution = new Solution(Config.nVariable, Config.nObjective, Config.nConstraint);
		for (int i = 0; i < Config.nVariable; i++) {
			int ind = rand.nextInt(acceptList[i].size());
			RealVariable r = EncodingUtils.newReal(0,1000);
			EncodingUtils.setReal(r, ind);			
			solution.setVariable(i, r);			
			//System.out.println(acceptList[i].size() + " " + r.getValue() + " " + (int) EncodingUtils.getReal(solution.getVariable(i)));			
		}
		return solution;
	}

	float variance(float a[], int n) {		
		// Compute mean (average
		// of elements)		
		float sum = 0;

		for (int i = 0; i < n; i++)
			sum += a[i];
		float mean = (float) sum / (float) n;

		// Compute sum squared
		// differences with mean.
		float sqDiff = 0;
		for (int i = 0; i < n; i++)
			sqDiff += (a[i] - mean) * (a[i] - mean);

		return (float) sqDiff / n;
	}

	


	private void initTasks() {
		this.tasks = new ArrayList<Task>();
        Task task;
	    // task 1
		task = new Task(1, 6, 15, 6);
		task.addSkillNeeded(1, "PM");
        task.addSkillNeeded(1, "LEADER");
        task.addSkillNeeded(1, "PHP");
        task.addSkillNeeded(1, "CSS");
        task.addSkillNeeded(2, "TEST");
		this.tasks.add(task);

        // task 2
        task = new Task(16, 6, 1, 8);
        task.addSkillNeeded(1, "LEADER");
        task.addSkillNeeded(1, "PHP");
        task.addSkillNeeded(2, "CSS");
        task.addSkillNeeded(1, "TEST");
        this.tasks.add(task);

        // task 3
        task = new Task(16, 6, 1, 8);
        task.addSkillNeeded(1, "LEADER");
        task.addSkillNeeded(3, "PHP");
        task.addSkillNeeded(2, "CSS");
        task.addSkillNeeded(1, "TEST");
        this.tasks.add(task);

        // task 4
        task = new Task(16, 6, 1, 8);
        task.addSkillNeeded(1, "LEADER");
        task.addSkillNeeded(2, "PHP");
        task.addSkillNeeded(2, "CSS");
        task.addSkillNeeded(1, "TEST");
        this.tasks.add(task);

        // task 5
        task = new Task(2, 8, 19, 8);
        task.addSkillNeeded(1, "PM");
        task.addSkillNeeded(1, "LEADER");
        task.addSkillNeeded(1, "PHP");
        task.addSkillNeeded(1, "CSS");
        task.addSkillNeeded(1, "TEST");
        this.tasks.add(task);

        // task 6
        task = new Task(2, 8, 15, 10);
        task.addSkillNeeded(1, "LEADER");
        task.addSkillNeeded(2, "PHP");
        task.addSkillNeeded(2, "CSS");
        task.addSkillNeeded(1, "TEST");
        this.tasks.add(task);

        // task 7
        task = new Task(2, 8, 26, 10);
        task.addSkillNeeded(1, "LEADER");
        task.addSkillNeeded(2, "PHP");
        task.addSkillNeeded(3, "CSS");
        task.addSkillNeeded(1, "TEST");
        this.tasks.add(task);

        // task 8
        task = new Task(2, 8, 26, 10);
        task.addSkillNeeded(1, "LEADER");
        task.addSkillNeeded(1, "PHP");
        task.addSkillNeeded(3, "CSS");
        task.addSkillNeeded(3, "TEST");
        this.tasks.add(task);

        // task 9
        task = new Task(16, 10, 22, 12);
        task.addSkillNeeded(1, "LEADER");
        task.addSkillNeeded(2, "PHP");
        task.addSkillNeeded(2, "CSS");
        task.addSkillNeeded(1, "TEST AUTOMATION");
        this.tasks.add(task);

        // task 9
        task = new Task(11, 10, 9, 12);
        task.addSkillNeeded(1, "LEADER");
        task.addSkillNeeded(1, "PHP");
        task.addSkillNeeded(2, "CSS");
        task.addSkillNeeded(1, "TEST AUTOMATION");
        this.tasks.add(task);

        // task 9
        task = new Task(27, 10, 30, 12);
        task.addSkillNeeded(1, "LEADER");
        task.addSkillNeeded(2, "PHP");
        task.addSkillNeeded(2, "CSS");
        task.addSkillNeeded(2, "TEST AUTOMATION");
        this.tasks.add(task);
    }

    private void initEmployee(){
    	this.employees = new ArrayList<Employee>();
        Employee employee;

        // employee 1
        employee = new Employee(25,1);
        employee.addSkill("PM");
        employee.addSkill("TEST");
        employee.addSkill("TEST AUTOMATION");
        employee.addSkill("PHP");
        employee.addSkill("JQUERY");
        employee.addSkill("NOTE JS");
        employee.addSkill("CSS");
        employee.addSkill("JAVA");
        employee.addSkill("C#");
        employee.addSkill("ANDROID");
        this.employees.add(employee);
        
        employee = new Employee(17,1);
        employee.addSkill("PM");
        employee.addSkill("LEADER");
        employee.addSkill("PHP");
        employee.addSkill("JQUERY");
        employee.addSkill("JAVA");
        employee.addSkill("NODEJS");
        employee.addSkill("CSS");
        employee.addSkill("C#");
        this.employees.add(employee);

        employee = new Employee(15,1);
        employee.addSkill("PM");
        employee.addSkill("LEADER");
        employee.addSkill("PHP");
        employee.addSkill("JAVA");
        employee.addSkill("C#");
        this.employees.add(employee);

        employee = new Employee(12,1);
        employee.addSkill("LEADER");
        employee.addSkill("PHP");
        employee.addSkill("TEST");
        employee.addSkill("TEST AUTOMATION");
        this.employees.add(employee);

        employee = new Employee(9.5f,2);
        employee.addSkill("PHP");
        employee.addSkill("C#");
        this.employees.add(employee);

        employee = new Employee(9.5f,3);
        employee.addSkill("PHP");
        employee.addSkill("JAVA");
        employee.addSkill("C#");
        this.employees.add(employee);

        employee = new Employee(9.5f,3);
        employee.addSkill("PHP");
        employee.addSkill("JAVA");
        this.employees.add(employee);

        employee = new Employee(9,3);
        employee.addSkill("PHP");
        this.employees.add(employee);

        employee = new Employee(9,3);
        employee.addSkill("PHP");
        employee.addSkill("JAVA");
        this.employees.add(employee);

        employee = new Employee(9,4);
        employee.addSkill("PHP");
        employee.addSkill("JAVA");
        this.employees.add(employee);

        employee = new Employee(9,4);
        employee.addSkill("PHP");
        employee.addSkill("JAVA");
        employee.addSkill("ANDROID");
        this.employees.add(employee);

        employee = new Employee(8.5f,3);
        employee.addSkill("TEST");
        this.employees.add(employee);

        employee = new Employee(7.5f,3);
        employee.addSkill("TEST");
        this.employees.add(employee);

        employee = new Employee(5,7);
        employee.addSkill("PHP");
        employee.addSkill("JAVA");
        employee.addSkill("RUBY");
        this.employees.add(employee);

        employee = new Employee(5,7);
        employee.addSkill("PHP");
        employee.addSkill("JAVA");
        employee.addSkill("PYTHON");
        this.employees.add(employee);

        employee = new Employee(5,6);
        employee.addSkill("TEST");
        this.employees.add(employee);

        employee = new Employee(5,6);
        employee.addSkill("TEST");
        this.employees.add(employee);

        employee = new Employee(5,7);
        employee.addSkill("CSS");
        employee.addSkill("PHP");
        employee.addSkill("JAVA");
        employee.addSkill("C#");
        this.employees.add(employee);

        employee = new Employee(5,7);
        employee.addSkill("RUBY");
        employee.addSkill("PHP");
        employee.addSkill("PYTHON");
        employee.addSkill("C#");
        this.employees.add(employee);

        employee = new Employee(4,8);
        employee.addSkill("PHP");
        employee.addSkill("JAVA");
        this.employees.add(employee);

        employee = new Employee(4,8);
        employee.addSkill("PHP");
        this.employees.add(employee);

        employee = new Employee(4,8);
        employee.addSkill("PHP");
        this.employees.add(employee);

        employee = new Employee(4,8);
        employee.addSkill("PHP");
        employee.addSkill("JAVA");
        this.employees.add(employee);

        employee = new Employee(3,9);
        employee.addSkill("PHP");
        this.employees.add(employee);

        employee = new Employee(2.5f,10);
        employee.addSkill("PHP");
        this.employees.add(employee);

        employee = new Employee(2,10);
        employee.addSkill("TEST");
        this.employees.add(employee);

        employee = new Employee(2,10);
        employee.addSkill("TEST");
        this.employees.add(employee);
    }
}