
public class Message {

	private String type; // 메시지의 유형
	private String id; // 클라이언트의 ID
	private String password; // 클라이언트의 비밀번호
	private String name; // 이름
	private String birth; // 생일
	private int time; // 남은 시간
	private String message; // 메시지 내용
	
	// 생성자와 각 get/set 메소드
	public Message() {	
		type = null;
		id = null;
		password = null;
		name = null;
		birth = null;
		message = null;
				
	}
	public Message( String type, String id, String password, String name,String birth, int time, String message) 
	{	
		this.type = type;
		this.id  = id;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.time = time;
		this.message = message;
	}
	
	public String getType() { return type; }
	public String getId() { return id; }
	public String getPassword() { return password; }
	public String getName() { return name; }
	public String getBirth() { return birth; }
	public int getTime() { return time; }
	public String getMessage() { return message; }
	
	public void setType(String type) {this.type = type;}
	public void setId(String id) {this.id = id;}
	public void setPassword(String password) {this.password = password;}
	public void setName(String name) {this.name = name;}
	public void setBirth(String birth) {this.birth = birth; }
	public void setTime(int time) {this.time = time;}
	public void setMessage(String message) {this.message = message;}
	
	
}