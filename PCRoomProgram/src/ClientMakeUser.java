import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class ClientMakeUser extends JDialog {
	
	private JLabel lblTitle,lblName,lblId,lblPassword,lblPasswordCheck,lblBirth;
	// 순서대로 제목, 이름, ID, 비밀번호, 비밀번호 재입력, 생일이라는 글귀를 띄울 라벨
	protected JTextField[] txt; // 각 정보들을 입력받을 배열 텍스트 필드
	protected JPasswordField password; // 비밀번호를 입력받을 JPasswordField
	protected JPasswordField checkpassword; // 비밀번호를 재입력받을 JPasswordField
	protected JButton btnOk,btnCancel; // 회원가입 확인 버튼과 취소버튼
	private Font fnt; // 폰트 값
	
	public ClientMakeUser() {
		
		ClientAppManager.getAppManager().setClientMakeUser(this);
		setResizable(false);
		setSize(350,400);
		setLayout(null);
	
		fnt = new Font("야놀자 야체 R",Font.BOLD,16);
		
		lblTitle = new JLabel("회원가입 하기");
		lblTitle.setFont(new Font("야놀자 아체 R", Font.BOLD, 20));
		lblTitle.setBounds(10,10,280,50);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times",Font.BOLD,25));
		add(lblTitle);
		
		lblId = new JLabel("아이디");
		lblId.setFont(fnt);
		lblId.setBounds(10,70,140,30);
		add(lblId);
		
		lblPassword = new JLabel("비밀번호");
		lblPassword.setFont(fnt);
		lblPassword.setBounds(10,110,140,30);
		add(lblPassword);
		
		lblPasswordCheck = new JLabel("비밀번호 확인");
		lblPasswordCheck.setFont(fnt);
		lblPasswordCheck.setBounds(10,150,140,30);
		add(lblPasswordCheck);
		
		lblName = new JLabel("이름");
		lblName.setFont(fnt);
		lblName.setBounds(10,190,140,30);
		add(lblName);
		
		lblBirth = new JLabel("생년월일");
		lblBirth.setFont(fnt);
		lblBirth.setBounds(10,230,140,30);
		add(lblBirth);
		
		txt = new JTextField[3];
		
		password = new JPasswordField(5);
		password.setEchoChar('*');
		password.setBounds(170, 110, 120, 30);
		add(password);
		
		checkpassword = new JPasswordField(5);
		checkpassword.setEchoChar('*');
		checkpassword.setBounds(170, 150, 120, 30);
		add(checkpassword);
		
		txt[0] = new JTextField(5); // 아이디 입력
		txt[0].setBounds(170,70,120,30);
		add(txt[0]);

		txt[1] = new JTextField(5); // 이름 입력
		txt[1].setBounds(170,190,120,30);
		add(txt[1]);
	
		txt[2] = new JTextField(5); // 생년월일 입력
		txt[2].setBounds(170,230,120,30);
		add(txt[2]);

		btnOk = new JButton("회원가입");
		btnOk.setFont(fnt);
		btnOk.setBackground(Color.white);
		btnOk.setBounds(50,280,100,30);
		add(btnOk);
		
		btnCancel = new JButton("취소");
		btnCancel.setFont(fnt);
		btnCancel.setBackground(Color.white);
		btnCancel.setBounds(180,280,60,30);
		add(btnCancel);
	}
	
	// 입력받았던 텍스트 필드의 값들을 모두 초기화하기 위한 함수
	public void resetData() {
		for(int i = 0; i < 3; i++) txt[i].setText("");
		password.setText("");
		checkpassword.setText("");
	}
	
	// 아이디, 이름, 생년월일, 비밀번호, 비밀번호 재입력 텍스트 필드와 각 버튼들에 대한 이벤트 리스너 // View 와 Controller 분리
	public void addTOAcListener(ActionListener listener) {
		txt[0].addActionListener(listener);
		txt[1].addActionListener(listener);
		txt[2].addActionListener(listener); 
		password.addActionListener(listener);
		checkpassword.addActionListener(listener);
		btnOk.addActionListener(listener);
		btnCancel.addActionListener(listener);
	}
}