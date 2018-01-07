import java.sql.*;
import java.util.*;
//�����ͺ��̽��� ������ �ְ� �޴� Ŭ����
public class PCDAO {

	private Connection conn;		//�����ͺ��̽��� ������ �ְ� ������ �ʿ��� 3���� ��ü
	private ResultSet rs;
	private PreparedStatement pstmt;
	
	private String jdbcUrl = "jdbc:mysql://localhost/pcManagement";	//�����Ͱ� ����Ǵ� �ּ�
	private String jdbcDriver = "com.mysql,jdbc.Driver";
	private String strName  = "root";				//���̵�
	private String strPassword = "123123";			//��й�ȣ
	
	private String sql;								//sql �� ���� ����

	
	public PCDAO() {}
	
	public void connectDB() {		//�����ͺ��̽��� �Ź� �����Ҷ� ���
		
		try {
			Class.forName(jdbcDriver);
			
			conn = DriverManager.getConnection(jdbcUrl,strName,strPassword);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	//connectDB()
	
	public void closeDB() {		//�����ͺ��̽��� �����ϰ� ��� ������ �ְ���� �Ŀ� ������ ���� ����
		
		try {
			
			pstmt.close();
			rs.close();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}	//closeDB()
	
	public ArrayList<PcData> getAll() {		//��� ������ �� �ҷ����� ����
		
		connectDB();
		sql = "select * from seat";		//�¼������� ���δ� �������� ������
		
		ArrayList<PcData> datas = new ArrayList<PcData>();	//�������� �����ϱ� ����
		
		try {		
			
			pstmt = conn.prepareStatement(sql);		
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				PcData data = new PcData();
				
				data.setIP(rs.getString("IP"));	//�� �ٸ��� �ִ� IP ���� ű���� data�� ������ �Ŀ� ArrayList�� �����Ѵ�.
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
	
	public PcData getPc(int key) {	//�� �¼��� ���� ������ �ҷ����� �޼ҵ�
		
		connectDB();
		sql = "select * from seat where key = ?";		//key���� ������ �¼� ��ġ ã��
		
		PcData data = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, key);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			data = new PcData();
			
			data.setIP(rs.getString("IP"));	//�����Ϳ� �ش� ������ �Է��� �Ŀ� return!
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