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
		setTitle("채팅창");
		setResizable(false);
		setLayout(new BorderLayout());
		setSize(400, 400);
		msgOut = new JTextArea("",18,30);
		msgOut.setEditable(false);
		
		scroll = new JScrollPane(msgOut, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scroll, BorderLayout.PAGE_START);
		
		msgInput = new JTextField();
		
		btnExit = new JButton("종료");
		btnExit.setFont(new Font("야놀자 야체 R",Font.BOLD,20));
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
