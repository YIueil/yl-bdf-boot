package cc.yiueil.entity;

import cc.yiueil.lang.instance.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户角色关联实体
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:14
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "user_role", schema = "yl_acc")
@SequenceGenerator(name = "userRoleEntitySeqGenerator", schema = "yl_acc", sequenceName = "s_user_role", allocationSize = 1)
public class UserRoleEntity implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userRoleEntitySeqGenerator")
    private Long id;
    private String guid;
    private Long userId;
    private Long roleId;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Long createUserId;
}
