package BaitoanXDNL;

public class NguonLuc {
	int id;
	float[] thuoctinh;
	String[] kynang;
	private final float[] hs= {0.5f,-0.1f};	
	public NguonLuc(int id, float[] tt, String[] kn) {
		id  = id;
		thuoctinh = tt;
		kynang = kn;
	}
	public float getValue() {
		float payoff = 0;
		for(int i=0;i<thuoctinh.length;i++) {
			payoff += hs[i]*thuoctinh[i];
		}
		return payoff;// return payoff cua nguon luc nay
	}
}
