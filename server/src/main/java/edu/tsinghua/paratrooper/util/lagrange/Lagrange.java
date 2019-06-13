package edu.tsinghua.paratrooper.util.lagrange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class Lagrange {

    @Value("${paratrooper.box.require}")
    public int t;

    @Value("${paratrooper.box.total}")
    private int n;

    private int mod = 991;

    private Integer[] kArr;

    private Map<Integer, MyInteger> keys;

    public Map<Integer, MyInteger> generate() {
        kArr = new Integer[t];
        Map<Integer, MyInteger> xyMap = new HashMap<>();

        Random random = new Random();
        for (int i=0; i<t; i++) {
            kArr[i] = random.nextInt(mod);
        }

        for (int i=1; i<=n; i++) {
            MyInteger bigTem = new MyInteger("0");
            for (int j=0; j<t; j++) {
                int power = (int) Math.pow(i, j);
                MyInteger bigPower = new MyInteger(String.valueOf(power));

                MyInteger myInteger = new MyInteger(String.valueOf(kArr[j]));
                MyInteger tem = bigPower.multiply(myInteger);
                bigTem = bigTem.add(tem);
            }
            xyMap.put(i, bigTem);
        }
        keys = xyMap;
        return xyMap;
    }


    public boolean verification(Map<Integer, MyInteger> inputMap) {
        int length = inputMap.size();
        if (length < t) {
            return false;
        }

        Integer[] bArr = new Integer[length];

        Integer[] xInput = new Integer[length];
        MyInteger[] yInput = new MyInteger[length];
        inputMap.keySet().toArray(xInput);
        inputMap.values().toArray(yInput);

        for (int i=0; i<t; i++) {
            Integer numerator = 1;
            Integer denominator = 1;

            for (int j=0; j<t; j++) {
                if (i != j) {
                    numerator *= xInput[j];
                    denominator *= (xInput[j] - xInput[i]);
                }
            }

            while (denominator < 0){
                denominator += mod;
            }

            if ((numerator % denominator) == 0) {
                numerator /= denominator;
            } else {
                for (int k=1; k<=mod; k++) {
                    if ((denominator*k)%mod == 1) {
                        numerator *= k;
                    }
                }
            }
            bArr[i] = numerator % mod;
        }

        MyInteger bigResult = new MyInteger("0");
        for (int i=0; i<t; i++) {
            MyInteger tem = new MyInteger(String.valueOf(bArr[i]));
            tem = tem.multiply(yInput[i]);
            bigResult = bigResult.add(tem);
        }

        MyInteger tMyInteger = new MyInteger(String.valueOf(mod));
        bigResult = bigResult.mod(tMyInteger);
        Integer aInteger =Integer.valueOf(String.valueOf(bigResult));

        return aInteger.equals(kArr[0]);
    }

    public Map<Integer, MyInteger> getKeys() {
        return keys;
    }

    public void setKeys(Map<Integer, MyInteger> keys) {
        this.keys = keys;
    }
}
