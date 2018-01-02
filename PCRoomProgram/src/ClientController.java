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
		
		gson = new Gson();
	}
	
	public void AppMain() {
		
	}
	
	public void ConnectServer(){
		
		try {
			socket = new Socket("127.0.0.1", 3010);// 
			
			inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outMsg = new PrintWriter(socket.getOutputStream(), true);
			
			System.out.println("연결성공");
			
			thread = new Thread(this);
			thread.start();
			
		}catch (Exception e) { e.printStackTrace();}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String msg;
		try{
			//msg = inMsg.readLine();
			
		
		}catch(Exception e) {e.printStackTrace();}
	}
	
	private class AcListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == loginPanel.btnInsert)	{//회원 가입을 누른 경우
				makeUser.show();
			}else if(obj == loginPanel.btnLogin) {// 로그인 버튼을 누른 경우
				ConnectServer();
				Message msg = new Message();
				String m = null;
				msg.setType("login");
				if(loginPanel.txtID.getText().equals(""))
					JOptionPane.showConfirmDialog(loginPanel, "아이디를 입력해주세요!!","알림",JOptionPane.CLOSED_OPTION);
				else if(loginPanel.txtPass.getText().equals(""))
					JOptionPane.showConfirmDialog(loginPanel, "비밀번호를 입력해주세요!!","알림",JOptionPane.CLOSED_OPTION);
				else {
					msg.setPassword(loginPanel.txtPass.getText());
					msg.setId(loginPanel.txtID.getText());
					outMsg.println(gson.toJson(msg));
					try {
						m = inMsg.readLine();
						msg = gson.fromJson(m, Message.class);
					}catch(Exception ex) {}
					if(msg.getType().equals("accept")) {
						userStatus.LoginUser(msg.getName(), String.valueOf(msg.getTime()));// 데이터 베이스로 읽어온 데이터 넘겨주어 초기상태를 넘겨주기
						loginPanel.txtID.setText("");
						loginPanel.txtPass.setText("");
						ui.changLogin();//로그인에 성공한 경우
					}else if(msg.getType().equals("notime")) { 
						JOptionPane.showConfirmDialog(loginPanel, "남은 시간이 없습니다!!","알림",JOptionPane.CLOSED_OPTION);
					}else if(msg.getType().equals("noid")) {
						JOptionPane.showConfirmDialog(loginPanel, "존재하지 않는 아이디입니다!!","알림",JOptionPane.CLOSED_OPTION);
						loginPanel.txtPass.setText("");
						loginPanel.txtID.setText("");
						//아이디가 없는 경우
					}else if(msg.getType().equals("diffpass")){
						JOptionPane.showConfirmDialog(loginPanel, "비밀번호가 틀렸습니다!!","알림",JOptionPane.CLOSED_OPTION);
						loginPanel.txtPass.setText("");
						//비밀번호가 틀린경우
					}else if(msg.getType().equals("already")) {
						JOptionPane.showConfirmDialog(loginPanel, "이미 로그인 되어 있습니다!!","알림",JOptionPane.CLOSED_OPTION);
					}	
				}//else
			}else if(obj == makeUser.btnOk) {
				ConnectServer();
				String m;
				Message msg = new Message();
				boolean flag = false;
				for(int i = 0; i < 3; i++) {
					if( makeUser.txt[i].getText().equals("") ) {
						JOptionPane.showConfirmDialog(makeUser, "정보를 다 적어주세요!!","알림",JOptionPane.CLOSED_OPTION);
						flag = false;
						break;
					}
					else
						flag = true;
				}
				if(flag && makeUser.password.getText().equals(makeUser.checkpassword.getText())) {
					msg.setType("makeuser");
					msg.setId(makeUser.txt[0].getText());
					msg.setPassword(makeUser.password.getText());
					msg.setName(makeUser.txt[1].getText());
					msg.setBirth(makeUser.txt[2].getText());
					outMsg.println(gson.toJson(msg));
					
					try {
						m = inMsg.readLine();
						msg = gson.fromJson(m, Message.class);
						if(msg.getType().equals("accept")) {

							makeUser.resetData();
							makeUser.dispose();
						}
						else if(msg.getType().equals("already"))
							JOptionPane.showConfirmDialog(makeUser, "이미 있는 아이디입니다!!","알림",JOptionPane.CLOSED_OPTION);
						else if(msg.getType().equals("fail"))
							JOptionPane.showConfirmDialog(makeUser, "회원가입에 실패했습니다!!","알림",JOptionPane.CLOSED_OPTION);
					}catch(Exception ex) {}
				
				}//if
				else if(flag){
					JOptionPane.showConfirmDialog(makeUser, "비밀번호가 같지 않습니다!!","알림",JOptionPane.CLOSED_OPTION);
				}//else
				
				//..... 비밀번호 중복 체크
				//..... 빈 공간이 없는지 확인 메소드로 구현
				//Message msg = new Message("makeuser", makeUser.txt[0].getText(), makeUser.txt[1].getText() ,
				//	makeUser.txt[3].getText(),makeUser.txt[4].getText(),0,"");
			}else if(obj == makeUser.btnCancel) {
				makeUser.resetData();
				makeUser.dispose();
			}else if(obj == userStatus.btnLogout) {
				if(userStatus.LogOutCheack()) {
					Message msg = new Message();
					msg.setType("logout");
					msg.setId(userStatus.txtUserId.getText());
					outMsg.println(gson.toJson(msg));
					ui.changStart();
				}
					
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
