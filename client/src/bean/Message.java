package src.bean;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Message {

	/**
	 *  1. 客服端请求登录信息
	 *  2. 上报自己的坐标信息并拉取全局的信息
	 *  4. 士兵坐标移动
	 */
	private int code ;
	private Map<String,Object> content;
	private String timestamp;

	public Message(int code, Map<String,Object> content,String timestamp) {
		this.code = code;
		this.content = content;
		this.timestamp = timestamp;
	}

	public Map<String,Object> getContent() {
		return content;
	}

	public void setContent(Map<String,Object> content) {
		this.content = content;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
