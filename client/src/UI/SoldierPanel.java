package src.UI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.bean.Soldier;
import src.common.Const;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SoldierPanel extends JPanel{

	private Soldier soldier;
	JLabel name ;
	JButton level ;  //是否是队长
	JButton group ;  //组号
//	JButton verify;
	JLabel pri_key;

	
	public SoldierPanel() {
		soldier = new Soldier();
		this.lanch();
	}
	public SoldierPanel(Soldier soldier) {
		this.soldier = soldier;
		this.lanch();
	}
	
	public void lanch() {
        setLayout(null);
		this.setOpaque(false);
		this.setSize(Const.SOLDIER_WIDTH , 30+30+Const.SOLDIER_HEIGTH+30);
		this.setVisible(true);
		JPanel panel_info = new JPanel();
		panel_info.setLayout(null);
		panel_info.setBounds(0, 0, Const.SOLDIER_WIDTH, 30);
		panel_info.setOpaque(false);
		this.add(panel_info);
		
		name = new JLabel(this.soldier.getName());
		name.setBounds(0, 0, Const.SOLDIER_WIDTH, 30);
		panel_info.add(name);
		
		JPanel panel_info_second = new JPanel();
		panel_info_second.setOpaque(false);
		panel_info_second .setLayout(null);
		panel_info_second .setBounds(0, 30, Const.SOLDIER_WIDTH, 30);
		this.add(panel_info_second);
		
		level = new JButton(this.soldier.levelToString());
		level.setBounds(0, 0, 30, 30);
		panel_info_second .add(level);
		
		group = new JButton(String.valueOf(this.soldier.getGroup()));
		group.setBounds(30+10, 0, 30, 30);
		panel_info_second .add(group);
		
//		verify = new JButton(String.valueOf("验证"));
//		verify.setBounds(30+10+30+10, 0, 30, 30);
//		panel_info_second .add(verify);
		
		JLabel panel_image = new JLabel();
		panel_image.setOpaque(false);
		panel_image.setBounds(0, 60, Const.SOLDIER_WIDTH, Const.SOLDIER_HEIGTH);
		
		ImageIcon icon = new ImageIcon(Const.SOLDIER_IMAGE);   
		icon.setImage(icon.getImage().getScaledInstance(Const.SOLDIER_WIDTH,Const.SOLDIER_HEIGTH,Image.SCALE_DEFAULT));//80和100为大小 可以自由设置
		panel_image.setIcon(icon);
		this.add(panel_image);
		
		panel_image.addMouseListener(new OperationListener());
		
	    pri_key = new JLabel("双击该士兵图标进行验证");
	    pri_key.setFont(new java.awt.Font("Dialog", 1, 15));
	    pri_key.setForeground(Color.WHITE);
	    pri_key.setOpaque(false);
	    pri_key.setBounds(0, 60+ Const.SOLDIER_HEIGTH, Const.SOLDIER_WIDTH, 30);
	    pri_key.setVisible(false);
	    this.add(pri_key);
	}
	
	/**
	 * 鼠标移动到士兵的图片时，会显示一系列可执行的操作，如士兵的pri_key
	 * @author zhangsukun
	 *
	 */
	class OperationListener implements MouseListener{


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			pri_key.setVisible(true);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			pri_key.setVisible(false);
			
		}
      
		@Override
		public void mouseClicked(MouseEvent e) {      //鼠标双击进行验证
			// TODO Auto-generated method stub
			if(e.getClickCount()==2){
                System.out.println("发送验证消息");
          }
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/*
	 * 测试函数
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame f  = new JFrame();
		f.setVisible(true);
		Soldier so = new Soldier();
		SoldierPanel sop = new SoldierPanel(so);
		f.add(sop);
		
	}
}
