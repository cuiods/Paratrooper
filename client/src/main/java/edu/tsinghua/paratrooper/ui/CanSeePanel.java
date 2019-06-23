package edu.tsinghua.paratrooper.ui;

import edu.tsinghua.paratrooper.common.Const;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CanSeePanel extends JPanel{

	 private BufferedImage bi ;
     private int point_x;
     private int point_y;
     private JLabel jlb_me;
     
	/**
	 * 构造函数 
	 * @param point_x 我的相对x坐标
	 * @param point_y  我的相对y坐标
	 * @throws IOException 
	 */
	public CanSeePanel(int point_x,int point_y)  {
		
		this.point_x = point_x;
		this.point_y = point_y;
		
		ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.ME_IMAGE_DOWN));
		icon.setImage(icon.getImage().getScaledInstance(Const.ME_SIZE,Const.ME_SIZE,Image.SCALE_DEFAULT));//80和100为大小 可以自由设置
		jlb_me = new JLabel();
		jlb_me.setIcon(icon);
		jlb_me.setBounds(Const.PANEL_SIZE/2 - Const.ME_SIZE/2, Const.PANEL_SIZE/2 - Const.ME_SIZE/2, Const.ME_SIZE, Const.ME_SIZE);
		this.add(jlb_me);
		//得到背景信息
	     try {
			 bi = ImageIO.read(this.getClass().getResourceAsStream(Const.BACKGROUND_IMAGE));
		} catch (IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		}
	     this.setOpaque(false);
		 this.lanch();
	}
	
	/**
	 * 重新设置移动坐标并重绘图片
	 * @param point_x
	 * @param point_y
     * @param type
	 */
	public void resetPoint(int point_x,int point_y,int type) {
		this.point_x = point_x;
		this.point_y = point_y;
        this.repaint();
        if(type == 1) {
            ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.ME_IMAGE_UP));
            icon.setImage(icon.getImage().getScaledInstance(Const.ME_SIZE, Const.ME_SIZE, Image.SCALE_DEFAULT));
            jlb_me.setIcon(icon);
        }else if(type == 2) {
            ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.ME_IMAGE_DOWN));
            icon.setImage(icon.getImage().getScaledInstance(Const.ME_SIZE, Const.ME_SIZE, Image.SCALE_DEFAULT));
            jlb_me.setIcon(icon);
        }else if( type == 3) {
            ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.ME_IMAGE_LEFT));
            icon.setImage(icon.getImage().getScaledInstance(Const.ME_SIZE, Const.ME_SIZE, Image.SCALE_DEFAULT));
            jlb_me.setIcon(icon);
        }else{
            ImageIcon  icon  = new ImageIcon(this.getClass().getResource(Const.ME_IMAGE_RIGHT));
            icon.setImage(icon.getImage().getScaledInstance(Const.ME_SIZE,Const.ME_SIZE,Image.SCALE_DEFAULT));
            jlb_me.setIcon(icon);
        }
        jlb_me.setBounds(Const.PANEL_SIZE/2 - Const.ME_SIZE/2, Const.PANEL_SIZE/2 - Const.ME_SIZE/2, Const.ME_SIZE, Const.ME_SIZE);
        jlb_me.setVisible(true);
	}
	
	/**
	 * 重绘函数
	 */
	protected void paintComponent(Graphics g) { 
		super.paintComponent(g); 
		
		BufferedImage img = null;
		try {
			img = dealImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 g.drawImage(img, 
					0,0,
					Const.PANEL_SIZE, Const.PANEL_SIZE,null); 
	}
	
	/**
	 * 加载panel信息
	 */
	public void lanch() {
	       this.setVisible(true);
		   this.setLayout(null);
		   this.setSize(Const.PANEL_SIZE, Const.PANEL_SIZE);
	}
	
	/**
	 *  切割视角图片为圆形
	 * @return
	 * @throws IOException
	 */
	public BufferedImage dealImage() throws IOException{
		BufferedImage temp_bi = bi.getSubimage(point_x -(Const.RDDIUS), 
				                               point_y -(Const.RDDIUS), 
				                               Const.PANEL_SIZE, Const.PANEL_SIZE);//前两个值是坐标位置X、Y，后两个是长和宽
		
		BufferedImage resultImg = null;
		resultImg = new BufferedImage(temp_bi.getWidth(), temp_bi.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resultImg.createGraphics();
		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, temp_bi.getWidth(), temp_bi.getHeight());
		// 使用 setRenderingHint 设置抗锯齿
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		resultImg = g.getDeviceConfiguration().createCompatibleImage(temp_bi.getWidth(), temp_bi.getHeight(),Transparency.TRANSLUCENT);
		//g.fill(new Rectangle(buffImg2.getWidth(), buffImg2.getHeight()));
		g = resultImg.createGraphics();
		// 使用 setRenderingHint 设置抗锯齿
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setClip(shape);
		g.drawImage(temp_bi, 0, 0, null);
		g.dispose();
		return resultImg;
	}
	/*
	 * 测试函数
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame f = new JFrame();
		f.setSize(2000, 2000);  //宽  高
		f.setLocation(600, 200);
		f.setLayout(null);
		
		CanSeePanel test = new CanSeePanel(2000,1500);
		f.add(test);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  

	}
}
