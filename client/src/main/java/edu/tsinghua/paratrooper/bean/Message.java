package edu.tsinghua.paratrooper.bean;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Message {

	private int code ;
	private Map<String,Object> data;

	public Message(int code, Map<String,Object> data) {
		this.code = code;
		this.data = data;
	}

	public Map<String,Object> getData() {
		return data;
	}

	public void setData(Map<String,Object> data) {
		this.data = data;
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
