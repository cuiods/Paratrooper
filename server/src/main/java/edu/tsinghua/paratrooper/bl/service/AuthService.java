package edu.tsinghua.paratrooper.bl.service;

import edu.tsinghua.paratrooper.bl.vo.AuthVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;

public interface AuthService {

    /**
     * Login to system
     * @param name soldier name / id
     * @param password password
     * @return {@link AuthVo}
     */
    ResultVo<AuthVo> login(String name, String password);
}
