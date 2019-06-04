package edu.tsinghua.paratrooper.bl.serviceimpl;

import edu.tsinghua.paratrooper.BaseTest;
import edu.tsinghua.paratrooper.bl.service.AuthService;
import edu.tsinghua.paratrooper.bl.vo.AuthVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class AuthServiceImplTest extends BaseTest {

    @Resource
    AuthService authService;

    @Test
    public void login() {
        ResultVo<AuthVo> authVoResultVo = authService.login("000001","12345");
        System.out.println(authVoResultVo);
    }
}