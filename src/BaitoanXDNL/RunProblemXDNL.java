package BaitoanXDNL;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import org.moeaframework.analysis.plot.Plot;

//https://github.com/MOEAFramework/MOEAFramework/issues/119

//GDE3 is not supported because it can only work on real-valued problems (knapsack uses a binary string encoding).
//IBEA will not work on the Knapsack problem because this implementation of IBEA does not support constraints.


// NSGAII cannot run with maximum evaluation = 100000
//https://github.com/MOEAFramework/MOEAFramework/issues/136

//Yea, that's a bug somewhere. It's usually (1) the Comparator used for sorting violates the interface's requirements 
//(a lot of Java code has this issue since the old merge sort worked fine but broke when they switched to TimSort), 
//or (2) the values (e.g., objectives) are being modified while sorting is ongoing.


public class RunProblemXDNL {
	// private boolean compare(Solution s1, Solution s2) {
	// return s1.getObjective(3) < s2.getObjective(3);
	// }
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		NondominatedPopulation result = new Executor().withAlgorithm("GDE3").withProblemClass(ProblemXDNL.class)
				.withMaxEvaluations(100000).run();
		Solution optSolution = null;
		boolean mark = true;
		for (Solution solution : result) {
			if (mark == false) {
				if (solution.getObjective(Constants.nObjective - 1) < optSolution
						.getObjective(Constants.nObjective - 1)) {
					optSolution = solution;
					mark = false;
				}
			} else {
				optSolution = solution;
				mark = false;
			}

			showSolution(solution);
		}
		System.out.println("1 Opt solution ");
		showSolution(optSolution);
		
		long end = System.currentTimeMillis();		
		System.out.print("Execution time is " + (end - start) + "(ms)");
	}

	public static void showSolution(Solution solution) {
		if (solution == null)
			System.out.println("NULL");
		for (int i = 0; i < Constants.nNguonLuc; i++) {
			boolean[] values = EncodingUtils.getBinary(solution.getVariable(i));
			System.out.print(" ");
			for (boolean v : values)
				System.out.print(v == true ? 1 : 0);
		}
		System.out.format(" : ");
		for (int i = 0; i < Constants.nObjective; i++) {
			double out = solution.getObjective(i);
			if (i < Constants.nNguoiChoi) {
				out = -solution.getObjective(i);
			}
			System.out.format("%7.3f", out);
		}
		System.out.println();
	}
}