package edu.tsinghua.paratrooper.web.json;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class RegisterJson {

    @NotEmpty
    private String publicKey;

}
