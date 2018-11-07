package BaitoanXDNL;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class ProblemXDNL extends AbstractProblem {

	int nNguonLuc = Constants.nNguonLuc;
	int nNguoiChoi = Constants.nNguoiChoi;
	NguonLuc[] dsNguonLuc = new NguonLuc[nNguonLuc];
	NguoiChoi[] dsNguoiChoi = new NguoiChoi[nNguoiChoi];
	boolean[][] accept = new boolean[nNguoiChoi][nNguonLuc];

	public ProblemXDNL() {
		super(Constants.nNguonLuc, Constants.nNguoiChoi+1+1, Constants.nNguonLuc+1);  
		float tt[][] = { { 4, 17 }, { 6, 19 }, { 2, 7 }, { 2, 7.5f }, { 3, 8.5f }, { 4, 9.5f }, { 5, 10 }, { 6, 11 },
				{ 7, 22 } };
		String kn[][] = { { "PHP", "JavaScript", "Python" }, { "MySQL", "C++", "C#" }, { "Python", "Java", "C++" },
				{ "Java", "C#" }, { "PHP", "JavaScript" }, { "MySQL", "C#","Ruby" }, { "MySQL", "C++" }, { "Java", "C++","Ruby" },
				{ "JavaScript", "Python" } };

		for (int i = 0; i < nNguonLuc; i++) {
			dsNguonLuc[i] = new NguonLuc(i, tt[i], kn[i]);
		}
		String kn_nc[][] = { { "PHP", "MySQL", "JavaScript" }, { "JavaScript", "Python", "Java" },
				{ "Java", "C++", "C#" },{ "Java", "PHP","Ruby" },{ "MySQL", "C++","Ruby"} };
		int[][] thoi_gian_du_an= { {1,7},{3,4},{5,6},{5,8},{8,11} };
		
		for (int i = 0; i < nNguoiChoi; i++) {
			dsNguoiChoi[i] = new NguoiChoi(kn_nc[i],thoi_gian_du_an[i][0], thoi_gian_du_an[i][1]);
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
			int dem  =0;
			boolean[] values = EncodingUtils.getBinary(solution.getVariable(i));			
			for(int j=0;j<nNguoiChoi;j++) {
				if(values[j]==false) continue;
				dem ++;
				objective[j] += dsNguonLuc[i].getValue();
				cnt[j] ++;
				if (accept[j][i] == false) {
					constraint[i] ++;
				}
				for(int k=0;k<j;k++) {
					if(values[k]==true && dsNguoiChoi[j].checkNguoiChoi_NguoiChoi(dsNguoiChoi[k])) constraint[i] ++; 					
				}
			}
			if(dem == 0) constraint[i] = nNguoiChoi;				
		}
		
		int zeroObj = 0;
		for(int i=0;i<nNguoiChoi;i++) {
			solution.setObjective(i, -objective[i]);
			if(objective[i]==0)
				zeroObj++;
		}
		for(int i=0;i<nNguonLuc;i++) {
			solution.setConstraint(i, constraint[i]);
		}			
		solution.setObjective(nNguoiChoi, variance(objective, nNguoiChoi));
		solution.setObjective(nNguoiChoi+1, variance(cnt, nNguoiChoi));
		if(zeroObj>0) {
			 solution.setConstraint(nNguoiChoi, zeroObj);
		}
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(Constants.nNguonLuc, Constants.nNguoiChoi+1+1, Constants.nNguonLuc+1);
		for (int i = 0; i < nNguonLuc; i++) {
			solution.setVariable(i, EncodingUtils.newBinary(nNguoiChoi));			
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