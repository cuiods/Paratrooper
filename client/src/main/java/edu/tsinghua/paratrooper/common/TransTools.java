package edu.tsinghua.paratrooper.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;


//一系列转化工具
public class TransTools {

    private static final TransTools trans =  new TransTools();
    
    public static TransTools getInstance(){
         return trans;
    }
    
    /**
     * object转化为string
     * @param map
     * @return
     */  
    public static String objectToJson(Object oj) {
       	Gson gson = new Gson();
       	return gson.toJson(oj);
    }
    
    /**
     *  时间转换为时间戳
     * @param s
     * @return
     * @throws ParseException
     */
    public static String dateToStamp(String s) throws ParseException {
       	String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    
    /**
     * 时间戳转换为时间
     * @param s
     * @return
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    
}
