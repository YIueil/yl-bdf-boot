package cc.yiueil.controller;

import cc.yiueil.enums.ResultCode;
import cc.yiueil.general.RestUrl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * VersionController
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/9/14 14:11
 */
@Tag(name = "应用版本")
@RestController
@RequestMapping(value = RestUrl.BASE_PATH + "/version")
public class VersionController implements BaseController{
    @Operation(description = "获取状态码相关信息")
    @GetMapping(value="getStatusCode")
    public String statusCode(){
        return success(Arrays.stream(ResultCode.values()).map(ResultCode::toString).collect(Collectors.toList()));
    }

    @Operation(description = "错误测试")
    @GetMapping(value = "errorTest")
    public String errorTest(){
        throw new RuntimeException("一个示例的运行时错误");
    }

}
