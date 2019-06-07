package src.UI;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.bean.Box;
import src.common.Const;

public class BoxPanel extends JPanel{

	private JLabel jl_box;   //宝箱的图片 open or close
	private JLabel box_info;  //宝箱的信息
	private Box box;
	
	private int x ;
	private int y ;
	
	public BoxPanel() {
		jl_box = new JLabel();
		box_info = new JLabel();
		box = new Box();
		this.lanch();
	}
	public BoxPanel(Box box,int x,int y) {
		jl_box = new JLabel();
		box_info = new JLabel();
		this.box = box;
		this.lanch();
	}
	
	public void lanch() {
		
		box_info.setBounds(0, 0, Const.BOX_PANEL_WIDTH,Const.BOX_INFO_HEIGHT);
		box_info.setOpaque(false);
		
		ImageIcon box_icon = new ImageIcon(Const.BOX_OPEN_IMAGE); 
		box_icon.setImage(box_icon.getImage().getScaledInstance(Const.BOX_IMAGE_SIZE,Const.BOX_IMAGE_SIZE,Image.SCALE_DEFAULT));
		jl_box.setIcon(box_icon);
		jl_box.setOpaque(false);
		jl_box.setBounds(0,Const.BOX_INFO_HEIGHT,Const.BOX_IMAGE_SIZE,Const.BOX_IMAGE_SIZE);
		//jl_box.addMouseListener(new BoxListener());
		
		this.setBounds(box.getPoint_x() - Const.BOX_PANEL_WIDTH/2, box.getPoint_y() - Const.BOX_PANEL_HEIGHT/2, Const.BOX_PANEL_WIDTH, Const.BOX_PANEL_HEIGHT);
		this.setLayout(null);
		this.add(jl_box);
		this.add(box_info);
		this.setOpaque(false);
		this.setVisible(true);
		this.addMouseListener(new BoxListener());
	}

	/**
	 * 刷新宝箱的信息和图片
	 * @param box
	 * @param x   我的x坐标
	 * @param y   我的y坐标
	 */
	public void resetBox(Box box,int x ,int y) {
		this.box = box;
		if(box.isOpen()) {
			ImageIcon box_icon = new ImageIcon(Const.BOX_OPEN_IMAGE); 
			box_icon.setImage(box_icon.getImage().getScaledInstance(Const.BOX_IMAGE_SIZE,Const.BOX_IMAGE_SIZE,Image.SCALE_DEFAULT));
		}else{
			ImageIcon box_icon = new ImageIcon(Const.BOX_CLOSE_IMAGE); 
			box_icon.setImage(box_icon.getImage().getScaledInstance(Const.BOX_IMAGE_SIZE,Const.BOX_IMAGE_SIZE,Image.SCALE_DEFAULT));
		}
		
		String info = "<html>"+box.getHaveSodiler()+"/" + Const.NEED_SOLDIER + "(已参与队友/所需)</html>";
		box_info.setText(info);
		IsshowBox(x,y);
	}
	
	
	/**
	 * 判断宝箱是否在视野范围内
	 */
	public void IsshowBox(int x,int y) {
//		
//		if(box.getPoint_x() > x- Const.PANEL_SIZE/2 && box.getPoint_x() < x + Const.PANEL_SIZE/2 &&
//				box.getPoint_y() > y- Const.PANEL_SIZE/2 && box.getPoint_y() < y + Const.PANEL_SIZE/2) {
//			this.setVisible(true);
//		}else {
//			this.setVisible(false);
//		}
		int a = Math.abs(x - box.getPoint_x());
		int b = Math.abs(y - box.getPoint_y());
		if(a *a + b* b < Const.RDDIUS* Const.RDDIUS) {
			this.setVisible(true);
		}else {
			this.setVisible(false);
		}	
	}
	
	/**
	 * 判断是否能开箱
	 * @return
	 */
	public boolean judgeCanOpenBox() {
		if(box.getPoint_x()- Const.BOX_PANEL_WIDTH/2 - Const.DISTANCE < this.x + Const.SOLDIER_WIDTH/2 &&
				box.getPoint_x()+ Const.BOX_PANEL_WIDTH/2 + Const.DISTANCE > this.x - Const.SOLDIER_WIDTH/2 &&
				box.getPoint_y() - Const.BOX_PANEL_HEIGHT/2 - Const.DISTANCE < this.y + Const.SOLDIER_HEIGTH/2 &&
				box.getPoint_y() + Const.BOX_PANEL_HEIGHT/2 + Const.DISTANCE > this.y - Const.SOLDIER_HEIGTH/2) {
			return true;
		}
		return false;
	}
	
	/**
	 * 点击宝箱盒子，进行开箱
	 * @author zhangsukun
	 *
	 */
	class BoxListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			//判断是否接近我，在宝箱附近才可以开
			if(judgeCanOpenBox()) {
			    System.out.println("请求开箱子的逻辑");
			    
			}else {
				System.out.println("不能开箱");
			}
			//todo
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	/**
	 * 该值在父页面被更新，所以有get方法
	 * @return
	 */
	public JLabel getJL_Box() {
		return this.jl_box;
	}
	
	/**
	 * 该值
	 * @return
	 */
	public JLabel getBoxInfo() {
		return this.box_info;
	}
	
}

