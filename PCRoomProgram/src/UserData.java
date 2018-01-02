public class UserData {

	private String id;
	private String password;
	private String name;
	private String birth;
	private int time;
	private String type;
	private boolean flag;
	
	public UserData() {
		id = null;
		password = null;
		name = null;
		birth = null;
		type = null;
		flag = false;
	}
	
	public UserData(UserData data) {
		id = data.getId();
		password = data.getPassword();
		name = data.name;
		birth = data.birth;
		type = data.type;
		time = data.time;
		flag = data.flag;
	}
	
	//get/set
	
	public String getId() 		{return id;}
	public String getPassword() {return password;}
	public String getName() 	{return name;}
	public String getBirth() 	{return birth;}
	public String getType()		{return type;}
	public int getTime() 		{return time;}
	public boolean getFlag()	{return flag;}
	
	public void setId(String s) 		{id = s;}
	public void setPassword(String s) 	{password = s;}
	public void setName(String s) 		{name = s;}
	public void setBirth(String s) 		{birth =s;}
	public void setType(String s)		{type = s;}
	public void setTime(int i) 			{time = i;}
	public void setFlag(boolean f)		{flag = f;}
}