package cc.yiueil.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * LinkDto 链接数据传输对象
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/3/2 22:42
 */
@Getter
@Setter
@ToString
@Schema(name = "链接数据传输对象")
public class LinkDto {
    private Long id;
    private String guid;
    @Schema(name = "链接名称")
    private String name;
    @Schema(name = "链接图标")
    private String iconUrl;
    @Schema(name = "链接地址")
    private String url;
    @Schema(name = "链接用户")
    private Long userId;
}
