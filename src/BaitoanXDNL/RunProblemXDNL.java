package BaitoanXDNL;

import java.util.Comparator;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.analysis.plot.Plot;

public class RunProblemXDNL {
//	private boolean compare(Solution s1, Solution s2) {
//        return s1.getObjective(3) < s2.getObjective(3);
//    }
	public static void main(String[] args) {
		NondominatedPopulation result = new Executor().withAlgorithm("NSGAII").withProblemClass(ProblemXDNL.class)
				.withMaxEvaluations(10000).run();
		Solution optSolution = new Solution(9,3+1,9);
		boolean mark  = true;
		for (Solution solution : result) {
			if (!solution.violatesConstraints()) {
				if(mark || solution.getObjective(3) < optSolution.getObjective(3)) {
					optSolution = solution;
					mark = false;
				}
				for(int i=0;i<9;i++) {
					System.out.print(EncodingUtils.getInt(solution.getVariable(i)) + " ");
//					System.out.format("%7.3f", solution.getVariable(i));					
				}
				System.out.format(" : %7.3f %7.3f %7.3f %7.3f %7.3f\n", -solution.getObjective(0), -solution.getObjective(1),-solution.getObjective(2), solution.getObjective(3), solution.getObjective(4));
			}
		}		
		System.out.format("\n Optimize solution : %7.3f %7.3f %7.3f %7.3f %7.3f\n", -optSolution.getObjective(0), -optSolution.getObjective(1),-optSolution.getObjective(2), optSolution.getObjective(3),  optSolution.getObjective(4));
	}

}