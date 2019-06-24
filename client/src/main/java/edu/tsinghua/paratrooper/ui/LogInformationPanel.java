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
    private Font font = new Font("Dialog", Font.BOLD, 15);

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
        int ext = 0;
        for(int i = Math.min(information.size() - 1, 15); i > -1; i--, pos++){
            JLabel cur = new JLabel(information.get(i));
            if(information.get(i).indexOf("成功") != -1)
                cur.setForeground(new Color(137, 181, 38));
            else if(information.get(i).indexOf("失败") != -1)
                cur.setForeground(Color.RED);
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

    /**中文字符 */
    private int chCharacter = 0;

    /**英文字符 */
    private int enCharacter = 0;

    /**空格 */
    private int spaceCharacter = 0;

    /**数字 */
    private int numberCharacter = 0;

    /**其他字符 */
    private int otherCharacter = 0;

    /***
     * 统计字符串中中文，英文，数字，空格等字符个数
     * @param str 需要统计的字符串
     */
    public int count(String str) {
        if (null == str || str.equals("")) {
            System.out.println("字符串为空");
            return 0;
        }

        for (int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);
            if ((tmp >= 'A' && tmp <= 'Z') || (tmp >= 'a' && tmp <= 'z')) {
                enCharacter ++;
            } else if ((tmp >= '0') && (tmp <= '9')) {
                numberCharacter ++;
            } else if (tmp ==' ') {
                spaceCharacter ++;
            } else if (isChinese(tmp)) {
                chCharacter ++;
            } else {
                otherCharacter ++;
            }
        }
        System.out.println("中文字符有:" + chCharacter);
        System.out.println("英文字符有:" + enCharacter);
        System.out.println("数字有:" + numberCharacter);
        System.out.println("空格有:" + spaceCharacter);
        System.out.println("其他字符有:" + otherCharacter);

        return chCharacter + enCharacter + numberCharacter;
    }

    /***
     * 判断字符是否为中文
     * @param ch 需要判断的字符
     * @return 中文返回true，非中文返回false
     */
    private boolean isChinese(char ch) {
        //获取此字符的UniCodeBlock
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        //  GENERAL_PUNCTUATION 判断中文的“号
        //  CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
        //  HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
}
