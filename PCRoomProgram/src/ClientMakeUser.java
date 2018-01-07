import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class ClientMakeUser extends JDialog {
	
	private JLabel lblTitle,lblName,lblId,lblPassword,lblPasswordCheck,lblBirth;
	protected JTextField[] txt;
	protected JPasswordField password;
	protected JPasswordField checkpassword;
	protected JButton btnOk,btnCancel;
	private Font fnt;
	
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
		
		txt[0] = new JTextField(5);
		txt[0].setBounds(170,70,120,30);
		add(txt[0]);

		txt[1] = new JTextField(5);
		txt[1].setBounds(170,190,120,30);
		add(txt[1]);
	
		txt[2] = new JTextField(5);
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
	
	public void resetData() {
		for(int i = 0; i < 3; i++) txt[i].setText("");
		password.setText("");
		checkpassword.setText("");
	}
	
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