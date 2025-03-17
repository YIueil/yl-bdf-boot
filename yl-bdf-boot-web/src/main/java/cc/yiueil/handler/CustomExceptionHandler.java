package cc.yiueil.handler;

import cc.yiueil.exception.BusinessException;
import cc.yiueil.exception.UnauthorizedException;
import cc.yiueil.util.StringUtils;
import cc.yiueil.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CustomExceptionHandler 自定义异常处理
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:20
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResultVo globalExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ResultVo.error("服务异常", e);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResultVo runtimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        if (StringUtils.isBlank(e.getMessage())) {
            return ResultVo.error("未知运行时异常", e);
        } else {
            return ResultVo.error(e.getMessage(), e);
        }
    }

    @ExceptionHandler({BusinessException.class})
    public ResultVo businessExceptionHandler(BusinessException e) {
        log.error(e.getMessage(), e);
        if (StringUtils.isBlank(e.getMessage())) {
            return ResultVo.error("未知业务功能异常", e);
        } else {
            return ResultVo.error(e.getMessage(), e);
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    public ResultVo methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errorParams = new HashMap<>();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        for (ObjectError objectError : allErrors) {
            errorParams.put(objectError.getObjectName(), objectError.getDefaultMessage());
        }
        return ResultVo.validateFail(errorParams, "入参数校验失败");
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResultVo unauthorizedExceptionHandler(UnauthorizedException e) {
        return ResultVo.unauthorized(e.getMessage());
    }
}