import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PcMemberPanel extends JPanel {
	
	private JScrollPane scroll;
	private String[] columnNames = {"아이디","이름","비밀번호","남은시간","생년월일"};
	private Object rowData[][] = {
			{"gjwlsrb","허진규","123","1","950811"},		
			{"rlaalsdn","김민우","323","2","950811"},
			{"tpdud","서세영","241","3","950811"}
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
