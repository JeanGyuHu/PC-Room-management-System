import java.awt.event.*;

import java.io.*;
import java.net.*;

import javax.swing.*;

import com.google.gson.Gson;

import java.awt.*;
import java.util.*;
import java.util.Timer;

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
   private ClientEndMessage warning;
   private AcListener acL;
   private boolean loginFlag = false;
   
   private Timer timer;

   int num;
   
   public static void main(String[] args) {
      ClientController a = new ClientController();
   }
   
   public ClientController() {
      ui = new ClientUI();
      loginPanel = ClientAppManager.getAppManager().getClientLoginPanel();
      makeUser = ClientAppManager.getAppManager().getClientMakeUser();
      userStatus = ClientAppManager.getAppManager().getClientUserStatus();
      chatWindow = ClientAppManager.getAppManager().getClientChatWindow();
      warning = ClientAppManager.getAppManager().getClientEndMessage();
      acL = new AcListener();
      
      loginPanel.addTOAcListener(acL);
      userStatus.addTOAcListener(acL);
      chatWindow.addTOAcListener(acL);
      makeUser.addTOAcListener(acL);
      
      gson = new Gson();
      // 데이터 생성
   }// ClientController()
   
   public void ConnectServer(){
      
      try {
         socket = new Socket("127.0.0.1", 3010); 
         
         inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         outMsg = new PrintWriter(socket.getOutputStream(), true);
         
         thread = new Thread(this);// 스레드를 생성 후에 바로 start 하지 않고 로그인 후에 채팅을 할 때에 start  
         
      }catch (Exception e) { e.printStackTrace();}
      
   }
   
   @Override
   public void run() {
      // TODO Auto-generated method stub
      String msg;
      Message m;
      try{
    	  
    	  while(loginFlag) {// 로그인이 된 경우
    		  msg = inMsg.readLine();// 서버로부터 메세지를 읽어온 후에 
    		  m = gson.fromJson(msg, Message.class);// Gson으로 메세지를 파싱
    		  if(m.getType().equals("warning")) {//만약 넘어온 메세지의 타입이 warning인 경우에 사용종료하라는 창을 띄워줌
    			  System.out.println("오그아웃");
    			  warning.show();
    		  }else if(m.getType().equals("exit")) {
    			  loginFlag = false;
                  chatWindow.msgOut.setText("");// 여태까지의 메세지 내용을 초기화
                  ui.changStart();
    		  }else if(m.getType().equals("changTime")){
    			  num = m.getTime();
    			  System.out.println("chag");
    			  userStatus.LoginUser(m.getId(), String.valueOf(m.getTime()));
    		  }else {
    			  System.out.println(m.getType());
    			  chatWindow.msgOut.append("안 사장님  >> " + m.getMessage() + "\n");// txtArea에 읽어온 메세지를 사용자에게 표시
        		  chatWindow.msgOut.setCaretPosition(chatWindow.msgOut.getDocument().getLength());// txtArea의 제일 마지막 부분을 사용자가 볼 수 있게 하는 것 
    		  }
    	  }
      
      }catch(Exception e) {e.printStackTrace();}
   }
   
   private class AcListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         Object obj = e.getSource();
         if(obj == loginPanel.btnInsert)   {//회원 가입을 누른 경우
            makeUser.show();
         }else if(obj == loginPanel.btnLogin) {// 로그인 버튼을 누른 경우
            ConnectServer();// 서버와 연결
            Message msg = new Message();
            String m = null;
            msg.setType("login");
            if(loginPanel.txtID.getText().equals(""))// 아이디 입력 창에 아이디를 입력하지 않은 경우
               JOptionPane.showConfirmDialog(loginPanel, "아이디를 입력해주세요!!","알림",JOptionPane.CLOSED_OPTION);
            else if(loginPanel.txtPass.getText().equals(""))// 비밀번호 입력 창에 비밀번호를 입력하지 않은 경우
               JOptionPane.showConfirmDialog(loginPanel, "비밀번호를 입력해주세요!!","알림",JOptionPane.CLOSED_OPTION);
            else {
               msg.setPassword(loginPanel.txtPass.getText());
               msg.setId(loginPanel.txtID.getText());
               outMsg.println(gson.toJson(msg));// 클라이언트가 입력한 아이디와 비밀번호를 서버로 넘겨줌
               
               try {
                  m = inMsg.readLine();
                  msg = gson.fromJson(m, Message.class);
                  // 메세지를 하나 읽어오고 Gson으로 파싱
               }catch(Exception ex) {}
               if(msg.getType().equals("accept")) {// 만약 넘어온 메세지 타입이 accept이면 로그인에 성공
                  userStatus.LoginUser(msg.getId(), String.valueOf(msg.getTime()));// 데이터 베이스로 읽어온 데이터 넘겨주어 초기상태를 넘겨주기
                  loginPanel.txtID.setText("");
                  loginPanel.txtPass.setText("");             
                  loginFlag = true;// 플래그를 트루로 변경
                  thread.start();// 메세지를 주고 받을 수 있는 상태를 만들어줌
                  ui.changLogin();//로그인 화면을 클라이언트에게 보여줌
                  
                  num = msg.getTime();
                  timer = new Timer();
                  TimerTask timerTask = new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message msg = new Message();
						if(num == 0) {
							
				               msg.setType("logout");
				               msg.setId(userStatus.txtUserId.getText());
				               msg.setTime(0);
				               outMsg.println(gson.toJson(msg));//서버로 로그아웃 메세지를 넘겨줌
				               loginFlag = false;
				               chatWindow.msgOut.setText("");// 여태까지의 메세지 내용을 초기화
				               ui.changStart();
				               warning.dispose();
				               chatWindow.dispose();
						}else {
							num--;	//60초에 1씩 시간을 줄인다 시간이 전부 분으로 표현되어있음
							msg.setType("time");					//시간이 없음으로 서버에 보낸다.
							msg.setId(userStatus.txtUserId.getText());	
							msg.setTime(num);
	               			userStatus.txtUserRemainTime.setText(String.valueOf(num));		//텍스트필드의 떠있는 숫자를 변화시킨다.
	               			outMsg.println(gson.toJson(msg));
	               		 }
	               		 
					}
				};
				timer.schedule(timerTask, 60000,60000);
               }else if(msg.getType().equals("notime")) {// 시간이 없는 경우 
                  JOptionPane.showConfirmDialog(loginPanel, "남은 시간이 없습니다!!","알림",JOptionPane.CLOSED_OPTION);
               }else if(msg.getType().equals("noid")) {// 아이디가 존재하지 않는 경우
                  JOptionPane.showConfirmDialog(loginPanel, "존재하지 않는 아이디입니다!!","알림",JOptionPane.CLOSED_OPTION);
                  loginPanel.txtPass.setText("");
                  loginPanel.txtID.setText("");
               }else if(msg.getType().equals("diffpass")){// 비밀번호를 틀린 경우
                  JOptionPane.showConfirmDialog(loginPanel, "비밀번호가 틀렸습니다!!","알림",JOptionPane.CLOSED_OPTION);
                  loginPanel.txtPass.setText("");
               }else if(msg.getType().equals("already")) {// 이미 다른 곳에서 로그인이 되어있는 경우
                  JOptionPane.showConfirmDialog(loginPanel, "이미 로그인 되어 있습니다!!","알림",JOptionPane.CLOSED_OPTION);
               }   
            }//else
         }else if(obj == makeUser.btnOk) {//회원 가입 버튼을 누른 경우
            ConnectServer();// 서버와 연결
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
            }// 정보 입력란에 빠진 곳이 있다면 flag를 false로 두어서 다른 작업을 수행하지 못하게 함
            
            if(flag && makeUser.password.getText().equals(makeUser.checkpassword.getText())) {
            	// 비밀번호를 맞게 입력한 경우
               msg.setType("makeuser");
               msg.setId(makeUser.txt[0].getText());
               msg.setPassword(makeUser.password.getText());
               msg.setName(makeUser.txt[1].getText());
               msg.setBirth(makeUser.txt[2].getText());
               outMsg.println(gson.toJson(msg));// 클라이언트가 입력한 정보를 서버로 보내줌
               
               try {
                  m = inMsg.readLine();
                  msg = gson.fromJson(m, Message.class);
                  // 서버로 정보를 하나 읽어옴
                  if(msg.getType().equals("accept")) {// 만약 회원가입에 성공한 경우
                     makeUser.resetData();
                     makeUser.dispose();
                  }
                  else if(msg.getType().equals("already"))// 이미 아이디가 있는 경우
                     JOptionPane.showConfirmDialog(makeUser, "이미 있는 아이디입니다!!","알림",JOptionPane.CLOSED_OPTION);
                  else if(msg.getType().equals("fail"))// 나머지 다른 이유에서 회원가입에 실패한 경우
                     JOptionPane.showConfirmDialog(makeUser, "회원가입에 실패했습니다!!","알림",JOptionPane.CLOSED_OPTION);
               }catch(Exception ex) {}
            
            }//if
            else if(flag){// 만약 클라이언트가 입력한 비밀번호와 비밀번호 재확인을 위해 입력한 값이 다른 경우 
               JOptionPane.showConfirmDialog(makeUser, "비밀번호가 같지 않습니다!!","알림",JOptionPane.CLOSED_OPTION);
            }//else
         }else if(obj == makeUser.btnCancel) {// 회원가입 종료 버튼을 누른 경우 창의 값들을 초기화 후 창을 닫음
            makeUser.resetData();
            makeUser.dispose();
         }else if(obj == userStatus.btnLogout) {// 사용종료 버튼을 누른 경우
            if(userStatus.LogOutCheack()) {// JOptionPane을 보여주고 클라이언트에게 재확인 후에 만약 로그아웃을 누른다면
               Message msg = new Message();
               msg.setType("logout");
               msg.setId(userStatus.txtUserId.getText());
               outMsg.println(gson.toJson(msg));//서버로 로그아웃 메세지를 넘겨줌
               loginFlag = false;
               timer.cancel();
               chatWindow.msgOut.setText("");// 여태까지의 메세지 내용을 초기화
               ui.changStart();
               warning.dispose();
               chatWindow.dispose();
            }
               
         }else if(obj == userStatus.btnMessage) {
            chatWindow.show();// 메세지 창을 클라이언트에게 보여줌
         }else if(obj == chatWindow.btnExit) {
            chatWindow.dispose();// 메세지 창을 닫음
         }else if (obj == chatWindow.msgInput) {
        	 //클라이언트가 입력한 메세지를 서버로 전송
        	 outMsg.println(gson.toJson(new Message("message","","","","",0,chatWindow.msgInput.getText())));
        	 chatWindow.msgOut.append("나  >> " + chatWindow.msgInput.getText() + "\n");// 입력한 메세지를 클라이언트에게도 보여줌
        	 chatWindow.msgInput.setText("");
         }
      }
   }
}