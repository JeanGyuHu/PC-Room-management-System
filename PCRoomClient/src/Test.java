import javax.swing.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		LoginPanel login = new LoginPanel();
		frame.getContentPane().add(login);
		
		frame.pack();
		frame.setVisible(true);
	}

}
