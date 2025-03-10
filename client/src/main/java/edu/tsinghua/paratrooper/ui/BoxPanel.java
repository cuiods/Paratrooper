package edu.tsinghua.paratrooper.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.tsinghua.paratrooper.bean.Box;
import edu.tsinghua.paratrooper.common.Const;
import edu.tsinghua.paratrooper.common.HttpHelper;
import edu.tsinghua.paratrooper.common.TransTools;
import okhttp3.Response;

public class BoxPanel extends JPanel{

	private JLabel jl_box;   //宝箱的图片 open or close
	private JLabel box_info;  //宝箱的信息
	private Box box;
	private int cur_box_apply;
	
	private int x ;
	private int y ;
	private String boxkey ;
	private String token;
	private LogInformationPanel logInformationPanel;
	
	public BoxPanel() {
		jl_box = new JLabel();
		box_info = new JLabel();
		box = new Box();
		this.lanch();
	}
	public BoxPanel(String token,String boxkey, LogInformationPanel logInformationPanel) {
		jl_box = new JLabel();
		box_info = new JLabel();
		this.token = token;
		this.boxkey = boxkey;
		this.logInformationPanel = logInformationPanel;
		this.cur_box_apply = 0;
		this.lanch();
	}
	
	public void lanch() {
		
		box_info.setBounds(0, 0, Const.BOX_PANEL_WIDTH,Const.BOX_INFO_HEIGHT);
		box_info.setOpaque(false);
		box_info.setFont(new java.awt.Font("Dialog", 1, 15));
		box_info.setForeground(Color.WHITE);
		box_info.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon box_icon = new ImageIcon(this.getClass().getResource(Const.BOX_OPEN_IMAGE));
		box_icon.setImage(box_icon.getImage().getScaledInstance(Const.BOX_IMAGE_SIZE,Const.BOX_IMAGE_SIZE,Image.SCALE_DEFAULT));
		jl_box.setIcon(box_icon);
		jl_box.setOpaque(false);
		jl_box.setBounds(Const.BOX_PANEL_WIDTH/2 - Const.BOX_IMAGE_SIZE/2 ,Const.BOX_INFO_HEIGHT+ Const.BOX_INFO_GEZI,Const.BOX_IMAGE_SIZE,Const.BOX_IMAGE_SIZE);
		//jl_box.addMouseListener(new BoxListener());
		
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
		this.x = x;
		this.y = y;
		if(box.getApply() > cur_box_apply && box.getStatus()!= 1){   //别新的队友点击开箱了
			logInformationPanel.addInfo("<html>宝箱信息更新："+box.getApply()+"/" + box.getTotal() + "(已参与/所需)</html>");
			cur_box_apply = box.getApply();
		}
		String info = "";
		if(box.getApply() > cur_box_apply && box.getStatus()== 1) {//宝箱被开启
			cur_box_apply = box.getApply();
			info = "<html>该宝箱已被开启</html>";
			logInformationPanel.addInfo("<html>该宝箱已被开启</html>");
		}

		if(box.getStatus() == 1) {
			ImageIcon box_icon = new ImageIcon(this.getClass().getResource(Const.BOX_OPEN_IMAGE));
			box_icon.setImage(box_icon.getImage().getScaledInstance(Const.BOX_IMAGE_SIZE,Const.BOX_IMAGE_SIZE,Image.SCALE_DEFAULT));
			jl_box.setIcon(box_icon);
			info = "<html>该宝箱已被开启</html>";

		}else{
			ImageIcon box_icon = new ImageIcon(this.getClass().getResource(Const.BOX_CLOSE_IMAGE));
			box_icon.setImage(box_icon.getImage().getScaledInstance(Const.BOX_IMAGE_SIZE,Const.BOX_IMAGE_SIZE,Image.SCALE_DEFAULT));
			jl_box.setIcon(box_icon);
			info = "<html>"+box.getApply()+"/" + box.getTotal() + "<br>(已参与/所需)</html>";
		}
		this.setBounds(box.getPoint_x() - Const.BOX_PANEL_WIDTH/2, box.getPoint_y() - Const.BOX_PANEL_HEIGHT/2, Const.BOX_PANEL_WIDTH, Const.BOX_PANEL_HEIGHT);

		box_info.setText(info);
		IsshowBox(x,y);
	}

	/**
	 * 请求开宝箱后重设宝箱信息
	 * @param box
	 */
	public void resetBox(Box box){

		if(box.getStatus() == 1) {
			ImageIcon box_icon = new ImageIcon(this.getClass().getResource(Const.BOX_OPEN_IMAGE));
			box_icon.setImage(box_icon.getImage().getScaledInstance(Const.BOX_IMAGE_SIZE,Const.BOX_IMAGE_SIZE,Image.SCALE_DEFAULT));
			jl_box.setIcon(box_icon);
		}else{
			ImageIcon box_icon = new ImageIcon(this.getClass().getResource(Const.BOX_CLOSE_IMAGE));
			box_icon.setImage(box_icon.getImage().getScaledInstance(Const.BOX_IMAGE_SIZE,Const.BOX_IMAGE_SIZE,Image.SCALE_DEFAULT));
			jl_box.setIcon(box_icon);
		}

		String info = "<html>"+box.getApply()+"/" + box.getTotal() +"<br>" +"(已有/所需)</html>";
		box_info.setText(info);
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
		if((box.getPoint_x()- Const.BOX_PANEL_WIDTH/2 - Const.DISTANCE < this.x + Const.SOLDIER_SIZE/2) &&
				(box.getPoint_x()+ Const.BOX_PANEL_WIDTH/2 + Const.DISTANCE > this.x - Const.SOLDIER_SIZE/2 )&&
				(box.getPoint_y() - Const.BOX_PANEL_HEIGHT/2 - Const.DISTANCE < this.y + Const.SOLDIER_SIZE/2) &&
				(box.getPoint_y() + Const.BOX_PANEL_HEIGHT/2 + Const.DISTANCE > this.y - Const.SOLDIER_SIZE/2)) {
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
			    System.out.println("请求开箱子:");
				Map<String,Object> req_map = new HashMap<>();
				req_map.put("boxId",box.getId());
				req_map.put("key",boxkey);
				String req = TransTools.objectToJson(req_map);
				System.out.println("发送请求消息："+req);
				System.out.println("看一下token：" + token);

				//应该发送同步请求
				Response response = HttpHelper.syncPost(Const.APPLY,req,token);
				String str = null;
				try {
					str = URLDecoder.decode(response.body().string(), "utf-8");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JsonObject res_json = (JsonObject) new JsonParser().parse(str).getAsJsonObject();

				int code = res_json.get("code").getAsInt();
				switch(code){
					case 200:
						JsonObject object = res_json.get("data").getAsJsonObject();
						Gson gson = new Gson();
						box = gson.fromJson(object, Box.class);
						logInformationPanel.addInfo("<html>申请开箱成功，当前参与开箱人数："+box.getApply()+"共需" + box.getTotal() + "</html>");
						resetBox(box);
						break;
					default:   //其他开箱失败信息
						System.out.println( res_json.get("message").getAsString());
						logInformationPanel.addInfo(res_json.get("message").getAsString());
				}
			    
			}else {
				logInformationPanel.addInfo("不在开箱范围内");
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

