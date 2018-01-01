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
	private boolean flag = true; // 접속중인 아이디를 띄우기위해서 로그인과 로그아웃시 설정될 수 있도록 받는 플래그
	
	private DataHandle dataHandle;
	//private PrimaryPanel primary;
	//private PCPanel pcPanel = new PCPanel();
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	private ServerUI serverUI;
	
	private UserDAO userDAO = new UserDAO();
	private UserData userData = new UserData();
	private PcData pcData = new PcData();
	private ArrayList<String> user; // 사용자의 아이디를 저장할 배열리스트
	
	Gson gson = new Gson();
	
	public ServerController(DataHandle data, ServerUI serverUI) {
		logger = Logger.getLogger(this.getClass().getName());
		this.serverUI = serverUI;
		dataHandle = data;
		 
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
	                  System.exit(1);
	               }else if( result == JOptionPane.NO_OPTION) {
	                  
	               }
	            } else if(obj == serverUI.primary.btnCharge) { //TODO:시간충전
	               userDAO.updateUser(serverUI.primary.txtInfo[0].getText(), serverUI.primary.txtInfo[2].getText(), Integer.parseInt(serverUI.primary.txtInfo[4].getText()));
	            } else if(obj == serverUI.primary.btnModify) { //TODO:사용자 정보 수정
	               userDAO.updateUser(serverUI.primary.txtInfo[0].getText(), serverUI.primary.txtInfo[2].getText(), Integer.parseInt(serverUI.primary.txtInfo[4].getText()));
	            } else if(obj == serverUI.primary.btnDelete){ //TODO:사용자 삭제
	               userDAO.delUser(serverUI.primary.memberPanel.getValue());
	            } else if(obj == serverUI.primary.btnPowerOff) { //TODO:사용자에게 접속종료 창 띄움
	               
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
	                  // pc좌석 눌렀을때
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
			ct.outMsg.println(msg);
			if(flag) { // 만약 로그인, 로그아웃 메시지일 때,
				ct.outMsg.println("user"); // user라는 메시지 전송
				//ct.outMsg.println(gson.toJson(userData)); // user의 아아디가 있는 배열을 전송
			}
		} // 보내진 모든 메시지를 받아옴
	}
	
	class Threads extends Thread{
		private String msg;
		private Message m;
		private UserData d;
		
		private String id;
		private BufferedReader inMsg = null;
		private PrintWriter outMsg = null;
		private boolean loginStatus = false;
		private boolean status = false;
		
		public void run() {
			
			try {
				// 통신소켓의 스트림을 받아 입출력 스트림 연결
				inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
				outMsg = new PrintWriter(s.getOutputStream(), true);
				
				System.out.println(userDAO.getAll());
				status = true;
				while(status) {
					msg = inMsg.readLine();
					m = gson.fromJson(msg, Message.class);
					if(m.getType().equals("login")) {
						System.out.println(m.getId());
						if(userDAO.checkUserId(m.getId())) {
							d = new UserData(userDAO.getUser(m.getId()));
							if(d.getPassword().equals(m.getPassword())) {
								if(!d.getFlag()) {
									m.setType("accept");
									m.setId(d.getId());
									m.setName(d.getName());
									m.setTime(d.getTime());
									userDAO.updateFlag(d.getId(), true);
									outMsg.println(gson.toJson(m));
									status = false;
									loginStatus = true; // 로그인했으므로  메세지를 주고 받을 수 있게 준비
									
								}//if
								else
								{
									m.setType("already");
									outMsg.println(gson.toJson(m));
								}//else
							}//if
							else {
								m.setType("diffpass");
								outMsg.println(gson.toJson(m));
							}//else
							
						}//if
						else {
							m.setType("noid");
							outMsg.println(gson.toJson(m));
						}//else
						
					}//if
					if(m.getType().equals("makeuser")) {
						if(userDAO.checkUserId(m.getId())) {
							m.setType("already");
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
							if(userDAO.newUser(d)) {
								m.setType("accept");
								outMsg.println(gson.toJson(m));
							}//if
							else {
								m.setType("fail");
								outMsg.println(gson.toJson(m));
							}//else
						}//else
					}
				}

				while(loginStatus) {
					msg = inMsg.readLine();
					m = gson.fromJson(msg, Message.class);
					if(m.getType().equals("message")) {
						
					}
					else if(m.getType().equals("logout")) {
						
						userDAO.updateTime(m.getId(), m.getTime());
						loginStatus = false;
					}
					
				} // while()
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// 루프를 벗어나면 클라이언트 연결이 종료되므로 스레드 인터럽트
			this.interrupt();
			logger.info(this.getName() + "종료됨!!");
		} // run()
	} // Threads class
	

	public static void main(String[] args) {
		ServerController c = new ServerController(new DataHandle(), new ServerUI());
		c.start();
		
	} // main()
	 
	
} // ServerContrller