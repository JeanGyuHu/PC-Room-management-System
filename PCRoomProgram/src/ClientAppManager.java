public class ClientAppManager {

	private static ClientAppManager s_instance;
	private ClientUI ui;
	private ClientLoginPanel loginPanel;
	private ClientMakeUser makeUser;
	private ClientUserStatus userStatus;
	private ClientChatWindow chatWindow;
	
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
	
	public void setClientUI(ClientUI u) {
		this.ui = u;
	}
	public ClientUI getClientUI() {
		return this.ui;
	}
}