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
import java.util.List;

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

	HttpHelper httphelper = HttpHelper.getInstance();
	
	Soldier me ;
	LoginFrame loginFrame ;
	String url = "";
 
	public static void main(String[] args) throws IOException {     
		ClientStarter client = new ClientStarter();
		System.out.println("异步的,客户端启动了");
		         
	}
		    
	/**
	 * 构造函数
	 */
	public ClientStarter()  {
		this.generateSoliderInfo();
		System.out.println("33333");
		loginFrame = new LoginFrame(me); 
	}
		   
	/**
	 * 生成士兵的信息，密匙啊巴拉巴拉。。
	 */
	public void generateSoliderInfo() {
		me = new Soldier();
	}
		

}
