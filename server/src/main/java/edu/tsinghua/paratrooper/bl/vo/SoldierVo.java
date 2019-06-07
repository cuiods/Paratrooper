package edu.tsinghua.paratrooper.bl.vo;

import edu.tsinghua.paratrooper.data.entity.TSoldierEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class SoldierVo {
    private int id;
    private String name;
    private int locationX;
    private int locationY;
    private String publicKey;
    private int captain;
    private int groupNum;
    private int updateStatus;
    private int alive;

    public SoldierVo(TSoldierEntity entity) {
        BeanUtils.copyProperties(entity, this,
                "password","createdAt","deletedAt","authorityEntities");
    }
}
