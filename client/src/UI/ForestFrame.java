package src.UI;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import src.bean.Box;
import src.bean.Message;
import src.bean.Soldier;
import src.common.Const;
import src.common.HttpHelper;
import src.common.TransTools;

import java.util.Queue;


public class ForestFrame extends JFrame{

	private CanSeePanel canseePanel;

	private HttpHelper httphelper = HttpHelper.getInstance();
	private TransTools transtools = TransTools.getInstance();

	private Soldier me;
	private List<Soldier> otherSoldiers ;
	private List<SoldierPanel> jlb_otherSolders;
	private int others_sum = 10;  //初始化10个，全设置为隐藏
	
	private List<Box> boxlists;
	private List<BoxPanel> boxPanelists;
	private int box_sum = 10;   
	
	private JPanel forestPanel;
	private JPanel friendListPanel;
	private List<Soldier> friendLists ;
	
	private Queue<Message> message_queue ;
	private MessagePanel messagePanel;
	
	private PullingThread pt;
	private MessageThread mt;
  	
	public ForestFrame(Soldier me) {
		this.me = me;
		
		canseePanel = new CanSeePanel(me.getPoint_x(),me.getPoint_y());
		forestPanel = new JPanel();
		
		//士兵相关
		otherSoldiers = new ArrayList<Soldier>();
		jlb_otherSolders = new ArrayList<SoldierPanel>();
		for(int i = 0 ; i< others_sum ;i++) {
			
			SoldierPanel sp = new SoldierPanel();
			sp.setVisible(false);
			sp.setOpaque(false);
			sp.setSize(Const.SOLDIER_WIDTH, 30+30+Const.SOLDIER_HEIGTH+30);
			jlb_otherSolders.add(sp);
			this.add(sp);
		}
		//宝箱相关
		boxlists = new ArrayList<Box>();
		boxPanelists = new ArrayList<BoxPanel>();
		for(int i = 0 ; i< box_sum; i++) {
			BoxPanel bp = new BoxPanel();
			bp.setVisible(false);
			bp.setOpaque(false);
			bp.setSize(Const.BOX_PANEL_WIDTH, Const.BOX_PANEL_HEIGHT);
			boxPanelists.add(bp);
			this.add(bp);
		}
		
		//好友相关
		Soldier so = new Soldier();
		Soldier so2 = new Soldier();
		Soldier so3 = new Soldier();
		List<Soldier> friendLists = new ArrayList<Soldier>();
		friendLists.add(so);
		friendLists.add(so2);
		friendLists.add(so3);
		friendListPanel = new FriendPanel(friendLists);
		friendLists = new ArrayList<Soldier>();
		
		//消息列表相关
		messagePanel = new MessagePanel();
		message_queue  = new LinkedList<Message>();
		this.lanch();
		this.startNetwork();
		
	}
	
	/**
	 * 加载frame信息
	 */
	public void lanch() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		this.setSize(Const.FRAME_WIDTH, Const.FRAME_HRIGHT);
		this.setLayout(null);
		
		forestPanel.setVisible(true);
		forestPanel.setLayout(null);
		//forestPanel.setOpaque(false);
		forestPanel.setBackground(Color.black);
		forestPanel.setSize(Const.FOREST_WIDTH, Const.FOREST_HEIGTH);
		
		canseePanel.setBounds(me.getPoint_x() - Const.PANEL_SIZE/2, me.getPoint_y() - Const.PANEL_SIZE/2, Const.PANEL_SIZE, Const.PANEL_SIZE); 	
		canseePanel.addMouseListener(new GainFoucsListener());
		
		//消息列表
		messagePanel.setBounds(Const.FOREST_WIDTH, 600 +10, Const.MESSAGE_PANEL_WIDTH, Const.MESSAGE_PANEL_HRIGHT);
		this.add(messagePanel);
		messagePanel.setVisible(false);
				
		//好友列表
		JPanel contentpanel = new JPanel();
		contentpanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		this.add(contentpanel);
		contentpanel.setBounds(Const.FOREST_WIDTH,0,240,600);
		contentpanel.setLayout(null);
		
		JScrollPane  jsp = new JScrollPane();
		//jsp.add(sop);
		jsp.setViewportView(friendListPanel);
		jsp.setBounds(0, 0, 210, 600);
		jsp.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		contentpanel.add(jsp);
		
		this.add(canseePanel);  //后加入，使其置于底层
		this.add(forestPanel);
		this.requestFocus();
		this.addKeyListener(new MyKeyListener());
		this.setLayout(null);
		this.setSize(Const.FOREST_WIDTH + contentpanel.getWidth(), Const.FOREST_HEIGTH);
		this.setVisible(true);
	}
	
	/**
	 *  键盘移动重新设置移动坐标并重绘图片
	 * @param point_x
	 * @param point_y
	 */
	public void resetPoint(int point_x ,int point_y) {
		this.me.setPoint_x(point_x);
		this.me.setPoint_y(point_y);
		canseePanel.setBounds(point_x - Const.PANEL_SIZE/2, point_y - Const.PANEL_SIZE/2, Const.PANEL_SIZE, Const.PANEL_SIZE); 
		canseePanel.resetPoint(point_x,point_y);
		resetBoxPoint(this.boxlists);
		resetOtherSoldierPoint(this.otherSoldiers);
	}
	
	/**
	 * 刷新视野内其他士兵移动的坐标
	 * @param otherSoldiers
	 */
	public void resetOtherSoldierPoint(List<Soldier> otherSoldiers) {
		this.otherSoldiers = otherSoldiers;
		int i = 0;
		for(i = 0 ; i< otherSoldiers.size();i++) {
			Soldier soldier = otherSoldiers.get(i);
			jlb_otherSolders.get(i).setBounds(soldier.getPoint_x() - Const.SOLDIER_WIDTH/2, soldier.getPoint_y() - Const.SOLDIER_PANEL_HEIGHT/2, Const.SOLDIER_WIDTH, Const.SOLDIER_PANEL_HEIGHT);
			jlb_otherSolders.get(i).setVisible(true);
		}
		
		while(i < this.others_sum) {
			jlb_otherSolders.get(i).setVisible(false);
			i++;
		}
	}
	
	/**
	 * 刷新宝箱的位置和状态
	 * @param boxlists
	 */
	public void resetBoxPoint(List<Box> boxlists) {
		this.boxlists = boxlists;
		int i = 0;
		for(i = 0 ; i < boxlists.size();i++) {
			Box box = boxlists.get(i);
			boxPanelists.get(i).setBounds(box.getPoint_x() - Const.BOX_PANEL_WIDTH/2, box.getPoint_y() - Const.BOX_PANEL_HEIGHT/2, Const.BOX_PANEL_WIDTH, Const.BOX_PANEL_HEIGHT);
			boxPanelists.get(i).resetBox(box,me.getPoint_x(),me.getPoint_y());
			boxPanelists.get(i);
		}
		
		while(i < this.box_sum) {
			boxPanelists.get(i).setVisible(false);
			i++;
		}
	}
	
	
	/**
	 * 键盘移动的监听事件
	 * @author zhangsukun
	 *
	 */
	class MyKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e.getKeyCode());
			int point_x = me.getPoint_x();
			int point_y = me.getPoint_y();
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_UP:
					point_y-=Const.STEP;
					break;
				case KeyEvent.VK_DOWN:
					point_y+=Const.STEP;
					break;
				case KeyEvent.VK_LEFT:
					point_x-=Const.STEP;
					break;
				case KeyEvent.VK_RIGHT:
					point_x+=Const.STEP;
					break;
			}
			if(judgePoint(point_x,point_y)) {
		         resetPoint(point_x,point_y);
		         //发送我的坐标信息给服务端
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("soldier_id", "1");
					map.put("point_x", String.valueOf(me.getPoint_x()));
					map.put("point_y", String.valueOf(me.getPoint_y()));
				    Date date = new Date();
					Message message = new Message(4,map, String.valueOf(date.getTime()));
					sendMessage(message);
			}
			
		}
		
		public boolean judgePoint(int x, int y) {
		     if(x>Const.FOREST_WIDTH - Const.RDDIUS || x < Const.RDDIUS 
		    		 || y > Const.FOREST_HEIGTH - Const.RDDIUS || y < Const.RDDIUS) {
		    	   return false;
		     }
		    	 return true;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	
	
	/**
	 * 发送消息给服务端，不需要服务端的回执信息
	 * @param message
	 */
	public void sendMessage(Message message) {
		String param = transtools.objectToJson(message);
		String url = "http://"+ Const.SERVER_IP + ":" + Const.SERVER_PORT +"/" +param;
		System.out.println(url);
		httphelper.asyncGet(url, "token",null);
	}
	
	/**
	 * 开启网络监听消息 轮询
	 */
	public void startNetwork() {

	    pt = new PullingThread();  
		pt.start();
		
		mt = new MessageThread();
		mt.start();
	}
		    
	public class PullingThread extends Thread{
		@Override
		public void run() {
		  while(true) {
			    Map<String,Object> map = new HashMap<String,Object>();
				map.put("soldier_id", "1");
				map.put("point_x", String.valueOf(me.getPoint_x()));
				map.put("point_y", String.valueOf(me.getPoint_y()));
			    Date date = new Date();
			    Message message = new Message(Const.MESSAGE_PULL,map,String.valueOf(date.getTime()));
			    String url = "http://"+ Const.SERVER_IP + ":" + Const.SERVER_PORT +"/" + transtools.objectToJson(message);
			    System.out.println(url);
			    httphelper.asyncGet(url, "token",ForestFrame.this);
			
			try {
				sleep(Const.SLEEP_SECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		}
	}
	
	/**
	 * 监听消息队列是否有新的消息，有的话显示出来，默认10s没有相应消息，则该消息失效
	 * @author zhangsukun
	 *
	 */
	public class MessageThread extends Thread{
		@Override
		public void run() {
			while(true) {
				System.out.println("到达22");
			      if(!message_queue.isEmpty()) {
			    	  System.out.println("到达33");
			    	       Message message= message_queue.poll();
			    	       messagePanel.resetMessage(message);
			    	       messagePanel.setVisible(true);
			    	       System.out.println("到达55");
			    	       try {
			   				sleep(Const.MESSAGE_SHOW_SECONDS);
			   			} catch (InterruptedException e) {
			   				// TODO Auto-generated catch block
			   				e.printStackTrace();
			   			}
			    	       if(!messagePanel.getIsReply()) {   //消息没回复，过期了
			    	    	        messagePanel.clearMessage();
			    	       }
			      }
			      
			      try {
		   				sleep(Const.MESSAGE_SHOW_SECONDS);
		   			} catch (InterruptedException e) {
		   				// TODO Auto-generated catch block
		   				e.printStackTrace();
		   			}
			}
		}
	}
	
	/**
	 * 一个新的消息到达
	 * @param message
	 */
	public void addMessageArrive(Message message) {
		this.message_queue.add(message);
		System.out.println("到达11");
	}
	/**
	 * 重新获得鼠标焦点
	 * @author zhangsukun
	 *
	 */
	class GainFoucsListener implements MouseListener{


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			ForestFrame.this.requestFocus();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
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
		
		//ForestFrame f = new ForestFrame();
 
	}
}
