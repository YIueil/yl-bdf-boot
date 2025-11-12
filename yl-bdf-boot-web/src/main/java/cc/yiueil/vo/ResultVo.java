package cc.yiueil.vo;

import cc.yiueil.enums.ResultCode;
import cc.yiueil.lang.instance.CodeStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * ResultVo 接口返回包装视图类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:23
 * @version 1.0
 */
@Getter
@Schema(title = "标准返回视图对象")
@NoArgsConstructor
public class ResultVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -5220502988169199802L;

    @Schema(name ="应用响应状态码")
    private Integer statusCode;

    @Schema(name ="接口返回状态信息")
    private String statusMsg;

    @Schema(name ="错误请求栈信息(错误时必须)")
    private String stackTrace;

    @Schema(name ="接口提示信息")
    private String tips;

    @Schema(name ="返回结果体")
    private Object body;

    @Schema(name ="新令牌")
    private String token;

    private ResultVo(CodeStatus codeStatus, String tips, String stackTrace, Object body) {
        this.statusCode = codeStatus.getCode();
        this.statusMsg = codeStatus.getMsg();
        this.stackTrace = stackTrace;
        this.tips = tips;
        this.body = body;
    }

    private ResultVo(CodeStatus codeStatus, String tips, String stackTrace, Object body, String token) {
        this.statusCode = codeStatus.getCode();
        this.statusMsg = codeStatus.getMsg();
        this.stackTrace = stackTrace;
        this.tips = tips;
        this.body = body;
        this.token = token;
    }

    public static ResultVo success() {
        return new ResultVo(ResultCode.SUCCESS, null, null, null);
    }

    public static ResultVo success(Object body) {
        return new ResultVo(ResultCode.SUCCESS, null, null, body);
    }

    public static ResultVo success(Object body, String tips) {
        return new ResultVo(ResultCode.SUCCESS, tips, null, body);
    }

    public static Object success(Object body, String tips, String newToken) {
        return new ResultVo(ResultCode.SUCCESS, tips, null, body, newToken);
    }


    public static ResultVo fail(Object body, String tips) {
        return new ResultVo(ResultCode.FAILED, tips, null, body);
    }

    public static ResultVo error(String tips, Exception e) {
        return new ResultVo(ResultCode.ERROR, tips, Arrays.toString(e.getStackTrace()), null);
    }

    public static ResultVo error(Object body, String tips, Exception e) {
        return new ResultVo(ResultCode.ERROR, tips, Arrays.toString(e.getStackTrace()), body);
    }

    public static ResultVo validateFail(Object body, String tips) {
        return new ResultVo(ResultCode.VALIDATE_FAIL, tips, null, body);
    }

    public static ResultVo unauthorized(String tips) {
        return new ResultVo(ResultCode.UNAUTHORIZED, tips, null, null);
    }
}
