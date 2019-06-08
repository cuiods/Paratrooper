package src.UI;

import javax.net.ssl.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import RSA_auth.RSA_Tool;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import src.bean.Box;
import src.bean.Soldier;
import src.common.Const;
import src.common.HttpHelper;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginFrame  {
	private JFrame loginFrame;
	private JTextField tf_name;
	private JTextField tf_psw;
	private JTextField tf_info;
	public JButton btn_login;
	private Soldier me;
	private boolean isLogin = true;  //false时不接听服务端的消息
	private ForestFrame forestFrame;
	private Map<String,String>  map;

	public LoginFrame(Soldier me,Map<String,String> map) {

		this.me = me;
		this.map = map;
		
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

				JsonObject req_obj = new JsonObject();
				req_obj.addProperty("name",tf_name.getText());
				req_obj.addProperty("password",tf_psw.getText());
				String json = req_obj.toString();
				System.out.println("登录："+json);

				Response response = HttpHelper.syncPost(Const.LOGIN,json,null);

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
						System.out.print("登录成功");
						isLogin = true;
						String token = res_json.get("data").getAsJsonObject().get("accessToken").getAsString();
						//id
						int id = res_json.get("data").getAsJsonObject().get("me").getAsJsonObject().get("id").getAsInt();
						me.setId(id);
						//name
						String name = res_json.get("data").getAsJsonObject().get("me").getAsJsonObject().get("name").getAsString();
						me.setName(name);
						//captain

						//groupnum

						System.out.println("token:"+token);
						openChildFrame(token);
						loginFrame.dispose();  //隐藏父窗口
						break;
					default:
						System.out.print("登录失败");
				}
			}
		});
		panel_4.add(btn_login);
		
		JPanel panel_5 = new JPanel();
		main_panel.add(panel_5);
		loginFrame.setVisible(true);
	}
	
	public void openChildFrame(String token) {
		map.put("token",token);
		HttpHelper.setToken(token);

		//将我的士兵信息异步发送给服务端
		JsonObject req_obj = new JsonObject();
		req_obj.addProperty("publicKey",map.get("N"));
		String req = req_obj.toString();
		System.out.print(req);
		HttpHelper.asyncPost(Const.REGISTER_N,token,req,null);

		forestFrame = new ForestFrame(me,map);
	}
	
	public ForestFrame getChildFrame() {
		return this.forestFrame;
	}

	public boolean isLogin() {
		return this.isLogin;
	}


}
