package cc.yiueil.entity;

import cc.yiueil.lang.instance.BaseEntity;
import cc.yiueil.lang.instance.SortAble;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * MessageEntity 消息实体
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/4/5 18:37
 */
@Getter
@Setter
@Entity
@Table(name = "message", schema = "yl_acc")
@SequenceGenerator(name = "messageEntitySeqGenerator", schema = "yl_acc", sequenceName = "s_message", allocationSize = 1)
public class MessageEntity implements BaseEntity<Long>, SortAble {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageEntitySeqGenerator")
    private Long id;
    private String guid;
    private Long senderId;
    private Long receiverId;
    private String content;
    private String type;
    private String state;
    private Long createUserId;
    private LocalDateTime modifyTime;
    private LocalDateTime createTime;
    private Integer sort;
}
