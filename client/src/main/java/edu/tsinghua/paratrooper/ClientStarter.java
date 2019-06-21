package edu.tsinghua.paratrooper;

import edu.tsinghua.paratrooper.bean.Soldier;
import edu.tsinghua.paratrooper.common.Const;
import edu.tsinghua.paratrooper.rsa.RSA_Tool;
import edu.tsinghua.paratrooper.ui.LoginFrame;

import java.io.IOException;
import java.util.Map;

public class ClientStarter {


    private Soldier me ;

    public static void main(String[] args) throws IOException {
        ClientStarter client = new ClientStarter();

        System.out.println("异步的,客户端启动了");

    }

    /**
     * 构造函数
     */
    public ClientStarter()  {

        me = new Soldier();
        Map<String,String> map = this.generateSoliderInfo();
        me.setPublicKey(map.get("N"));
        this.generatePoint();
        LoginFrame loginFrame = new LoginFrame(me, map);
    }

    /**
     * 生成士兵的信息，密匙啊巴拉巴拉。。
     */
    public Map<String,String> generateSoliderInfo() {
        return RSA_Tool.generateKeys();
    }

    /**
     * 生成士兵的初始坐标
     */
    public void generatePoint(){

        int x = Const.RDDIUS + (int)(Math.random()*(Const.FOREST_WIDTH - Const.RDDIUS *2));
        int y = Const.RDDIUS + (int)(Math.random()*(Const.FOREST_HEIGTH - Const.RDDIUS *2));
        this.me.setLocationX(x);
        this.me.setLocationY(y);

    }

}
