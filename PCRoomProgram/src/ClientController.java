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
		String msg;
		try{
			msg = inMsg.readLine();
			
		
		}catch(Exception e) {e.printStackTrace();}
	}
	
	private class AcListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == loginPanel.btnInsert)	{//ȸ�� ������ ���� ���
				makeUser.show();
			}else if(obj == loginPanel.btnLogin) {// �α��� ��ư�� ���� ���
				ConnectServer();
				Message msg = new Message("login", loginPanel.txtID.getText(), loginPanel.txtPass.getText(),
						"","",0,"");
				//..... ������ �ش� �α��� ������ �ִ��� Ȯ��
				//Ʋ�� ��� ��� �޼��� �����ֱ�
				//���̵� ���� ���
				JOptionPane.showConfirmDialog(makeUser, "�������� �ʴ� ���̵��Դϴ�!!","�˸�",JOptionPane.CLOSED_OPTION);
				//���̵�� ���� ��й�ȣ Ʋ�����
				JOptionPane.showConfirmDialog(makeUser, "��й�ȣ�� Ʋ�Ƚ��ϴ�!!","�˸�",JOptionPane.CLOSED_OPTION);
				//�α��ο� ������ ���
				//userStatus.LoginUser(userName, remainTime); ������ ���̽��� �о�� ������ �Ѱ��־� �ʱ���¸� �Ѱ��ֱ�
				ui.changLogin();
			}else if(obj == makeUser.btnOk) {
				ConnectServer();
				//..... ������� �ִ���
				Message msg = new Message();
				for(int i = 0; i < 5; i++) {
					if( makeUser.txt[i].getText().equals("") ) {
						JOptionPane.showConfirmDialog(makeUser, "������ �� �����ּ���!!","�˸�",JOptionPane.CLOSED_OPTION);
						break;
					}
				}
				//..... ���̵� �ߺ� üũ ������ Ȯ��
				msg.setType("idcheak");
				msg.setId(makeUser.txt[0].getText());
				outMsg.println(gson.toJson(msg));
				JOptionPane.showConfirmDialog(makeUser, "�̹� �ִ� ���̵��Դϴ�!!","�˸�",JOptionPane.CLOSED_OPTION);
				
				//..... ��й�ȣ �ߺ� üũ
				//..... �� ������ ������ Ȯ�� �޼ҵ�� ����
				//Message msg = new Message("makeuser", makeUser.txt[0].getText(), makeUser.txt[1].getText() ,
				//	makeUser.txt[3].getText(),makeUser.txt[4].getText(),0,"");
				
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
