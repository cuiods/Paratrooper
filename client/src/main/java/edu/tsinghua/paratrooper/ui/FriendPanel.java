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
	private List<LabelPanel> PanelLabelList;

	private List<Soldier> friendList;
	private List<Soldier> strangerList;
	private List<Box> boxList;

	private int panelCount;

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
		PanelLabelList = new ArrayList<LabelPanel>();


		LabelPanel friLabelPanel = new LabelPanel("队友");
		PanelLabelList.add(friLabelPanel);
		this.add(friLabelPanel);
		friLabelPanel.setBounds(Const.FRIEND_CARD_GEZI,  5, Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_TITLE_HEIGHT);

		int start = 5;
		int panelCount = 0;
		for(int i= 0 ; i< friendList.size();i++) {
			FriendCardPanel temp = new FriendCardPanel(friendList.get(i));
			this.add(temp);
			PanelFriendList.add(temp);
			temp.setBounds(Const.FRIEND_CARD_GEZI,  Const.FRIEND_CARD_TITLE_SPACE + Const.FRIEND_CARD_TITLE_HEIGHT + start + panelCount * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
			panelCount++;
		}

		LabelPanel strangerPanel = new LabelPanel("陌生人");
		PanelLabelList.add(strangerPanel);
		this.add(strangerPanel);
		strangerPanel.setBounds(Const.FRIEND_CARD_GEZI,  Const.FRIEND_CARD_TITLE_SPACE + Const.FRIEND_CARD_TITLE_HEIGHT + start + panelCount * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_TITLE_HEIGHT);

		for(int i= 0 ; i< strangerList.size();i++) {
			StrangerCardPanel temp = new StrangerCardPanel(friendList.get(i));
			this.add(temp);
			PanelStrangerList.add(temp);
			temp.setBounds(Const.FRIEND_CARD_GEZI,  2*Const.FRIEND_CARD_TITLE_SPACE + 2*Const.FRIEND_CARD_TITLE_HEIGHT + start + panelCount * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
			panelCount++;
		}

		LabelPanel boxPanel = new LabelPanel("宝箱");
		PanelLabelList.add(boxPanel);
		this.add(boxPanel);
		boxPanel.setBounds(Const.FRIEND_CARD_GEZI,  2*Const.FRIEND_CARD_TITLE_SPACE + 2*Const.FRIEND_CARD_TITLE_HEIGHT + start + panelCount * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_TITLE_HEIGHT);

		for(int i= 0 ; i< 1;i++) {
			BoxCardPanel temp = new BoxCardPanel(friendList.get(i));
			this.add(temp);
			PanelBoxList.add(temp);
			temp.setBounds(Const.FRIEND_CARD_GEZI,  3*Const.FRIEND_CARD_TITLE_SPACE + 3*Const.FRIEND_CARD_TITLE_HEIGHT + start + panelCount * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
			panelCount++;
		}

		this.setLayout(new BorderLayout(0,0));
		this.setSize(Const.FRIEND_PANEL_WIDTH, Const.FRIEND_PANEL_HEIGHT);
		this.setPreferredSize(new Dimension(Const.FRIEND_PANEL_WIDTH, Const.FRIEND_PANEL_HEIGHT));
		this.setVisible(true);
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

			name.setText("<html>士兵"+soldier.getId()+"</html>");
			name.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL_HEIGHT);
//			if(soldier.getPublicKey() == null || soldier.getPublicKey() == "") {
//				pub_key.setText("<html>还未验证该士兵</html>");
//			}
			pub_key.setText("<html>"+soldier.getName()+"</html>");
			pub_key.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_LABEL_HEIGHT , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL2_HEIGHT);
			this.add(image);
			this.add(name);
			this.add(pub_key);
			this.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		}

		public void reset_info(Soldier soldier){

			if (soldier.getAlive() == 0) {
				pub_key.setText("离线");
				this.setBackground(Color.lightGray);
			} else {
				pub_key.setText("在线");
				this.setBackground(Color.white);
			}

			name.setText("<html>队友"+soldier.getId()+"</html>");
			ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_SOLDIER));
			if(soldier.isCaptain()==1) {
				name.setText("<html>队长"+soldier.getId()+"</html>");
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
		private JLabel dect;
		private Soldier soldier;
		private JButton authenBtn;

		public StrangerCardPanel(Soldier soldier) {
			image = new JLabel();
			name = new JLabel();
			dect = new JLabel();
			this.soldier = soldier;
			authenBtn = new JButton("验证");
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

			name.setText("<html>士兵"+soldier.getId()+"</html>");
			name.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL_HEIGHT);

			dect.setText("未验证");
			dect.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_LABEL_HEIGHT , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL2_HEIGHT);

			authenBtn.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_LABEL_HEIGHT , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL2_HEIGHT);
			authenBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					System.out.println("click");
				}
			});
			authenBtn.setVisible(false);

			this.add(image);
			this.add(name);
			this.add(dect);
			this.add(authenBtn);

			this.setBorder(BorderFactory.createRaisedSoftBevelBorder());

		}

		public void reset_info(Soldier me, Soldier soldier){
			name.setText("<html>士兵"+soldier.getId()+"</html>");
			ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_SOLDIER));
			icon.setImage(icon.getImage().getScaledInstance(Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_IMAGE_SIZE,Image.SCALE_DEFAULT));//80和100为大小 可以自由设置
			image.setIcon(icon);
			if (isHalfDistance(me, soldier)){
				authenBtn.setVisible(true);
				dect.setVisible(false);
			} else {
				authenBtn.setVisible(false);
				dect.setVisible(true);
			}
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
		private JLabel dect;

		private Soldier soldier;
		private JButton authenBtn;

		public BoxCardPanel(Soldier soldier) {
			image = new JLabel();
			name = new JLabel();
			dect = new JLabel();
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

		public void reset_info(Box box){
			name.setText("<html>宝箱"+box.getId()+"</html>");
			ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_SOLDIER));
			if(box.getStatus()==1) {
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

			name.setBounds(Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_IMAGE_SIZE, 16);
			this.add(name);
			this.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		}
	}


	/**
	 * 刷新好友卡片
	 * @param friendList
	 */
	public void resetPerFriend(List<Soldier> friendList){

		int start = 5;
		int i = 0 ;
		int j = 0 ;
		panelCount = 0;

		for (i = 0,j= 0; i < friendList.size() && j < PanelFriendList.size(); i++,j++) {
			FriendCardPanel temp = PanelFriendList.get(i);
			temp.setBounds(Const.FRIEND_CARD_GEZI,  Const.FRIEND_CARD_TITLE_SPACE + Const.FRIEND_CARD_TITLE_HEIGHT + start + panelCount * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
			temp.reset_info(friendList.get(i));
			temp.setVisible(true);
			panelCount++;
		}
		while(i < friendList.size()) {// 需要new
			FriendCardPanel temp = new FriendCardPanel(friendList.get(i));
			this.add(temp);
			PanelFriendList.add(temp);
			temp.setBounds(Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_TITLE_SPACE + Const.FRIEND_CARD_TITLE_HEIGHT + start + i * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
			i++;
			panelCount++;
		}
		while(j < PanelFriendList.size()){
			PanelFriendList.get(j).setVisible(false);
			j++;
		}
	}


	/**
	 * 刷新陌生人卡片
	 * @param strangerList
	 */
	public void resetPerStranger(List<Soldier> strangerList, Soldier me){

		int start = 5;
		int i = 0 ;
		int j = 0 ;

		LabelPanel strangerPanel = PanelLabelList.get(1);
		strangerPanel.setBounds(Const.FRIEND_CARD_GEZI,  Const.FRIEND_CARD_TITLE_SPACE + Const.FRIEND_CARD_TITLE_HEIGHT + start + panelCount * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_TITLE_HEIGHT);

		for (i = 0,j= 0; i < strangerList.size() && j < PanelStrangerList.size(); i++,j++) {
			StrangerCardPanel temp = PanelStrangerList.get(i);
			temp.setBounds(Const.FRIEND_CARD_GEZI,  2*Const.FRIEND_CARD_TITLE_SPACE + 2*Const.FRIEND_CARD_TITLE_HEIGHT + start + panelCount * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
			temp.reset_info(me, strangerList.get(i));
			temp.setVisible(true);
			panelCount++;
		}
		while(i < strangerList.size()) {// 需要new
			StrangerCardPanel temp = new StrangerCardPanel(strangerList.get(i));
			this.add(temp);
			PanelStrangerList.add(temp);
			temp.setBounds(Const.FRIEND_CARD_GEZI,  2*Const.FRIEND_CARD_TITLE_SPACE + 2*Const.FRIEND_CARD_TITLE_HEIGHT + start + panelCount * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
			i++;
			panelCount++;
		}
		while(j < PanelStrangerList.size()){
			PanelStrangerList.get(j).setVisible(false);
			j++;
		}
	}


	/**
	 * 刷新宝箱卡片
	 * @param boxList
	 */
	public void resetPerBox(List<Box> boxList){

		int start = 5;

		LabelPanel boxPanel = PanelLabelList.get(2);
		boxPanel.setBounds(Const.FRIEND_CARD_GEZI,  2*Const.FRIEND_CARD_TITLE_SPACE + 2*Const.FRIEND_CARD_TITLE_HEIGHT + start + panelCount * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_TITLE_HEIGHT);

		if (boxList != null) {
			BoxCardPanel temp = PanelBoxList.get(0);
			temp.setVisible(true);
			temp.setBounds(Const.FRIEND_CARD_GEZI,  3*Const.FRIEND_CARD_TITLE_SPACE + 3*Const.FRIEND_CARD_TITLE_HEIGHT + start + panelCount * (Const.FRIEND_CARD_HEIGTH + Const.FRIEND_CARD_GEZI), Const.FRIEND_CARD_WIDTH, Const.FRIEND_CARD_HEIGTH);
			temp.reset_info(boxList.get(0));
		} else {
			PanelBoxList.get(0).setVisible(false);
		}
	}


	public static boolean isHalfDistance(Soldier me,Soldier soldier) {
		int x = me.getLocationX();
		int y = me.getLocationY();
		int a = Math.abs(x - soldier.getLocationX());
		int b = Math.abs(y - soldier.getLocationY());
		if(a *a + b* b < Const.RDDIUS* Const.RDDIUS*0.3) {
			return true;
		}else {
			return false;
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
