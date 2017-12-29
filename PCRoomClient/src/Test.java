import javax.swing.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		LoginPanel loginPanel = new LoginPanel();
		frame.getContentPane().add(loginPanel);
		
		frame.pack();
		frame.setVisible(true);
		
	}

}
