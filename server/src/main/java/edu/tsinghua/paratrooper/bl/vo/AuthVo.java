package edu.tsinghua.paratrooper.bl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthVo {

    private String accessToken;

    private long expiresIn;

}
