package BaitoanXDNL;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class ProblemXDNL extends AbstractProblem {

	int nNguonLuc = 9;
	int nNguoiChoi = 3;
	NguonLuc[] dsNguonLuc = new NguonLuc[nNguonLuc];
	NguoiChoi[] dsNguoiChoi = new NguoiChoi[nNguoiChoi];
	boolean[][] accept = new boolean[nNguoiChoi][nNguonLuc];

	public ProblemXDNL() {
		super(9, 3+1+1, 9); // 3 = nNguoiChoi, 9 = nNguonLuc , 1 + 1 = objective for payoff variance + objective for number of employee variance  
		float tt[][] = { { 1, 17 }, { 1, 15 }, { 2, 15 }, { 2, 9.5f }, { 3, 9.5f }, { 4, 9.5f }, { 5, 9 }, { 6, 9 },
				{ 7, 9 } };
		String kn[][] = { { "PHP", "JavaScript", "Python" }, { "MySQL", "C++", "C#" }, { "Python", "Java", "C++" },
				{ "Java", "C#" }, { "PHP", "JavaScript" }, { "MySQL", "C#" }, { "MySQL", "C++" }, { "Java", "C++" },
				{ "JavaScript", "Python" } };

		for (int i = 0; i < nNguonLuc; i++) {
			dsNguonLuc[i] = new NguonLuc(i, tt[i], kn[i]);
		}
		String kn_nc[][] = { { "PHP", "MySQL", "JavaScript" }, { "JavaScript", "Python", "Java" },
				{ "Java", "C++", "C#" } };
		for (int i = 0; i < nNguoiChoi; i++) {
			dsNguoiChoi[i] = new NguoiChoi(kn_nc[i]);
		}
		for (int i = 0; i < nNguoiChoi; i++) {
			System.out.println("");
			for (int j = 0; j < nNguonLuc; j++) {
				accept[i][j] = dsNguoiChoi[i].checkNguonLuc_NguoiChoi(dsNguonLuc[j]);
				System.out.print(accept[i][j]+ " ");
			}
		}
		System.out.println();
	}

	@Override
	public void evaluate(Solution solution) {
		float[] objective = new float[nNguoiChoi];
		float[] cnt= new float[nNguoiChoi];
		int[] constraint= new int[nNguonLuc];
		for(int i=0;i<nNguoiChoi;i++) {
			objective[i] = 0;
			cnt[i]=0;
		}
		for(int i=0;i<nNguonLuc;i++) {
			constraint[i] = 0;
		}
		for (int i = 0; i < nNguonLuc; i++) {
			int x = EncodingUtils.getInt(solution.getVariable(i));			
			objective[x] += dsNguonLuc[i].getValue();
			cnt[x] ++;
			if (accept[x][i] == false) {
				constraint[i] ++;
			}
		}
		
		for(int i=0;i<nNguoiChoi;i++) {
			solution.setObjective(i, -objective[i]);
		}
		for(int i=0;i<nNguonLuc;i++) {
			solution.setConstraint(i, constraint[i]);
		}			
		solution.setObjective(nNguoiChoi, variance(objective, nNguoiChoi));
		solution.setObjective(nNguoiChoi+1, variance(cnt, nNguoiChoi));
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(9, 3+1+1, 9);

		for (int i = 0; i < nNguonLuc; i++) {
			solution.setVariable(i, EncodingUtils.newInt(0, nNguoiChoi-1));			
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

}