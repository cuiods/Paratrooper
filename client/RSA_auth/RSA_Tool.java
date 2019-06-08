package RSA_auth;

import RSA_auth.hash.md5;
import RSA_auth.rsa.RSASignature;
import RSA_auth.util.StringConvert;
import src.common.Const;

import java.util.HashMap;
import java.util.Map;

public class RSA_Tool {

    private static RSASignature signature = new RSASignature();;
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
        map.put("N",N);
        map.put("E",E);
        map.put("D",D);
        map.put("P",P);
        map.put("Q",Q);
        return map;
    }


    /**
     * 加密报文
     * @param text
     * @return
     */
    public static String encodeRSA(String text, String pub_key) {   //加密
        return signature.encryption(text, StringConvert.convert(map.get("E"), 16, 10),
                StringConvert.convert(pub_key, 16, 10), Const.BIT/2);
    }

    /**
     * 解密报文
     * @param text
     * @return
     */
    private static String decodeRSA(String text) {   //解密
        return signature.decryption(text, StringConvert.convert(map.get("D"), 16, 10),
                StringConvert.convert(map.get("P"), 16, 10), StringConvert.convert(map.get("Q"), 16, 10), Const.BIT/2);
    }

    /**
     * 用传来的公钥签名
     * @param text
     * @return
     */
    public static String[] enSgn(String text,String pub_key) {   //签名认证
        String enHS = encodeRSA(md5.encode(text),pub_key);
        String[] val = {enHS, text};
        return val;
    }

    /**
     * 用自己的私钥进行签名认证
     * @param sgn
     * @return
     */
    public static boolean sgnCheck(String[] sgn) {
        if (sgn.length != 2) {
            return  false;
        }
        String deHS = decodeRSA(sgn[0]);
        String hs   = md5.encode(sgn[1]);

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
         System.out.print(sgnCheck(str_list));


    }
}
