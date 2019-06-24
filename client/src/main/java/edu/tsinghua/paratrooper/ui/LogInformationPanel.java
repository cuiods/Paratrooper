package edu.tsinghua.paratrooper.ui;



import edu.tsinghua.paratrooper.bean.Message;
import edu.tsinghua.paratrooper.common.Const;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class LogInformationPanel extends JPanel {
    private List<JLabel> infoLogs;
    private List<String> information;
    private Message message;
    private Font font = new Font("宋体", Font.BOLD, 12);

    public LogInformationPanel(){
        information = new ArrayList<>();
        addInfo("正在连接...");
        addInfo("连接成功");
        addInfo("请开始探索");
        lanch();
    }

    public void addInfo(String str){
        information.add(0, str);
        this.removeAll();
        int pos = 0;
        for(int i = Math.min(information.size() - 1, 5); i > -1; i--, pos++){
            JLabel cur = new JLabel(information.get(i));
            if(information.get(i).indexOf("成功") != -1)
                cur.setForeground(Color.GREEN);
            else if(information.get(i).indexOf("失败") != -1)
                cur.setForeground(Color.RED);
            cur.setFont(font);
            //cur.setSize(Const.LOG_PER_WIRDTH, 0);
            this.add(cur);
            cur.setVisible(true);
            cur.setBounds(Const.LOG_PANEL_GEZI, Const.LOG_PANEL_GEZI + pos * Const.LOG_PER_HEIGHT + 20,
                    Const.LOG_PER_WIRDTH, Const.LOG_PER_HEIGHT);
        }
    }

    public void lanch(){
        this.setLayout(null);
        this.setBackground(Color.white);
        //informationArea.setBounds(Const.MESSAGE_PANEL_GEZI, Const.MESSAGE_PANEL_GEZI + 10,
        //        Const.MESSAGE_PANEL_BODY_WIDTH, Const.MESSAGE_PANEL_BODY_HEIGHT * 2 - 301);
        this.setSize(Const.MESSAGE_PANEL_WIDTH,Const.MESSAGE_PANEL_HRIGHT);
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,2),"信息日志", TitledBorder.CENTER,TitledBorder.TOP,new java.awt.Font("宋体",0,16)));
        this.setVisible(true);
    }

    /**
     * 一条新消息，刷新页面
     * @param message
     */
    public void resetMessage(Message message) {
        this.message = message;
        int code = message.getCode();
        String str ="";
        switch(code) {
            case Const.MESSAGE_OPERATION_TWO :  //有人向我发起认证
                str = "士兵:" + message.getData().get("from_id")+"\n向您发起认证";
                addInfo(str);
                break;
            case Const.MESSAGE_OPERATION_FIVE : //认证成功
                //str = "认证成功！";
                //addInfo(str);
                break;
            case Const.MESSAGE_CAPTAIN_ONE:  //竞选队长
                str = "与:"+ message.getData().get("other_captain_id")+"\n竞选队长。";
                addInfo(str);
                break;
            case Const.MESSAGE_BOX_OPEN:
                str = "宝箱:"+ message.getData().get("box_id")+"\n已被开启。";
                addInfo(str);
                break;
            case Const.MESSAGE_IDENTIFY_RESULT:
                str = "与" + message.getData().get("name") + "认证成功";
                addInfo(str);
                break;
            case Const.MESSAGE_CHOOSE_CAPTAIN_RESULT:
                str = "队长是：" + message.getData().get("name");
                addInfo(str);
                break;
        }
    }
}
