import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.event.AncestorListener;

import com.google.gson.Gson;

public class ServerController{
	private ServerSocket connect = null;
	private Socket s= null;
	ArrayList<Threads> ChatThreads = new ArrayList<Threads>();
	Logger logger;
	
	private DataHandle dataHandle;
	private PCPanel pcPanel;
	private PrimaryPanel primary;
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	
	Gson gson = new Gson();
	
	public static void main(String[] args) {
		ServerController c = new ServerController();
		c.start();
	} // main()
	 
	public void appMain() { // control
		//dataHandle.addObj();
		
		pcPanel.addButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object obj = e.getSource();
				for(int i = 0 ;i<24;i++) {
					if(obj == pcPanel.btnPC[i]) {
						// pc�¼� ��������
					}
				}
			}
		
		});
		
		// PrimaryPanel ��ư ����
		primary.addButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object obj = e.getSource();

				if(obj == primary.btnMember) {
					primary.rightPanel.setVisible(true);
					primary.modifyPanel.setVisible(true);
					primary.showInformationPanel.setVisible(false);
					primary.messagePanel.setVisible(false);
					primary.memberPanel.setVisible(true);
					pcPanel.setVisible(false);
					
				} else if(obj == primary.btnSeat) {
					primary.rightPanel.setVisible(true);
					primary.modifyPanel.setVisible(false);
					primary.showInformationPanel.setVisible(true);
					primary.messagePanel.setVisible(false);
					primary.memberPanel.setVisible(false);
					pcPanel.setVisible(true);
					
				} else if(obj == primary.btnMessage) {
					primary.rightPanel.setVisible(false);
					primary.modifyPanel.setVisible(false);
					primary.showInformationPanel.setVisible(false);
					primary.messagePanel.setVisible(true);
					
				} else if(obj == primary.btnLogout) {
					
					int result = JOptionPane.showConfirmDialog(primary.topPanel, "�����Ͻðڽ��ϱ�?","�˸�",JOptionPane.YES_NO_OPTION);
					
					if(result == JOptionPane.YES_OPTION) {
						System.exit(1);
					}else if( result == JOptionPane.NO_OPTION) {
						
					}
				}
			}
			
		});
		
	} // appMain()
	
	public void start() {
		logger = Logger.getLogger(this.getClass().getName());
		
		try {
			connect = new ServerSocket(3010); 
			logger.info("Server start");
			
			while(true) {
				s = connect.accept(); // ��������� ����Ǹ� ��ż��Ͽ� Ŭ���̾�Ʈ�� ���ϰ� ������
				// ����� Ŭ���̾�Ʈ�� ���� ������ Ŭ���� ����
				Threads chat = new Threads();
				// Ŭ���̾�Ʈ ����Ʈ �߰�
				ChatThreads.add(chat);
				//������ ����
				chat.start();
			}
		}catch(Exception e) {
			logger.info("[Server]Start() Exception �߻�!!");
			e.printStackTrace();
		}
	} // start()
	
	class Threads extends Thread{
		private String msg;
		private UserData userData = new UserData();
		private PcData pcData = new PcData();
		
		private BufferedReader inMsg = null;
		private PrintWriter outMsg = null;
		private boolean status = false;
		
		public void run() {
			status = true; // �α��������Ƿ� �����¸� true��
			try {
				// ��ż����� ��Ʈ���� �޾� ����� ��Ʈ�� ����
				inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
				outMsg = new PrintWriter(s.getOutputStream(), true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// ���� ������ true�̸� ������ ���鼭 ����ڿ��Լ� ���ŵ� �޽��� ó��
			while(status) {
				
				// ���ŵ� �޽����� msg ������ ����
				try {
					msg = inMsg.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// JSON �޽����� UserData ��ü�� ����
				userData = gson.fromJson(msg, UserData.class);

				if(userData.getType().equals("logout")) {
					// ������Ḧ ��������				
				} 
				
				else if(userData.getType().equals("login")) {
					// �α����� ��������
				} 
				
				else if(userData.getType().equals("message")) {
					// �޽����� ��������
				} 
				
				else if(userData.getType().equals("make")) {
					// ȸ�������� ��������
				} 
				
				// �� ���� ���, �� �Ϲ� �޽����� ��
				else {
					// ä���Ҷ�
				}
			} // while()
			
			// ������ ����� Ŭ���̾�Ʈ ������ ����ǹǷ� ������ ���ͷ�Ʈ
			this.interrupt();
			logger.info(this.getName() + "�����!!");
		} // run()
	} // Threads class
	
} // ServerContrller