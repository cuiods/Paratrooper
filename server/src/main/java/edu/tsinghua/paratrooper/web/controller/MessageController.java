package edu.tsinghua.paratrooper.web.controller;

import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.util.constant.ErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/msg")
public class MessageController {

    @GetMapping(value = "/auth")
    public ResultVo<User> testAuth() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResultVo<>(ErrorCode.SUCCESS, "", user);
    }
}
