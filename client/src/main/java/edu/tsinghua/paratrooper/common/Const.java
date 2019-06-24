package edu.tsinghua.paratrooper.common;


public class Const{
	
	//login
	public static final int FORM_WIDTH = 250;
	public static final int FORM_HEIGHT = 42;
	public static final String FORM_NAME_IMAGE= "/images/form/name.png";
	public static final String FORM_PSD_IMAGE= "/images/form/password.png";

	public static final int BUTTON_WIDTH = 165;
	public static final int BUTTON_HEIGHT = 42;
	public static final String BUTTON_PRESSED= "/images/form/button1.png";
	public static final String BUTTON_NOT_PRESSED= "/images/form/button.png";

	public static final int LOGIN_PANEL_WIDTH = 424;
	public static final int LOGIN_PANEL_HEIGHT = 340;
	public static final String LOGIN_PANEL_IMAGE= "/images/form/form.png";

	//client.ui.FriendPanel
	public static final int FRIEND_PANEL_WIDTH = 180;
	public static final int FRIEND_PANEL_HEIGHT = 1000;
	
	public static final int FRIEND_CARD_WIDTH = 170;
	public static final int FRIEND_CARD_HEIGTH = 80;
	public static final int FRIEND_CARD_IMAGE_SIZE = 60;
	public static final int FRIEND_CARD_GEZI = 10;
	public static final int FRIEND_CARD_LABEL_WIDTH = 70;
	public static final int FRIEND_CARD_LABEL_HEIGHT = 20;
	public static final int FRIEND_CARD_LABEL2_HEIGHT = 30;
	public static final String FRIEND_CARD_IMAGE_SOLDIER = "/images/soldier_head.png";
	public static final String FRIEND_CARD_IMAGE_LEADER = "/images/leader_head.png";
	public static final int FRIEND_CARD_TITLE_HEIGHT = 30;
	public static final int FRIEND_CARD_TITLE_SPACE = 5;

	//client.ui.MessagePanel
	public static final int MESSAGE_PANEL_WIDTH = 200;
	public static final int MESSAGE_PANEL_HRIGHT = 200;
	public static final int MESSAGE_PANEL_GEZI = 10;
	public static final int MESSAGE_PANEL_BODY_WIDTH = 180;
	public static final int MESSAGE_PANEL_BODY_HEIGHT = 100;
	public static final int MESSAGE_PANEL_BUTTON_WIDTH = 80;
	public static final int MESSAGE_PANEL_BUTTIN_HEIGHT = 50;
		
	
	// client.ui.CanSeePanel
	public static final int RDDIUS = 250;
	public static final int PANEL_SIZE = 500;
	public static final String SCAN_IMAGE = "/images/radar1.gif";
	
	// client.ui.ForestPanel
	public static final int FOREST_WIDTH = 1200;
	public static final int FOREST_HEIGTH = 900;
	
	public static final int STEP = 25;
	
	//background map
	public static final String BACKGROUND_IMAGE = "/images/background.jpg";
    public static final String BACKGROUND_IMAGE_COVER = "/images/background-cover.jpg";
	public static final int BACKGROUND_WIDTH = 1200;
	public static final int BACKGROUND_HEIGTH = 900;
	
	//box
	public static final String BOX_OPEN_IMAGE = "/images/box2.png";
	public static final String BOX_CLOSE_IMAGE = "/images/box1.png";
	public static final int BOX_IMAGE_SIZE = 30;
	public static final int BOX_INFO_HEIGHT = 40;
	public static final int BOX_INFO_GEZI = 10;
	public static final int BOX_PANEL_WIDTH = 100;
	public static final int BOX_PANEL_HEIGHT = 40 + 10 + 30 ;

	public static int DISTANCE = 30 ;// 在宝箱多少范围内可以开箱
	
	//client.ui.soldierPanel
	public static final String SOLDIER_GROUP_IMAGE = "/images/soldier/soldier2.png";
	public static final String SOLDIER_NOT_GROUP_IMAGE = "/images/soldier/soldier4.png";
	public static final String CAPTAIN_IMAGE = "/images/captain/captain6.png";
	public final static int SOLDIER_WIDTH = 150;
	public final static int SOLDIER_NAME_HEIGHT_SIZE = 20;
	public static final int SOLDIER_SIZE = 30;
	public static final int SOLDIER_PANEL_HEIGHT = 30 +30 + 30;
	public static final int SOLDIER_PANEL_GE = 10;

	//me
	public static final String ME_IMAGE_UP = "/images/me/soldier1.png";
	public static final String ME_IMAGE_RIGHT = "/images/me/soldier2.png";
	public static final String ME_IMAGE_DOWN = "/images/me/soldier3.png";
	public static final String ME_IMAGE_LEFT = "/images/me/soldier4.png";
	public static final int ME_SIZE = 30;
	
	//client.ui.frame
	public static final int FRAME_WIDTH = FRIEND_PANEL_WIDTH + FOREST_WIDTH;
	public static final int FRAME_HRIGHT = FOREST_HEIGTH;
		
	//net
	//public static final int CLIENT_PORT = 19017;
	//public static final int SERVER_PORT = 19018;
	public static final String SERVER_IP = "https://39.106.7.201";
	public static final String LOGIN = "/api/v1/auth";                 //登录
	public static final String SYNC_POINT = "/api/v1/soldier/update";  //同步我的坐标并拉取消息
	public static final String REGISTER_N = "/api/v1/soldier/register"; //注册公钥
	public static final String MESG_SEND = "/api/v1/msg/send";           //发送一系列消息，服务器进行转发操作
	public static final String CONFIRM = "/api/v1/msg/confirm";         //认证身份完成，向服务端发送认证消息
	public static final String CAPTAIN = "/api/v1/msg/captain";         //队长竞选结束，告诉服务端结果
	public static final String APPLY = "/api/v1/msg/apply";              //开箱请求
	public static final String VOTE =  "/api/v1/msg/vote";

	// message code
	public static final int MESSAGE_OPERATION_ONE = 3001;   //我A向服务端发起 对B认证 ，告诉B 我是A
	public static final int MESSAGE_OPERATION_TWO = 3002;   //其他人向我发起认证
	public static final int MESSAGE_OPERATION_THREE = 3003;   //我接到其他人发送的认证，并认证成功 发送我的信息给服务端
	public static final int MESSAGE_OPERATION_FOUR = 3004;   //我接到其他人发送的认证，并认证失败 终止
	public static final int MESSAGE_OPERATION_FIVE = 3005;   //我发送给其他人的认证 被那个人认证通过 
	public static final int MESSAGE_OPERATION_SIX = 3006;   //我发送给其他人的认证 被那个人认证失败

	public static final int MESSAGE_CAPTAIN_ONE = 4001;
	public static final int MESSAGE_CAPTAIN_TWO = 4002;
	public static final int MESSAGE_CAPTAIN_THREE = 4003;
	public static final int MESSAGE_CAPTAIN_FOUR = 4004;
	public static final int MESSAGE_CAPTAIN_FIVE = 4005;
	public static final int MESSAGE_CAPTAIN_SIX = 4006;
	public static final int MESSAGE_CHOOSE_CAPTAIN = 4007;

	public static final int MESSAGE_BOX_OPEN = 5001;   //宝箱被成功开启

	public static final int MESSAGE_IDENTIFY_RESULT = 6001;
	public static final int MESSAGE_CHOOSE_CAPTAIN_RESULT = 6002;

	//thread 
	public static final int SLEEP_SECONDS = 1000;
	public static final int MESSAGE_SHOW_SECONDS = 5000;

	//generate rsa
	public static final int BIT = 512;
	public static final String CIPER = "975zsk";
	public static final String CIPER_REPLY ="975zsk_reply";
	
	
}
