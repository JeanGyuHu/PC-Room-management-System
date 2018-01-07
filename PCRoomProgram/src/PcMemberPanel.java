import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.*;

public class PcMemberPanel extends JPanel {
   
   private JScrollPane scroll;
   protected DefaultTableModel model;   
   private String[] columnNames = {"아이디","이름","비밀번호","생년월일","남은시간"};
   protected JTable memberTable;
   protected Vector<String> vec;
   private UserDAO dao;
   protected ArrayList<UserData> datas;
   
   public PcMemberPanel() {
      setPreferredSize(new Dimension(480, 400));
      setLayout(new BorderLayout());      
      setBackground(Color.white);
      
      dao = new UserDAO();
      
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
   
   
   public void refreshTable() {
      datas = dao.getAll();// 데이터 베이스에서 존재하는 회원 정보를 다 읽어온후
      if(datas!=null) {
         for(UserData d :datas) {
        	 vec = new Vector<String>();
             vec.addElement(d.getId());
             vec.addElement(d.getName());
             vec.addElement(d.getPassword());
             vec.addElement(d.getBirth());
             vec.addElement(Integer.toString(d.getTime())+ "분");               
             model.addRow(vec);// 테이블에 삽입
            }
        datas.clear();   
      }
   }
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
		   vec.addElement(Integer.toString(d.getTime()) + "분");
		   System.out.println(col);
		   model.removeRow(col);// 그전의 있던 정보를 삭제하고
		   model.insertRow(col, vec);// 새로 갱신된 정보를 삽입
	   }
   }//updateTable(UserData d)
   
   public void updateTime(UserData d) {
	   int col = 0;
	
	   for(col = 0; col < memberTable.getRowCount(); col++) 
		  if( memberTable.getValueAt(col, 0).equals(d.getId()))// 테이블에서 넘어온 아이디와 같은 경우 
			  break;
	   
	   vec = new Vector<String>();
	   vec.addElement(d.getId());
	   vec.addElement(d.getName());
	   vec.addElement(d.getPassword());
	   vec.addElement(d.getBirth());
	   vec.addElement(Integer.toString(d.getTime())+ "분");
	   model.removeRow(col);// 그전의 있던 정보를 삭제하고
	   model.insertRow(col, vec);// 새로 갱신된 정보를 삽입
	   
	   
   }//updateTime(UserData d)
   
   public void insertTable(UserData d) {
	   vec = new Vector<String>();
	   vec.addElement(d.getId());
	   vec.addElement(d.getName());
	   vec.addElement(d.getPassword());
	   vec.addElement(d.getBirth());
	   vec.addElement(Integer.toString(d.getTime())+ "분");
	   model.addRow(vec);
	   // 새로운 회원가입이 들어온 경우에
   }//insertTable(UserData d)
   
}