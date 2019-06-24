package edu.tsinghua.paratrooper.web.controller;

import edu.tsinghua.paratrooper.bl.service.AuthService;
import edu.tsinghua.paratrooper.bl.vo.AuthVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.util.constant.ErrorCode;
import edu.tsinghua.paratrooper.web.json.AuthJson;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @ApiOperation(value = "用户登陆接口", response = AuthVo.class, notes = "返回token和过期时间")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<AuthVo> auth(@RequestBody AuthJson authJson) {
        return authService.login(authJson.getName(), authJson.getPassword());
    }

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "测试接口", response = String.class, notes = "测试接口，请勿调用")
    public ResultVo<String> updateTest() {
        return new ResultVo<>(ErrorCode.SUCCESS, "ok", "message");
    }

}
