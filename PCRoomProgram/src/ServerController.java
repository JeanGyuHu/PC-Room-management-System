import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
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
 
   private BufferedReader inMsg = null;
   private PrintWriter outMsg = null;
   private ServerUI serverUI;
   
   private UserDAO userDAO = new UserDAO();
   private UserData userData = new UserData();
   private PcData pcData = new PcData();
   private Vector<String> loginUser; // ���� �������� Ŭ���̾�Ʈ�� ���̵� ������ ����
   
   private int seat = 0;//�¼��� ���������� ��ġ�ϱ� ���ؼ�
   
   Gson gson = new Gson();
   
   public ServerController(ServerUI serverUI) {
      logger = Logger.getLogger(this.getClass().getName());
      this.serverUI = serverUI;
       
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
            			msgSendAll("exit");//Ŭ���̾�Ʈ���� �� �α׾ƿ� ��Ű��
            			for(String user : loginUser) {
            				if(!user.equals("��ü"))
            					userDAO.updateFlag(user, false);// �α��εǾ��ִ� ȸ���� �α��� �÷��׸� ����
            			}
            			System.exit(1);
            		}else if( result == JOptionPane.NO_OPTION) {}
            	} else if(obj == serverUI.primary.btnCharge) { //TODO:�ð�����   
            		userDAO.updateTime(serverUI.primary.txtInfo[0].getText(),
            			   Integer.parseInt(serverUI.primary.txtInfo[4].getText()));
            		serverUI.primary.memberPanel.updateTable(userDAO.getUser(serverUI.primary.txtInfo[0].getText()));
            		UserData d = userDAO.getUser(serverUI.primary.txtInfo[0].getText());
            		if(d.getFlag()) {
            			for(Threads ct : ChatThreads)
                	   		if(ct.id.equals(d.getId())) {// ������ ȸ���� ���� ��ų ��
                	   			ct.outMsg.println(gson.toJson(new Message("changTime", d.getId(),"","","",d.getTime(),"")));
                	   			serverUI.primary.pcPanel.lblPC[ct.pos][0].setText(String.valueOf(d.getTime())+ "��");
                	   			break;
                	   		}
            		}
            		serverUI.primary.resetTXT();
            		//�ð����� �Ŀ� ���̺��� ������ �ֽ�ȭ
            	} else if(obj == serverUI.primary.btnModify) { //TODO:����� ���� ���� 
            		userDAO.updateUser(serverUI.primary.txtInfo[0].getText(),
            			   serverUI.primary.txtInfo[2].getText(),
            			   Integer.parseInt(serverUI.primary.txtInfo[4].getText()));
            		serverUI.primary.memberPanel.updateTable(userDAO.getUser(serverUI.primary.txtInfo[0].getText()));
            		UserData d = userDAO.getUser(serverUI.primary.txtInfo[0].getText());
            		if(d.getFlag()) {
            			for(Threads ct : ChatThreads)
                	   		if(ct.id.equals(d.getId())) {// ������ ȸ���� ���� ��ų ��
                	   			ct.outMsg.println(gson.toJson(new Message("changTime", d.getId(),"","","",d.getTime(),"")));
                	   			serverUI.primary.pcPanel.lblPC[ct.pos][0].setText(String.valueOf(d.getTime() + "��"));
                	   			break;
                	   		}
            		}
            		serverUI.primary.resetTXT();
            	   // ������ ���� �Ŀ��� ���̺��� ������ �ֽ�ȭ
               } else if(obj == serverUI.primary.btnDelete){ //TODO:����� ����
            	   	userDAO.delUser(serverUI.primary.txtInfo[0].getText());
            	   	serverUI.primary.memberPanel.updateTable(null);
            	   	serverUI.primary.resetTXT();
            	   // ȸ�� ���� �Ŀ��� �ٷιٷ� ���̺��� ���� ����
               } else if(obj == serverUI.primary.btnPowerOff) { //TODO:����ڿ��� �������� â ���
            	   	for(Threads ct : ChatThreads)
            	   		if(ct.id.equals(serverUI.primary.txtInfo[0].getText())) {// ������ ȸ���� ���� ��ų ��
            	   			ct.outMsg.println(gson.toJson(new Message("warning", "","","","",0,"")));
            	   			break;
            	   		}
            	   	userDAO.updateFlag(serverUI.primary.txtInfo[0].getText(), false);
               } else if(obj == serverUI.primary.txtMessage) {
            	   	msgSendAll(serverUI.primary.txtMessage.getText());
            	   	serverUI.primary.taMessage.append("�� ����  >> "+serverUI.primary.combo.getSelectedItem()+" : "
            		   + serverUI.primary.txtMessage.getText() + "\n");
            	   		
            	   	serverUI.primary.txtMessage.setText("");
            	   	serverUI.primary.taMessage.setCaretPosition(serverUI.primary.taMessage.getDocument().getLength());
            	  
               }
               
            }
            
         });
         serverUI.primary.memberPanel.memberTable.addMouseListener(new MouseListener() {
        	 
        	 @Override
 			public void mouseReleased(MouseEvent e) {}
 			public void mousePressed(MouseEvent e) {}
 			public void mouseExited(MouseEvent e) {}
 			public void mouseEntered(MouseEvent e) {}
 			public void mouseClicked(MouseEvent e) {
 				int row;
 				Object value;
 				
 				row = serverUI.primary.memberPanel.memberTable.getSelectedRow();
 				
 				for (int i =0 ; i<5;i++) {
 					value = serverUI.primary.memberPanel.memberTable.getValueAt(row, i);
 	 				serverUI.primary.txtInfo[i].setText(value.toString());
 				}
 			}
 		});
			
         serverUI.primary.pcPanel.addButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // TODO Auto-generated method stub
               Object obj = e.getSource();
               for(int i = 0 ;i<24;i++) {
                  if(obj == serverUI.primary.pcPanel.btnPC[i] && !serverUI.primary.pcPanel.lblPC[i][1].getText().equals("")) {
                     // pc�¼� ��������
                     userData = userDAO.getUser(serverUI.primary.pcPanel.lblPC[i][1].getText());
                     if(userData != null) {
                        serverUI.primary.txtInfo[0].setText(userData.getId());
                        serverUI.primary.txtInfo[1].setText(userData.getName());
                        serverUI.primary.txtInfo[2].setText(userData.getBirth());
                        serverUI.primary.txtInfo[3].setText(userData.getId());
                        serverUI.primary.txtInfo[4].setText(Integer.toString(userData.getTime()));
                     }
                     
                  }
               }
            }
         });
         loginUser = new Vector<String>();
         loginUser.add("��ü");
         serverUI.primary.combo.setModel(new DefaultComboBoxModel(loginUser));
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

    	  if(msg.equals("exit")) {//������ ����Ǵ� ���
    		  ct.outMsg.println(gson.toJson(new Message("exit", "","","","",0,"")));
          }else if(serverUI.primary.combo.getSelectedItem().equals("��ü"))// ���� �������� ��� Ŭ���̾�Ʈ���� �޼����� ����
    		  ct.outMsg.println(gson.toJson(new Message("message", "","","","",0,msg)));
    	  else if(serverUI.primary.combo.getSelectedItem().equals(ct.id)) {// combobox�� ���� ������ Ŭ���̾�Ʈ���Ը� �޼����� ���� ��
    		  ct.outMsg.println(gson.toJson(new Message("message", "","","","",0,msg)));
    		  break;
    	  }
      } // ������ ��� �޽����� �޾ƿ�
   }
   
   class Threads extends Thread{
      private String msg;
      private Message m;
      private UserData d;
      
      protected String id;// ���� �α����� Ŭ���̾�Ʈ�� ���̵� �����ϱ� ���ؼ�
      protected int pos;
      private BufferedReader inMsg = null;
      private PrintWriter outMsg = null;
      private boolean loginStatus = false;
      private boolean status = false;

      
      public void run() {
         try {
            // ��ż����� ��Ʈ���� �޾� ����� ��Ʈ�� ����
            inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
            outMsg = new PrintWriter(s.getOutputStream(), true);
            
            status = true;
            while(status) {
               msg = inMsg.readLine();
               m = gson.fromJson(msg, Message.class);
              
               if(m.getType().equals("login")) {// Ŭ���̾�Ʈ���� �α��� �޼����� ���� ���� ����
                  if(userDAO.checkUserId(m.getId())) {// �Ѿ�� ���̵� �����ϴ��� üũ
                     d = new UserData(userDAO.getUser(m.getId()));// �ش���̵� �����ϸ� �ش� ������ �о��
                     if(d.getPassword().equals(m.getPassword())) {
                        if(!d.getFlag()) {// �ش� ���̵� �α����� �Ǿ��ִ��� üũ 
                           if(d.getTime() != 0) {// �ش� ���̵��� �ܿ��ð��� �ִ°��
                              m.setType("accept");
                              m.setId(d.getId());
                              m.setName(d.getName());
                              m.setTime(d.getTime());
                              outMsg.println(gson.toJson(m));// Ŭ���̾�Ʈ�� �ش� ���̵��� ������ �Ѱ���
                              id = d.getId();
                              userDAO.updateFlag(d.getId(), true);// �ش���̵��� �α��� �÷��׸� ����
                              status = false;// �α����� �����Ƿ�  false��
                              loginStatus = true; // �α��������Ƿ�  �޼����� �ְ� ���� �� �ְ� �غ�
                              pos = seat;// �ش� �������� �¼� ������ ����
                              serverUI.primary.pcPanel.btnPC[seat].setBackground(Color.CYAN);
                              serverUI.primary.pcPanel.lblPC[seat][0].setText(String.valueOf(m.getTime()) + "��");
                              serverUI.primary.pcPanel.lblPC[seat++][1].setText(m.getId());
                              
                              loginUser.add(id);
                              serverUI.primary.combo.setModel(new DefaultComboBoxModel(loginUser));
                              // �α����� id�� �޺��ڽ��� ���� ����
                           }//if
                           else {// �ð��� ���� ���
                              m.setType("notime");// Ŭ���̾�Ʈ���� �ð��� ���ٰ� �˷���
                              outMsg.println(gson.toJson(m));
                           }
                              
                        }//if
                        else
                        {
                           m.setType("already");// �̹� �α����� �Ǿ� �ִ� ���
                           outMsg.println(gson.toJson(m));
                        }//else
                     }//if
                     else {
                        m.setType("diffpass");// ���̵�� ���������� ��й�ȣ�� Ʋ�� ���
                        outMsg.println(gson.toJson(m));
                     }//else
                     
                  }//if
                  else {
                     m.setType("noid");// ������ ���̽��� ���̵� �������� �ʴ� ���
                     outMsg.println(gson.toJson(m));
                  }//else
               }//if
               
               if(m.getType().equals("makeuser")) {// Ŭ���̾�Ʈ���� ȸ������ �޼����� ���� ���
                  if(userDAO.checkUserId(m.getId())) {// ������ ���̽����� �ش� ���̵� �����ϴ��� Ȯ��
                     m.setType("already");// Ŭ���̾�Ʈ���� �̹� ���̵� �����Ѵٰ� �˷���
                     outMsg.println(gson.toJson(m));
                  }//if
                  else {
                     d = new UserData();
                     d.setBirth(m.getBirth());
                     d.setFlag(false);
                     d.setId(m.getId());
                     d.setName(m.getName());
                     d.setPassword(m.getPassword());
                     d.setTime(0);
                     d.setType("");
                     if(userDAO.newUser(d)) {//������ ���̽��� �ش� ȸ���� ���� ����
                    	 serverUI.primary.memberPanel.insertTable(userDAO.getUser(d.getId()));// ���θ������ ȸ�� ������ ���̺� ����
                    	 m.setType("accept");// Ŭ���̾�Ʈ���� ȸ�����Կ� �����ߴٰ� �޼����� ����
                    	 outMsg.println(gson.toJson(m));
                     }//if
                     else {// �����ͺ��̽��� ������ ���� ���� ���
                        m.setType("fail");
                        outMsg.println(gson.toJson(m));
                     }//else
                  }//else
               }
            }

            while(loginStatus) {// �α����� �� �Ŀ� �޼����� �ְ� �ޱ� ���ؼ�
               msg = inMsg.readLine();
               m = gson.fromJson(msg, Message.class);
               if(m.getType().equals("message")) {// Ŭ���̾�Ʈ �ʿ��� �޼����� ������ ���
                  serverUI.primary.taMessage.append(id + " >> " + m.getMessage() + "\n");
                  //JTextArea�� �Ѿ�� �޼����� �Է�
               }
               else if(m.getType().equals("logout")) {// Ŭ���̾�Ʈ �ʿ��� �α׾ƿ��� �� ���
                  seat--;// �¼� ���� �ϳ� ���� 
                  serverUI.primary.pcPanel.btnPC[pos].setBackground(Color.white);
                  serverUI.primary.pcPanel.lblPC[pos][0].setText("x");
                  serverUI.primary.pcPanel.lblPC[pos][1].setText("");
                  // �ش� Ŭ���̾�Ʈ�� �¼��� ������ ����
                  
                  userDAO.updateFlag(id, false);
                  // �����ͺ��̽��� ȸ���� �α��� �÷��׸� ����
                  
                  loginStatus = false;
                  
                  loginUser.remove(id);
                  serverUI.primary.combo.setModel(new DefaultComboBoxModel(loginUser));
                  // �޺� �ڽ��� �α׾ƿ��� ȸ���� ������ ����
               }else if(m.getType().equals("time")) {
            	   serverUI.primary.pcPanel.lblPC[pos][0].setText(String.valueOf(m.getTime()) + "��");// Ŭ���̾�Ʈ���� 1�� ���� �Ѿ���� ������ ����
            	   userDAO.updateTime(m.getId(), m.getTime());// 1�� ���� �ﰢ������ �����ͺ��̽��� �ð��� ����
            	   serverUI.primary.memberPanel.updateTime(userDAO.getUser(m.getId()));// ���� ȸ�� ���� â�� ���̺��� ����
               }
            } // while()
         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         this.interrupt();
         // ������ ����� Ŭ���̾�Ʈ ������ ����ǹǷ� ������ ���ͷ�Ʈ
         logger.info(this.getName() + "�����!!");
      } // run()
   } // Threads class
   

   public static void main(String[] args) {
      ServerController c = new ServerController( new ServerUI());
      c.start();
      
   } // main()
    
   
} // ServerContrller