import java.sql.*;
import java.util.*;
//데이터베이스와 정보를 주고 받는 클래스
public class PCDAO {

	private Connection conn;		//데이터베이스와 정보를 주고 받을때 필요한 3개의 객체
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	private String jdbcUrl = "jdbc:mysql://localhost/pcManagement";	//데이터가 저장되는 주소
	private String jdbcDriver = "com.mysql,jdbc.Driver";
	private String strName  = "root";				//아이디
	private String strPassword = "123123";			//비밀번호
	
	private String sql;								//sql 문 설정 위함

	
	public PCDAO() {}
	
	public void connectDB() {		//데이터베이스에 매번 연결할때 사용
		
		try {
			Class.forName(jdbcDriver);
			
			conn = DriverManager.getConnection(jdbcUrl,strName,strPassword);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	//connectDB()
	
	public void closeDB() {		//데이터베이스에 연결하고 모두 정보를 주고받은 후에 연결을 끊기 위함
		
		try {
			
			pstmt.close();
			rs.close();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}	//closeDB()
	
	public ArrayList<PcData> getAll() {		//모든 정보를 다 불러오기 위함
		
		connectDB();
		sql = "select * from seat";		//좌석정보를 전부다 가져오는 쿼리문
		
		ArrayList<PcData> datas = new ArrayList<PcData>();	//정보들을 저장하기 위함
		
		try {		
			
			pstmt = conn.prepareStatement(sql);		
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				PcData data = new PcData();
				
				data.setIP(rs.getString("IP"));	//매 줄마다 있는 IP 값과 킥값을 data에 저장한 후에 ArrayList에 저장한다.
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
	
	public PcData getPc(int key) {	//한 좌석에 대한 정보만 불러오는 메소드
		
		connectDB();
		sql = "select * from seat where key = ?";		//key값을 가지고 좌석 위치 찾기
		
		PcData data = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, key);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			data = new PcData();
			
			data.setIP(rs.getString("IP"));	//데이터에 해당 정보를 입력한 후에 return!
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