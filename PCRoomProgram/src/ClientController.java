import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import com.google.gson.Gson;

import java.awt.*;

public class ClientController implements Runnable {
	
	private ClientUI ui;
	private Socket socket;
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	private Thread thread;
	private Gson gson;
	private ClientLoginPanel loginPanel;
	private ClientMakeUser makeUser;
	private ClientUserStatus userStatus;
	private ClientChatWindow chatWindow;
	private AcListener acL;
	
	public static void main(String[] args) {
		ClientController a = new ClientController();
	}
	
	public ClientController() {
		ui = new ClientUI();
		loginPanel = ClientAppManager.getAppManager().getClientLoginPanel();
		makeUser = ClientAppManager.getAppManager().getClientMakeUser();
		userStatus = ClientAppManager.getAppManager().getClientUserStatus();
		chatWindow = ClientAppManager.getAppManager().getClientChatWindow();
		
		acL = new AcListener();
		
		loginPanel.addTOAcListener(acL);
		userStatus.addTOAcListener(acL);
		chatWindow.addTOAcListener(acL);
		makeUser.addTOAcListener(acL);
	}
	
	public void AppMain() {
		
	}
	
	public void ConnectServer(){
		
		try {
			socket = new Socket("127.0.0.1", 1593);// 
			
			inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outMsg = new PrintWriter(socket.getOutputStream(), true);
			
			thread = new Thread(this);
			thread.start();
			
		}catch (Exception e) { e.printStackTrace();}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	
	private class AcListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == loginPanel.btnInsert)	{//ȸ�� ������ ���� ���
				makeUser.show();
			}else if(obj == loginPanel.btnLogin) {// �α��� ��ư�� ���� ���
				//..... ������ �ش� �α��� ������ �ִ��� Ȯ��
				//Ʋ�� ��� ��� �޼��� �����ֱ�
				
				//�α��ο� ������ ���
				//userStatus.LoginUser(userName, remainTime); ������ ���̽��� �о�� ������ �Ѱ��־� �ʱ���¸� �Ѱ��ֱ�
				ui.changLogin();
			}else if(obj == makeUser.btnOk) {
				//..... ������� �ִ���
				//..... ���̵� �ߺ� üũ ������ Ȯ��
				//..... ��й�ȣ �ߺ� üũ
				//..... �� ������ ������ Ȯ�� �޼ҵ�� ����
				makeUser.resetData();
				makeUser.dispose();
			}else if(obj == makeUser.btnCancel) {
				makeUser.resetData();
				makeUser.dispose();
			}else if(obj == userStatus.btnLogout) {
				if(userStatus.LogOutCheack())
					ui.changStart();
			}else if(obj == userStatus.btnMessage) {
				chatWindow.show();
			}else if(obj == chatWindow.btnExit) {
				chatWindow.dispose();
			}else if (obj == chatWindow.msgInput) {
				//������ ���� �޼����� �о�� �Ŀ� �޼��� ����
				//chatWindow.msgOut.append(arg0);
			}
		}
	}
}
