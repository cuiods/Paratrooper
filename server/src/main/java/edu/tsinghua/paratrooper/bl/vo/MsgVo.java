package edu.tsinghua.paratrooper.bl.vo;

import edu.tsinghua.paratrooper.data.entity.TMsgEntity;
import lombok.Data;

@Data
public class MsgVo {
    private int code;
    private String data;

    public MsgVo(TMsgEntity entity) {
        this.code = entity.getCode();
        this.data = entity.getData();
    }
}
