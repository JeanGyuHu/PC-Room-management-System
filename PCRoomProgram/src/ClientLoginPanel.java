import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientLoginPanel extends JPanel{
	private JLabel lblID, lblPass; // ID와 비밀번호라는 글을 띄울 라벨
	protected JTextField txtID; // ID를 입력받을 텍스트 필드
	protected JPasswordField txtPass; // 비밀번호를 입력받을 텍스트 필드, 입력값이 보이지 않도록 JPasswordField로 선언 
	protected JButton btnInsert, btnLogin; // 회원가입 버튼과 로그인 버튼
	private Font fnt; // 폰트 값
	
	public ClientLoginPanel() {
		
		ClientAppManager.getAppManager().setClientLoginPanel(this);
		setPreferredSize(new Dimension(500, 300));
	    setLayout(null);
	    setBackground(Color.white);
	    
	    fnt = new Font("야놀자 야체 R",Font.BOLD,20);
	    
	    lblID = new JLabel("ID ");
	    lblID.setFont(fnt);
	    lblID.setBounds(50, 25, 100, 70);
	    lblID.setHorizontalAlignment(SwingConstants.CENTER);
	    add(lblID);
	    
	    lblPass = new JLabel("Password ");
	    lblPass.setFont(fnt);
	    lblPass.setBounds(50, 95, 100, 70);
	    lblPass.setHorizontalAlignment(SwingConstants.CENTER);
	    add(lblPass);
	    
	    txtID = new JTextField(20);
	    txtID.setBounds(170, 25, 300, 50);
	    add(txtID);
	    
	    txtPass = new JPasswordField(20);
	    txtPass.setEchoChar('*');
	    txtPass.setBounds(170, 95, 300, 50);
	    add(txtPass);
	    
	    btnInsert = new JButton("회원가입");
	    btnInsert.setFont(fnt);
	    btnInsert.setBounds(70, 200, 150, 50);
	    btnInsert.setBackground(Color.white);
	    add(btnInsert);
	    
	    btnLogin = new JButton("로그인");
	    btnLogin.setFont(fnt);
	    btnLogin.setBounds(280, 200, 150, 50);
	    btnLogin.setBackground(Color.white);
	    add(btnLogin);
	   
	}
	
	// 입력한 ID와 비밀번호, 회원가입, 종료버튼 처리를 위한 이벤트 리스너 // View 와 Controller 분리
	public void addTOAcListener(ActionListener listener) {
		txtID.addActionListener(listener);
		txtPass.addActionListener(listener);
		btnInsert.addActionListener(listener); 
		btnLogin.addActionListener(listener);
		
	}
}