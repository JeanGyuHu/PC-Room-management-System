import java.awt.*;
import javax.swing.*;

public class ClientUI extends JFrame {
	
	protected Container tab; // 각 버튼에 따라서 화면에 보여지는 패널을 전환하기 위한 패널
	protected CardLayout cardLayout; // 화면을 겹치게 하여 패널들을 필요에 따라 전환하기 위한 레이아웃
	
	private ClientLoginPanel loginPanel; // 클라이언트 로그인을 담당하는 클래스
	private ClientMakeUser makeUser; // 회원가입을 담당하는 클래스
	private ClientUserStatus userStatus; // 로그인 후 클라이언트의 상태를 보여주는 클래스
	private ClientChatWindow chatWindow; // 메시지 채팅을 위한 클래스
	private ClientEndMessage warning; // 서버가 사용종료를 누르면, 클라이언트에게 사용종료 메시지를 띄울 클래스
	
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
	
	// 이벤트에 따라 화면을 변경하기 위한 함수들 
	public void changLogin() { // 로그인 화면으로 전환
		cardLayout.show(tab, "login");
	}
	public void changStart() { // 사용자의 상태 패널로 전환
		cardLayout.show(tab, "start");
	}
}