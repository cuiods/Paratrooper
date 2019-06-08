package edu.tsinghua.paratrooper.web.json;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class CaptainJson {

    @Min(value = 1)
    @ApiModelProperty("当前队长与哪位队长比较的")
    private int compareId;

    @Min(value = -1)
    @Max(value = 1)
    @ApiModelProperty("当前队长比比较队长军衔高则为1，否则为-1")
    private int result;

}
