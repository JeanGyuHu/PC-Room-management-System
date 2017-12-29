import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientLoginPanel extends JPanel{
	private JLabel lblID, lblPass;
	private JTextField txtID, txtPass;
	private JButton btnInsert, btnLogin;
	private ClientMakeUser makeUser;
	private ClientUserStatus userStatus;
	private AcListener aL;
	private ShowPanel show;
	public ClientLoginPanel(ShowPanel s) {
		
		show = s;
		
		setPreferredSize(new Dimension(500, 300));
	    setLayout(null);
	    setBackground(Color.white);

	    aL = new AcListener();
	    
	    lblID = new JLabel("ID ");
	    lblID.setBounds(50, 25, 100, 70);
	    lblID.setHorizontalAlignment(SwingConstants.CENTER);
	    add(lblID);
	    
	    lblPass = new JLabel("Password ");
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
	    btnInsert.setBounds(70, 200, 150, 50);
	    btnInsert.setBackground(Color.white);
	    btnInsert.addActionListener(aL);
	    add(btnInsert);
	    
	    makeUser = new ClientMakeUser();
	    
	    btnLogin = new JButton("로그인");
	    btnLogin.setBounds(280, 200, 150, 50);
	    btnLogin.setBackground(Color.white);
	    btnLogin.addActionListener(aL);
	    add(btnLogin);
	   
	    userStatus = new ClientUserStatus();
	    
	}
	
	private class AcListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			
			if(obj == btnInsert) {
				//회원가입 일때
				makeUser.show();
			}
			else if(obj == btnLogin) {
				// 로그인일때
				show.setVisibleUser();
			}
			
		}
		
	}
	
}
