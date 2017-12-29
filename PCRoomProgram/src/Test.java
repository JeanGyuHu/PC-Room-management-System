import javax.swing.JFrame;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		//PrimaryPanel primary = new PrimaryPanel();
		
		//frame.getContentPane().add(primary);
		PcMemberPanel memberPanel = new PcMemberPanel(); 
		frame.getContentPane().add(memberPanel);
		frame.pack();
		frame.setVisible(true);
	}

}
