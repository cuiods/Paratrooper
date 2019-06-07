package src.common;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import src.UI.ForestFrame;
import src.bean.Box;
import src.bean.Message;
import src.bean.Soldier;
 
public class HttpHelper {
 
    private static OkHttpClient client =  new OkHttpClient();
    
    private static final HttpHelper httpHelper=new HttpHelper();
    public static HttpHelper getInstance(){
         return httpHelper;
    }
 
    /**
     * 同步get请求
     */
    public static void syncGet(String url,String token) throws Exception{
     
        Request request = new Request.Builder().url(url).
        		     addHeader("Access-User-Token",token).build();
        Response response = client.newCall(request).execute(); // 返回实体
        
        if (response.isSuccessful()) { // 判断是否成功
            
            System.out.println(response.body().string()); // 打印数据
        }else {
            System.out.println("失败"); // 链接失败
        }
    }
    
    
    /**
     * 同步Post请求
     */
    public static void syncPost(String url,String token,FormBody formBody) throws Exception{
     
        Request request = new Request.Builder()
        		.url(url)
        		.addHeader("Access-User-Token",token)
        		.post(formBody)
        		.build();
        Response response = client.newCall(request).execute(); // 返回实体
        
        if (response.isSuccessful()) { // 判断是否成功
            
            System.out.println(response.body().string()); // 打印数据
        }else {
            System.out.println("失败"); // 链接失败
        }
    }
    
    /**
     * 异步Get请求,
     */
    public static void asyncGet(String url,String token,ForestFrame frame) {

        Request request = new Request.Builder().url(url).
        		                 addHeader("Access-User-Token",token).build();
        client.newCall(request).enqueue(new MyCallback(frame));
    }
    
    /**
     * 异步Post请求,
     */
    public static void asyncPost(String url,String token,FormBody formBody,ForestFrame frame) {

        Request request = new Request.Builder().url(url).
        		                 addHeader("Access-User-Token",token).post(formBody).build();
        client.newCall(request).enqueue(new MyCallback(frame));
    }
    
    
    static class MyCallback implements okhttp3.Callback{ // 回调
        
     public  ForestFrame frame;
     public  MyCallback(ForestFrame frame) {
    	       this.frame = frame;
     }
    	 public void onResponse(Call call, Response response) throws IOException {
             // 请求成功调用，该回调在子线程
           
		         String str = URLDecoder.decode(response.body().string(), "utf-8");  
		         System.out.println(str);
		         dealMessage(str,frame);
              
         }
         
         public void onFailure(Call call, IOException e) {
             // 请求失败调用
             System.out.println(e.getMessage());
         }

    }
    
	/**
	 * 根据传来的消息进行处理
	 * @param messagejson
	 */
	public static void  dealMessage(String messagejson,ForestFrame frame) {
		Gson gson = new Gson();
		
		JsonObject jsonObject = (JsonObject) new JsonParser().parse(messagejson).getAsJsonObject();
		int code =  jsonObject.get("code").getAsInt();
		switch (code) {
		case  1:
			break; 
		case  Const.MESSAGE_PULL_REPLAY :
			refreshPoint( jsonObject.get("content").getAsJsonObject().get("soldier_lists").getAsJsonArray(),frame);
			refreshBox( jsonObject.get("content").getAsJsonObject().get("box_lists").getAsJsonArray(),frame);
			refreshMessage(jsonObject.get("content").getAsJsonObject().get("operation").getAsJsonObject(),frame);
			break;
		}
	}
	
	/**
	 * 通知消息队列
	 * @param array
	 */
	public static void refreshMessage(JsonObject object,ForestFrame  frame) {
		Gson gson = new Gson();
	     int code = object.get("op_code").getAsInt();
	     String timestamp = object.get("timestamp").getAsString();
	     
	     if(code == Const.MESSAGE_OPERATION_TWO) {   //有人向我发起认证
	    	      int sodiler_id = object.get("sodiler_id").getAsInt();
	    	      String pri_key = object.get("pri_key").getAsString();
	    	      Map<String,Object> map = new HashMap<String,Object>();
	    	      map.put("sodiler_id", sodiler_id);
	    	      map.put("pri_key", pri_key);
	    	      Message message = new Message(code,map,timestamp);
	    	     frame.addMessageArrive(message);
	     }
	     
	     if(code == Const.MESSAGE_OPERATION_FIVE) {   //我发起的认证 被 认证成功
	    	 
	     }
	     
	     if(code == Const.MESSAGE_OPERATION_SIX) {     //我发起的认证 被 认证失败
	    	 
	     }
	}
	
	/**
	 * 通知界面刷新其他士兵的点的信息
	 * @param other_soldiers
	 */
	public static void refreshPoint(JsonArray array,ForestFrame  frame) {
		Gson gson = new Gson();
		List<Soldier> soldiers_list = gson.fromJson(array.toString(),new TypeToken<List<Soldier>>(){}.getType());
        frame.resetOtherSoldierPoint(soldiers_list);
	}
	
	/**
	 * 通知页面刷新宝箱的信息
	 * @param array
	 * @param frame
	 */
	public static void refreshBox(JsonArray array,ForestFrame  frame) {
		Gson gson = new Gson();
		List<Box> box_list = gson.fromJson(array.toString(),new TypeToken<List<Box>>(){}.getType());
        frame.resetBoxPoint(box_list);
	}
    
    public static void main(String[] args) throws Exception {
 
        //post请求
        FormBody formBody = new FormBody.Builder().add("code","1234").add("name","zp").add("age","25").build();
        
        HttpHelper hp = HttpHelper.getInstance();
        hp.syncGet("http://183.172.51.173:19017/a", "huiewfgerwyuofgyer");
 
    }
}