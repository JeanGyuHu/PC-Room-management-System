public class ClientAppManager {

	private static ClientAppManager s_instance; // 싱클톤 클래스
	private ClientUI ui; // 클라이언트 UI를 총괄하는 클래스
	private ClientLoginPanel loginPanel; // 클라이언트 로그인을 담당하는 클래스
	private ClientMakeUser makeUser; // 회원가입을 담당하는 클래스
	private ClientUserStatus userStatus; // 로그인 후 클라이언트의 상태를 보여주는 클래스
	private ClientChatWindow chatWindow; // 메시지 채팅을 위한 클래스
	private ClientEndMessage warning; // 서버가 사용종료를 누르면, 클라이언트에게 사용종료 메시지를 띄울 클래스
	
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