package edu.tsinghua.paratrooper.rsa.rsa;


import edu.tsinghua.paratrooper.rsa.integer.MyInteger;
import edu.tsinghua.paratrooper.rsa.integer.Prime;
import edu.tsinghua.paratrooper.rsa.integer.SpeedUp;
import edu.tsinghua.paratrooper.rsa.util.StringConvert;

public class RSASignature {

    private static final int ASCII_BIT = 8;
    private static final int MAX_ASCII = 256;

    /**
     * Generate RSA keys
     * @param bit RSA bit length (768, 1024, 2048)
     * @return 0:N  1:E  2:D   3:P  4:Q
     */
    public String[] generateKeys(int bit) {
        MyInteger E = new MyInteger("65537");
        // Random P, Q
        MyInteger P = Prime.randomPrime((bit+1)/2);
        MyInteger Q = Prime.randomPrime((bit-1)/2);
        MyInteger N = P.multiply(Q);
        MyInteger fi = P.subtract(MyInteger.ONE).multiply(Q.subtract(MyInteger.ONE));
        // Confirm E
        while (E.gcd(fi).compareAbs(MyInteger.ONE)!=0) {
            E = E.add(MyInteger.TWO);
        }
        // Get D
        MyInteger D = E.inverse(fi);
        return new String[]{
                N.toString(), E.toString(),
                D.toString(), P.toString(), Q.toString()
        };
    }

    /**
     * Encryption
     * @param message message
     * @param d d in DECIMAL representation
     * @param n n in DECIMAL representation
     * @return message
     */
    public String encryption(String message, String d, String n) {
        MyInteger M = new MyInteger(message);
        MyInteger D = new MyInteger(d);
        MyInteger N = new MyInteger(n);

        return SpeedUp.speedUpMod(M,D,N).toString();
    }

    /**
     * Decryption
     * @param message message
     * @param n n in DECIMAL representation
     * @return origin message
     */
    public String decryption(String message, String n) {
        MyInteger M = new MyInteger(message);
        MyInteger E = new MyInteger("65537");
        MyInteger N = new MyInteger(n);

        return SpeedUp.speedUpMod(M,E,N).toString();
    }

}
