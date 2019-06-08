package edu.tsinghua.paratrooper.web.json;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class IdJson {

    @Min(value = 1)
    @ApiModelProperty("当前士兵与哪位士兵认证的")
    private int confirmId;

}
