package edu.tsinghua.paratrooper.web.controller;

import edu.tsinghua.paratrooper.bl.service.MsgService;
import edu.tsinghua.paratrooper.bl.vo.BoxVo;
import edu.tsinghua.paratrooper.bl.vo.MsgVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.web.json.ApplyBoxJson;
import edu.tsinghua.paratrooper.web.json.CaptainJson;
import edu.tsinghua.paratrooper.web.json.IdJson;
import edu.tsinghua.paratrooper.web.json.MsgJson;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/msg")
public class MessageController {

    @Resource
    private MsgService msgService;

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "发送消息接口", response = MsgVo.class, notes = "返回发送的消息本身")
    public ResultVo<MsgVo> sendMessage(@RequestBody MsgJson msgJson) {
        return msgService.sendMsg(msgJson.getReceiveId(), msgJson.getCode(), msgJson.getMessage());
    }

    @PostMapping(value = "/confirm", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "反馈身份认证结果", response = String.class,
            notes = "触发后续队长比较，本队队长会收到消息,返回本队队长的名字")
    public ResultVo<String> confirmAuthentication(@RequestBody IdJson idJson) {
        return msgService.confirmAuthentication(idJson.getConfirmId());
    }

    @PostMapping(value = "/captain", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "反馈队长比较结果", response = String.class,
            notes = "被取消的队长会收到消息，且所有相关士兵组号会更新")
    public ResultVo<String> confirmCaptain(@RequestBody CaptainJson captainJson) {
        return msgService.confirmCaptain(captainJson.getCompareId(), captainJson.getResult());
    }

    @PostMapping(value = "/apply", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "申请开箱子", response = BoxVo.class,
            notes = "如果成功开箱则所有申请者收到消息")
    public ResultVo<BoxVo> applyBox(@RequestBody ApplyBoxJson applyBoxJson) {
        return msgService.applyBox(applyBoxJson.getBoxId(), applyBoxJson.getKey());
    }
}
