import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientLoginPanel extends JPanel{
	private JLabel lblID, lblPass;
	protected JTextField txtID, txtPass;
	protected JButton btnInsert, btnLogin;
	private Font fnt;
	
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
	    
	    txtPass = new JTextField(20);
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
	

	public void addTOAcListener(ActionListener listener) {
		txtID.addActionListener(listener);
		txtPass.addActionListener(listener);
		btnInsert.addActionListener(listener); 
		btnLogin.addActionListener(listener);
		
	}
}
