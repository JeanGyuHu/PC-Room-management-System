import javax.swing.*;
import java.awt.*;

public class UserStatus extends JPanel {

	private JLabel lblUserId, lblUserRemainTime;
	private JTextField txtUserId, txtUserRemainTime;
	private JButton btnMessage, btnLogout;
	private Font fnt;
	
	public UserStatus() {
		
		setPreferredSize(new Dimension(500,300));
		setBackground(Color.white);
		setLayout(null);
		
		fnt = new Font("야놀자 야체 R",Font.BOLD,20);
		
		lblUserId = new JLabel("아이디");
		lblUserId.setFont(fnt);
		lblUserId.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserId.setBounds(50, 25, 100, 70);
		add(lblUserId);
		
		lblUserRemainTime = new JLabel("남은시간");
		lblUserRemainTime.setFont(fnt);
		lblUserRemainTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserRemainTime.setBounds(50, 95, 100, 70);
		add(lblUserRemainTime);
		
		txtUserId = new JTextField();
		txtUserId.setFont(fnt);
		txtUserId.setBounds(170, 25, 300, 50);
		add(txtUserId);
		
		txtUserRemainTime = new JTextField();
		txtUserRemainTime.setFont(fnt);
		txtUserRemainTime.setBounds(170, 95, 300, 50);
		add(txtUserRemainTime);
		
		btnMessage = new JButton("메세지");
		btnMessage.setBounds(70, 200, 150, 50);
		btnMessage.setBackground(Color.WHITE);
		btnMessage.setFont(fnt);
		add(btnMessage);
		
		btnLogout = new JButton("사용종료");
		btnLogout.setBounds(280, 200, 150, 50);
		btnLogout.setBackground(Color.WHITE);
		btnLogout.setFont(fnt);
		add(btnLogout);
	}
	
}
