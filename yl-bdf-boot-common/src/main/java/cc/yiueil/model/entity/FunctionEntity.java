package cc.yiueil.model.entity;

import cc.yiueil.lang.instance.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 应用功能实体
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/5/30 22:10
 */
@Getter
@Setter
@Entity
@Table(name = "function", schema = "yl_acc")
public class FunctionEntity implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "generator")
    @SequenceGenerator(name = "generator", schema = "yl_acc", sequenceName = "s_function", allocationSize = 1)
    private Long id;
    private String guid;
    private String name;
    private String rightName;
    private String description;
    private String url;
    private String type;
    private String method;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Long createUserId;
    private Long parentId;
    private Long applicationId;
}