package edu.tsinghua.paratrooper.web.controller;

import edu.tsinghua.paratrooper.bl.service.MsgService;
import edu.tsinghua.paratrooper.bl.vo.MsgVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.web.json.MsgJson;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
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
    @ApiOperation(value = "发送消息接口", response = String.class, notes = "返回发送的消息本身")
    public ResultVo<MsgVo> sendMessage(@RequestBody MsgJson msgJson) {
        return msgService.sendMsg(msgJson.getReceiveId(), msgJson.getCode(), msgJson.getMessage());
    }
}
