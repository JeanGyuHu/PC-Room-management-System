import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.AncestorListener;

import com.google.gson.Gson;

public class ServerController{
	private ServerSocket connect = null;
	private Socket s= null;
	ArrayList<Threads> ChatThreads = new ArrayList<Threads>();
	Logger logger;
	private boolean flag = true; // �������� ���̵� �������ؼ� �α��ΰ� �α׾ƿ��� ������ �� �ֵ��� �޴� �÷���
	
	private DataHandle dataHandle;
	//private PrimaryPanel primary;
	//private PCPanel pcPanel = new PCPanel();
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	private ServerUI serverUI;
	
	private UserDAO userDAO = new UserDAO();
	private UserData userData = new UserData();
	private PcData pcData = new PcData();
	private ArrayList<String> user; // ������� ���̵� ������ �迭����Ʈ
	
	Gson gson = new Gson();
	
	public ServerController(DataHandle data, ServerUI serverUI) {
		logger = Logger.getLogger(this.getClass().getName());
		 this.serverUI = serverUI;
		 dataHandle = data;
		 
	}
	
	public void appMain() { // control
		
		// dataHandle.addObj(primary.topPanel);
		// PrimaryPanel ��ư ����
		serverUI.primary.addButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object obj = e.getSource();

				if(obj == serverUI.primary.btnMember) { // ��ü ����ڵ��� ����
					serverUI.primary.rightPanel.setVisible(true);
					serverUI.primary.modifyPanel.setVisible(true);
					serverUI.primary.showInformationPanel.setVisible(false);
					serverUI.primary.messagePanel.setVisible(false);
					serverUI.primary.memberPanel.setVisible(true);
					serverUI.primary.pcPanel.setVisible(false);
					
				} else if(obj == serverUI.primary.btnSeat) { // ����� ���� ����
					serverUI.primary.rightPanel.setVisible(true);
					serverUI.primary.modifyPanel.setVisible(false);
					serverUI.primary.showInformationPanel.setVisible(true);
					serverUI.primary.messagePanel.setVisible(false);
					serverUI.primary.memberPanel.setVisible(false);
					serverUI.primary.pcPanel.setVisible(true);
					
				} else if(obj == serverUI.primary.btnMessage) { // �޽���
					serverUI.primary.rightPanel.setVisible(false);
					serverUI.primary.modifyPanel.setVisible(false);
					serverUI.primary.showInformationPanel.setVisible(false);
					serverUI.primary.messagePanel.setVisible(true);
					
				} else if(obj == serverUI.primary.btnLogout) { // ���� â�� ����
					
					int result = JOptionPane.showConfirmDialog(serverUI.primary.topPanel, "�����Ͻðڽ��ϱ�?","�˸�",JOptionPane.YES_NO_OPTION);
					
					if(result == JOptionPane.YES_OPTION) {
						System.exit(1);
					}else if( result == JOptionPane.NO_OPTION) {
						
					}
				} else if(obj == serverUI.primary.btnCharge) { //TODO:�ð�����
					userDAO.updateUser(serverUI.primary.txtInfo[0].getText(), serverUI.primary.txtInfo[2].getText(), Integer.parseInt(serverUI.primary.txtInfo[4].getText()));
				} else if(obj == serverUI.primary.btnModify) { //TODO:����� ���� ����
					userDAO.updateUser(serverUI.primary.txtInfo[0].getText(), serverUI.primary.txtInfo[2].getText(), Integer.parseInt(serverUI.primary.txtInfo[4].getText()));
				} else if(obj == serverUI.primary.btnDelete){ //TODO:����� ����
					userDAO.delUser(serverUI.primary.memberPanel.getValue());
				} else if(obj == serverUI.primary.btnPowerOff) { //TODO:����ڿ��� �������� â ���
					
				}
			}
			
		});
		
		serverUI.primary.pcPanel.addButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object obj = e.getSource();
				for(int i = 0 ;i<24;i++) {
					if(obj == serverUI.primary.pcPanel.btnPC[i]) {
						// pc�¼� ��������
						userData = userDAO.getUser(serverUI.primary.pcPanel.lblPC[i][1].getText());
						if(userData != null) {
							serverUI.primary.lblId.setText(userData.getId());
							serverUI.primary.lblName.setText(userData.getName());
							serverUI.primary.lblPassword.setText(userData.getBirth());
							serverUI.primary.lblBirth.setText(userData.getId());
							serverUI.primary.lblTime.setText(Integer.toString(userData.getTime()));
						}
						
					}
				}
			}
		
		});
		
		//TODO:PcUserPanel�� ��ư ��� :����Ʈ�� ������ ��������, �ش� ������ �������� ���
		
		
	} // appMain()
	
	public void start() {		
		logger = Logger.getLogger(this.getClass().getName());
		
		try {
			connect = new ServerSocket(3010); 
			logger.info("Server start");
			appMain();
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
	
	// ����� ��� Ŭ���̾�Ʈ�� �޽��� �߰�
	public void msgSendAll(String msg) {
		for(Threads ct : ChatThreads) { // �������� �޽������� �޾ƿ� Ŭ���̾�Ʈ�� �޽����� ����
			ct.outMsg.println(msg);
			if(flag) { // ���� �α���, �α׾ƿ� �޽����� ��,
				ct.outMsg.println("user"); // user��� �޽��� ����
				//ct.outMsg.println(gson.toJson(userData)); // user�� �ƾƵ� �ִ� �迭�� ����
			}
		} // ������ ��� �޽����� �޾ƿ�
	}
	
	class Threads extends Thread{
		private String msg;
		
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
					flag = true; // �α׾ƿ����� �˸�
					ChatThreads.remove(this); // �����带 ��� ����
					
					/*TODO:
					 * ���������� IP�� ������ �޾ƿ�
					 * -> �ش� �ڸ��� ������ ������ �ڸ� ������ "X"�� ����
					 */
					/*for(int j=0;j<3;j++) {
				    	if(j==0)serverUI.primary.pcPanel.lblPC[���������� ��ǻ���� key][j].setText("X");
				    	else lblPC[���������� ��ǻ���� key][j].setText("");
			    	}*/
					status = false; // �α׾ƿ� �����Ƿ� ���� ���¸� false��
				} 
				
				else if(userData.getType().equals("login")) {
					// �α����� ��������
					flag = true; // �α������� �˸�
					/*TODO:
					 * ������ ������ ����Ǿ� �ִ��� Ȯ�� 
					 * -> �α��� ack��ȣ ���� 
					 * -> �α��� ��Ű�� ������ ȭ���� ���������
					 * -> �ڸ��� IP�� ��ȣ �޾ƿ�
					 * -> �ش� �ڸ��� ������ ������ ������ ���
					 */
				} 
				
				else if(userData.getType().equals("message")) {
					// �޽����� ��������
					//TODO:�̰� �ʿ��Ѱ�..
				} 
				
				else if(userData.getType().equals("make")) {
					// ȸ�������� ��������
					/*TODO:
					 * ȸ������â���� ������ �� ������ �޾ƿ�
					 * -> �ش� ������ DB�� �ִ��� Ȯ�� (���̵� �ߺ�����)
					 * -> DB�� ���� ����
					 * -> �������� ȸ������â�� �����Ŵ
					 */
				} 
				
				// �� ���� ���, �� �Ϲ� �޽����� ��
				else {
					// ä���Ҷ�
					flag = false;
					msgSendAll(msg); // �޽��� ����
				}
			} // while()
			
			// ������ ����� Ŭ���̾�Ʈ ������ ����ǹǷ� ������ ���ͷ�Ʈ
			this.interrupt();
			logger.info(this.getName() + "�����!!");
		} // run()
	} // Threads class
	

	public static void main(String[] args) {
		ServerController c = new ServerController(new DataHandle(), new ServerUI());
		c.start();
		
	} // main()
	 
	
} // ServerContrller