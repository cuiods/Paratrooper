package edu.tsinghua.paratrooper.web.controller;

import edu.tsinghua.paratrooper.bl.service.SoldierService;
import edu.tsinghua.paratrooper.bl.vo.AuthVo;
import edu.tsinghua.paratrooper.bl.vo.PublicKeyVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.bl.vo.StatusVo;
import edu.tsinghua.paratrooper.util.constant.ErrorCode;
import edu.tsinghua.paratrooper.web.json.LocationJson;
import edu.tsinghua.paratrooper.web.json.RegisterJson;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/v1/soldier")
public class SoldierController {

    @Resource
    private SoldierService soldierService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "注册公钥接口", response = String.class, notes = "返回注册的公钥")
    public ResultVo<String> registerPublicKey(@RequestBody RegisterJson json) {
        return soldierService.registerPublicKey(json.getPublicKey());
    }

    @GetMapping(value = "/keys", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取所有公钥", response = PublicKeyVo.class, responseContainer = "List", notes = "返回所有公钥")
    public ResultVo<List<PublicKeyVo>> getPublicKeys() {
        return soldierService.getPublicKeys();
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "汇报状态并获取消息", response = StatusVo.class, notes = "返回最新消息和队友信息")
    public ResultVo<StatusVo> updateStatus(@RequestBody LocationJson locationJson) {
        return soldierService.updateStatus(locationJson.getX(), locationJson.getY());
    }

    @GetMapping(value = "/updateStatus", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "测试接口", response = String.class, notes = "测试接口，请勿调用")
    public ResultVo<String> updateTest() {
        return new ResultVo<>(ErrorCode.SUCCESS, "ok", "message");
    }

}
