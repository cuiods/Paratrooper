package src.common;


public class Const{
	
	
	//client.UI.FriendPanel
	public static final int FRIEND_PANEL_WIDTH = 180;
	public static final int FRIEND_PANEL_HEIGHT = 1000;
	
	public static final int FRIEND_CARD_WIDTH = 160;
	public static final int FRIEND_CARD_HEIGTH = 80;
	public static final int FRIEND_CARD_IMAGE_SIZE = 60;
	public static final int FRIEND_CARD_GEZI = 10;
	public static final int FRIEND_CARD_LABEL_WIDTH = 70;
	public static final int FRIEND_CARD_LABEL_HEIGHT = 20;
	public static final int FRIEND_CARD_LABEL2_HEIGHT = 30;
	public static final String FRIEND_CARD_IMAGE_SOLDIER = "resource/images/soldier_head.jpg";
	public static final String FRIEND_CARD_IMAGE_LEADER = "resource/images/leader_head.jpg";
	
	//client.UI.MessagePanel
	public static final int MESSAGE_PANEL_WIDTH = 200;
	public static final int MESSAGE_PANEL_HRIGHT = 200;
	public static final int MESSAGE_PANEL_GEZI = 10;
	public static final int MESSAGE_PANEL_BODY_WIDTH = 180;
	public static final int MESSAGE_PANEL_BODY_HEIGHT = 100;
	public static final int MESSAGE_PANEL_BUTTON_WIDTH = 80;
	public static final int MESSAGE_PANEL_BUTTIN_HEIGHT = 50;
		
	
	// client.UI.CanSeePanel
	public static final int RDDIUS = 250;
	public static final int PANEL_SIZE = 500;
	public static final String ME_IMAGE = "resource/images/soldier.jpg";
	
	// client.UI.ForestPanel
	public static final int FOREST_WIDTH = 1200;
	public static final int FOREST_HEIGTH = 900;
	
	public static final int STEP = 25;
	
	//background map
	public static final String BACKGROUND_IMAGE = "resource/images/background.jpg";
	public static final int BACKGROUND_WIDTH = 1200;
	public static final int BACKGROUND_HEIGTH = 900;
	
	//box
	public static final String BOX_OPEN_IMAGE = "resource/images/box_open.png";
	public static final String BOX_CLOSE_IMAGE = "resource/images/box_close.png";
	public static final int BOX_IMAGE_SIZE = 80;
	public static final int BOX_INFO_HEIGHT = 30;
	public static final int BOX_PANEL_WIDTH = 80;
	public static final int BOX_PANEL_HEIGHT = 80+30+30;
	
	public static int NEED_SOLDIER = 3;
	public static int DISTANCE = 30 ;// 在宝箱多少范围内可以开箱
	
	//client.UI.soldierPanel
	public static final String SOLDIER_IMAGE = "resource/images/soldier.jpg";
	public final static int SOLDIER_WIDTH = 100;
	public static final int SOLDIER_HEIGTH = 120;
	public static final int SOLDIER_PANEL_HEIGHT = 120 +30+30+30;
	public static final int SOLDIER_PANEL_GE = 30;
	
	//client.UI.frame
	public static final int FRAME_WIDTH = FRIEND_PANEL_WIDTH + FOREST_WIDTH;
	public static final int FRAME_HRIGHT = FOREST_HEIGTH;
		
	//net
	//public static final int CLIENT_PORT = 19017;
	public static final int SERVER_PORT = 19018;
	public static final String SERVER_IP = "127.0.0.1";
	
	// message code
	public static final int MESSAGE_LOGIN = 1;
	public static final int MESSAGE_PULL = 2;
	public static final int MESSAGE_PULL_REPLAY = 3;
	
	public static final int MESSAGE_OPERATION_ONE = 3001;   //我A向服务端发起 对B认证 ，告诉B 我是A
	public static final int MESSAGE_OPERATION_TWO = 3002;   //其他人向我发起认证
	public static final int MESSAGE_OPERATION_THREE = 3003;   //我接到其他人发送的认证，并认证成功 发送我的信息给服务端
	public static final int MESSAGE_OPERATION_FOUR = 3004;   //我接到其他人发送的认证，并认证失败 终止
	public static final int MESSAGE_OPERATION_FIVE = 3005;   //我发送给其他人的认证 被那个人认证通过 
	public static final int MESSAGE_OPERATION_SIX = 3006;   //我发送给其他人的认证 被那个人认证失败

	
	//thread 
	public static final int SLEEP_SECONDS = 10000;
	public static final int MESSAGE_SHOW_SECONDS = 5000;

	
	
}
