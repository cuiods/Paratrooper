package src.UI;


import src.bean.Message;
import src.common.Const;

import javax.swing.*;
import java.util.*;

public class LogInformationPanel extends JPanel {
    private JTextArea informationArea;
    private List<String> information;
    private Message message;

    public LogInformationPanel(){
        information = new ArrayList<>();
        information.add(0, "连接成功");
        information.add(0, "请开始探索");
        informationArea = new JTextArea(getText());
        lanch();
    }

    public void lanch(){
        this.setLayout(null);
        this.add(informationArea);
        informationArea.setBounds(Const.MESSAGE_PANEL_GEZI, Const.MESSAGE_PANEL_GEZI + 10,
                Const.MESSAGE_PANEL_BODY_WIDTH, Const.MESSAGE_PANEL_BODY_HEIGHT * 2 - 301);
        this.setSize(Const.MESSAGE_PANEL_WIDTH,Const.MESSAGE_PANEL_HRIGHT);
        this.setBorder(BorderFactory.createTitledBorder("当前信息"));
        this.setVisible(false);
    }

    String getText(){
        String text = "";
        for(int i = 0; i < information.size(); i++){
            text += information.get(i);
            if(i < information.size() - 1)
                text += '\n';
        }
        return text;
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
                str = "士兵:" + message.getData().get("from_id")+"向您发起认证";
                information.add(0, str);
                informationArea.setText(getText());
                break;
            case Const.MESSAGE_OPERATION_FIVE : //认证成功
                str = "您对士兵:"+ message.getData().get("from_id")+"发起的认证成功！";
                information.add(0, str);
                informationArea.setText(getText());
                break;
            case Const.MESSAGE_CAPTAIN_ONE:  //竞选队长
                str = "您需要与:"+ message.getData().get("other_captain_id")+"竞选队长。";
                information.add(0, str);
                informationArea.setText(getText());
                break;
            case Const.MESSAGE_BOX_OPEN:
                str = "宝箱:"+ message.getData().get("box_id")+"已被您和您的队友开启。";
                information.add(0, str);
                informationArea.setText(getText());
                break;
        }
    }
}
