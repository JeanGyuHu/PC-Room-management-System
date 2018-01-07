import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientChatWindow extends JDialog {

	private JPanel bottomPanel; // 메시지입력필드와 종료버튼이 들어갈 패널
	protected JTextArea msgOut; // 주고받은 메시지가 뜨는 창
	protected JTextField msgInput; // 사용자가 채팅을 입력할 텍스트 필드 
	protected JButton btnExit; // 메시지창 종료 버튼
	private JScrollPane scroll; // 메시지 창에 들어갈 스크롤바
	
	public ClientChatWindow(){
		ClientAppManager.getAppManager().setClientChatWindow(this);
		setTitle("메시지");
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
	
	// 종료 버튼과 메시지 입력 텍스트 필드의 이벤트 리스너 처리를 위한 함수  // View 와 Controller 분리
	public void addTOAcListener(ActionListener listener) {
		btnExit.addActionListener(listener);
		msgInput.addActionListener(listener);
	}
	
}