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
      // ������ ����
   }// ClientController()
   
   public void ConnectServer(){
      
      try {
         socket = new Socket("127.0.0.1", 3010); 
         
         inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         outMsg = new PrintWriter(socket.getOutputStream(), true);
         
         thread = new Thread(this);// �����带 ���� �Ŀ� �ٷ� start ���� �ʰ� �α��� �Ŀ� ä���� �� ���� start  
         
      }catch (Exception e) { e.printStackTrace();}
      
   }
   
   @Override
   public void run() {
      // TODO Auto-generated method stub
      String msg;
      Message m;
      try{
    	  
    	  while(loginFlag) {// �α����� �� ���
    		  msg = inMsg.readLine();// �����κ��� �޼����� �о�� �Ŀ� 
    		  m = gson.fromJson(msg, Message.class);// Gson���� �޼����� �Ľ�
    		  if(m.getType().equals("warning")) {//���� �Ѿ�� �޼����� Ÿ���� warning�� ��쿡 ��������϶�� â�� �����
    			  System.out.println("���׾ƿ�");
    			  warning.show();
    		  }else if(m.getType().equals("exit")) {
    			  loginFlag = false;
                  chatWindow.msgOut.setText("");// ���±����� �޼��� ������ �ʱ�ȭ
                  ui.changStart();
    		  }else if(m.getType().equals("changTime")){
    			  num = m.getTime();
    			  System.out.println("chag");
    			  userStatus.LoginUser(m.getId(), String.valueOf(m.getTime()));
    		  }else {
    			  System.out.println(m.getType());
    			  chatWindow.msgOut.append("�� �����  >> " + m.getMessage() + "\n");// txtArea�� �о�� �޼����� ����ڿ��� ǥ��
        		  chatWindow.msgOut.setCaretPosition(chatWindow.msgOut.getDocument().getLength());// txtArea�� ���� ������ �κ��� ����ڰ� �� �� �ְ� �ϴ� �� 
    		  }
    	  }
      
      }catch(Exception e) {e.printStackTrace();}
   }
   
   private class AcListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         Object obj = e.getSource();
         if(obj == loginPanel.btnInsert)   {//ȸ�� ������ ���� ���
            makeUser.show();
         }else if(obj == loginPanel.btnLogin) {// �α��� ��ư�� ���� ���
            ConnectServer();// ������ ����
            Message msg = new Message();
            String m = null;
            msg.setType("login");
            if(loginPanel.txtID.getText().equals(""))// ���̵� �Է� â�� ���̵� �Է����� ���� ���
               JOptionPane.showConfirmDialog(loginPanel, "���̵� �Է����ּ���!!","�˸�",JOptionPane.CLOSED_OPTION);
            else if(loginPanel.txtPass.getText().equals(""))// ��й�ȣ �Է� â�� ��й�ȣ�� �Է����� ���� ���
               JOptionPane.showConfirmDialog(loginPanel, "��й�ȣ�� �Է����ּ���!!","�˸�",JOptionPane.CLOSED_OPTION);
            else {
               msg.setPassword(loginPanel.txtPass.getText());
               msg.setId(loginPanel.txtID.getText());
               outMsg.println(gson.toJson(msg));// Ŭ���̾�Ʈ�� �Է��� ���̵�� ��й�ȣ�� ������ �Ѱ���
               
               try {
                  m = inMsg.readLine();
                  msg = gson.fromJson(m, Message.class);
                  // �޼����� �ϳ� �о���� Gson���� �Ľ�
               }catch(Exception ex) {}
               if(msg.getType().equals("accept")) {// ���� �Ѿ�� �޼��� Ÿ���� accept�̸� �α��ο� ����
                  userStatus.LoginUser(msg.getId(), String.valueOf(msg.getTime()));// ������ ���̽��� �о�� ������ �Ѱ��־� �ʱ���¸� �Ѱ��ֱ�
                  loginPanel.txtID.setText("");
                  loginPanel.txtPass.setText("");             
                  loginFlag = true;// �÷��׸� Ʈ��� ����
                  thread.start();// �޼����� �ְ� ���� �� �ִ� ���¸� �������
                  ui.changLogin();//�α��� ȭ���� Ŭ���̾�Ʈ���� ������
                  
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
				               outMsg.println(gson.toJson(msg));//������ �α׾ƿ� �޼����� �Ѱ���
				               loginFlag = false;
				               chatWindow.msgOut.setText("");// ���±����� �޼��� ������ �ʱ�ȭ
				               ui.changStart();
				               warning.dispose();
				               chatWindow.dispose();
						}else {
							num--;	//60�ʿ� 1�� �ð��� ���δ� �ð��� ���� ������ ǥ���Ǿ�����
							msg.setType("time");					//�ð��� �������� ������ ������.
							msg.setId(userStatus.txtUserId.getText());	
							msg.setTime(num);
	               			userStatus.txtUserRemainTime.setText(String.valueOf(num));		//�ؽ�Ʈ�ʵ��� ���ִ� ���ڸ� ��ȭ��Ų��.
	               			outMsg.println(gson.toJson(msg));
	               		 }
	               		 
					}
				};
				timer.schedule(timerTask, 60000,60000);
               }else if(msg.getType().equals("notime")) {// �ð��� ���� ��� 
                  JOptionPane.showConfirmDialog(loginPanel, "���� �ð��� �����ϴ�!!","�˸�",JOptionPane.CLOSED_OPTION);
               }else if(msg.getType().equals("noid")) {// ���̵� �������� �ʴ� ���
                  JOptionPane.showConfirmDialog(loginPanel, "�������� �ʴ� ���̵��Դϴ�!!","�˸�",JOptionPane.CLOSED_OPTION);
                  loginPanel.txtPass.setText("");
                  loginPanel.txtID.setText("");
               }else if(msg.getType().equals("diffpass")){// ��й�ȣ�� Ʋ�� ���
                  JOptionPane.showConfirmDialog(loginPanel, "��й�ȣ�� Ʋ�Ƚ��ϴ�!!","�˸�",JOptionPane.CLOSED_OPTION);
                  loginPanel.txtPass.setText("");
               }else if(msg.getType().equals("already")) {// �̹� �ٸ� ������ �α����� �Ǿ��ִ� ���
                  JOptionPane.showConfirmDialog(loginPanel, "�̹� �α��� �Ǿ� �ֽ��ϴ�!!","�˸�",JOptionPane.CLOSED_OPTION);
               }   
            }//else
         }else if(obj == makeUser.btnOk) {//ȸ�� ���� ��ư�� ���� ���
            ConnectServer();// ������ ����
            String m;
            Message msg = new Message();
            boolean flag = false;
            for(int i = 0; i < 3; i++) {
               if( makeUser.txt[i].getText().equals("") ) {
                  JOptionPane.showConfirmDialog(makeUser, "������ �� �����ּ���!!","�˸�",JOptionPane.CLOSED_OPTION);
                  flag = false;
                  break;
               }
               else
                  flag = true;
            }// ���� �Է¶��� ���� ���� �ִٸ� flag�� false�� �ξ �ٸ� �۾��� �������� ���ϰ� ��
            
            if(flag && makeUser.password.getText().equals(makeUser.checkpassword.getText())) {
            	// ��й�ȣ�� �°� �Է��� ���
               msg.setType("makeuser");
               msg.setId(makeUser.txt[0].getText());
               msg.setPassword(makeUser.password.getText());
               msg.setName(makeUser.txt[1].getText());
               msg.setBirth(makeUser.txt[2].getText());
               outMsg.println(gson.toJson(msg));// Ŭ���̾�Ʈ�� �Է��� ������ ������ ������
               
               try {
                  m = inMsg.readLine();
                  msg = gson.fromJson(m, Message.class);
                  // ������ ������ �ϳ� �о��
                  if(msg.getType().equals("accept")) {// ���� ȸ�����Կ� ������ ���
                     makeUser.resetData();
                     makeUser.dispose();
                  }
                  else if(msg.getType().equals("already"))// �̹� ���̵� �ִ� ���
                     JOptionPane.showConfirmDialog(makeUser, "�̹� �ִ� ���̵��Դϴ�!!","�˸�",JOptionPane.CLOSED_OPTION);
                  else if(msg.getType().equals("fail"))// ������ �ٸ� �������� ȸ�����Կ� ������ ���
                     JOptionPane.showConfirmDialog(makeUser, "ȸ�����Կ� �����߽��ϴ�!!","�˸�",JOptionPane.CLOSED_OPTION);
               }catch(Exception ex) {}
            
            }//if
            else if(flag){// ���� Ŭ���̾�Ʈ�� �Է��� ��й�ȣ�� ��й�ȣ ��Ȯ���� ���� �Է��� ���� �ٸ� ��� 
               JOptionPane.showConfirmDialog(makeUser, "��й�ȣ�� ���� �ʽ��ϴ�!!","�˸�",JOptionPane.CLOSED_OPTION);
            }//else
         }else if(obj == makeUser.btnCancel) {// ȸ������ ���� ��ư�� ���� ��� â�� ������ �ʱ�ȭ �� â�� ����
            makeUser.resetData();
            makeUser.dispose();
         }else if(obj == userStatus.btnLogout) {// ������� ��ư�� ���� ���
            if(userStatus.LogOutCheack()) {// JOptionPane�� �����ְ� Ŭ���̾�Ʈ���� ��Ȯ�� �Ŀ� ���� �α׾ƿ��� �����ٸ�
               Message msg = new Message();
               msg.setType("logout");
               msg.setId(userStatus.txtUserId.getText());
               outMsg.println(gson.toJson(msg));//������ �α׾ƿ� �޼����� �Ѱ���
               loginFlag = false;
               timer.cancel();
               chatWindow.msgOut.setText("");// ���±����� �޼��� ������ �ʱ�ȭ
               ui.changStart();
               warning.dispose();
               chatWindow.dispose();
            }
               
         }else if(obj == userStatus.btnMessage) {
            chatWindow.show();// �޼��� â�� Ŭ���̾�Ʈ���� ������
         }else if(obj == chatWindow.btnExit) {
            chatWindow.dispose();// �޼��� â�� ����
         }else if (obj == chatWindow.msgInput) {
        	 //Ŭ���̾�Ʈ�� �Է��� �޼����� ������ ����
        	 outMsg.println(gson.toJson(new Message("message","","","","",0,chatWindow.msgInput.getText())));
        	 chatWindow.msgOut.append("��  >> " + chatWindow.msgInput.getText() + "\n");// �Է��� �޼����� Ŭ���̾�Ʈ���Ե� ������
        	 chatWindow.msgInput.setText("");
         }
      }
   }
}