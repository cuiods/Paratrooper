package edu.tsinghua.paratrooper.web.json;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class AuthJson {

    @NotEmpty
    @Length(max = 255, message = "max length of name is 255")
    private String name;

    @NotEmpty
    @Length(max = 255, message = "max length of password is 255")
    private String password;

}
