
public class Message {

	private String type;
	private String id;
	private String password;
	private String name;
	private String birth;
	private int time;
	private String message;
	private boolean flag;
	
	
	public Message() {	
		type = null;
		id = null;
		password = null;
		name = null;
		birth = null;
		message = null;
		flag = false;
				
	}
	public Message( String type, String id, String password, String name,String birth, int time, String message, boolean flag) 
	{	
		this.type = type;
		this.id  = id;
		this.password = password;
		this.name = name;
		this.birth = birth;
		this.time = time;
		this.message = message;
		this.flag = flag;
	}
	
	public String getType() { return type; }
	public String getId() { return id; }
	public String getPassword() { return password; }
	public String getName() { return name; }
	public String getBirth() { return birth; }
	public int getTime() { return time; }
	public String getMessage() { return message; }
	public boolean getFlag() {return flag;}
	
	public void setType(String type) {this.type = type;}
	public void setId(String id) {this.id = id;}
	public void setPassword(String password) {this.password = password;}
	public void setName(String name) {this.name = name;}
	public void setBirth(String birth) {this.birth = birth; }
	public void setTime(int time) {this.time = time;}
	public void setMessage(String message) {this.message = message;}
	public void setFlag(boolean flag) {this.flag = flag;}
	
}
