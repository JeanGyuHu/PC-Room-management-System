import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class PCPanel extends JPanel{
	
	private JLabel lblCounter; // 카운터 위치를 띄울 라벨
	private JPanel col1, col2, col3; // PC방의 좌석 위치의 각 열들
	protected JButton btnPC[]; // 접속한 사용자들의 좌석을 각 버튼으로 설정
	protected JLabel lblPC[][]; // 버튼에 띄울 사용자들의 정보
	private Font fnt; // 폰트 값
	public static final int TIME = 0; // 버튼에 띄울 시간
	public static final int ID = 1; // 버튼에 띄울 ID
	public static final int MESSAGE = 2; // 버튼에 띄울 메시지 유무의 여부를 상수로 선언하여 라벨에 접근이 용이
	
	public PCPanel() {
		setPreferredSize(new Dimension(500, 400));
	    setLayout(null);
	    setBackground(Color.white);
	    
	    lblCounter = new JLabel("COUNTER");
	    lblCounter.setBounds(340, 10, 140, 40);
	    lblCounter.setBackground(Color.white);
	    lblCounter.setHorizontalAlignment(SwingConstants.CENTER);
        lblCounter.setVerticalAlignment(SwingConstants.CENTER);
        lblCounter.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        add(lblCounter);
        
	    JPanel col1 = new JPanel();
	    col1.setBounds(20, 60, 140, 320);
	    col1.setLayout(new GridLayout(4,2));
	    add(col1);
	    
	    JPanel col2 = new JPanel();
	    col2.setBounds(180, 60, 140, 320);
	    col2.setLayout(new GridLayout(4,2));
	    add(col2);
	    
	    JPanel col3 = new JPanel();
	    col3.setBounds(340, 60, 140, 320);
	    col3.setLayout(new GridLayout(4,2));
	    add(col3);
	    
	    fnt = new Font("야놀자 야체 R",Font.LAYOUT_LEFT_TO_RIGHT, 9);
	    
	    btnPC = new JButton[24];
	    for(int i=0;i<24;i++) {
	    	btnPC[i] = new JButton();
	    	btnPC[i].setLayout(new GridLayout(3,1));
	    	btnPC[i].setBackground(Color.white);
	    	// 버튼이 들어갈 열을 설정
	    	if(i%6 == 0 || i%6 == 1) col1.add(btnPC[i]);
	    	else if(i%6 == 2 || i%6 == 3) col2.add(btnPC[i]);
	    	else if(i%6 == 4 || i%6 == 5) col3.add(btnPC[i]);
	    }
	    
	    lblPC = new JLabel[24][3];
	    for(int i=0;i<24;i++) { // 2차원 배열 라벨 생성
	    	for(int j=0;j<3;j++) {
		    	if(j==0)lblPC[i][j] = new JLabel("X");
		    	else lblPC[i][j] = new JLabel("");
		    	lblPC[i][j].setFont(fnt);
		    	btnPC[i].add(lblPC[i][j]);
	    	}
	    }
	} // PCPanel()
	
	// 각 좌석 버튼에 대한 이벤트 리스너 처리 // View 와 Controller 분리
	public void addButtonActionListener(ActionListener listener) {
		for(int i=0;i<24;i++) {
			btnPC[i].addActionListener(listener);
		}
	}

} // class