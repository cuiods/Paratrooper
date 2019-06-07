package src.UI;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.xml.internal.ws.util.StringUtils;

import src.common.Const;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author zhangsukun
 *
 */
public class Demo extends JFrame{

		
		public Demo() {
			JPanel jpanel = new JPanel() {
				
				protected void paintComponent(Graphics g) { 
					super.paintComponent(g); 
					// 0,0 画在panel左上角    con.RDDIUS, con.RDDIUS panel 大小   100,100, con.RDDIUS,con.RDDIUS 图片的位置
					 try {
						BufferedImage img = dealImage();
						 g.drawImage(img, 
									0,0,
									Const.PANEL_SIZE, Const.PANEL_SIZE,null); 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			this.setLayout(null);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
			jpanel.setBounds(0,0,Const.PANEL_SIZE,Const.PANEL_SIZE);
			this.getContentPane().setBackground(Color.BLACK); 
			jpanel.setOpaque(false);
			jpanel.repaint();
			this.add(jpanel);
			this.setSize(600, 600);
			this.setVisible(true);
		}
		
		public BufferedImage dealImage() throws IOException{
			BufferedImage bi = ImageIO.read(new File("resource/images/background.jpg"));
			bi.getSubimage(0, 0, 500, 500);//前两个值是坐标位置X、Y，后两个是长和宽
			
			
			BufferedImage resultImg = null;
			resultImg = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = resultImg.createGraphics();
			Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi.getWidth(), bi.getHeight());
			// 使用 setRenderingHint 设置抗锯齿
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			resultImg = g.getDeviceConfiguration().createCompatibleImage(bi.getWidth(), bi.getHeight(),
					Transparency.TRANSLUCENT);
			//g.fill(new Rectangle(buffImg2.getWidth(), buffImg2.getHeight()));
			g = resultImg.createGraphics();
			// 使用 setRenderingHint 设置抗锯齿
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setClip(shape);
			g.drawImage(bi, 0, 0, null);
			g.dispose();
			return resultImg;
		}
		

    public static void main(String[] args) throws IOException {
    	
       	Demo nt = new Demo();

    }
}
