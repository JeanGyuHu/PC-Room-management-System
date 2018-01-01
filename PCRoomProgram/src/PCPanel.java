import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class PCPanel extends JPanel{
	
	private JLabel lblCounter;
	private JPanel col1, col2, col3;
	protected JButton btnPC[];
	protected JLabel lblPC[][];
	private Font fnt;
	public static final int TIME = 0;
	public static final int ID = 1;
	public static final int MESSAGE = 2;
	
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
	    
	    fnt = new Font("Consolas",Font.LAYOUT_LEFT_TO_RIGHT, 8);
	    
	    btnPC = new JButton[24];
	    for(int i=0;i<24;i++) {
	    	btnPC[i] = new JButton();
	    	btnPC[i].setLayout(new GridLayout(3,1));
	    	btnPC[i].setBackground(Color.white);
	    	if(i%6 == 0 || i%6 == 1) col1.add(btnPC[i]);
	    	else if(i%6 == 2 || i%6 == 3) col2.add(btnPC[i]);
	    	else if(i%6 == 4 || i%6 == 5) col3.add(btnPC[i]);
	    }
	    
	    lblPC = new JLabel[24][3];
	    
	    for(int i=0;i<24;i++) {
	    	for(int j=0;j<3;j++) {
		    	if(j==0)lblPC[i][j] = new JLabel("X");
		    	else lblPC[i][j] = new JLabel("");
		    	lblPC[i][j].setFont(fnt);
		    	btnPC[i].add(lblPC[i][j]);
	    	}
	    }
	    /*
	    lblPC[20][TIME].setText("1");
	    lblPC[20][ID].setText("ssyaoao");
	    lblPC[20][MESSAGE].setText("YES");
	    */
	    
	} // PCPanel()
	
	public void addButtonActionListener(ActionListener listener) {
		for(int i=0;i<24;i++) {
			btnPC[i].addActionListener(listener);
		}
	}

} // class
