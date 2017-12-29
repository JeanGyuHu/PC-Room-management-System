import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PcMemberPanel extends JPanel {
	
	private JList memberList;
	private JScrollPane scroll;
	private String[] str = {};
	private Vector vec;
	private JLabel title;
	
	public PcMemberPanel() {
		setPreferredSize(new Dimension(480, 400));
		setLayout(new BorderLayout());		
		setBackground(Color.white);

		memberList = new JList();
		title = new JLabel("아이디                이름                비밀번호                생년월일");
		add(title, BorderLayout.PAGE_START);
		
		vec = new Vector();

		for(int i = 0; i < str.length; i++) {
			vec.addElement(str[i]);
		}
		memberList.setListData(vec);
		
		scroll = new JScrollPane(memberList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);
		
		
	}
}
