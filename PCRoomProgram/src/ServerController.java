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
   private Vector<String> loginUser; // 현재 접속중인 클라이언트의 아이디를 저장할 벡터
   
   private int seat = 0;//좌석을 순자적으로 배치하기 위해서
   
   Gson gson = new Gson();
   
   public ServerController(ServerUI serverUI) {
      logger = Logger.getLogger(this.getClass().getName());
      this.serverUI = serverUI;
       
   }
   
   public void appMain() { // control
         
         // dataHandle.addObj(primary.topPanel);
         // PrimaryPanel 버튼 구현
	   
	   serverUI.primary.addButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // TODO Auto-generated method stub
            	Object obj = e.getSource();

            	if(obj == serverUI.primary.btnMember) { // 전체 사용자들의 정보
            		serverUI.primary.rightPanel.setVisible(true);
            		serverUI.primary.modifyPanel.setVisible(true);
            		serverUI.primary.showInformationPanel.setVisible(false);
            		serverUI.primary.messagePanel.setVisible(false);
            		serverUI.primary.memberPanel.setVisible(true);
            		serverUI.primary.pcPanel.setVisible(false);
                  
            	} else if(obj == serverUI.primary.btnSeat) { // 사용자 접속 정보
            		serverUI.primary.rightPanel.setVisible(true);
            		serverUI.primary.modifyPanel.setVisible(false);
            		serverUI.primary.showInformationPanel.setVisible(true);
            		serverUI.primary.messagePanel.setVisible(false);
            		serverUI.primary.memberPanel.setVisible(false);
            		serverUI.primary.pcPanel.setVisible(true);
                  
            	} else if(obj == serverUI.primary.btnMessage) { // 메시지
            		serverUI.primary.rightPanel.setVisible(false);
            		serverUI.primary.modifyPanel.setVisible(false);
            		serverUI.primary.showInformationPanel.setVisible(false);
            		serverUI.primary.messagePanel.setVisible(true);
                  
            	} else if(obj == serverUI.primary.btnLogout) { // 서버 창을 종료
                  
            		int result = JOptionPane.showConfirmDialog(serverUI.primary.topPanel, "종료하시겠습니까?","알림",JOptionPane.YES_NO_OPTION);
                  
            		if(result == JOptionPane.YES_OPTION) {
            			msgSendAll("exit");//클라이언트들을 다 로그아웃 시키고
            			for(String user : loginUser) {
            				if(!user.equals("전체"))
            					userDAO.updateFlag(user, false);// 로그인되어있는 회원의 로그인 플래그를 수정
            			}
            			System.exit(1);
            		}else if( result == JOptionPane.NO_OPTION) {}
            	} else if(obj == serverUI.primary.btnCharge) { //TODO:시간충전   
            		userDAO.updateTime(serverUI.primary.txtInfo[0].getText(),
            			   Integer.parseInt(serverUI.primary.txtInfo[4].getText()));
            		serverUI.primary.memberPanel.updateTable(userDAO.getUser(serverUI.primary.txtInfo[0].getText()));
            		UserData d = userDAO.getUser(serverUI.primary.txtInfo[0].getText());
            		if(d.getFlag()) {
            			for(Threads ct : ChatThreads)
                	   		if(ct.id.equals(d.getId())) {// 선택한 회원을 종료 시킬 때
                	   			ct.outMsg.println(gson.toJson(new Message("changTime", d.getId(),"","","",d.getTime(),"")));
                	   			serverUI.primary.pcPanel.lblPC[ct.pos][0].setText(String.valueOf(d.getTime())+ "분");
                	   			break;
                	   		}
            		}
            		serverUI.primary.resetTXT();
            		//시간충전 후에 테이블의 정보를 최신화
            	} else if(obj == serverUI.primary.btnModify) { //TODO:사용자 정보 수정 
            		userDAO.updateUser(serverUI.primary.txtInfo[0].getText(),
            			   serverUI.primary.txtInfo[2].getText(),
            			   Integer.parseInt(serverUI.primary.txtInfo[4].getText()));
            		serverUI.primary.memberPanel.updateTable(userDAO.getUser(serverUI.primary.txtInfo[0].getText()));
            		UserData d = userDAO.getUser(serverUI.primary.txtInfo[0].getText());
            		if(d.getFlag()) {
            			for(Threads ct : ChatThreads)
                	   		if(ct.id.equals(d.getId())) {// 선택한 회원을 종료 시킬 때
                	   			ct.outMsg.println(gson.toJson(new Message("changTime", d.getId(),"","","",d.getTime(),"")));
                	   			serverUI.primary.pcPanel.lblPC[ct.pos][0].setText(String.valueOf(d.getTime() + "분"));
                	   			break;
                	   		}
            		}
            		serverUI.primary.resetTXT();
            	   // 정보를 수정 후에도 테이블의 정보를 최신화
               } else if(obj == serverUI.primary.btnDelete){ //TODO:사용자 삭제
            	   	userDAO.delUser(serverUI.primary.txtInfo[0].getText());
            	   	serverUI.primary.memberPanel.updateTable(null);
            	   	serverUI.primary.resetTXT();
            	   // 회원 삭제 후에도 바로바로 테이블을 새로 갱신
               } else if(obj == serverUI.primary.btnPowerOff) { //TODO:사용자에게 접속종료 창 띄움
            	   	for(Threads ct : ChatThreads)
            	   		if(ct.id.equals(serverUI.primary.txtInfo[0].getText())) {// 선택한 회원을 종료 시킬 때
            	   			ct.outMsg.println(gson.toJson(new Message("warning", "","","","",0,"")));
            	   			break;
            	   		}
            	   	userDAO.updateFlag(serverUI.primary.txtInfo[0].getText(), false);
               } else if(obj == serverUI.primary.txtMessage) {
            	   	msgSendAll(serverUI.primary.txtMessage.getText());
            	   	serverUI.primary.taMessage.append("안 사장  >> "+serverUI.primary.combo.getSelectedItem()+" : "
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
                     // pc좌석 눌렀을때
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
         loginUser.add("전체");
         serverUI.primary.combo.setModel(new DefaultComboBoxModel(loginUser));
         //TODO:PcUserPanel의 버튼 기능 :리스트의 유저를 눌렀을때, 해당 유저의 정보띄우는 기능
         
      } // appMain()
   
   public void start() {      
      logger = Logger.getLogger(this.getClass().getName());
      
      try {
         connect = new ServerSocket(3010); 
         logger.info("Server start");
         appMain();
         while(true) {
            s = connect.accept(); // 연결소켓이 연결되면 통신소켓에 클라이언트의 소켓과 연결함
            
            // 연결된 클라이언트에 대해 스레드 클래스 생성
            Threads chat = new Threads();
            // 클라이언트 리스트 추가
            ChatThreads.add(chat);
            //스레드 시작
            chat.start();
         }
      }catch(Exception e) {
         logger.info("[Server]Start() Exception 발생!!");
         e.printStackTrace();
      }
   } // start()
   
   // 연결된 모든 클라이언트에 메시지 중계
   public void msgSendAll(String msg) {
      for(Threads ct : ChatThreads) { // 스레드의 메시지들을 받아와 클라이언트에 메시지를 전송

    	  if(msg.equals("exit")) {//서버가 종료되는 경우
    		  ct.outMsg.println(gson.toJson(new Message("exit", "","","","",0,"")));
          }else if(serverUI.primary.combo.getSelectedItem().equals("전체"))// 현재 접속중인 모든 클라이언트에게 메세지를 보냄
    		  ct.outMsg.println(gson.toJson(new Message("message", "","","","",0,msg)));
    	  else if(serverUI.primary.combo.getSelectedItem().equals(ct.id)) {// combobox를 통해 선택한 클라이언트에게만 메세지를 보낼 때
    		  ct.outMsg.println(gson.toJson(new Message("message", "","","","",0,msg)));
    		  break;
    	  }
      } // 보내진 모든 메시지를 받아옴
   }
   
   class Threads extends Thread{
      private String msg;
      private Message m;
      private UserData d;
      
      protected String id;// 현재 로그인한 클라이언트의 아이디어를 저장하기 위해서
      protected int pos;
      private BufferedReader inMsg = null;
      private PrintWriter outMsg = null;
      private boolean loginStatus = false;
      private boolean status = false;

      
      public void run() {
         try {
            // 통신소켓의 스트림을 받아 입출력 스트림 연결
            inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
            outMsg = new PrintWriter(s.getOutputStream(), true);
            
            status = true;
            while(status) {
               msg = inMsg.readLine();
               m = gson.fromJson(msg, Message.class);
              
               if(m.getType().equals("login")) {// 클라이언트에서 로그인 메세지를 보내 왔을 때에
                  if(userDAO.checkUserId(m.getId())) {// 넘어온 아이디가 존재하는지 체크
                     d = new UserData(userDAO.getUser(m.getId()));// 해당아이디가 존재하면 해당 정보를 읽어옴
                     if(d.getPassword().equals(m.getPassword())) {
                        if(!d.getFlag()) {// 해당 아이디가 로그인이 되어있는지 체크 
                           if(d.getTime() != 0) {// 해당 아이디의 잔여시간이 있는경우
                              m.setType("accept");
                              m.setId(d.getId());
                              m.setName(d.getName());
                              m.setTime(d.getTime());
                              outMsg.println(gson.toJson(m));// 클라이언트로 해당 아이디의 정보를 넘겨줌
                              id = d.getId();
                              userDAO.updateFlag(d.getId(), true);// 해당아이디의 로그인 플래그를 변경
                              status = false;// 로그인을 했으므로  false로
                              loginStatus = true; // 로그인했으므로  메세지를 주고 받을 수 있게 준비
                              pos = seat;// 해당 스레드의 좌석 정보를 저장
                              serverUI.primary.pcPanel.btnPC[seat].setBackground(Color.CYAN);
                              serverUI.primary.pcPanel.lblPC[seat][0].setText(String.valueOf(m.getTime()) + "분");
                              serverUI.primary.pcPanel.lblPC[seat++][1].setText(m.getId());
                              
                              loginUser.add(id);
                              serverUI.primary.combo.setModel(new DefaultComboBoxModel(loginUser));
                              // 로그인한 id를 콤보박스에 새로 갱신
                           }//if
                           else {// 시간이 없는 경우
                              m.setType("notime");// 클라이언트에게 시간이 없다고 알려줌
                              outMsg.println(gson.toJson(m));
                           }
                              
                        }//if
                        else
                        {
                           m.setType("already");// 이미 로그인이 되어 있는 경우
                           outMsg.println(gson.toJson(m));
                        }//else
                     }//if
                     else {
                        m.setType("diffpass");// 아이디는 존재하지만 비밀번호를 틀린 경우
                        outMsg.println(gson.toJson(m));
                     }//else
                     
                  }//if
                  else {
                     m.setType("noid");// 데이터 베이스에 아이디가 존재하지 않는 경우
                     outMsg.println(gson.toJson(m));
                  }//else
               }//if
               
               if(m.getType().equals("makeuser")) {// 클라이언트에서 회원가입 메세지를 보낸 경우
                  if(userDAO.checkUserId(m.getId())) {// 데이터 베이스에서 해당 아이디가 존재하는지 확인
                     m.setType("already");// 클라이언트에게 이미 아이디가 존재한다고 알려줌
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
                     if(userDAO.newUser(d)) {//데이터 베이스에 해당 회원을 새로 생성
                    	 serverUI.primary.memberPanel.insertTable(userDAO.getUser(d.getId()));// 새로만들어진 회원 정보를 테이블에 갱신
                    	 m.setType("accept");// 클라이언트에게 회원가입에 성공했다고 메세지를 보냄
                    	 outMsg.println(gson.toJson(m));
                     }//if
                     else {// 데이터베이스에 생성이 되지 않은 경우
                        m.setType("fail");
                        outMsg.println(gson.toJson(m));
                     }//else
                  }//else
               }
            }

            while(loginStatus) {// 로그인을 한 후에 메세지를 주고 받기 위해서
               msg = inMsg.readLine();
               m = gson.fromJson(msg, Message.class);
               if(m.getType().equals("message")) {// 클라이언트 쪽에서 메세지를 보내온 경우
                  serverUI.primary.taMessage.append(id + " >> " + m.getMessage() + "\n");
                  //JTextArea에 넘어온 메세지를 입력
               }
               else if(m.getType().equals("logout")) {// 클라이언트 쪽에서 로그아웃을 한 경우
                  seat--;// 좌석 수를 하나 감소 
                  serverUI.primary.pcPanel.btnPC[pos].setBackground(Color.white);
                  serverUI.primary.pcPanel.lblPC[pos][0].setText("x");
                  serverUI.primary.pcPanel.lblPC[pos][1].setText("");
                  // 해당 클라이언트의 좌석의 정보를 변경
                  
                  userDAO.updateFlag(id, false);
                  // 데이터베이스의 회원의 로그인 플래그를 수정
                  
                  loginStatus = false;
                  
                  loginUser.remove(id);
                  serverUI.primary.combo.setModel(new DefaultComboBoxModel(loginUser));
                  // 콤보 박스에 로그아웃한 회원의 정보를 삭제
               }else if(m.getType().equals("time")) {
            	   serverUI.primary.pcPanel.lblPC[pos][0].setText(String.valueOf(m.getTime()) + "분");// 클라이언트에서 1분 마다 넘어오는 정보를 갱신
            	   userDAO.updateTime(m.getId(), m.getTime());// 1분 마다 즉각적으로 데이터베이스의 시간을 갱신
            	   serverUI.primary.memberPanel.updateTime(userDAO.getUser(m.getId()));// 서버 회원 관리 창의 테이블을 갱신
               }
            } // while()
         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         this.interrupt();
         // 루프를 벗어나면 클라이언트 연결이 종료되므로 스레드 인터럽트
         logger.info(this.getName() + "종료됨!!");
      } // run()
   } // Threads class
   

   public static void main(String[] args) {
      ServerController c = new ServerController( new ServerUI());
      c.start();
      
   } // main()
    
   
} // ServerContrller