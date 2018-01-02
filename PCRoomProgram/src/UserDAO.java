import java.sql.*;
import java.util.ArrayList;

public class UserDAO {

	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	private String jdbcUrl = "jdbc:mysql://localhost/pcManagement";
	private String jdbcDriver = "com.mysql.jdbc.Driver";
	private String strName  = "root";
	private String strPassword = "123123";
	
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
				data.setPassword(rs.getString("pw"));
				data.setName(rs.getString("username"));
				data.setBirth(rs.getString("birth"));
				data.setTime(rs.getInt("remaintime"));
				data.setType(rs.getString("type1"));
				data.setFlag(rs.getBoolean("flag"));
				
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
			data.setPassword(rs.getString("pw"));
			data.setName(rs.getString("username"));
			data.setBirth(rs.getString("birth"));
			data.setTime(rs.getInt("remaintime"));
			data.setType(rs.getString("type1"));
			data.setFlag(rs.getBoolean("flag"));
			
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
		
		sql = "insert into member values(?,?,?,?,?,?,?)";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, data.getId());
			pstmt.setString(2, data.getPassword());
			pstmt.setString(3, data.getName());
			pstmt.setString(4, data.getBirth());
			pstmt.setInt(5, data.getTime());
			pstmt.setString(6, data.getType());
			pstmt.setBoolean(7,data.getFlag());
			
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
		
		sql = "update member set password =?, time =?, type1=?, flag=? where id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pass);
			pstmt.setInt(2, t);
			pstmt.setString(3, id);
			
			result = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(result > 0)
			return true;
		else
			return false;
	}
	
	public boolean checkUserId(String user) {
		
		boolean flag = false;
		
		sql = "select * from member";
		
		connectDB();
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getString("id").equals(user))
					flag = true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		closeDB();
		
		return flag;
		
	}
	public boolean checkUserPw(String user,String pass) {
		
		boolean flag = false;
		
		sql = "select * from member";
		
		connectDB();
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.getString("id").equals(user)) {
					if(rs.getString("pw").equals(pass))
						flag = true;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		closeDB();
		
		return flag;
		
	}
	 public boolean updateFlag(String id,boolean f) {
	      
	      connectDB();
	      sql = "update member set flag =? where id = ?";
	      
	      try {
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setBoolean(1,f);
	         pstmt.setString(2, id);
	         
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
	 public boolean updateTime(String id,int time ) {
	      
	      connectDB();
	      sql = "update member set remaintime =? where id = ?";
	      
	      try {
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1,time);
	         pstmt.setString(2, id);
	         
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