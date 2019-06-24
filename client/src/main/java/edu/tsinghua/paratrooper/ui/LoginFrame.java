package edu.tsinghua.paratrooper.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.tsinghua.paratrooper.bean.Soldier;
import edu.tsinghua.paratrooper.common.Const;
import edu.tsinghua.paratrooper.common.HttpHelper;
import okhttp3.*;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.net.URLDecoder;
import java.util.Map;

public class LoginFrame  {
	private JFrame loginFrame;
	private JTextField tf_name;
	private JPasswordField tf_psw;
	private JTextField tf_info;
	public JButton btn_login;
	private Soldier me;
	private boolean isLogin = true;  //false时不接听服务端的消息
	private ForestFrame forestFrame;
	private Map<String,String>  map;
	private boolean flag = false;

	public LoginFrame(Soldier me,Map<String,String> map) {

		this.me = me;
		this.map = map;
		
		loginFrame = new JFrame();
		loginFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(Const.FRIEND_CARD_IMAGE_LEADER)));
		loginFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		loginFrame.setSize(1200,900);
		loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);

		JPanel main_panel = new JPanel();
		loginFrame.add(main_panel);
		loginFrame.setLayout(null);
		main_panel.setLayout(null);
		main_panel.setOpaque(false);
		main_panel.setBounds(384 ,250,Const.LOGIN_PANEL_WIDTH,Const.LOGIN_PANEL_HEIGHT);

		//main_panel 背景图
		ImageIcon icon_form = new ImageIcon(this.getClass().getResource(Const.LOGIN_PANEL_IMAGE));
		icon_form.setImage(icon_form.getImage().getScaledInstance(Const.LOGIN_PANEL_WIDTH,Const.LOGIN_PANEL_HEIGHT, Image.SCALE_DEFAULT));


		//tf_info.setFont(new java.awt.Font("Dialog", 1, 15));

		ImageIcon icon_name = new ImageIcon(this.getClass().getResource(Const.FORM_NAME_IMAGE));
		icon_name.setImage(icon_name.getImage().getScaledInstance(Const.FORM_WIDTH,Const.FORM_HEIGHT, Image.SCALE_DEFAULT));

		ImageIcon icon_psd = new ImageIcon(this.getClass().getResource(Const.FORM_PSD_IMAGE));
		icon_psd.setImage(icon_psd.getImage().getScaledInstance(Const.FORM_WIDTH,Const.FORM_HEIGHT, Image.SCALE_DEFAULT));//80和100为大小 可以自由设置

		TextBorderUtlis border  = new TextBorderUtlis(new Color(169,169,169),1,true);
		TextBorderUtlis border_white  = new TextBorderUtlis(new Color(255,255,255),1,true);

		//name
		tf_name = new JTextField();
		tf_name.setOpaque(false);
		tf_name.setBorder(null);
		main_panel.add(tf_name);

		JLabel name_label = new JLabel();
		name_label.setIcon(icon_name);
		name_label.setBorder(border);
		main_panel.add(name_label);
		tf_name.setBounds(Const.LOGIN_PANEL_WIDTH/2 - Const.FORM_WIDTH/2 + 40,100,Const.FORM_WIDTH-40,Const.FORM_HEIGHT);
		name_label.setBounds(Const.LOGIN_PANEL_WIDTH/2 - Const.FORM_WIDTH/2 ,100,Const.FORM_WIDTH,Const.FORM_HEIGHT);
		//String empty_str = "       ";
		//tf_name.setText(empty_str);
		tf_name.setFont(new java.awt.Font("Dialog", 1, 15));
		tf_name.setOpaque(false);
		tf_name.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				//设置文本框不可编辑后设置光标的可用性为false
				tf_name.setEditable(false);
				tf_name.getCaret().setVisible(false);

				String s=tf_name.getText();
				String reg = "^((?!@).)*$ ";

				if(s.length()>= 10){
					tf_info.setText("输入的姓名长度不超过10个字符，请检查");
					flag = false;
					return ;
				}
				if(s.matches(reg)){
					tf_info.setText("输入的姓名不合法，请检查");
					flag = false;
					return ;
				}else{
					tf_info.setText("");
					flag = true;
				}

			}
			@Override
			public void focusGained(FocusEvent e) {
				tf_name.setEditable(true);
				//设置光标的可用性为true
				tf_name.getCaret().setVisible(true);
				//设置光标位置为文本内容最后面
				tf_name.setCaretPosition(tf_name.getText().length());
			}
		});

		//psd
		tf_psw = new JPasswordField();
		tf_psw.setOpaque(false);
		tf_psw.setBorder(null);
		main_panel.add(tf_psw);

		JLabel label_psw = new JLabel();
		label_psw.setIcon(icon_psd);
		label_psw.setBorder(border);
		main_panel.add(label_psw);
		label_psw.setBounds(Const.LOGIN_PANEL_WIDTH/2 - Const.FORM_WIDTH/2,170,Const.FORM_WIDTH,Const.FORM_HEIGHT);
		tf_psw.setBounds(Const.LOGIN_PANEL_WIDTH/2 - Const.FORM_WIDTH/2 + 40 ,170,Const.FORM_WIDTH - 40 ,Const.FORM_HEIGHT);

		tf_psw.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent e) {

					String s = String.valueOf(tf_psw.getPassword());

					if (s.length() >= 10 || s.length() <= 5) {
						tf_info.setText("输入的密码不超过10个字符，不小于5个字符请检查");
						flag = false;
						return;
					}

					flag = true;
			}
		});

		//login
		btn_login = new JButton();

		ImageIcon icon_login = new ImageIcon(this.getClass().getResource(Const.BUTTON_NOT_PRESSED));
		icon_login.setImage(icon_login.getImage().getScaledInstance(Const.BUTTON_WIDTH,Const.BUTTON_HEIGHT, Image.SCALE_DEFAULT));
		btn_login.setIcon(icon_login);
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//检验输入值
                String name_str = tf_name.getText();
                String password = String.valueOf(tf_psw.getPassword());
                if(name_str.equals("")){
                	tf_info.setText("姓名不能为空");
					flag = false;
				}else if (password.equals("")){
					tf_info.setText("密码不能为空");
					flag = false;
				}else if(flag){
					tf_info.setText("姓名和密码输入格式错误，请检查");
				}

				JsonObject req_obj = new JsonObject();
				req_obj.addProperty("name",name_str);
				req_obj.addProperty("password",password);
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
						int isCaptain = res_json.get("data").getAsJsonObject().get("me").getAsJsonObject().get("captain").getAsInt();
						me.setCaptain(isCaptain);
						//groupnum
						int groupNum = res_json.get("data").getAsJsonObject().get("me").getAsJsonObject().get("groupNum").getAsInt();
						me.setGroupNum(groupNum);
						//level
						int level = res_json.get("data").getAsJsonObject().get("me").getAsJsonObject().get("level").getAsInt();
						me.setLevel(level);
						//box_key
						String boxKey = res_json.get("data").getAsJsonObject().get("me").getAsJsonObject().get("boxKey").getAsString();
						me.setBoxKey(boxKey);

						//别问我为什么上面不知道把json直接转成一个对象。。因为我喜欢

						System.out.println("token:"+token);
						openChildFrame(token);
						loginFrame.dispose();  //隐藏父窗口
						break;
					default:
						System.out.print("登录失败");
				}
			}
		});
		main_panel.add(btn_login);
		btn_login.setBounds(Const.LOGIN_PANEL_WIDTH/2 - Const.BUTTON_WIDTH/2,230,Const.BUTTON_WIDTH,Const.BUTTON_HEIGHT);

        //tips
		tf_info = new JTextField();
		tf_info.setOpaque(false);
		tf_info.setEditable(false);
		tf_info.setBounds(20,280,Const.LOGIN_PANEL_WIDTH - 50,30);
		tf_info.setHorizontalAlignment(SwingConstants.CENTER);
		tf_info.setBorder(null);
		tf_info.setFont(new java.awt.Font("Dialog", 1, 15));
		tf_info.setForeground(new Color(137, 181, 38));
		main_panel.add(tf_info);

		JLabel background_label = new JLabel(icon_form);
		main_panel.add(background_label);
		background_label.setBounds(0,0,Const.LOGIN_PANEL_WIDTH,Const.LOGIN_PANEL_HEIGHT);

		ImageIcon icon_bottom = new ImageIcon(this.getClass().getResource(Const.BACKGROUND_IMAGE_COVER));
		icon_bottom.setImage(icon_bottom.getImage().getScaledInstance(Const.BACKGROUND_WIDTH,Const.BACKGROUND_HEIGTH, Image.SCALE_DEFAULT));

		JLabel bottom = new JLabel(icon_bottom);
		bottom.setBounds(0,0,Const.BACKGROUND_WIDTH ,Const.BACKGROUND_HEIGTH);
		loginFrame.add(bottom);

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
