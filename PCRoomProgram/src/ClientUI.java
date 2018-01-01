import java.awt.*;
import javax.swing.*;

public class ClientUI extends JFrame {
	
	protected Container tab;
	protected CardLayout cardLayout;
	
	private ClientLoginPanel loginPanel;
	private ClientMakeUser makeUser;
	private ClientUserStatus userStatus;
	private ClientChatWindow chatWindow;
	
	public ClientUI() {
		
		ClientAppManager.getAppManager().setClientUI(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		loginPanel = new ClientLoginPanel();
		makeUser = new ClientMakeUser();
		userStatus = new ClientUserStatus();
		chatWindow = new ClientChatWindow();
		
		tab = new JPanel();
		cardLayout = new CardLayout();
		tab.setLayout(cardLayout);
		tab.add(loginPanel, "start");
		tab.add(userStatus, "login");
		
		getContentPane().add(tab);
	
		pack();
		setVisible(true);
	}
	public void changLogin() {
		cardLayout.show(tab, "login");
	}
	public void changStart() {
		cardLayout.show(tab, "start");
	}
}