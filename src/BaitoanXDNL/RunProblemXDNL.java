package BaitoanXDNL;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import org.moeaframework.analysis.plot.Plot;

public class RunProblemXDNL {
	// private boolean compare(Solution s1, Solution s2) {
	// return s1.getObjective(3) < s2.getObjective(3);
	// }
	public static void main(String[] args) {
		NondominatedPopulation result = new Executor().withAlgorithm("eMOEA").withProblemClass(ProblemXDNL.class)
				.withMaxEvaluations(100000).run();
		Solution optSolution = new Solution(Constants.nVariable, Constants.nObjective, Constants.nConstraint);
		boolean mark = true;
		for (Solution solution : result) {
			if (!solution.violatesConstraints()) {
				if (mark || solution.getObjective(Constants.nObjective - 1) < optSolution
						.getObjective(Constants.nObjective - 1)) {
					optSolution = solution;
					mark = false;
				}				
			}
			showSolution(solution);
		}
		System.out.println("1 Opt solution ");
		showSolution(optSolution);
	}
	public static void showSolution(Solution solution) {
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