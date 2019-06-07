package serverTest;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import src.bean.Box;
import src.bean.Message;
import src.bean.Soldier;
import src.common.Const;
import src.common.TransTools;



public class ServerStarter {

	HttpServerProvider provider = null;
	HttpServer httpserver = null;
	private ClientThread ct;
	static TransTools transtools = TransTools.getInstance();
	
	public static void main(String[] args) throws IOException {
        
//		Soldier test = new Soldier();
//        List<Soldier> lists = new ArrayList<Soldier>();
//        lists.add(test);
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("soldier_lists", lists);
//        Message message = new Message(1,map,"timestamp");
//        String str = transtools.objectToJson(message);
//        System.out.println(str);
//        
//        Gson gson = new Gson();
//       // Message message2 = gson.fromJson(str, Message.class);
//       // Map<String,Object> map2 = message2.getContent();
// 
//      //  System.out.println(map2);
//        
//        JsonObject jsonObject = (JsonObject) new JsonParser().parse(str).getAsJsonObject();
//        JsonArray array = jsonObject.get("content").getAsJsonObject().get("soldier_lists").getAsJsonArray();
//        List<Soldier> mydeptList = gson.fromJson(array.toString(),new TypeToken<List<Soldier>>(){}.getType());
//        System.out.println(mydeptList.toString());
        
        ServerStarter client = new ServerStarter();
		System.out.println("服务端启动了");
		         
	}
	
	/**
	 * 构造函数
	 */
	public ServerStarter()  {
		this.startNetwork();
	
	}
	
	/**
	 * 开启网络监听服务端消息
	 */
	public void startNetwork() {
		provider  = HttpServerProvider.provider(); 
	    	try {
			httpserver = provider.createHttpServer(new InetSocketAddress(Const.SERVER_PORT), 100);  //监听端口19017,能同时接受100个请求  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	httpserver.createContext("/", new MyResponseHandler());   
		httpserver.setExecutor(null); 
	    ct = new ClientThread();  
		ct.start();
	}
		    
	public class ClientThread extends Thread{
		@Override
		public void run() {
		    httpserver.start(); 
		}
	}
		    
	static class MyResponseHandler implements HttpHandler {
		 @Override
		 public void handle(HttpExchange httpExchange) throws IOException {
		     System.out.println("receive a message");
		     String requestMethod = httpExchange.getRequestMethod();
		     if(requestMethod.equalsIgnoreCase("GET")){//客户端的请求是get方法
		         
		         Headers responseHeaders = httpExchange.getResponseHeaders();
		         responseHeaders.set("Content-Type", "text/html;charset=utf-8");

		         URI url = httpExchange.getRequestURI();
		         String str = URLDecoder.decode(url.toString(), "utf-8");  
		         System.out.println(str);
		         
		         String response = null;
		       
		         if(str.equals("/login")) response = "ok";
		         else {
		        	        //soldier
		        	        Soldier test = new Soldier();
		        	        test.setPoint_x(800);
		        	        test.setPoint_y(300);
		        	        List<Soldier> lists = new ArrayList<Soldier>();
		        	        lists.add(test);
		        	        
		        	        Soldier test2 = new Soldier();
		        	        test2.setPoint_x(300);
		        	        test2.setPoint_y(200);
		        	        lists.add(test2);
		        	        
		        	        Map<String,Object> map = new HashMap<String,Object>();
		        	        map.put("soldier_lists", lists);
		        	        
		        	        //box
		        	        Box box = new Box();
		        	        box.setPoint_x(400);
		        	        box.setPoint_y(600);
		        	        
		        	        Box box2 = new Box();
		        	        box2.setPoint_x(800);
		        	        box2.setPoint_y(400);
		        	        
		        	        List<Box> boxlists = new ArrayList<Box>();
		        	        boxlists.add(box);
		        	        boxlists.add(box2);
		        	        map.put("box_lists", boxlists);
		        	        
		        	        //message 
		        	        Map<String,Object> operation = new HashMap<String,Object>();
		        	        operation.put("op_code", Const.MESSAGE_OPERATION_TWO);
		        	        operation.put("sodiler_id", 1);
		        	        operation.put("pri_key", "testetstpri_keykey");
		        	        operation.put("timestamp", "operation_timestamp");
		        	        map.put("operation", operation);
		        	        
		        	        Message message = new Message(3,map,"timestamp");
		        	        response = transtools.objectToJson(message);
		         }
		         
		         System.out.println("reply a message");
		         System.out.println(response);
		         httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes("UTF-8").length);

		         OutputStream responseBody = httpExchange.getResponseBody();
		         OutputStreamWriter writer = new OutputStreamWriter(responseBody, "UTF-8");
		         writer.write(response);
		         writer.close();
		         responseBody.close();  
		    }else if(requestMethod.equalsIgnoreCase("POST")) {   //咱们用的到post么？get就行了吧
		            	
		    }
		}
	}		
}
