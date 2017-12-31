import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientMakeUser extends JDialog {
	
	private JLabel lblTitle,lblName,lblId,lblPassword,lblPasswordCheck,lblBirth;
	protected JTextField[] txt;
	protected JButton btnOk,btnCancel;
	private Font fnt;
	
	public ClientMakeUser() {
		
		ClientAppManager.getAppManager().setClientMakeUser(this);
		setResizable(false);
		setSize(350,400);
		setLayout(null);
	
		fnt = new Font("�߳��� ��ü R",Font.BOLD,16);
		
		lblTitle = new JLabel("ȸ������ �ϱ�");
		lblTitle.setFont(new Font("�߳��� ��ü R", Font.BOLD, 20));
		lblTitle.setBounds(10,10,280,50);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times",Font.BOLD,25));
		add(lblTitle);
		
		lblId = new JLabel("���̵�");
		lblId.setFont(fnt);
		lblId.setBounds(10,70,140,30);
		add(lblId);
		
		lblPassword = new JLabel("��й�ȣ");
		lblPassword.setFont(fnt);
		lblPassword.setBounds(10,110,140,30);
		add(lblPassword);
		
		lblPasswordCheck = new JLabel("��й�ȣ Ȯ��");
		lblPasswordCheck.setFont(fnt);
		lblPasswordCheck.setBounds(10,150,140,30);
		add(lblPasswordCheck);
		
		lblName = new JLabel("�̸�");
		lblName.setFont(fnt);
		lblName.setBounds(10,190,140,30);
		add(lblName);
		
		lblBirth = new JLabel("�������");
		lblBirth.setFont(fnt);
		lblBirth.setBounds(10,230,140,30);
		add(lblBirth);
		
		txt = new JTextField[5];
		
		for(int i=0;i<5;i++) {
			txt[i] = new JTextField(5);
			txt[i].setBounds(170,70+40*i,120,30);
			add(txt[i]);
		}
		btnOk = new JButton("ȸ������");
		btnOk.setFont(fnt);
		btnOk.setBackground(Color.white);
		btnOk.setBounds(50,280,100,30);
		add(btnOk);
		
		btnCancel = new JButton("���");
		btnCancel.setFont(fnt);
		btnCancel.setBackground(Color.white);
		btnCancel.setBounds(180,280,60,30);
		add(btnCancel);
	}
	
	public void resetData() {
		for(int i = 0; i < 5; i++) txt[i].setText("");
	}
	
	public void addTOAcListener(ActionListener listener) {
		txt[0].addActionListener(listener);
		txt[1].addActionListener(listener);
		txt[2].addActionListener(listener); 
		txt[3].addActionListener(listener);
		txt[4].addActionListener(listener);
		btnOk.addActionListener(listener);
		btnCancel.addActionListener(listener);
	}
}
