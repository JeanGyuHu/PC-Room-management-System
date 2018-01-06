
public class PcData {

	private String IP; // 301호 PC의 IP
	private int key; // PC의 번호 (1,2,3...,24)
		
	public PcData(){
		IP = null;
	}
	
	//get/set
	
	public String getIP() {return IP;}
	public int getKey() {return key;}
	public void setIP(String i) {IP = i;}
	public void setKey(int k) { key = k;}
}