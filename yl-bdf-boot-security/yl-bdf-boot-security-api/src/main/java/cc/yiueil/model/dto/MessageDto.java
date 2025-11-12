package cc.yiueil.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * MessageDto 消息数据传输对象
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/4/5 18:39
 */
@Getter
@Setter
@ToString
@Schema(name = "消息数据传输对象")
public class MessageDto {
    private Long id;
    private String guid;
    @Schema(name = "发送人id")
    private Long senderId;
    @Schema(name = "接收人id")
    private Long receiverId;
    @Schema(name = "类型")
    private String type;
    @Schema(name = "消息创建时间")
    private LocalDateTime createTime;
    @Schema(name = "排序")
    private Integer sort;
}
