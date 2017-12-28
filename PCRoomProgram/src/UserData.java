
public class UserData {

	private String id;
	private String password;
	private String name;
	private String birth;
	private int time;
	
	public UserData() {
		id = null;
		password = null;
		name = null;
		birth = null;
	}
	
	//get/set
	
	public String getId() 		{return id;}
	public String getPassword() {return password;}
	public String getName() 	{return name;}
	public String getBirth() 	{return birth;}
	public int getTime() 		{return time;}
	
	public void setId(String s) 		{id = s;}
	public void setPassword(String s) 	{password = s;}
	public void setName(String s) 		{name = s;}
	public void setBirth(String s) 		{birth =s;}
	public void setTime(int i) 			{time = i;}
}
