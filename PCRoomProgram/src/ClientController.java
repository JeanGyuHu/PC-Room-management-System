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
			if(obj == loginPanel.btnInsert)	{//회원 가입을 누른 경우
				makeUser.show();
			}else if(obj == loginPanel.btnLogin) {// 로그인 버튼을 누른 경우
				ConnectServer();
				Message msg = new Message("login", loginPanel.txtID.getText(), loginPanel.txtPass.getText(),
						"","",0,"");
				//..... 서버로 해당 로그인 정보가 있는지 확인
				//틀린 경우 경고 메세지 보여주기
				//아이디가 없는 경우
				JOptionPane.showConfirmDialog(makeUser, "존재하지 않는 아이디입니다!!","알림",JOptionPane.CLOSED_OPTION);
				//아이디는 존재 비밀번호 틀린경우
				JOptionPane.showConfirmDialog(makeUser, "비밀번호가 틀렸습니다!!","알림",JOptionPane.CLOSED_OPTION);
				//로그인에 성공한 경우
				//userStatus.LoginUser(userName, remainTime); 데이터 베이스로 읽어온 데이터 넘겨주어 초기상태를 넘겨주기
				ui.changLogin();
			}else if(obj == makeUser.btnOk) {
				ConnectServer();
				//..... 빈공간이 있는지
				Message msg = new Message();
				for(int i = 0; i < 5; i++) {
					if( makeUser.txt[i].getText().equals("") ) {
						JOptionPane.showConfirmDialog(makeUser, "정보를 다 적어주세요!!","알림",JOptionPane.CLOSED_OPTION);
						break;
					}
				}
				//..... 아이디 중복 체크 서버로 확인
				msg.setType("idcheak");
				msg.setId(makeUser.txt[0].getText());
				outMsg.println(gson.toJson(msg));
				JOptionPane.showConfirmDialog(makeUser, "이미 있는 아이디입니다!!","알림",JOptionPane.CLOSED_OPTION);
				
				//..... 비밀번호 중복 체크
				//..... 빈 공간이 없는지 확인 메소드로 구현
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
				//서버로 부터 메세지를 읽어온 후에 메세지 갱신
				//chatWindow.msgOut.append(arg0);
			}
		}
	}
}
