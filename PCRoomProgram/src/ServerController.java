import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gson.Gson;

public class ServerController{
	private ServerSocket connect = null;
	private Socket s= null;
	ArrayList<Threads> ChatThreads = new ArrayList<Threads>();
	Logger logger;
	
	private BufferedReader inMsg = null;
	private PrintWriter outMsg = null;
	
	Gson gson = new Gson();
	
	public static void main(String[] args) {
		ServerController c = new ServerController();
		c.start();
	}
	
	public void start() {
		logger = Logger.getLogger(this.getClass().getName());
		
		try {
			connect = new ServerSocket(3010); 
			logger.info("Server start");
			
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
	}
	
	class Threads extends Thread{
		private String msg;
		private UserData userData = new UserData();
		private PcData pcData = new PcData();
		
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
				} 
				
				else if(userData.getType().equals("login")) {
					// 로그인을 눌렀을때
				} 
				
				else if(userData.getType().equals("message")) {
					// 메시지를 눌렀을때
				} 
				
				else if(userData.getType().equals("make")) {
					// 회원가입을 눌렀을때
				} 
				
				// 그 밖의 경우, 즉 일반 메시지일 때
				else {
					// 채팅할때
				}
			} // while()
			
			// 루프를 벗어나면 클라이언트 연결이 종료되므로 스레드 인터럽트
			this.interrupt();
			logger.info(this.getName() + "종료됨!!");
		}
	}
	
}