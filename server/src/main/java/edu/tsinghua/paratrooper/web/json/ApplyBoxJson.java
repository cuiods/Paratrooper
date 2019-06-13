package edu.tsinghua.paratrooper.web.json;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

@Data
public class ApplyBoxJson {

    @Min(value = 1)
    private int boxId;

    @NotEmpty
    private String key;
}
