import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientChatWindow extends JDialog {

	private JPanel bottomPanel; // 메시지입력과 종료버튼을 띄울 패널
	protected JTextArea msgOut; // 채팅되는 전체 메시지들을 띄울 객체
	protected JTextField msgInput; // 클라이언트가 메시지를 입력할 텍스트 필드
	protected JButton btnExit; // 메시지 창을 종료하는 버튼
	private JScrollPane scroll; // 채팅메시지 부분의 스크롤
	
	public ClientChatWindow(){
		ClientAppManager.getAppManager().setClientChatWindow(this);
		setTitle("메시지"); // 창 제목을 '메시지'로 설정
		setResizable(false); // 크기 조정 불가
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
	
	// 나가기 버튼과 메시지 입력버튼에 대한 이벤트 리스너 // View 와 Controller 분리
	public void addTOAcListener(ActionListener listener) {
		btnExit.addActionListener(listener);
		msgInput.addActionListener(listener);
	}
	
}