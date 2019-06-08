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

import Millionare.Millionaire_Tool;
import RSA_auth.RSA_Tool;
import com.google.gson.JsonObject;
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

	private JLabel tips ;
	
	private PullingThread pt;
	private MessageThread mt;

	private Map<String,String> map ;
	public ForestFrame(Soldier me,Map<String,String> map) {
		this.me = me;
		this.map = map;

		canseePanel = new CanSeePanel(me.getLocationX(),me.getLocationY());
		forestPanel = new JPanel();
		tips = new JLabel();  //一些提示操作  你们补充
		
		//士兵相关
		otherSoldiers = new ArrayList<Soldier>();
		jlb_otherSolders = new ArrayList<SoldierPanel>();
		for(int i = 0 ; i< others_sum ;i++) {
			
			SoldierPanel sp = new SoldierPanel(me.getId(),map.get("token"));
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
		messagePanel = new MessagePanel(this.otherSoldiers,me,map.get("token"));
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
		
		canseePanel.setBounds(me.getLocationX() - Const.PANEL_SIZE/2, me.getLocationY() - Const.PANEL_SIZE/2, Const.PANEL_SIZE, Const.PANEL_SIZE);
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
		this.me.setLocationX(point_x);
		this.me.setLocationY(point_y);
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
			jlb_otherSolders.get(i).setBounds(soldier.getLocationX() - Const.SOLDIER_WIDTH/2, soldier.getLocationY() - Const.SOLDIER_PANEL_HEIGHT/2, Const.SOLDIER_WIDTH, Const.SOLDIER_PANEL_HEIGHT);
			jlb_otherSolders.get(i).setVisible(true);
			jlb_otherSolders.get(i).setSoldierInfo(soldier);
		}
		
		while(i < this.others_sum) {
			jlb_otherSolders.get(i).setVisible(false);
			i++;
		}
		messagePanel.resetNeedInfo(otherSoldiers,me);
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
			boxPanelists.get(i).resetBox(box,me.getLocationX(),me.getLocationY());
			boxPanelists.get(i);
		}
		
		while(i < this.box_sum) {
			boxPanelists.get(i).setVisible(false);
			i++;
		}
	}

	/**
	 * 刷新我的一些信息
	 * @param new_me
	 */
	public void resetMe(Soldier new_me){

		//我的队长的切换消息
		if(new_me.isCaptain() ==1 && me.isCaptain()==0){

			System.out.println("您被选为长官");
		}else if(new_me.isCaptain() == 0 && me.isCaptain() == 1){

			System.out.println("您已被编入其他小队，不再是长官");
		}

		//我的队伍切换信息
		if(new_me.getGroupNum() != me.getGroupNum()){
			System.out.println("您的小队被重新整编");
		}
		me.setId(new_me.getId());
		me.setCaptain(new_me.isCaptain());
		me.setGroupNum(new_me.getGroupNum());
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
			int point_x = me.getLocationX();
			int point_y = me.getLocationY();
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
				  updateMyPointToServer();
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
	 */
	public void updateMyPointToServer() {

		JsonObject req_obj = new JsonObject();
		req_obj.addProperty("x",me.getLocationX());
		req_obj.addProperty("y",me.getLocationY());
		String req = req_obj.toString();
		HttpHelper.asyncPost(Const.SYNC_POINT,map.get("token"),req,null);
	}

	/**
	 * 收到4001 后，主动向发起请求竞选队长
	 * @param other_captain
	 */
	public void chooseCaptain(Soldier other_captain){
		Map<String,String> message_map = new HashMap();
		String level_code = Millionaire_Tool.getFirstInfo(me.getId(),other_captain.getPublicKey(),map.get("E"));  //me.getId() 应该是一个军衔
		message_map.put("level_code",level_code);
		message_map.put("from_id",String.valueOf(me.getId()));
		String message_map_str = TransTools.objectToJson(message_map);

		Map<String,Object> req_map = new HashMap<>();
		req_map.put("code",Const.MESSAGE_CAPTAIN_THREE);
		req_map.put("message",message_map_str);
		req_map.put("receiveId",other_captain.getId());

		String req = TransTools.objectToJson(req_map);
		System.out.println("发起竞选队长消息："+req);
		HttpHelper.asyncPost(Const.MESG_SEND,map.get("token"),req,this);
	}

	/**
	 * 回复竞争队长结果
	 * @param level_code
	 * @param from_id
	 */
	public void responseCaptain(String level_code,int from_id){

		String[] nums = Millionaire_Tool.getSecondInfo(level_code,me.getId(),map.get("D"),map.get("E")); //me.getId() 应该是一个军衔
		String P = Millionaire_Tool.getP();

		Map<String,String> message_map = new HashMap();
		message_map.put("num1",nums[0]);
		message_map.put("num2",nums[1]);
		message_map.put("P",P);
		message_map.put("from_id",String.valueOf(me.getId()));
		String message_map_str = TransTools.objectToJson(message_map);

		Map<String,Object> req_map = new HashMap<>();
		req_map.put("code",Const.MESSAGE_CAPTAIN_FIVE);
		req_map.put("message",message_map_str);
		req_map.put("receiveId",from_id);

		String req = TransTools.objectToJson(req_map);
		System.out.println("回复竞选队长消息："+req);
		HttpHelper.asyncPost(Const.MESG_SEND,map.get("token"),req,this);
	}

	/**
	 * 得到队长竞争结果最后的结果
	 * @param nums
	 * @param P
	 */
	public void finalResultCaptain(String[] nums,String P,int from_id){
		boolean flag = Millionaire_Tool.getThirdInfo(nums, me.getId(), P);  //me.getId() 应该是一个军衔

		Map<String,Object> req_map = new HashMap<>();
		req_map.put("compareId",from_id);
		req_map.put("result",flag ? 1:0);

		String req = TransTools.objectToJson(req_map);
		System.out.println("告诉服务器竞选队长的最终消息消息："+req);
		HttpHelper.asyncPost(Const.CAPTAIN,map.get("token"),req,null);
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
			  JsonObject req_obj = new JsonObject();
			  req_obj.addProperty("x",me.getLocationX());
			  req_obj.addProperty("y",me.getLocationY());
			  String req = req_obj.toString();
			  HttpHelper.asyncPost(Const.SYNC_POINT,map.get("token"),req,ForestFrame.this);

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