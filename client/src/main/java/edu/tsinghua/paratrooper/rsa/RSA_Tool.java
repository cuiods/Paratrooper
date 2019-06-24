package edu.tsinghua.paratrooper.rsa;


import edu.tsinghua.paratrooper.common.Const;
import edu.tsinghua.paratrooper.rsa.hash.md5;
import edu.tsinghua.paratrooper.rsa.rsa.RSASignature;
import edu.tsinghua.paratrooper.rsa.util.StringConvert;

import java.util.HashMap;
import java.util.Map;

public class RSA_Tool {

    private static RSASignature signature = new RSASignature();
    private static md5 md = new md5();
    private static Map<String,String> map = new HashMap<>();

    public static RSASignature getInstance(){
        return signature;
    }

    /**
     * 生成密匙信息
     * @return
     */
    public static Map<String,String> generateKeys(){
        String[] keys = signature.generateKeys(Const.BIT);  //得到所有的钥匙
        String N = StringConvert.convert(keys[0],10,16);
        String E = StringConvert.convert(keys[1],10,16);   //转16进制
        String D = StringConvert.convert(keys[2],10,16);
        String P = StringConvert.convert(keys[3],10,16);
        String Q = StringConvert.convert(keys[4],10,16);
        map.put("N",N);    //公钥
        map.put("E",E);    //是一个常量
        map.put("D",D);    //私钥
        map.put("P",P);
        map.put("Q",Q);
        return map;
    }


    /**
     * 加密报文
     * @param text
     * @return
     */
    public static String encodeRSA(String text, String pri_key) {   //加密
        return signature.encryption(text, StringConvert.convert(pri_key, 16, 10),
                StringConvert.convert(map.get("N"), 16, 10));
    }

    /**
     * 解密报文
     * @param text
     * @return
     */
    private static String decodeRSA(String text, String pub_key) {   //解密
        return signature.decryption(text, StringConvert.convert(pub_key, 16, 10));
    }

    /**
     * 用自己的私钥签名
     * @param text
     * @return
     */
    public static String[] enSgn(String text,String pri_key) {   //签名认证
        String enHS = encodeRSA(md5.encode(text),pri_key);
        String[] val = {enHS, text};
        // String[] val = {encodeRSA(text,pri_key), text};

        return val;
    }

    /**
     * 用对方的公钥进行解密
     * @param sgn
     * @return
     */
    public static boolean sgnCheck(String[] sgn,String pub_key) {
        if (sgn.length != 2) {
            return  false;
        }
        String deHS = decodeRSA(sgn[0],pub_key);
        String hs   = md5.encode(sgn[1]);
        // String deHS = decodeRSA(sgn[0],pub_key);
        // String hs   = sgn[1];

        if (deHS.equals(hs)) {
            return true;
        }
        return false;
    }

//    String[] sgnCheck = enSgn(text);
//    boolean check = sgnCheck(sgnCheck);
//    String d0 = decodeRSA(sgnCheck[0]);
//    String d1 = md5.encode(sgnCheck[1]);

    public static void main(String[] args) {
         RSA_Tool.generateKeys();

         String[] str_list = RSA_Tool.enSgn(" LIMING", RSA_Tool.map.get(""));
        // System.out.print(sgnCheck(str_list));

    }
}
