package edu.tsinghua.paratrooper.common;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.*;

import com.google.gson.*;

import com.google.gson.reflect.TypeToken;
import edu.tsinghua.paratrooper.bean.Box;
import edu.tsinghua.paratrooper.bean.Message;
import edu.tsinghua.paratrooper.bean.Soldier;
import edu.tsinghua.paratrooper.rsa.RSA_Tool;
import edu.tsinghua.paratrooper.ui.ForestFrame;
import okhttp3.*;
 
public class HttpHelper {

	private static OkHttpClient client = new OkHttpClient.Builder()
			//不加以下两行代码,https请求不到自签名的服务器
			.sslSocketFactory(createSSLSocketFactory())//创建一个证书对象
			.hostnameVerifier(new TrustAllHostnameVerifier())//校验名称,这个对象就是信任所有的主机,也就是信任所有https的请求
			.connectTimeout(10, TimeUnit.SECONDS)//连接超时时间
			.readTimeout(10, TimeUnit.SECONDS)//读取超时时间
			.writeTimeout(10, TimeUnit.SECONDS)//写入超时时间
			.retryOnConnectionFailure(false)//连接不上是否重连,false不重连
			.build();

    public static OkHttpClient getInstance(){
         return client;
    }

	private static MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    private static String token = "";

    public static void setToken(String token){
    	token = token;
	}

    /**
     * 同步get请求
     */
    public static void syncGet(String url,String token) throws Exception{
     
        Request request = new Request.Builder().url(url).
        		     addHeader("Authorization",token).build();
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
    public static Response syncPost(String url,String req,String token) {

		RequestBody requestBody = RequestBody.create(JSON, req);

		Request request = null;
		if(token != null){
			request = new Request.Builder()
					.url(Const.SERVER_IP + url)
					.post(requestBody)
					.addHeader("Authorization",token)
					.build();
		}else{
			request = new Request.Builder()
					.url(Const.SERVER_IP + url)
					.post(requestBody)
					.build();
		}
		Response response = null; // 返回实体
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return response;
    }
    
    /**
     * 异步Get请求,
     */
    public static void asyncGet(String url,String token,ForestFrame frame) {


        Request request = new Request.Builder().url(url).
        		                 addHeader("Authorization",token).build();
        client.newCall(request).enqueue(new MyCallback(frame,token));
    }
    
    /**
     * 异步Post请求,
     */
    public static void asyncPost(String url, String token, String req, ForestFrame frame) {

		RequestBody requestBody = RequestBody.create(JSON, req);
		Request request = new Request.Builder()
				.url(Const.SERVER_IP + url)
				.post(requestBody)
				.addHeader("Authorization",token)
				.build();
        client.newCall(request).enqueue(new MyCallback(frame,token));
    }
    
    
    static class MyCallback implements Callback{ // 回调
        
     public  ForestFrame frame;
     public  String token;
     public  MyCallback(ForestFrame frame,String token) {

     	this.frame = frame;
     	this.token = token;
     }
    	 public void onResponse(Call call, Response response) throws IOException {
             // 请求成功调用，该回调在子线程

			 String str = URLDecoder.decode(response.body().string(), "utf-8");
			 System.out.println("异步消息结果：" + str);

			 JsonObject jsonObject =  new JsonParser().parse(str).getAsJsonObject();

			 if(jsonObject.get("code").getAsInt() == 200) {
				 if (frame != null) {
					 if(jsonObject.get("data").getAsJsonObject().has("code")){   //系统的回执消息，无用
						 return;
					 }
					 dealMessage(jsonObject.get("data").getAsJsonObject(), token,frame);
				 }
			 }
         }
         
         public void onFailure(Call call, IOException e) {
             // 请求失败调用
             System.out.println(e.getMessage());
         }

    }

	/**
	 * 根据传来的消息进行处理
	 * @param data
	 * @param frame
	 */
	public static void  dealMessage(JsonObject data,String token,ForestFrame frame) {

		refreshMe(data.get("me").getAsJsonObject(),frame);
		refreshPoint(data.get("inVisionSoldiers").getAsJsonArray(),frame);
		refreshBox( data.get("boxes").getAsJsonArray(),frame);
		refreshGroupMembers(data.get("groupMembers").getAsJsonArray(),frame);
		dealMessage(data.get("messages").getAsJsonArray(),token,frame);


	}

	/**
	 * 通知页面更新我的小队列表
	 * @param array
	 * @param frame
	 */
	public static void refreshGroupMembers(JsonArray array,ForestFrame frame){

		Gson gson = new Gson();
		List<Soldier> soldiers_list = gson.fromJson(array.toString(),new TypeToken<List<Soldier>>(){}.getType());
		frame.resetFriendList(soldiers_list);
	}

	/**
	 * 通知页面刷新我的信息
	 * @param object
	 * @param frame
	 */
	public static void refreshMe(JsonObject object,ForestFrame frame){

		Gson gson = new Gson();
		Soldier m = gson.fromJson(object, Soldier.class);
		frame.resetMe(m);
	}

	/**
	 * 得到每个消息
	 * @param array
	 * @param frame
	 */
	public static void dealMessage(JsonArray array,String token,ForestFrame  frame) {

		Iterator it = array.iterator();
		while(it.hasNext()){
			JsonElement e = (JsonElement)it.next();
			JsonObject jobject = e.getAsJsonObject();
			refreshMessage(jobject,token,frame);
		}
	}
	/**
	 * 通知消息队列
	 * @param object
	 * @param frame
	 */
	public static void refreshMessage(JsonObject object,String token,ForestFrame  frame) {

	     int code = object.get("code").getAsInt();
	     Gson g = new Gson();
		JsonObject data = g.fromJson(object.get("data").getAsString(), JsonObject.class);

	     if(code == Const.MESSAGE_OPERATION_TWO) {   //有人向我发起认证

	     	System.out.println("有人向我发起认证:"+ object);
			 Map<String,Object> map = new HashMap<String,Object>();
			 map.put("ciper", data.get("ciper").getAsString());
			 map.put("text",data.get("text").getAsString());
			 map.put("from_id",data.get("from_id").getAsInt());
			 Message message = new Message(code,map);
			 frame.addMessageArrive(message);
			 return ;
	     }
	     
	     if(code == Const.MESSAGE_OPERATION_FOUR) {   //回执验证，直接在这里解密了

			 String[] strlist= new String[2];
			 strlist[0] = data.get("ciper").getAsString();
			 strlist[1] = data.get("text").getAsString();
			 if(RSA_Tool.sgnCheck(strlist)) {   //回执验证成功
			 	 //告诉server
				 Map<String,Object> req_map = new HashMap<>();
				 req_map.put("confirmId",data.get("from_id").getAsString());
				 String req = TransTools.objectToJson(req_map);
				 System.out.println("回执验证成功，告诉服务器验证成功消息："+req);
				 System.out.println("看一下token：" + token);
				 HttpHelper.asyncPost(Const.CONFIRM,token,req,null);
				 //frame.getLogInformationPanel().addInfo("zcm test 验证成功");

				 //通知消息队列
				 Map<String,Object> map = new HashMap<String,Object>();
				 map.put("from_id",data.get("from_id").getAsInt());
				 Message message = new Message (Const.MESSAGE_OPERATION_FIVE,map);
				 frame.addMessageArrive(message);
			 }
			 return ;
	     }
	     if(code == Const.MESSAGE_CAPTAIN_ONE){  //4001 我需要重新竞选长官

	     	Soldier other_captain = g.fromJson(object.get("data").getAsString(),Soldier.class);

			 //通知消息队列
//			 Map<String,Object> map = new HashMap<String,Object>();
//			 map.put("other_captain_id",other_captain.getId());
//			 Message message = new Message (Const.MESSAGE_CAPTAIN_ONE,map);
//			 frame.addMessageArrive(message);

			 //通知我主动发起
			 frame.chooseCaptain(other_captain);
			 return ;
		 }

		 if(code == Const.MESSAGE_CAPTAIN_THREE){  //4003

			 String level_code = data.get("level_code").getAsString();
			 int from_id = data.get("from_id").getAsInt();

			 //通知页面回复
			 frame.responseCaptain(level_code,from_id);
			 return ;
		 }

		 if(code == Const.MESSAGE_CAPTAIN_FIVE){  //4005
	     	//String num1 = data.get("num1").getAsString();
	     	//String num2 = data.get("num2").getAsString();
			 String str_list = data.get("nums_list").getAsString();
			 String[] nums= str_list.split(",");

	     	 String P = data.get("P").getAsString();
	     	 int from_id = data.get("from_id").getAsInt();

	     	//通知页面比较最终结果
			 frame.finalResultCaptain(nums,P,from_id);
			 return;
		 }
		 if(code == Const.MESSAGE_BOX_OPEN){
	     	 Gson gson = new Gson();
	     	 Box box =  gson.fromJson(object, Box.class);
	     	 //List<Box> box_lists = new ArrayList<Box>();
	     	 //box_lists.add(box);
	     	// frame.resetBoxPoint(box_lists);

			 //通知消息队列
			 Map<String,Object> map = new HashMap<String,Object>();
			 map.put("box_id",box.getId());
			 Message message = new Message (Const.MESSAGE_BOX_OPEN,map);
			 frame.addMessageArrive(message);
		  }
	}

	/**
	 * 通知界面刷新其他士兵的点的信息
	 * @param array
	 * @param frame
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

	private static SSLSocketFactory createSSLSocketFactory() {
		SSLSocketFactory ssfFactory = null;
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[]{
					new TrustAllCerts()}, new SecureRandom());
			ssfFactory = sc.getSocketFactory();
		} catch (Exception e) {

		} return ssfFactory;
	}

	private static class TrustAllCerts implements X509TrustManager {
		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}
		@Override
		public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

		}
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}
	//信任所有的服务器,返回true
	private static class TrustAllHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * 测试函数
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		//post请求
		FormBody formBody = new FormBody.Builder().add("code","1234").add("name","zp").add("age","25").build();

		//HttpHelper hp = HttpHelper.getInstance();
		// hp.syncGet("http://183.172.51.173:19017/a", "huiewfgerwyuofgyer");

	}
}