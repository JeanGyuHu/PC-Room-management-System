import java.awt.*;
import javax.swing.*;

public class ClientUI extends JFrame {
	
	protected Container tab; // �� ��ư�� ���� ȭ�鿡 �������� �г��� ��ȯ�ϱ� ���� �г�
	protected CardLayout cardLayout; // ȭ���� ��ġ�� �Ͽ� �гε��� �ʿ信 ���� ��ȯ�ϱ� ���� ���̾ƿ�
	
	private ClientLoginPanel loginPanel; // Ŭ���̾�Ʈ �α����� ����ϴ� Ŭ����
	private ClientMakeUser makeUser; // ȸ�������� ����ϴ� Ŭ����
	private ClientUserStatus userStatus; // �α��� �� Ŭ���̾�Ʈ�� ���¸� �����ִ� Ŭ����
	private ClientChatWindow chatWindow; // �޽��� ä���� ���� Ŭ����
	private ClientEndMessage warning; // ������ ������Ḧ ������, Ŭ���̾�Ʈ���� ������� �޽����� ��� Ŭ����
	
	public ClientUI() {
		
		ClientAppManager.getAppManager().setClientUI(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		loginPanel = new ClientLoginPanel();
		makeUser = new ClientMakeUser();
		userStatus = new ClientUserStatus();
		chatWindow = new ClientChatWindow();
		warning = new ClientEndMessage();
		
		tab = new JPanel();
		cardLayout = new CardLayout();
		tab.setLayout(cardLayout);
		tab.add(loginPanel, "start");
		tab.add(userStatus, "login");
		
		getContentPane().add(tab);
	
		pack();
		setVisible(true);
	}
	
	// �̺�Ʈ�� ���� ȭ���� �����ϱ� ���� �Լ��� 
	public void changLogin() { // �α��� ȭ������ ��ȯ
		cardLayout.show(tab, "login");
	}
	public void changStart() { // ������� ���� �гη� ��ȯ
		cardLayout.show(tab, "start");
	}
}