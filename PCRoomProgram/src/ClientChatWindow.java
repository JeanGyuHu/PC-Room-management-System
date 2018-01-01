import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientChatWindow extends JDialog {

	private JPanel bottomPanel;
	protected JTextArea msgOut;
	protected JTextField msgInput;
	protected JButton btnExit;
	private JScrollPane scroll;
	
	public ClientChatWindow(){
		ClientAppManager.getAppManager().setClientChatWindow(this);
		setTitle("Ã¤ÆÃÃ¢");
		setResizable(false);
		setLayout(new BorderLayout());
		setSize(400, 400);
		msgOut = new JTextArea("",18,30);
		
		scroll = new JScrollPane(msgOut, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scroll, BorderLayout.PAGE_START);
		
		msgInput = new JTextField();
		
		btnExit = new JButton("Á¾·á");
		btnExit.setFont(new Font("¾ß³îÀÚ ¾ßÃ¼ R",Font.BOLD,20));
		btnExit.setBackground(Color.white);
		btnExit.setBackground(Color.WHITE);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		
		bottomPanel.add(msgInput, BorderLayout.CENTER);
		bottomPanel.add(btnExit, BorderLayout.WEST);
		
		add(bottomPanel);
	}
	
	public void addTOAcListener(ActionListener listener) {
		btnExit.addActionListener(listener);
		msgInput.addActionListener(listener);
	}
	
}