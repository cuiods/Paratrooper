package edu.tsinghua.paratrooper.web.controller;

import edu.tsinghua.paratrooper.bl.service.SoldierService;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.util.constant.ErrorCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Resource
    private SoldierService soldierService;

    @GetMapping(value = "/init", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "初始化所有信息", response = String.class, notes = "初始化士兵和消息")
    public ResultVo<String> initialize() {
        soldierService.initializeSoldierStatus();
        return new ResultVo<>(ErrorCode.SUCCESS, "ok", "");
    }

}
