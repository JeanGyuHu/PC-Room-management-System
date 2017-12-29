
public class UserData {

	private String id;
	private String password;
	private String name;
	private String birth;
	private int time;
	private String type;
	
	public UserData() {
		id = null;
		password = null;
		name = null;
		birth = null;
		type = null;
	}
	
	//get/set
	
	public String getId() 		{return id;}
	public String getPassword() {return password;}
	public String getName() 	{return name;}
	public String getBirth() 	{return birth;}
	public String getType()		{return type;}
	public int getTime() 		{return time;}
	
	public void setId(String s) 		{id = s;}
	public void setPassword(String s) 	{password = s;}
	public void setName(String s) 		{name = s;}
	public void setBirth(String s) 		{birth =s;}
	public void setType(String s)		{type = s;}
	public void setTime(int i) 			{time = i;}
}
