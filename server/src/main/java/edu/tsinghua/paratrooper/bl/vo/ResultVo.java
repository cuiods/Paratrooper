package edu.tsinghua.paratrooper.bl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo<T> {

    private int code;
    private String message;
    private T data;

    public ResultVo(T data) {
        this.data = data;
    }

}
