import javax.swing.JComponent;
import javax.swing.JTextArea;

public class DataHandle {
	protected JTextArea msgOut;
	
	public void addObj(JComponent comp) {
		msgOut = (JTextArea) comp;
	} // ������ ��ü���� ������ ��ȭ�� ó���� UI��ü �߰� // �ش� ������Ʈ�� text area���·� ��ȯ�Ͽ� ����
	public void refreshData(String msg) {
		msgOut.append(msg +"\n");
	} // MultiChatData ��ü�� ������ ���� // ���ڿ��� �ʵ忡 ���
	public void delData() {
		msgOut.setText("");
	} // �ʵ带 �ʱ�ȭ
}