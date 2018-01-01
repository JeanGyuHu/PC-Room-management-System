import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.*;

public class PcMemberPanel extends JPanel {
   
   private JScrollPane scroll;
   protected DefaultTableModel model;   
   private String[] columnNames = {"아이디","이름","비밀번호","남은시간","생년월일"};
   protected JTable memberTable;
   protected Vector<String> vec;
   private UserDAO dao;
   protected ArrayList<UserData> datas;
   
   public PcMemberPanel() {
      setPreferredSize(new Dimension(480, 400));
      setLayout(new BorderLayout());      
      setBackground(Color.white);
      
      dao = new UserDAO();
      
      model = new DefaultTableModel(columnNames, 0);
      
      memberTable = new JTable(model);
      memberTable.setForeground(Color.black);
      
      vec = new Vector<String>();
      /*
       * 삽입예시
      for(int j = 0; j  < 10 ; j++) {
         for( int i = 0; i < 5; i++) {
            vec.addElement(i + "aa"); 
         }
         model.addRow(vec);
      }*/
      
      scroll = new JScrollPane(memberTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      
      add(scroll);
      
      datas = new ArrayList<UserData>();
      
      refreshTable();
   }
   public void refreshTable() {
	   datas = dao.getAll();
		
	   if(datas!=null) {
		   for(UserData d :datas) {
			   
			  	vec = new Vector<String>();
	            vec.addElement(d.getId());
	            vec.addElement(d.getName());
	            vec.addElement(d.getPassword());
	            vec.addElement(Integer.toString(d.getTime()));
	            vec.addElement(d.getBirth());
	            
	            model.addRow(vec);
	         }
	        
	   }
	
   }
   
   public String getValue() {
	   int row = memberTable.getSelectedRow();
	   int col = memberTable.getSelectedColumn();
	   String answer=null;
	   Object value = memberTable.getValueAt(row, col);
	   
	   answer = value.toString();
	   
	   return answer;
   }
}