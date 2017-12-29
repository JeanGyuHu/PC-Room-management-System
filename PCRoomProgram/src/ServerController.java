import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gson.Gson;

public class ServerController{
	private ServerSocket connect = null;
	private Socket s= null;
	ArrayList<Threads> ChatThreads = new ArrayList<Threads>();
	Logger logger;
	
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	
	Gson gson = new Gson();
	
	public static void main(String[] args) {
		ServerController c = new ServerController();
		c.start();
	}
	
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
	}
	
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
		}
	}
	
}