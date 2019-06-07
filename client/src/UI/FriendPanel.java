package src.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import src.bean.Soldier;
import src.common.Const;

public class FriendPanel extends JPanel{

	private List<FriendCardPanel> PanelFriendList;
	private List<Soldier> friendList;
	
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
			this.setBackground(Color.GRAY);
			
			ImageIcon icon = new ImageIcon(Const.FRIEND_CARD_IMAGE_SOLDIER);   
			if(soldier.isLeader()) {
				icon = new ImageIcon(Const.FRIEND_CARD_IMAGE_LEADER);   
			}
			icon.setImage(icon.getImage().getScaledInstance(Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_IMAGE_SIZE,Image.SCALE_DEFAULT));//80和100为大小 可以自由设置
			image.setIcon(icon);
			image.setBounds(Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_GEZI, Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_IMAGE_SIZE);
			
			name.setText(soldier.getName());
			name.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL_HEIGHT);
			if(soldier.getPub_key() == null || soldier.getPub_key() == "") {
				pub_key.setText("<html>还未验证该士兵</html>");
			}
			pub_key.setText("<html>"+soldier.getPub_key()+"44444444</html>");
			pub_key.setBounds(Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_IMAGE_SIZE,Const.FRIEND_CARD_GEZI*2 + Const.FRIEND_CARD_LABEL_HEIGHT , Const.FRIEND_CARD_LABEL_WIDTH, Const.FRIEND_CARD_LABEL2_HEIGHT);
			this.add(image);
			this.add(name);
			this.add(pub_key);
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
		Soldier so3 = new Soldier();
		List<Soldier> lists = new ArrayList<Soldier>();
		lists.add(so);
		lists.add(so2);
		lists.add(so3);
		
		FriendPanel sop = new FriendPanel(lists);
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
