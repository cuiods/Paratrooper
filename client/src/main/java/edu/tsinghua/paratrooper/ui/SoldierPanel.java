package edu.tsinghua.paratrooper.ui;

import edu.tsinghua.paratrooper.bean.Soldier;
import edu.tsinghua.paratrooper.common.Const;
import edu.tsinghua.paratrooper.common.HttpHelper;
import edu.tsinghua.paratrooper.common.TransTools;
import edu.tsinghua.paratrooper.rsa.RSA_Tool;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class SoldierPanel extends JPanel{

	private Soldier soldier;
	private int id;
	private String token;
	private JLabel name ;
	private JButton level ;  //是否是队长
	private JButton group ;  //组号
	private JLabel pri_key;
	private LogInformationPanel logInformationPanel;

	public SoldierPanel(int id,String token, LogInformationPanel logInformationPanel) {
		soldier = new Soldier();
		this.id = id;
		this.token = token;
		this.lanch();
		this.logInformationPanel = logInformationPanel;
	}
	public SoldierPanel(Soldier soldier,int id,String token) {
		this.soldier = soldier;
		this.id = id;
		this.token = token;
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
		
		level = new JButton(this.soldier.isCaptain() == 1? "队长" :"士兵");
		level.setBounds(0, 0, 30, 30);
		panel_info_second .add(level);
		
		group = new JButton(String.valueOf(this.soldier.getGroupNum()));
		group.setBounds(30+10, 0, 30, 30);
		panel_info_second .add(group);
		
//		verify = new JButton(String.valueOf("验证"));
//		verify.setBounds(30+10+30+10, 0, 30, 30);
//		panel_info_second .add(verify);
		
		JLabel panel_image = new JLabel();
		panel_image.setOpaque(false);
		panel_image.setBounds(0, 60, Const.SOLDIER_WIDTH, Const.SOLDIER_HEIGTH);
		
		ImageIcon icon = new ImageIcon(this.getClass().getResource(Const.SOLDIER_IMAGE));
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
	 * 设置该panel的士兵信息
	 * @param soldier
	 */
	public void setSoldierInfo(Soldier soldier){
		this.soldier = soldier;
		name.setText(soldier.getName());

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
                Map<String,String> message_map = new HashMap<>();
				String str_list[] = RSA_Tool.enSgn(Const.CIPER,soldier.getPublicKey());
				message_map.put("ciper",str_list[0]);
				message_map.put("text",str_list[1]);
				message_map.put("from_id",String.valueOf(id));

				String message_map_str = TransTools.objectToJson(message_map);
				Map<String,Object> req_map = new HashMap<>();
				req_map.put("code",Const.MESSAGE_OPERATION_TWO);
				req_map.put("message",message_map_str);
				req_map.put("receiveId",soldier.getId());

                String req = TransTools.objectToJson(req_map);
                System.out.print("发起验证消息："+req);
				HttpHelper.asyncPost(Const.MESG_SEND,token,req,null);
				System.out.println("**************************************");
				logInformationPanel.addInfo("向士兵：" + soldier.getId() + "发起验证");
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
		//SoldierPanel sop = new SoldierPanel(so);
		//f.add(sop);
		
	}
}