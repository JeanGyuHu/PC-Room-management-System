import javax.swing.*;

public class ClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("클라이언트 v.0310");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ClientShowPanel showPanel = new ClientShowPanel();
		frame.getContentPane().add(showPanel);
		
		frame.pack();
		frame.setVisible(true);
	}

}
