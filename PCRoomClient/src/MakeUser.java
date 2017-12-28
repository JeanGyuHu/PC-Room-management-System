import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MakeUser extends JDialog {

	private JLabel lblTitle,lblName,lblId,lblPassword,lblPasswordCheck,lblBirth;
	private JTextField[] txt;
	private JButton btnOk,btnCancel;
	
	public MakeUser() {
		
		setSize(350,400);
		//setPreferredSize(new Dimension(300,500));
		setLayout(null);
		
		lblTitle = new JLabel("ȸ������ �ϱ�");
		lblTitle.setBounds(10,10,280,50);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times",Font.BOLD,25));
		add(lblTitle);
		
		lblId = new JLabel("���̵�");
		lblId.setBounds(10,70,140,30);
		add(lblId);
		
		lblPassword = new JLabel("��й�ȣ");
		lblPassword.setBounds(10,110,140,30);
		add(lblPassword);
		
		lblPasswordCheck = new JLabel("��й�ȣ Ȯ��");
		lblPasswordCheck.setBounds(10,150,140,30);
		add(lblPasswordCheck);
		
		lblName = new JLabel("�̸�");
		lblName.setBounds(10,190,140,30);
		add(lblName);
		
		lblBirth = new JLabel("�������");
		lblBirth.setBounds(10,230,140,30);
		add(lblBirth);
		
		txt = new JTextField[5];
		
		for(int i=0;i<5;i++) {
			txt[i] = new JTextField(5);
			txt[i].setBounds(170,70+40*i,120,30);
			add(txt[i]);
		}
		btnOk = new JButton("ȸ������");
		btnOk.setBounds(50,280,100,30);
		add(btnOk);
		
		btnCancel = new JButton("���");
		btnCancel.setBounds(180,280,60,30);
		add(btnCancel);
	}
}
