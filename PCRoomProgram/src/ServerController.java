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
		
		private BufferedReader inMsg = null;
		private PrintWriter outMsg = null;
		private boolean status = false;
		
		public void run() {
			status = true; // 로그인했으므로 현상태를 true로
			try {
				// 통신소켓의 스트림을 받아 입출력 스트림 연결
				inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
				outMsg = new PrintWriter(s.getOutputStream(), true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// 상태 정보가 true이면 루프를 돌면서 사용자에게서 수신된 메시지 처리
			while(status) {
				
				// 수신된 메시지를 msg 변수에 저장
				try {
					msg = inMsg.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// JSON 메시지를 UserData 객체로 매핑
				userData = gson.fromJson(msg, UserData.class);

				if(userData.getType().equals("logout")) {
					// 사용종료를 눌렀을때			
					flag = true; // 로그아웃임을 알림
					ChatThreads.remove(this); // 스레드를 모두 지움
					
					/*TODO:
					 * 접속종료한 IP의 정보를 받아옴
					 * -> 해당 자리에 접속을 종료한 자리 정보를 "X"로 변경
					 */
					/*for(int j=0;j<3;j++) {
				    	if(j==0)serverUI.primary.pcPanel.lblPC[접속종료한 컴퓨터의 key][j].setText("X");
				    	else lblPC[접속종료한 컴퓨터의 key][j].setText("");
			    	}*/
					status = false; // 로그아웃 했으므로 현재 상태를 false로
				} 
				
				else if(userData.getType().equals("login")) {
					// 로그인을 눌렀을때
					flag = true; // 로그인임을 알림
					/*TODO:
					 * 유저의 정보가 저장되어 있는지 확인 
					 * -> 로그인 ack신호 보냄 
					 * -> 로그인 시키고 유저의 화면을 변경시켜줌
					 * -> 자리의 IP와 번호 받아옴
					 * -> 해당 자리에 접속한 유저의 정보를 띄움
					 */
				} 
				
				else if(userData.getType().equals("message")) {
					// 메시지를 눌렀을때
					//TODO:이거 필요한가..
				} 
				
				else if(userData.getType().equals("make")) {
					// 회원가입을 눌렀을때
					/*TODO:
					 * 회원가입창에서 유저가 쓴 정보를 받아옴
					 * -> 해당 정보가 DB에 있는지 확인 (아이디 중복여부)
					 * -> DB에 정보 저장
					 * -> 유저에서 회원가입창을 종료시킴
					 */
				} 
				
				// 그 밖의 경우, 즉 일반 메시지일 때
				else {
					// 채팅할때
					flag = false;
					msgSendAll(msg); // 메시지 보냄
				}
			} // while()
			
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