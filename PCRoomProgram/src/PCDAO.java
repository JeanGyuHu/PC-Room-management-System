import java.sql.*;
import java.util.*;

public class PCDAO {

	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	private String jdbcUrl = "jdbc:mysql://localhost/pcManagement";
	private String jdbcDriver = "com.mysql,jdbc.Driver";
	private String strName  = "root";
	private String strPassword = "123123";
	
	private String sql;

	
	public PCDAO() {}
	
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
	
	public ArrayList<PcData> getAll() {
		
		connectDB();
		sql = "select * from seat";
		
		ArrayList<PcData> datas = new ArrayList<PcData>();
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				PcData data = new PcData();
				
				data.setIP(rs.getString("IP"));
				data.setKey(rs.getInt("key"));
				
				datas.add(data);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		closeDB();
		
		if(!datas.isEmpty())
			return datas;
		else
			return null;
	}
	
	public PcData getPc(int key) {
		
		connectDB();
		sql = "select * from seat where key = ?";
		
		PcData data = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, key);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			data = new PcData();
			
			data.setIP(rs.getString("IP"));
			data.setKey(rs.getInt("key"));
			
		}catch(SQLException e) {
			
			e.printStackTrace();
		}
		
		closeDB();
		
		if (data!= null)
			return data;
		else
			return null;
	}
}