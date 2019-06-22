package edu.tsinghua.paratrooper.ui;

import edu.tsinghua.paratrooper.bean.Box;
import edu.tsinghua.paratrooper.bean.Soldier;
import edu.tsinghua.paratrooper.common.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FriendPanel extends JPanel {

	private List<FriendCardPanel> PanelFriendList;
	private List<StrangerCardPanel> PanelStrangerList;
	private List<BoxCardPanel> PanelBoxList;

	private List<Soldier> friendList;
	private List<Soldier> strangerList;
	private List<Box> boxList;
	
	public FriendPanel(List<Soldier> friendList) {
		this.friendList = friendList;
		PanelFriendList = new ArrayList<FriendCardPanel>();
		int start = 5;
		for(int i= 0 ; i< friendList.size();i++) {
			FriendCardPanel temp = new FriendCardPanel(friendList.get(i));
			this.add(temp);
			PanelFriendList.add(temp);
			temp.setBounds(Const.FRIEND_CARD_GEZI,  start + i * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
		}
		
		this.setLayout(new BorderLayout(0,0));
        this.setSize(Const.FRIEND_PANEL_WIDTH, Const.FRIEND_PANEL_HEIGHT);
        this.setPreferredSize(new Dimension(Const.FRIEND_PANEL_WIDTH, Const.FRIEND_PANEL_HEIGHT));
        this.setVisible(true);
	}

	public FriendPanel(List<Soldier> friendList, List<Soldier> strangerList, List<Box> boxList) {
		this.friendList = friendList;
		this.strangerList = strangerList;
		this.boxList = boxList;

		PanelFriendList = new ArrayList<FriendCardPanel>();
		PanelStrangerList = new ArrayList<StrangerCardPanel>();
		PanelBoxList = new ArrayList<BoxCardPanel>();

		int labelHeight = 30;

		LabelPanel friLabelPanel = new LabelPanel("队友");
		this.add(friLabelPanel);
		friLabelPanel.setBounds(Const.FRIEND_CARD_GEZI,  5, Const.FRIEND_CARD_WIDTH, labelHeight);


		int start = 5;
		int pos = 0;
		for(int i= 0 ; i< friendList.size();i++) {
			FriendCardPanel temp = new FriendCardPanel(friendList.get(i));
			this.add(temp);
			PanelFriendList.add(temp);
			temp.setBounds(Const.FRIEND_CARD_GEZI,  labelHeight + start + pos * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
			pos++;
		}

		LabelPanel strangerPanel = new LabelPanel("陌生人");
		this.add(strangerPanel);
		strangerPanel.setBounds(Const.FRIEND_CARD_GEZI,  labelHeight + start + pos * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, labelHeight);


		for(int i= 0 ; i< strangerList.size();i++) {
			StrangerCardPanel temp = new StrangerCardPanel(friendList.get(i));
			this.add(temp);
			PanelStrangerList.add(temp);
			temp.setBounds(Const.FRIEND_CARD_GEZI,  2*labelHeight + start + pos * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
			pos++;
		}

		LabelPanel boxPanel = new LabelPanel("宝箱");
		this.add(boxPanel);
		boxPanel.setBounds(Const.FRIEND_CARD_GEZI,  2*labelHeight + start + pos * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, labelHeight);

		for(int i= 0 ; i< boxList.size();i++) {
			BoxCardPanel temp = new BoxCardPanel(friendList.get(i));
			this.add(temp);
			PanelBoxList.add(temp);
			temp.setBounds(Const.FRIEND_CARD_GEZI,  3*labelHeight + start + pos * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
			pos++;
		}

		this.setLayout(new BorderLayout(0,0));
		this.setSize(Const.FRIEND_PANEL_WIDTH, Const.FRIEND_PANEL_HEIGHT);
		this.setPreferredSize(new Dimension(Const.FRIEND_PANEL_WIDTH, Const.FRIEND_PANEL_HEIGHT));
		this.setVisible(true);
	}

	/**
	 * 刷新好友卡片
	 * @param friendList
	 */
	public void resetPerFriend(List<Soldier> friendList){

		int start = 5;
		int i = 0 ;
		int j = 0 ;

//		for (i = 0,j= 0; i < friendList.size() && j < PanelFriendList.size(); i++,j++) {
//			FriendCardPanel temp = PanelFriendList.get(i);
//			StrangerCardPanel temp = PanelFriendList.get(i);
//			BoxCardPanel temp = PanelFriendList.get(i);
//			temp.setBounds(Const.FRIEND_CARD_GEZI, start + i * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
//		    temp.reset_info(friendList.get(i));
//		}
//		while(i < friendList.size()) {// 需要new
//			FriendCardPanel temp = new FriendCardPanel(friendList.get(i));
//			StrangerCardPanel temp = new StrangerCardPanel(friendList.get(i));
//			BoxCardPanel temp = new BoxCardPanel(friendList.get(i));
//			this.add(temp);
//			PanelFriendList.add(temp);
//			temp.setBounds(Const.FRIEND_CARD_GEZI, start + i * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
//		    i++;
//		}
		while(j < PanelFriendList.size()){
			PanelFriendList.get(j).setVisible(false);
			j++;
		}
	}
	
	/**
	 * 内部类，每个好友列表的卡片
	 * @author zhangsukun
	 *
	 */
	class FriendCardPanel extends JPanel{
		
		private JLabel image;
		private JLabel name;
		private JLabel pub_key;
		private Soldier soldier;
		
		public FriendCardPanel(Soldier soldier) {
			image = new JLabel();
			name = new JLabel();
			pub_key = new JLabel();
			this.soldier = soldier;
			lanch();
			
		}
		
		public void lanch() {
			this.setSize(Const.FRIEND_CARD_WIDTH,Const.FRIEND_CARD_HEIGTH);
			this.setLayout(null);
			this.setBackground(Color.lightGray);
			
			ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_SOLDIER));
			if(soldier.isCaptain()==1) {
				icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_LEADER));
			}
			icon.setImage(icon.getImage().getScaledInstance(Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_IMAGE_SIZE,Image.SCALE_DEFAULT));//80和100为大小 可以自由设置
			image.setIcon(icon);
			image.setBounds(Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_IMAGE_SIZE);
			
			name.setText(soldier.getName());
			name.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL_HEIGHT);
//			if(soldier.getPublicKey() == null || soldier.getPublicKey() == "") {
//				pub_key.setText("<html>还未验证该士兵</html>");
//			}
			pub_key.setText("<html>"+soldier.getPublicKey()+"</html>");
			pub_key.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_LABEL_HEIGHT , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL2_HEIGHT);
			this.add(image);
			this.add(name);
			this.add(pub_key);
			this.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		
		}

		public void reset_info(Soldier soldier){
			name.setText(soldier.getName());
			pub_key.setText("<html>"+soldier.getPublicKey()+"</html>");
			ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_SOLDIER));
			if(soldier.isCaptain()==1) {
				icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_LEADER));
			}
			icon.setImage(icon.getImage().getScaledInstance(Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_IMAGE_SIZE,Image.SCALE_DEFAULT));//80和100为大小 可以自由设置
			image.setIcon(icon);
		}
	}


	/**
	 * 内部类，陌生人列表的卡片
	 * @author zhangsukun
	 *
	 */
	class StrangerCardPanel extends JPanel{

		private JLabel image;
		private JLabel name;
		private Soldier soldier;
		private JButton authenBtn;

		public StrangerCardPanel(Soldier soldier) {
			image = new JLabel();
			name = new JLabel();
			this.soldier = soldier;
			authenBtn = new JButton();
			lanch();
		}

		public void lanch() {
			this.setSize(Const.FRIEND_CARD_WIDTH,Const.FRIEND_CARD_HEIGTH);
			this.setLayout(null);
			this.setBackground(Color.GRAY);

			ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_SOLDIER));
			if(soldier.isCaptain()==1) {
				icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_LEADER));
			}
			icon.setImage(icon.getImage().getScaledInstance(Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_IMAGE_SIZE,Image.SCALE_DEFAULT));//80和100为大小 可以自由设置
			image.setIcon(icon);
			image.setBounds(Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_IMAGE_SIZE);

			name.setText(soldier.getName());
			name.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL_HEIGHT);
//			if(soldier.getPublicKey() == null || soldier.getPublicKey() == "") {
//				pub_key.setText("<html>还未验证该士兵</html>");
//			}

			authenBtn.setLabel("验证");
			authenBtn.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_LABEL_HEIGHT , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL2_HEIGHT);
			authenBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					System.out.println("click");
				}
			});


			this.add(image);
			this.add(name);
			this.add(authenBtn);

			this.setBorder(BorderFactory.createRaisedSoftBevelBorder());

		}

		public void reset_info(Soldier soldier){
			name.setText(soldier.getName());
			ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_SOLDIER));
			if(soldier.isCaptain()==1) {
				icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_LEADER));
			}
			icon.setImage(icon.getImage().getScaledInstance(Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_IMAGE_SIZE,Image.SCALE_DEFAULT));//80和100为大小 可以自由设置
			image.setIcon(icon);
		}



	}


	/**
	 * 内部类，箱子列表的卡片
	 * @author zhangsukun
	 *
	 */
	class BoxCardPanel extends JPanel{

		private JLabel image;
		private JLabel name;

		private Soldier soldier;
		private JButton authenBtn;

		public BoxCardPanel(Soldier soldier) {
			image = new JLabel();
			name = new JLabel();
			this.soldier = soldier;
			authenBtn = new JButton();
			lanch();
		}

		public void lanch() {
			this.setSize(Const.FRIEND_CARD_WIDTH,Const.FRIEND_CARD_HEIGTH);
			this.setLayout(null);
			this.setBackground(Color.YELLOW);

			ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_SOLDIER));
			if(soldier.isCaptain()==1) {
				icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_LEADER));
			}
			icon.setImage(icon.getImage().getScaledInstance(Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_IMAGE_SIZE,Image.SCALE_DEFAULT));//80和100为大小 可以自由设置
			image.setIcon(icon);
			image.setBounds(Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_IMAGE_SIZE);

			name.setText(soldier.getName());
			name.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL_HEIGHT);
//			if(soldier.getPublicKey() == null || soldier.getPublicKey() == "") {
//				pub_key.setText("<html>还未验证该士兵</html>");
//			}

			authenBtn.setLabel("开箱");
			authenBtn.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_LABEL_HEIGHT , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL2_HEIGHT);
			authenBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					System.out.println("click");
				}
			});


			this.add(image);
			this.add(name);
			this.add(authenBtn);

			this.setBorder(BorderFactory.createRaisedSoftBevelBorder());

		}

		public void reset_info(Soldier soldier){
			name.setText(soldier.getName());
			ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_SOLDIER));
			if(soldier.isCaptain()==1) {
				icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_LEADER));
			}
			icon.setImage(icon.getImage().getScaledInstance(Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_IMAGE_SIZE,Image.SCALE_DEFAULT));//80和100为大小 可以自由设置
			image.setIcon(icon);
		}



	}


	/**
	 * 内部类，标签的卡片
	 * @author zhangsukun
	 *
	 */
	class LabelPanel extends JPanel{

		private JLabel name;

		public LabelPanel(String title) {
			name = new JLabel(title);
			lanch();
		}

		public void lanch() {
			this.setSize(Const.FRIEND_CARD_WIDTH,Const.FRIEND_CARD_HEIGTH);
			this.setLayout(null);
//			this.setBackground(Color.WHITE);

			name.setBounds(Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_IMAGE_SIZE, 16);
			this.add(name);
			this.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		}
	}




	/*
	 * 测试函数
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame f  = new JFrame();
		f.setSize(600, 800);
		f.setLayout(null);
		JPanel contentpanel = new JPanel();
		contentpanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		f.add(contentpanel);
		contentpanel.setBounds(0,0,240,600);
		contentpanel.setLayout(null);
		
		Soldier so = new Soldier();
		Soldier so2 = new Soldier();
		List<Soldier> lists = new ArrayList<Soldier>();
		lists.add(so);
		lists.add(so2);

		Box box1 = new Box();
		List<Box> boxLists = new ArrayList<>();
		boxLists.add(box1);
		
		FriendPanel sop = new FriendPanel(lists, lists, boxLists);
		JScrollPane  jsp = new JScrollPane();
		//jsp.add(sop);
		jsp.setViewportView(sop);
		jsp.setBounds(0, 0, 210, 600);
		jsp.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		contentpanel.add(jsp);
		
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		f.setVisible(true);
	}
}