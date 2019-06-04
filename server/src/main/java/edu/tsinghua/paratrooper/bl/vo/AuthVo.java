package edu.tsinghua.paratrooper.bl.vo;

import lombok.Data;

@Data
public class AuthVo {

    private String accessToken;

    private long expiresIn;

}
