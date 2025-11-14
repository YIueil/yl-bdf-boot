package cc.yiueil.entity;

import cc.yiueil.lang.instance.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * LinkEntity 用户链接
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/2/26 23:15
 */
@Getter
@Setter
@Entity
@Table(name = "link", schema = "yl_acc")
public class LinkEntity implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "generator")
    @SequenceGenerator(name = "generator", schema = "yl_acc", sequenceName = "s_link", allocationSize = 1)
    private Long id;
    private String guid;
    private String name;
    private String iconUrl;
    private String url;
    private Long userId;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Long createUserId;
}
