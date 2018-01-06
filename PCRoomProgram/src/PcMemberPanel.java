import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.*;

public class PcMemberPanel extends JPanel {
   
   private JScrollPane scroll; // 테이블에 적용할 스크롤
   protected DefaultTableModel model; // 테이블을 적용할 디폴트테이블모델
   private String[] columnNames = {"아이디","이름","비밀번호","생년월일","남은시간"}; // 각 학목의 이름
   protected JTable memberTable; // DB에서 받아온 사용자들의 정보를 띄울 테이블
   protected Vector<String> vec; // DB에서 받아온 사용자들의 정보를 저장할 벡터
   private UserDAO dao; // 사용자 정보가 저장된 DB에 접근할 클래스
   protected ArrayList<UserData> datas; // 벡터 저장전 DB에서 받아온 사용자 정보를 중간 저장할 배열리스트
   
   public PcMemberPanel() {
      setPreferredSize(new Dimension(480, 400));
      setLayout(new BorderLayout());      
      setBackground(Color.white);
      
      dao = new UserDAO();
      
      // 초기화, 테이블을 수정할 수 없도록 설정
      model = new DefaultTableModel(columnNames, 0) {public boolean isCellEditable(int i ,int c) {
    	  return false;
      }
      };
      
      memberTable = new JTable(model);
      memberTable.setForeground(Color.black);
      
      memberTable.getTableHeader().setReorderingAllowed(false);
      memberTable.isCellEditable(memberTable.getColumnCount(), memberTable.getRowCount());
      vec = new Vector<String>();
      
      scroll = new JScrollPane(memberTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      
      add(scroll);
      
      datas = new ArrayList<UserData>();
      
      refreshTable();
   }
   
   // 테이블 정보를 현재 DB정보로 업데이트 시킴
   public void refreshTable() {
      datas = dao.getAll();// 데이터 베이스에서 존재하는 회원 정보를 다 읽어온후
      if(datas!=null) {
         for(UserData d :datas) {
        	 vec = new Vector<String>();
             vec.addElement(d.getId());
             vec.addElement(d.getName());
             vec.addElement(d.getPassword());
             vec.addElement(d.getBirth());
             vec.addElement(Integer.toString(d.getTime()));               
             model.addRow(vec);// 테이블에 삽입
            }
        datas.clear();   
      }
   }
   
   // 테이블에 새로운 정보를 갱신하거나 해당 회원의 정보를 삭제
   public void updateTable(UserData d) {
	   int col = memberTable.getSelectedRow();// 선택된 회원
	   if(d == null)// 만약 삭제의 경우
		   model.removeRow(col);// 선택한 열을 삭제
	   else {
		   vec = new Vector<String>();
		   vec.addElement(d.getId());
		   vec.addElement(d.getName());
		   vec.addElement(d.getPassword());
		   vec.addElement(d.getBirth());
		   vec.addElement(Integer.toString(d.getTime()));
		   model.removeRow(col);// 그전의 있던 정보를 삭제하고
		   model.insertRow(col, vec);// 새로 갱신된 정보를 삽입
	   }
   }//updateTable(UserData d)
   
   // 새로운 회원이 등록된 경우, 테이블에 띄워줌
   public void insertTable(UserData d) {
	   vec = new Vector<String>();
	   vec.addElement(d.getId());
	   vec.addElement(d.getName());
	   vec.addElement(d.getPassword());
	   vec.addElement(d.getBirth());
	   vec.addElement(Integer.toString(d.getTime()));
	   model.addRow(vec);
	   // 새로운 회원가입이 들어온 경우에
   }//insertTable(UserData d)
   
}