package book.chapter3;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

import org.moeaframework.analysis.plot.Plot;


public class RunSrinivasProblem {

	public static void main(String[] args) {
		NondominatedPopulation result = new Executor().withAlgorithm("NSGAII").withProblemClass(SrinivasProblem.class)
				.withMaxEvaluations(10000).run();

		for (Solution solution : result) {
			if (!solution.violatesConstraints()) {
				System.out.format("%10.3f %10.3f%n", solution.getObjective(0), solution.getObjective(1));
			}
		}
		new Plot().add("NSGAII", result).show();
	}

}