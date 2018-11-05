package Examples;

import java.util.Arrays;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

public class Example1 {

	public static void main(String[] args) {
		NondominatedPopulation result = new Executor().withProblem("UF1").withAlgorithm("NSGAII")
				.withMaxEvaluations(10000).run();

		for (Solution solution : result) {
			System.out.println(solution.getObjective(0) + " " + solution.getObjective(1));
		}
	}

}