import javax.swing.*;
import java.awt.*;

public class ShowPanel extends JPanel {

	private ClientLoginPanel loginPanel;
	private ClientUserStatus userStatus;
	
	public ShowPanel() {
		
		setPreferredSize(new Dimension(500,300));
		
		loginPanel = new ClientLoginPanel(this);
		add(loginPanel);
		
		userStatus = new ClientUserStatus(); 
		userStatus.setVisible(false);
		add(userStatus);
	}
	
	public void setVisibleUser() {
		loginPanel.setVisible(false);
		userStatus.setVisible(true);
	}
	public void setVisibleLogin() {
		loginPanel.setVisible(true);
		userStatus.setVisible(false);
	}
}
