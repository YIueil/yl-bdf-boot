package cc.yiueil.entity;

import cc.yiueil.lang.instance.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * RoleFunctionEntity
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/9/11 21:42
 */
@Getter
@Setter
@Entity
@Table(name = "role_function", schema = "yl_acc")
public class RoleFunctionEntity implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "generator")
    @SequenceGenerator(name = "generator", schema = "yl_acc", sequenceName = "s_role_function", allocationSize = 1)
    private Long id;
    private String guid;
    private Long roleId;
    private Long functionId;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Long createUserId;
}
