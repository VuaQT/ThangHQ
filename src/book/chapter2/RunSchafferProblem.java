package book.chapter2;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

public class RunSchafferProblem {

	public static void main(String[] args) {
		NondominatedPopulation result = new Executor().withAlgorithm("NSGAII").withProblemClass(SchafferProblem.class)
				.withMaxEvaluations(10000).run();		
		for (Solution solution : result) {
			System.out.printf("%.5f => %.5f, %.5f, %.5f\n", EncodingUtils.getReal(solution.getVariable(0)),
					solution.getObjective(0), solution.getObjective(1), solution.getObjective(2));
		}
	}

}