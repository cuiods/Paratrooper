package edu.tsinghua.paratrooper.bl.vo;

import edu.tsinghua.paratrooper.data.entity.TBoxEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class BoxVo {
    private int id;
    private int locationX;
    private int locationY;
    private int apply;
    private int total;
    private int status;

    public BoxVo(TBoxEntity entity) {
        BeanUtils.copyProperties(entity, this, "soldierEntities");
    }

}
