import javax.swing.JComponent;
import javax.swing.JTextArea;

public class DataHandle {
	protected JTextArea msgOut;
	
	public void addObj(JComponent comp) {
		msgOut = (JTextArea) comp;
	} // 데이터 객체에서 데이터 변화를 처리할 UI객체 추가 // 해당 컴포넌트를 text area형태로 변환하여 저장
	public void refreshData(String msg) {
		msgOut.append(msg +"\n");
	} // MultiChatData 객체로 데이터 갱신 // 문자열을 필드에 띄움
	public void delData() {
		msgOut.setText("");
	} // 필드를 초기화
}