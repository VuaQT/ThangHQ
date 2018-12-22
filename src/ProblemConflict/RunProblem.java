package ProblemConflict;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

public class RunProblem {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		NondominatedPopulation result = new Executor().withAlgorithm("NSGAIII").withProblemClass(Problem.class)
				.withMaxEvaluations(100000).run();
		Solution optSolution = null;
		boolean mark = true;
		double bestSolution = 1000000000f;
		for (Solution solution : result) {
			//if(solution.violatesConstraints()) continue;
			if (mark == false) {
				if (Config.A * solution.getObjective(0) + Config.B * Config.nTask * solution.getObjective(1) < bestSolution ){
					optSolution = solution;										
				}
			} else {
				optSolution = solution;
				mark = false;
			}
			bestSolution = Config.A * solution.getObjective(0) + Config.B * Config.nTask * solution.getObjective(1) ;
			//showSolution(solution);
		}
		System.out.println("Opt solution " + (-bestSolution));
		showSolution(optSolution);
		
		long end = System.currentTimeMillis();		
		System.out.print("Execution time is " + (end - start) + "(ms)");
	}

	public static void showSolution(Solution solution) {
		if (solution == null)
			System.out.println("NULL");
		String s = "";
		for (int i = 0; i < Config.nVariable; i++) {			
			int employeeInd = (int) EncodingUtils.getReal(solution.getVariable(i));						
			s += Integer.toString(employeeInd) + " ";			
		}
		System.out.println(s);
		System.out.format(" : totalValue " + (-solution.getObjective(0)) + " variance :" + solution.getObjective(1));		
		System.out.println();
	}
}
