package cc.yiueil.model.entity;

import cc.yiueil.lang.instance.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 应用实体
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:10
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "application", schema = "yl_acc")
public class ApplicationEntity implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "generator")
    @SequenceGenerator(name = "generator", schema = "yl_acc", sequenceName = "s_application", allocationSize = 1)
    private Long id;
    private String guid;
    private String name;
    private String description;
    private String url;
    private String iconUrl;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Long createUserId;
}