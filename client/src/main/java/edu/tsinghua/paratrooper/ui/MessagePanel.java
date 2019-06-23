package edu.tsinghua.paratrooper.ui;

import edu.tsinghua.paratrooper.bean.Message;
import edu.tsinghua.paratrooper.bean.Soldier;
import edu.tsinghua.paratrooper.common.Const;
import edu.tsinghua.paratrooper.common.HttpHelper;
import edu.tsinghua.paratrooper.common.TransTools;
import edu.tsinghua.paratrooper.rsa.RSA_Tool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagePanel extends JPanel{

	private JLabel body;
	private JButton ok;
	private JButton cancel;
	private Message message;
	private boolean isReply ;
	private List<Soldier> soliderList;
	private Soldier me;
	private String token;
	private LogInformationPanel logInformationPanel;
	private int can_left_id;
	private int can_right_id;
	private String can_left_name;
	private String can_right_name;
	
	public MessagePanel(List<Soldier> soliderList,Soldier me,String token, LogInformationPanel logInformationPanel) {
		this.token = token;
		this.me = me;
		this.soliderList = soliderList;
		body = new JLabel("您暂无消息");
		ok = new JButton("确认");
		cancel = new JButton("取消");
		isReply = false;
		this.logInformationPanel = logInformationPanel;
		lanch();
	}

	/**
	 * 刷新士兵列表
	 * @param soliderList
	 */
	public void resetNeedInfo(List<Soldier> soliderList,Soldier me){
		this.soliderList = soliderList;
		this.me = me;
	}

	public void lanch() {
		this.setLayout(null);
		this.add(body);
		body.setBounds(Const.MESSAGE_PANEL_GEZI, Const.MESSAGE_PANEL_GEZI, Const.MESSAGE_PANEL_BODY_WIDTH, Const.MESSAGE_PANEL_BODY_HEIGHT);
		ok.setBounds(Const.MESSAGE_PANEL_GEZI, Const.MESSAGE_PANEL_GEZI *2 +  Const.MESSAGE_PANEL_BODY_HEIGHT , Const.MESSAGE_PANEL_BUTTON_WIDTH, Const.MESSAGE_PANEL_BUTTIN_HEIGHT);
		this.add(ok);
		this.add(cancel);
		cancel.setBounds(Const.MESSAGE_PANEL_GEZI*2 +Const.MESSAGE_PANEL_BUTTON_WIDTH , Const.MESSAGE_PANEL_GEZI *2 +  Const.MESSAGE_PANEL_BODY_HEIGHT, Const.MESSAGE_PANEL_BUTTON_WIDTH, Const.MESSAGE_PANEL_BUTTIN_HEIGHT);
		ok.addActionListener(new ReplyListener());
		cancel.addActionListener(new CancelListener());
		this.setSize(Const.MESSAGE_PANEL_WIDTH,Const.MESSAGE_PANEL_HRIGHT);
		this.setBorder(BorderFactory.createTitledBorder("您有新的消息"));
		this.setVisible(false);
		this.logInformationPanel.setVisible(true);
		System.out.println("logInformationPanal true");
	}
	
	/**
	 * 一条新消息，刷新页面
	 * @param message
	 */
	public boolean resetMessage(Message message) {
		this.message = message;
		int code = message.getCode();
		String str ="";
		ok.setText("确认");
		cancel.setText("取消");
		switch(code) {
		    case Const.MESSAGE_OPERATION_TWO :  //有人向我发起认证
			    str = "<html> 士兵:"+ message.getData().get("from_id")+"向您发起认证,是否继续" + "</html>";
			    body.setText(str);
			    break;
			case Const.MESSAGE_OPERATION_FIVE : //认证成功
				str = "<html> 您对士兵:"+ message.getData().get("from_id")+"发起的认证成功！" + "</html>";
				body.setText(str);
				break;
			case Const.MESSAGE_CAPTAIN_ONE:  //竞选队长
				str = "<html> 您需要与:"+ message.getData().get("other_captain_id")+"竞选队长。"+ "</html>";
				body.setText(str);
				break;
			case Const.MESSAGE_BOX_OPEN:
				str = "<html> 宝箱:"+ message.getData().get("box_id")+"已被您和您的队友开启。"+ "</html>";
				body.setText(str);
				break;
			case Const.MESSAGE_CHOOSE_CAPTAIN:
				for(String key : message.getData().keySet()){
					System.out.println(key + " : " + message.getData().get(key));
				}
				can_left_name = message.getData().get("candidata_left_name").toString();
				can_right_name = message.getData().get("candidata_right_name").toString();
				can_left_id = Integer.parseInt(message.getData().get("candidata_left_name").toString());
				can_right_id = Integer.parseInt(message.getData().get("candidata_right_name").toString());
				str = "请投票："+ can_left_name +":"+ can_right_name;
				body.setText(str);
				ok.setText(can_left_name);
				cancel.setText(can_right_name);

				logInformationPanel.addInfo(str);
				break;
			default:
				return false;
		}
		return true;
	}
	
	/**
	 * 消息失效，设置页面不可见
	 */
	public void clearMessage() {
		this.setVisible(false);
		this.logInformationPanel.setVisible(true);
	}
	
	/**
	 * 回复消息响应器
	 * @author zhangsukun
	 *
	 */
	class ReplyListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			//回复消息
			switch(message.getCode()){
				case Const.MESSAGE_OPERATION_TWO:  //有人向我发起验证

					String[] strlist= new String[2];
					strlist[0] = (String)message.getData().get("ciper");
					strlist[1] = (String)message.getData().get("text");
					if(RSA_Tool.sgnCheck(strlist)){
						System.out.println("验证成功，现在回执消息");
						String pub_key = "";
						//找该士兵的公钥
						for(Soldier soldier :soliderList){
							if(soldier.getId() == (int)message.getData().get("from_id")){
								pub_key = soldier.getPublicKey();
								break;
							}
						}
						Map<String,String> message_map = new HashMap();
						String str_list[] = RSA_Tool.enSgn(Const.CIPER,pub_key);
						message_map.put("ciper",str_list[0]);
						message_map.put("text",str_list[1]);
						message_map.put("from_id",String.valueOf(me.getId()));
						String message_map_str = TransTools.objectToJson(message_map);

						Map<String,Object> req_map = new HashMap<>();
						req_map.put("code",Const.MESSAGE_OPERATION_FOUR);
						req_map.put("message",message_map_str);
						req_map.put("receiveId",message.getData().get("from_id"));

						String req = TransTools.objectToJson(req_map);
						System.out.println("发起回执验证消息："+req);
						HttpHelper.asyncPost(Const.MESG_SEND,token,req,null);

					}else{
						System.out.println("验证失败");
					}
					break;
				case Const.MESSAGE_CHOOSE_CAPTAIN:
					Map<String,Object> req_map = new HashMap<>();
					req_map.put("rejectId",can_right_id);
					req_map.put("supportId",can_left_id);
					String req = TransTools.objectToJson(req_map);
					System.out.println("发起回执验证消息："+req);
					HttpHelper.asyncPost(Const.VOTE,token,req,null);

					String str = "您选择了：" + can_left_name;
					logInformationPanel.addInfo(str);
					break;
			}
			isReply = true;
			clearMessage();
		}
	}
	
	/**
	 * 忽略该消息响应器
	 * @author zhangsukun
	 *
	 */
	class CancelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(message.getCode() == Const.MESSAGE_CHOOSE_CAPTAIN){
				Map<String,Object> req_map = new HashMap<>();
				req_map.put("rejectId",can_left_id);
				req_map.put("supportId",can_right_id);
				String req = TransTools.objectToJson(req_map);
				System.out.println("发起回执验证消息："+req);
				HttpHelper.asyncPost(Const.VOTE,token,req,null);

				String str = "您选择了：" + can_right_name;
				logInformationPanel.addInfo(str);
			}
			isReply = true;
			clearMessage();
		}
	}
	
	/**
	 * 消息是否回复
	 * @return
	 */
	public boolean getIsReply() {
		return this.isReply;
	}


	
}
