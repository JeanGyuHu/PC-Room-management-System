import java.sql.*;
import java.util.ArrayList;

public class UserDAO {

	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	private String jdbcUrl = "jdbc:mysql://localhost/pcManagement";
	private String jdbcDriver = "com.mysql.jdbc.Driver";
	private String strName  = "root";
	private String strPassword = "0517";
	
	private String sql;
	private int result;
	
	public UserDAO() {}
	
	public void connectDB() {
		
		try {
			Class.forName(jdbcDriver);
			
			conn = DriverManager.getConnection(jdbcUrl,strName,strPassword);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	//connectDB()
	
	public void closeDB() {
		
		try {
			
			pstmt.close();
			rs.close();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}	//closeDB()
	
	public ArrayList<UserData> getAll() {
		
		connectDB();
		
		sql = "select * from member";
		
		ArrayList <UserData> datas = new ArrayList<UserData>();
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				UserData data = new UserData();
				
				data.setId(rs.getString("id"));
				data.setPassword(rs.getString("password"));
				data.setName(rs.getString("name"));
				data.setBirth(rs.getString("birth"));
				data.setTime(rs.getInt("time"));
				
				datas.add(data);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		closeDB();
		
		if(!datas.isEmpty())
		return datas;
		else
			return null;
	}
	
	public UserData getUser(String id) {
		
		connectDB();
		
		sql = "select * from member where id = ?";
		
		UserData data = null;
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			rs.next();
			
			data = new UserData();
			data.setId(rs.getString("id"));
			data.setPassword(rs.getString("password"));
			data.setName(rs.getString("name"));
			data.setBirth(rs.getString("birth"));
			data.setTime(rs.getInt("time"));
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		closeDB();
		
		if(data != null)
		return data;
		else
			return null;
	}
	
	public boolean newUser(UserData data) {
	
		connectDB();
		
		sql = "insert into member values(?,?,?,?,?)";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, data.getId());
			pstmt.setString(2, data.getPassword());
			pstmt.setString(3, data.getName());
			pstmt.setString(4, data.getBirth());
			pstmt.setInt(5, data.getTime());
			
			result = pstmt.executeUpdate();
		
		} catch(SQLException e) { e.printStackTrace();}
		
		closeDB();
		
		if(result > 0)
		return true;
		else
			return false;
	}
	
	public boolean delUser(String id) {
		
		connectDB();
		
		sql = "delete from member where id = ?";
		
		UserData data = null;
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		closeDB();
		
		if(result > 0)
			return true;
		else
			return false;
		
	}
	
	public boolean updateUser(String id,String pass,int t) {
		connectDB();
		
		sql = "update member set password =?, time =? where id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pass);
			pstmt.setInt(2, t);
			pstmt.setString(3, id);
			
			result = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		closeDB();
		
		if(result > 0)
			return true;
		else
			return false;
	}
}
