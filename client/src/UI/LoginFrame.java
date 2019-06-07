package src.UI;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import src.bean.Box;
import src.bean.Soldier;
import src.common.HttpHelper;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class LoginFrame  {
	private JFrame loginFrame;
	private JTextField tf_name;
	private JTextField tf_psw;
	private JTextField tf_info;
	public JButton btn_login;
	private Soldier me;
	private boolean isLogin = true;  //false时不接听服务端的消息
	private ForestFrame forestFrame;
	
	public LoginFrame(Soldier me) {
		this.me = me;
		
		loginFrame = new JFrame();
		loginFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		loginFrame.setSize(500,600);
		
		JPanel main_panel = new JPanel();
		main_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		loginFrame.getContentPane().add(main_panel);
		main_panel.setLayout(new GridLayout(6, 2, 5, 5));
		
		JPanel panel = new JPanel();
		main_panel.add(panel);
		
		JPanel panel_1 = new JPanel();
		main_panel.add(panel_1);
		
		tf_info = new JTextField();
		tf_info.setBackground(Color.LIGHT_GRAY);
		tf_info.setEditable(false);
		tf_info.setText("你当前的坐标为：（30，60），ip：126.36.23.45");
		panel_1.add(tf_info);
		tf_info.setColumns(25);
		
		JPanel panel_2 = new JPanel();
		main_panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label_name = new JLabel("士兵姓名:");
		panel_2.add(label_name);
		
		tf_name = new JTextField();
		panel_2.add(tf_name);
		tf_name.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		main_panel.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label_psw = new JLabel("New label");
		panel_3.add(label_psw);
		
		tf_psw = new JTextField();
		panel_3.add(tf_psw);
		tf_psw.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		main_panel.add(panel_4);
		
		btn_login = new JButton("登录");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				OkHttpClient client =  new OkHttpClient();
				Request request = new Request.Builder().url("http://127.0.0.1:19018/login").
	        		     addHeader("Access-User-Token","e5cHLWScbto3VfvYTU1llVZgl/WniA4QZZ8epmn8k/o=").build();
	            Response response;
				try {
					response = client.newCall(request).execute();
					if (response.isSuccessful()) { // 判断是否成功
		                 if(response.body().string().equals("ok")){
		                	      isLogin = true;
		                	      openChildFrame();  //到时候根据协议再改。。传回来的值
		                	      loginFrame.dispose();  //隐藏父窗口
		                 }
		            }else {
		                 System.out.println("登录失败"); // 链接失败
		            }
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // 返回实体
	       
				
			}
		});
		panel_4.add(btn_login);
		
		JPanel panel_5 = new JPanel();
		main_panel.add(panel_5);
		loginFrame.setVisible(true);
	}
	
	public void openChildFrame() {
		Box box = new Box ();
		forestFrame = new ForestFrame(me);
	}
	
	public ForestFrame getChildFrame() {
		return this.forestFrame;
	}

	public boolean isLogin() {
		return this.isLogin;
	}
}
