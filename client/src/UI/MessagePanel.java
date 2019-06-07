package src.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.bean.Message;
import src.common.Const;

public class MessagePanel extends JPanel{

	private JLabel body;
	private JButton ok;
	private JButton cancel;
	private Message message;
	private boolean isReply ;
	
	public MessagePanel() {
		body = new JLabel("您暂无消息");
		ok = new JButton("确认");
		cancel = new JButton("取消");
		isReply = false;
		lanch();
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
	}
	
	/**
	 * 一条新消息，刷新页面
	 * @param message
	 */
	public void resetMessage(Message message) {
		this.message = message;
		int code = message.getCode();
		switch(code) {
		case Const.MESSAGE_OPERATION_TWO :
			String str = "<html> 士兵:" + message.getContent().get("sodiler_id") +" 向您发起认证，他的私钥为：" +message.getContent().get("pri_key") +"</html>";
			body.setText(str);
		}
		System.out.println("到达44");
	}
	
	/**
	 * 消息失效，设置页面不可见
	 */
	public void clearMessage() {
		this.setVisible(false);
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
			isReply = true;
			//回复消息 定一下这个消息里面的结构
			
			
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
