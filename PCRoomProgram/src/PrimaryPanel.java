import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PrimaryPanel extends JPanel {

	private JPanel topPanel,leftPanel,rightPanel,modifyPanel,showInformationPanel,messagePanel;
	private JButton btnMember,btnSeat, btnMessage, btnLogout;
	private ImageIcon[] image;
	private JLabel lblId,lblName,lblPassword,lblBirth,lblTime;
	private JTextField[] txtInfo;
	private JButton btnModify,btnDelete,btnCharge,btnPowerOff;
	private Font fnt;
	private TopButtonListener btnL;

	
	private PcMemberPanel memberPanel;
	
	private PCPanel pcPanel;

	public PrimaryPanel(){
		
		setPreferredSize(new Dimension(810,510));
		setBackground(Color.white);
		setLayout(null);
		
		fnt = new Font("야놀자 야체 R",Font.BOLD,20);
		btnL = new TopButtonListener();
		
		topPanel = new JPanel();
		topPanel.setBounds(0,0,810,100);
		topPanel.setBackground(Color.white);
		add(topPanel);
		
		rightPanel = new JPanel();
		rightPanel.setBounds(510,110,300,400);
		rightPanel.setBackground(Color.white);
		rightPanel.setLayout(null);
		add(rightPanel);
	
		modifyPanel = new JPanel();
		modifyPanel.setBounds(0,300,300,100);
		modifyPanel.setBackground(Color.white);
		modifyPanel.setLayout(null);
		modifyPanel.setVisible(false);
		rightPanel.add(modifyPanel);
		
		btnCharge = new JButton("시간 충전");
		btnCharge.setBounds(20,15,260,30);
		
		btnCharge.setBackground(Color.white);
		modifyPanel.add(btnCharge);
		
		btnModify = new JButton("수정");
		btnModify.setBounds(20,60,120,30);
		btnModify.setBackground(Color.white);
		modifyPanel.add(btnModify);
		
		btnDelete = new JButton("탈퇴");
		btnDelete.setBounds(160,60,120,30);
		btnDelete.setBackground(Color.white);
		modifyPanel.add(btnDelete);

		showInformationPanel = new JPanel();
		showInformationPanel.setBounds(0,300,300,100);
		showInformationPanel.setBackground(Color.white);
		showInformationPanel.setLayout(null);
		showInformationPanel.setVisible(true);
		rightPanel.add(showInformationPanel);
		
		btnPowerOff = new JButton("사용 종료");
		btnPowerOff.setBounds(30,20,240,60);
		btnPowerOff.setBackground(Color.white);
		showInformationPanel.add(btnPowerOff);
		
		messagePanel = new JPanel();
		messagePanel.setBounds(510,110,300,400);
		messagePanel.setBackground(Color.white);
		messagePanel.setVisible(false);
		add(messagePanel);
		
		txtInfo = new JTextField[5];
		
		for(int i =0;i<5;i++) {
			txtInfo[i] = new JTextField(10);
			txtInfo[i].setBounds(150,50+50*i,140,30);
			rightPanel.add(txtInfo[i]);
		}
		
		lblId = new JLabel("아이디");
		lblId.setBounds(10,50,140,30);
		lblId.setFont(fnt);
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		rightPanel.add(lblId);
	
		lblName = new JLabel("이 름");
		lblName.setBounds(10,100,140,30);
		lblName.setFont(fnt);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		rightPanel.add(lblName);

		lblPassword = new JLabel("비밀번호");
		lblPassword.setBounds(10,150,140,30);
		lblPassword.setFont(fnt);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		rightPanel.add(lblPassword);
		
		lblBirth = new JLabel("생년월일");
		lblBirth.setBounds(10,200,140,30);
		lblBirth.setFont(fnt);
		lblBirth.setHorizontalAlignment(SwingConstants.CENTER);
		rightPanel.add(lblBirth);
		
		lblTime = new JLabel("남은시간");
		lblTime.setBounds(10,250,140,30);
		lblTime.setFont(fnt);
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		rightPanel.add(lblTime);
	
		leftPanel = new JPanel();
		leftPanel.setBounds(0,110,500,400);
		leftPanel.setBackground(Color.white);
		add(leftPanel);
		
		memberPanel = new PcMemberPanel();
		memberPanel.setBounds(0, 0, 500, 400);
		memberPanel.setVisible(false);
		leftPanel.add(memberPanel);
		
		pcPanel = new PCPanel();
		pcPanel.setVisible(true);
		leftPanel.add(pcPanel);
		
		image = new ImageIcon[4];
		
		image[0] = new ImageIcon("Images/사용자 설정.png");
		image[1] = new ImageIcon("Images/PC 관리.png");
		image[2] = new ImageIcon("Images/메세지.png");
		image[3] = new ImageIcon("Images/사용종료.png");
		
		btnMember = new JButton(image[0]);
		btnMember.setBorderPainted(false);
		btnMember.setContentAreaFilled(false);
		btnMember.setFocusPainted(false);
		btnMember.addActionListener(btnL);
		topPanel.add(btnMember);
		
		btnSeat = new JButton(image[1]);
		btnSeat.setBorderPainted(false);
		btnSeat.setContentAreaFilled(false);
		btnSeat.setFocusPainted(false);
		btnSeat.addActionListener(btnL);
		topPanel.add(btnSeat);
		
		btnMessage = new JButton(image[2]);
		btnMessage.setBorderPainted(false);
		btnMessage.setContentAreaFilled(false);
		btnMessage.setFocusPainted(false);
		btnMessage.addActionListener(btnL);
		topPanel.add(btnMessage);
		
		btnLogout = new JButton(image[3]);
		btnLogout.setBorderPainted(false);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setFocusPainted(false);
		btnLogout.addActionListener(btnL);
		topPanel.add(btnLogout);		
	}
	
	private class TopButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
			
			if(obj == btnMember) {
				rightPanel.setVisible(true);
				modifyPanel.setVisible(true);
				showInformationPanel.setVisible(false);
				messagePanel.setVisible(false);
				memberPanel.setVisible(true);
				pcPanel.setVisible(false);
				
			} else if(obj == btnSeat) {
				rightPanel.setVisible(true);
				modifyPanel.setVisible(false);
				showInformationPanel.setVisible(true);
				messagePanel.setVisible(false);
				memberPanel.setVisible(false);
				pcPanel.setVisible(true);
				
			} else if(obj == btnMessage) {
				rightPanel.setVisible(false);
				modifyPanel.setVisible(false);
				showInformationPanel.setVisible(false);
				messagePanel.setVisible(true);
				
			} else if(obj == btnLogout) {
				
				int result = JOptionPane.showConfirmDialog(rightPanel, "종료하시겠습니까?","알림",JOptionPane.YES_NO_OPTION);
				
				if(result == JOptionPane.YES_OPTION) {
					System.exit(1);
				}else if( result == JOptionPane.NO_OPTION) {
					
				}
			}
		}
	}
}
