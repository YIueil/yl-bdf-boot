package cc.yiueil.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * UserDto 用户 DTO
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/6/22 15:30
 * @see cc.yiueil.model.entity.UserEntity
 * @see cc.yiueil.model.vo.UserVo
 */
@Getter
@Setter
@ToString
@Schema(name = "用户数据传输对象")
public class UserDto implements Serializable {
    private Long id;
    private String guid;
    @Schema(name = "用户名")
    private String userName;
    @Schema(name = "登陆名")
    private String loginName;
    @Schema(name = "密码")
    private String password;
    @Schema(name = "联系电话")
    private String phoneNumber;
    @Schema(name = "邮箱")
    private String email;
    @Schema(name = "头像地址")
    private String avatarUrl;
    @Schema(name = "个性签名")
    private String signature;
    @Schema(name = "用户状态")
    private String state;
    @Schema(name = "创建用户id")
    private Long createUserId;
    @Schema(name = "创建时间")
    private LocalDateTime createTime;
    @Schema(name = "修改时间")
    private LocalDateTime modifyTime;
    @Schema(name = "扩展1")
    private String extendProperty1;
    @Schema(name = "扩展2")
    private String extendProperty2;
    @Schema(name = "扩展3")
    private String extendProperty3;
    @Schema(name = "扩展4")
    private String extendProperty4;
}
