import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PcMemberPanel extends JPanel {
	
	private JScrollPane scroll;
	private String[] columnNames = {"���̵�","�̸�","��й�ȣ","�����ð�","�������"};
	private Object rowData[][] = {
			{"gjwlsrb","������","123","1","950811"},		
			{"rlaalsdn","��ο�","323","2","950811"},
			{"tpdud","������","241","3","950811"}
	};
	private JTable memberTable;
	
	public PcMemberPanel() {
		setPreferredSize(new Dimension(480, 400));
		setLayout(new BorderLayout());		
		setBackground(Color.white);
		
		memberTable = new JTable(rowData, columnNames);
		
		add(memberTable);
	}
}
