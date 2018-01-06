import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PrimaryPanel extends JPanel {

   protected JPanel topPanel,leftPanel,rightPanel,modifyPanel,showInformationPanel,messagePanel;
   // topPanel : 사용자 관리, 좌석배치, 메시지, 종료버튼을 띄울 패널
   // leftPanel, : 사용자 정보를 띄울 PcmemberPanel과 좌석을 띄울 PCPanel을 띄울 왼쪽 패널
   // rightPanel : 사용자의 정보를 띄울 오른쪽 패널
   // modifyPanel : 시간 충전, 수정, 사용자 탈퇴 버튼이 있는 패널
   // showInformationPanel : 사용종료 버튼이 있는 패널
   // messagePanel : 메시지를 띄울 패널
   protected JButton btnMember,btnSeat, btnMessage, btnLogout;
   //순서대로 사용자 관리, 좌석배치, 메시지, 종료 버튼
   private ImageIcon[] image; // 각 사용자 관리, 좌석배치, 메시지, 종료 버튼들의 이미지
   protected JLabel lblId,lblName,lblPassword,lblBirth,lblTime;
   // 선택한 사용자의 ID, 이름, 비밀번호, 생일, 남은 시간 글귀를 띄울 라벨
   protected JTextField[] txtInfo; // 선택한 사용자의 ID, 이름, 비밀번호, 생일, 남은 시간을 띄울 텍스트 필드
   protected JButton btnModify,btnDelete,btnCharge,btnPowerOff;
   // 수정, 탈퇴, 시간 충전, 사용 종료 버튼
   private Font fnt; // 폰트 값
   protected JTextField txtMessage; // 메시지를 입력할 텍스트 필드
   protected JTextArea taMessage; // 전체 채팅 메시지를 띄울 창
   protected JScrollPane scroll; // 메시지 창에 띄울 스크롤
   protected JComboBox combo; // 1:1메시지를 위해 사용자의 id를 선택하는 콤보박스
   protected PcMemberPanel memberPanel;
   protected PCPanel pcPanel;

   public PrimaryPanel(){
      
      setPreferredSize(new Dimension(810,510));
      setBackground(Color.white);
      setLayout(null);
      
      fnt = new Font("야놀자 야체 R",Font.BOLD,20);
      
      topPanel = new JPanel();
      topPanel.setBounds(0,0,810,100);
      topPanel.setBackground(Color.white);
      add(topPanel);
      
      rightPanel = new JPanel();
      rightPanel.setBounds(510,110,300,400);
      rightPanel.setBackground(Color.white);
      rightPanel.setLayout(null);
      add(rightPanel);
   
      modifyPanel = new JPanel();
      modifyPanel.setBounds(0,300,300,100);
      modifyPanel.setBackground(Color.white);
      modifyPanel.setLayout(null);
      modifyPanel.setVisible(false);
      rightPanel.add(modifyPanel);
      
      btnCharge = new JButton("시간 충전");
      btnCharge.setBounds(20,15,260,30);
      
      btnCharge.setBackground(Color.white);
      modifyPanel.add(btnCharge);
      
      btnModify = new JButton("수정");
      btnModify.setBounds(20,60,120,30);
      btnModify.setBackground(Color.white);
      modifyPanel.add(btnModify);
      
      btnDelete = new JButton("탈퇴");
      btnDelete.setBounds(160,60,120,30);
      btnDelete.setBackground(Color.white);
      modifyPanel.add(btnDelete);

      showInformationPanel = new JPanel();
      showInformationPanel.setBounds(0,300,300,100);
      showInformationPanel.setBackground(Color.white);
      showInformationPanel.setLayout(null);
      showInformationPanel.setVisible(true);
      rightPanel.add(showInformationPanel);
      
      btnPowerOff = new JButton("사용 종료");
      btnPowerOff.setBounds(30,20,240,60);
      btnPowerOff.setBackground(Color.white);
      showInformationPanel.add(btnPowerOff);
      
      messagePanel = new JPanel();
      messagePanel.setBounds(510,110,280,400);
      messagePanel.setBackground(Color.white);
      messagePanel.setLayout(new BorderLayout());
      messagePanel.setVisible(false);
      add(messagePanel);
      
      taMessage = new JTextArea("",19,10);
      taMessage.setEditable(false);

      txtMessage = new JTextField(19);
      
      scroll = new JScrollPane(taMessage,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scroll.setBorder(BorderFactory.createTitledBorder("MESSAGE")); 
      
      combo = new JComboBox();
      
      messagePanel.add(scroll,BorderLayout.PAGE_START);
      messagePanel.add(txtMessage,BorderLayout.EAST);
      messagePanel.add(combo,BorderLayout.CENTER);
      
      txtInfo = new JTextField[5];
      
      for(int i =0;i<5;i++) { // 텍스트 필드가 각각 사용자의 ID, 이름, 비밀번호, 생일, 남은 시간
         txtInfo[i] = new JTextField(10);
         txtInfo[i].setBounds(150,50+50*i,140,30);
         rightPanel.add(txtInfo[i]);
      }
      
      lblId = new JLabel("아이디");
      lblId.setBounds(10,50,140,30);
      lblId.setFont(fnt);
      lblId.setHorizontalAlignment(SwingConstants.CENTER);
      rightPanel.add(lblId);
   
      lblName = new JLabel("이 름");
      lblName.setBounds(10,100,140,30);
      lblName.setFont(fnt);
      lblName.setHorizontalAlignment(SwingConstants.CENTER);
      rightPanel.add(lblName);

      lblPassword = new JLabel("비밀번호");
      lblPassword.setBounds(10,150,140,30);
      lblPassword.setFont(fnt);
      lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
      rightPanel.add(lblPassword);
      
      lblBirth = new JLabel("생년월일");
      lblBirth.setBounds(10,200,140,30);
      lblBirth.setFont(fnt);
      lblBirth.setHorizontalAlignment(SwingConstants.CENTER);
      rightPanel.add(lblBirth);
      
      lblTime = new JLabel("남은시간");
      lblTime.setBounds(10,250,140,30);
      lblTime.setFont(fnt);
      lblTime.setHorizontalAlignment(SwingConstants.CENTER);
      rightPanel.add(lblTime);
   
      leftPanel = new JPanel();
      leftPanel.setBounds(0,110,500,400);
      leftPanel.setBackground(Color.white);
      add(leftPanel);
      
      memberPanel = new PcMemberPanel();
      memberPanel.setBounds(0, 0, 500, 400);
      memberPanel.setVisible(false);
      leftPanel.add(memberPanel);
      
      pcPanel = new PCPanel();
      pcPanel.setVisible(true); // 기본 초기화면을 PCPanel로 설정
      leftPanel.add(pcPanel);
      
      image = new ImageIcon[4];
      image[0] = new ImageIcon("Images/사용자 설정.png");
      image[1] = new ImageIcon("Images/PC 관리.png");
      image[2] = new ImageIcon("Images/메세지.png");
      image[3] = new ImageIcon("Images/사용종료.png");
      
      btnMember = new JButton(image[0]);
      btnMember.setBorderPainted(false);
      btnMember.setContentAreaFilled(false);
      btnMember.setFocusPainted(false);
      topPanel.add(btnMember);
      
      btnSeat = new JButton(image[1]);
      btnSeat.setBorderPainted(false);
      btnSeat.setContentAreaFilled(false);
      btnSeat.setFocusPainted(false);
      topPanel.add(btnSeat);
      
      btnMessage = new JButton(image[2]);
      btnMessage.setBorderPainted(false);
      btnMessage.setContentAreaFilled(false);
      btnMessage.setFocusPainted(false);
      topPanel.add(btnMessage);
      
      btnLogout = new JButton(image[3]);
      btnLogout.setBorderPainted(false);
      btnLogout.setContentAreaFilled(false);
      btnLogout.setFocusPainted(false);
      topPanel.add(btnLogout);      
   }
   
// 이벤트 리스너 처리 // View 와 Controller 분리
   public void addButtonActionListener(ActionListener listener) {
      btnMember.addActionListener(listener); // 사용자 관리 버튼
      btnSeat.addActionListener(listener); // 좌석 관리 버튼
      btnMessage.addActionListener(listener); // 메시지 선택 버튼
      btnLogout.addActionListener(listener); // 서버의 사용 종료 버튼
      btnCharge.addActionListener(listener); // 클라이언트 시간 충전 버튼
      btnDelete.addActionListener(listener); // 클라이언트 탈퇴 버튼
      btnModify.addActionListener(listener); // 클라이언트 정보 수정 버튼
      btnPowerOff.addActionListener(listener); // 클라이언트의 사용 종료 버튼
      txtMessage.addActionListener(listener); // 보낼 메시지의 텍스트 필드
      
   }
   
}