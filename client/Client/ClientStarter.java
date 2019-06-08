package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import RSA_auth.RSA_Tool;
import RSA_auth.rsa.RSASignature;
import RSA_auth.util.StringConvert;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import src.UI.LoginFrame;
import src.bean.Message;
import src.bean.Soldier;
import src.common.Const;
import src.common.HttpHelper;
import src.common.TransTools;

public class ClientStarter {

	
	Soldier me ;
	LoginFrame loginFrame ;
 
	public static void main(String[] args) throws IOException {     
		ClientStarter client = new ClientStarter();

		System.out.println("异步的,客户端启动了");
		         
	}
		    
	/**
	 * 构造函数
	 */
	public ClientStarter()  {

		me = new Soldier();
		Map<String,String> map = this.generateSoliderInfo();
		me.setPublicKey(map.get("N"));
		this.generatePoint();
		loginFrame = new LoginFrame(me,map);
	}
		   
	/**
	 * 生成士兵的信息，密匙啊巴拉巴拉。。
	 */
	public Map<String,String> generateSoliderInfo() {
		return RSA_Tool.generateKeys();
	}

	/**
	 * 生成士兵的初始坐标
	 */
	public void generatePoint(){

		int x = Const.RDDIUS + (int)(Math.random()*(Const.FOREST_WIDTH - Const.RDDIUS *2));
		int y = Const.RDDIUS + (int)(Math.random()*(Const.FOREST_HEIGTH - Const.RDDIUS *2));
		this.me.setLocationX(x);
		this.me.setLocationY(y);

	}

}
