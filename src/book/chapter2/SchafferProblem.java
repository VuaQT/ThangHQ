package book.chapter2;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class SchafferProblem extends AbstractProblem {

	public SchafferProblem() {
		super(1, 3);
	}

	@Override
	public void evaluate(Solution solution) {
		double x = EncodingUtils.getReal(solution.getVariable(0));

		solution.setObjective(0, Math.pow(x, 2.0));
		solution.setObjective(1, Math.pow(x - 2.0, 2.0));
		solution.setObjective(2, Math.pow(x - 1.0, 2.0));
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, 3);
		solution.setVariable(0, EncodingUtils.newReal(-10.0, 10.0));
		return solution;
	}

}