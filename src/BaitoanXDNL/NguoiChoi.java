package BaitoanXDNL;

public class NguoiChoi {
	String[] yeucaukynang;
	public NguoiChoi(String[] kn) {
		yeucaukynang = kn;
	}
	public boolean checkNguonLuc_NguoiChoi(NguonLuc nl) {				
		for (String kn_nl : nl.kynang) {
			for (String kn_nc : yeucaukynang) {
				if(kn_nl.equals(kn_nc)) return true;
			}
		}
		return false;
	}
}
