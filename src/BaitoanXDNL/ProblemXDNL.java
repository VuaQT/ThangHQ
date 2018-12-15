package BaitoanXDNL;

import org.apache.commons.math3.analysis.function.Max;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;
import BaitoanXDNL.Constants;

public class ProblemXDNL extends AbstractProblem {

	int nNguonLuc = Constants.nNguonLuc;
	int nNguoiChoi = Constants.nNguoiChoi;
	NguonLuc[] dsNguonLuc = new NguonLuc[nNguonLuc];
	NguoiChoi[] dsNguoiChoi = new NguoiChoi[nNguoiChoi];
	boolean[][] accept = new boolean[nNguoiChoi][nNguonLuc];
	float[] trong_so_du_an = {3.4f,2.7f,5.8f,7.7f,3.2f,6.2f,7.5f};
	public ProblemXDNL() {
		super(Constants.nVariable, Constants.nObjective, Constants.nConstraint);
		float tt[][] = { { 4, 17 }, { 6, 19 }, { 2, 7 }, { 2, 7.5f }, { 3, 8.5f }, { 4, 9.5f }, { 5, 10 }, { 6, 11 },
				{ 7, 22 }, { 4.5f, 17.5f }, { 6.5f, 19.5f }, { 1, 3 }, { 8, 23 }, { 6, 18 }, { 4.5f, 9.5f },
				{ 5.5f, 10 }, { 6, 11.5f }, { 7, 22.2f }, { 4.9f, 17.1f }, { 6.6f, 19.1f }, { 4.4f, 17 }, { 6.6f, 14 },
				{ 2, 7 }, { 2, 7.5f }, { 3, 8.5f }, { 4, 9.5f }, { 5, 10 }, { 6, 11 }, { 7, 22 }, { 4.5f, 17.5f },
				{ 6.5f, 20.5f }, { 4, 12 }, { 8.6f, 23.3f }, { 6.2f, 18 }, { 4.5f, 9.35f }, { 5.35f, 10 }, { 6, 11.52f },
				{ 7, 22.32f }, { 4.9f, 17.31f }, { 6.26f, 19.21f } };
		String kn[][] = { { "PHP", "JavaScript", "Python" }, { "MySQL", "C++", "C#" }, { "Python", "Java", "C++" },
				{ "Java", "C#" }, { "PHP", "JavaScript" }, { "MySQL", "HTML", "Ruby" }, { "MySQL", "C++" },
				{ "Java", "C++", "Ruby" }, { "JavaScript", "Pascal" }, { "NodeJS", "ReactJS", "AngularJS" },
				{ "AngularJS", "Lua", "Android" }, { "NodeJS", "HTML", "Android" }, { "Lua", "HTML" },
				{ "PHP", "JavaScript" }, { "AngularJS", "HTML", "Ruby" }, { "Lua", "ReactJS" },
				{ "Android", "C++", "HTML" }, { "AngularJS", "Android" }, { "HTML", "ReactJS", "Ruby" },
				{ "AngularJS", "ReactJS" }, { "Kotlin", "JavaScript", "Delphi" }, { "Kotlin", "D", "Perl" },
				{ "D", "Delphi", "Perl" }, { "Perl", "Scala" }, { "Kotlin", "Scala" }, { "Scala", "Perl", "Pascal" },
				{ "MySQL", "Perl" }, { "D", "C++", "Delphi" }, { "Pascal", "Delphi" }, { "NodeJS", "D", "Pascal" },
				{ "Perl", "Lua", "Android" }, { "Perl", "Kotlin", "Ocalm" }, { "Lua", "HTML" }, { "D", "JavaScript" },
				{ "Scala", "Pascal", "Ruby" }, { "Lua", "Kotlin", "Ocalm" }, { "Pascal", "Kotlin", "Delphi" },
				{ "Ocalm", "D" }, { "Pascal", "Scala", "Perl" }, { "Delphi", "Kotlin" } };

		for (int i = 0; i < nNguonLuc; i++) {
			dsNguonLuc[i] = new NguonLuc(i, tt[i], kn[i]);
		}
		String kn_nc[][] = { { "PHP", "MySQL", "Kotlin", "D" }, { "JavaScript", "ReactJS", "Java" },
				{ "Java", "C++", "Perl", "Scala" }, { "Java", "Android", "Ruby" }, { "NodeJS", "AngularJS", "Delphi" },
				{ "Kotlin", "Android", "C#" }, { "Ocalm", "reactJS", "Lua" } };
		int[][] thoi_gian_du_an = { { 1, 7 }, { 3, 4 }, { 5, 6 }, { 5, 8 }, { 8, 11 }, { 9, 16 }, { 11, 18 } };		

		for (int i = 0; i < nNguoiChoi; i++) {
			dsNguoiChoi[i] = new NguoiChoi(kn_nc[i], thoi_gian_du_an[i][0], thoi_gian_du_an[i][1], trong_so_du_an[i]);
		}
		for (int i = 0; i < nNguoiChoi; i++) {			
			for (int j = 0; j < nNguonLuc; j++) {
				accept[i][j] = dsNguoiChoi[i].checkNguonLuc_NguoiChoi(dsNguonLuc[j]);
				// System.out.print(accept[i][j]+ " ");
			}
		}		
	}

	@Override
	public void evaluate(Solution solution) {
		float[] objective = new float[nNguoiChoi];
		float[] cnt = new float[nNguoiChoi];
		int[] constraint = new int[nNguonLuc];
		for (int i = 0; i < nNguoiChoi; i++) {
			objective[i] = 0;
			cnt[i] = 0;
		}
		for (int i = 0; i < nNguonLuc; i++) {
			constraint[i] = 0;
		}
		for (int i = 0; i < nNguonLuc; i++) {
			int dem = 0;
			boolean[] values = EncodingUtils.getBinary(solution.getVariable(i));
			for (int j = 0; j < nNguoiChoi; j++) {
				if (values[j] == false)
					continue;
				dem++;
				objective[j] += dsNguonLuc[i].getValue();
				cnt[j]++;
				if (accept[j][i] == false) {
					constraint[i]++;
				}
				for (int k = 0; k < j; k++) {
					if (values[k] == true && dsNguoiChoi[j].checkNguoiChoi_NguoiChoi(dsNguoiChoi[k]))
						constraint[i]++;
				}
			}
			if (dem == 0)
				constraint[i] = nNguoiChoi;
		}

		int zeroObj = 0;
		for (int i = 0; i < nNguoiChoi; i++) {
			solution.setObjective(i, -objective[i]);
			if (objective[i] == 0)
				zeroObj++;
		}
		for (int i = 0; i < nNguonLuc; i++) {
			solution.setConstraint(i, constraint[i]);
		}
		solution.setObjective(nNguoiChoi, variance(objective, nNguoiChoi, trong_so_du_an));		
		solution.setObjective(nNguoiChoi + 1, unfitness(solution));
		if (zeroObj > 0) {
			solution.setConstraint(nNguoiChoi, zeroObj);
		}
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(Constants.nVariable, Constants.nObjective, Constants.nConstraint);
		for (int i = 0; i < nNguonLuc; i++) {
			solution.setVariable(i, EncodingUtils.newBinary(nNguoiChoi));
		}
		return solution;
	}

	float variance(float c[], int n, float b[]) {		
		// Compute mean (average
		// of elements)
		float[] a = new float[n];
		for (int i = 0; i < n; i++) {
			a[i] = c[i] / b[i];
		}
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

	public int unfitness(Solution solution) {
		boolean[][] values = new boolean[Constants.nNguonLuc][Constants.nNguoiChoi];
		for (int i = 0; i < Constants.nNguonLuc; i++) {
			values[i] = EncodingUtils.getBinary(solution.getVariable(i));
		}
		int ans = 0;
		for (int nc = 0; nc < Constants.nNguoiChoi; nc++) {
			// calculate unfitness for nc
			boolean[] cant_add = new boolean[Constants.nNguonLuc];
			for (int i = 0; i < Constants.nNguonLuc; i++) {
				cant_add[i] = false;
			}
			for (int j = 0; j < Constants.nNguoiChoi; j++) {
				if (dsNguoiChoi[j].checkNguoiChoi_NguoiChoi(dsNguoiChoi[nc])) {
					for (int i = 0; i < Constants.nNguonLuc; i++) {
						if (values[i][j] == true)
							cant_add[i] = true;
					}
				}
			}
			int dem = 0;
			for (int i = 0; i < Constants.nNguonLuc; i++) {
				if (cant_add[i] == false && accept[nc][i])
					dem++;
			}
			// ans = Math.max(ans,dem);
			ans += dem;
		}
		return ans;
	}
}