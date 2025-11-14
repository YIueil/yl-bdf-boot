package cc.yiueil.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * UserLoginDto 用户登陆对象
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/9/9 23:42
 */
@Getter
@Setter
@ToString
@Schema(name = "用户登陆对象")
public class UserLoginDto {
    @Schema(name = "登陆名")
    private String loginName;
    @Schema(name = "密码")
    private String password;
    @Schema(name = "联系电话")
    private String phoneNumber;
    @Schema(name = "邮箱")
    private String email;
}
