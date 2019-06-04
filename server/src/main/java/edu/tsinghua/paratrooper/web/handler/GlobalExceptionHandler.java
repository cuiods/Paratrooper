package edu.tsinghua.paratrooper.web.handler;

import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.util.constant.ErrorCode;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Web exception handler
 * @author cuihao
 */
@ControllerAdvice(basePackages = "edu.tsinghua.paratrooper.web.controller")
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public ResultVo<List> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) throws Exception {
        exception.printStackTrace();
        List<ArgInvalidResult> invalidArguments = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            ArgInvalidResult invalidArgument = new ArgInvalidResult();
            invalidArgument.setDefaultMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArgument.setRejectedValue(error.getRejectedValue());
            invalidArguments.add(invalidArgument);
        });
        return new ResultVo<>(ErrorCode.WRONG_PARAMETER, "Invalid parameter", invalidArguments);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResultVo<String> accessDeniedExceptionHandler(AccessDeniedException exception)
            throws Exception {
        exception.printStackTrace();
        return new ResultVo<>(ErrorCode.NO_AUTHORITY,"Do not have authority",exception.getReason()
                +":"+exception.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResultVo<String> runtimeExceptionHandler(RuntimeException exception) throws Exception {
        exception.printStackTrace();
        return new ResultVo<>(ErrorCode.OTHER_ERROR, exception.getMessage(), null);
    }
}
