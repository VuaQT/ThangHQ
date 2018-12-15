package BaitoanXDNL;

public class NguoiChoi {
	String[] yeucaukynang;
	int start_day, end_day;
	float trong_so;
	public NguoiChoi(String[] kn, int start, int end, float ts) {
		yeucaukynang = kn;
		start_day = start;
		end_day = end;
		trong_so = ts;
	}
	public boolean checkNguonLuc_NguoiChoi(NguonLuc nl) {
		for (String kn_nl : nl.kynang) {
			for (String kn_nc : yeucaukynang) {
				if(kn_nl.equals(kn_nc)) return true;
			}
		}
		return false;
	}
	public boolean checkNguoiChoi_NguoiChoi(NguoiChoi nc) {
		if(nc.start_day > this.end_day || nc.end_day < this.end_day) 
			return true;
		return false;
	}
}
