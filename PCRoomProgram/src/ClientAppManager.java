public class ClientAppManager {

	private static ClientAppManager s_instance; // ��Ŭ�� Ŭ����
	private ClientUI ui; // Ŭ���̾�Ʈ UI�� �Ѱ��ϴ� Ŭ����
	private ClientLoginPanel loginPanel; // Ŭ���̾�Ʈ �α����� ����ϴ� Ŭ����
	private ClientMakeUser makeUser; // ȸ�������� ����ϴ� Ŭ����
	private ClientUserStatus userStatus; // �α��� �� Ŭ���̾�Ʈ�� ���¸� �����ִ� Ŭ����
	private ClientChatWindow chatWindow; // �޽��� ä���� ���� Ŭ����
	private ClientEndMessage warning; // ������ ������Ḧ ������, Ŭ���̾�Ʈ���� ������� �޽����� ��� Ŭ����
	
	public static ClientAppManager getAppManager() {
		if(s_instance == null) s_instance = new ClientAppManager();
		return s_instance;
	}
	public void setClientChatWindow(ClientChatWindow chatWindow) {
		this.chatWindow = chatWindow;
	}
	public ClientChatWindow getClientChatWindow() {
		return chatWindow;
	}
	public void setClientMakeUser(ClientMakeUser makeUser) {
		this.makeUser = makeUser;
	}
	public ClientMakeUser getClientMakeUser() {
		return makeUser;
	}
	public void setClientUserStatus(ClientUserStatus userStatus) {
		this.userStatus = userStatus;
	}
	public ClientUserStatus getClientUserStatus() {
		return userStatus;
	}
	public void setClientLoginPanel(ClientLoginPanel loginPanel) {
		this.loginPanel = loginPanel;
	}
	public ClientLoginPanel getClientLoginPanel() {
		return loginPanel;
	}
	
	public void setClientEndMessage(ClientEndMessage warning) {
		this.warning = warning;
	}
	
	public ClientEndMessage getClientEndMessage() {
		return warning;
	}
	
	public void setClientUI(ClientUI u) {
		this.ui = u;
	}
	public ClientUI getClientUI() {
		return this.ui;
	}
}