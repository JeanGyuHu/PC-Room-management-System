import javax.swing.JFrame;

public class ServerUI { // ������ ��ü���� UI�� ��� ���� Ŭ����

	protected static PrimaryPanel primary;
	
	public ServerUI() {
		// TODO Auto-generated method stub
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // ȭ�� ũ�� ���� �Ұ�
		primary = new PrimaryPanel();
		frame.getContentPane().add(primary);
		
		frame.pack();
		frame.setVisible(true);
	}

}