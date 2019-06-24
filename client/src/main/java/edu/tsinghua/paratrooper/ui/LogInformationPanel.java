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
    private Font font = new Font("Dialog", Font.PLAIN, 15);

    public LogInformationPanel(){
        information = new ArrayList<>();
        addInfo("登录成功，请开始探索");
        lanch();
    }

    public void addInfo(String str){
        information.add(0, str);
        this.removeAll();
        int pos = 0;
        int ext = 0;
        for(int i = Math.min(information.size() - 1, 5); i > -1; i--, pos++){
            JLabel cur = new JLabel(information.get(i));
            if(information.get(i).indexOf("成功") != -1)
                cur.setForeground(new Color(137, 181, 38));
            else if(information.get(i).indexOf("失败") != -1)
                cur.setForeground(new Color(165,42,42));
            cur.setFont(font);
            int height = Const.LOG_PER_HEIGHT;

            cur.setLocation(Const.LOG_PANEL_GEZI, Const.LOG_PANEL_GEZI + (pos+ext) * Const.LOG_PER_HEIGHT + 20);

            System.out.println(information.get(i) + "   length:"+information.get(i).length() + " "+pos + " "+ ext);
            if(information.get(i).length()>14){
                height+= Const.LOG_PER_HEIGHT;
                ext= ext+1;
            }
            this.add(cur);
            cur.setVisible(true);
            cur.setSize(Const.LOG_PER_WIRDTH, height);
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

}
