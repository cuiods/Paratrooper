package edu.tsinghua.paratrooper.bl.vo;

import edu.tsinghua.paratrooper.data.entity.TBoxEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class BoxVo {
    private int locationX;
    private int locationY;
    private int status;

    public BoxVo(TBoxEntity entity) {
        BeanUtils.copyProperties(entity, this, "id","apply");
    }

}
