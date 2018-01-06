import java.awt.*;
import javax.swing.*;

public class ClientEndMessage extends JDialog{
	
	private JPanel pan; // dialog에 넣을 메인 패널
	private JLabel lblMessage; // 사용종료 메시지를 띄울 라벨
	
	public ClientEndMessage() {
		ClientAppManager.getAppManager().setClientEndMessage(this);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // 윈도우 창의 x버튼을 비활성화
		
		setTitle("warning");
		setResizable(false);
		setSize(1500,1000);
		
		pan = new JPanel();
		pan.setBackground(Color.blue);
		pan.setLayout(new GridLayout(1,1));
		add(pan);
		
		lblMessage = new JLabel("사용을 종료하십시오.");
		lblMessage.setFont(new Font("AnonymousPro",Font.BOLD,30));
		lblMessage.setForeground(Color.white);
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);		
		lblMessage.setVerticalAlignment(SwingConstants.CENTER);
		pan.add(lblMessage);
		
		
	}

}
