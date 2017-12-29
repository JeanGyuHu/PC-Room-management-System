import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.*;

public class PcMemberPanel extends JPanel {
   
   private JScrollPane scroll;
   protected DefaultTableModel model;   
   private String[] columnNames = {"���̵�","�̸�","��й�ȣ","�����ð�","�������"};
   protected JTable memberTable;
   protected Vector<String> vec;
   
   public PcMemberPanel() {
      setPreferredSize(new Dimension(480, 400));
      setLayout(new BorderLayout());      
      setBackground(Color.white);
      
      model = new DefaultTableModel(columnNames, 0);
      
      memberTable = new JTable(model);
      memberTable.setForeground(Color.WHITE);
      
      vec = new Vector<String>();
      /*
       * ���Կ���
      for(int j = 0; j  < 10 ; j++) {
         for( int i = 0; i < 5; i++) {
            vec.addElement(i + "aa"); 
         }
         model.addRow(vec);
      }*/
      
      scroll = new JScrollPane(memberTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      
      add(scroll);
   }
}