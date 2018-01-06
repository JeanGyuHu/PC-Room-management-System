import javax.swing.JFrame;

public class ServerUI { // 서버의 전체적인 UI를 띄울 매인 클래스

	protected static PrimaryPanel primary;
	
	public ServerUI() {
		// TODO Auto-generated method stub
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); // 화면 크기 수정 불가
		primary = new PrimaryPanel();
		frame.getContentPane().add(primary);
		
		frame.pack();
		frame.setVisible(true);
	}

}