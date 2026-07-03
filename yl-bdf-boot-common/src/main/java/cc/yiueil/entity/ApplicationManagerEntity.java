package cc.yiueil.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 应用管理员实体
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:10
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "application_manager", schema = "yl_acc")
@SequenceGenerator(name = "applicationManagerEntitySeqGenerator", schema = "yl_acc", sequenceName = "s_application_manager", allocationSize = 1)
public class ApplicationManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "applicationManagerEntitySeqGenerator")
    private Long id;
    private String guid;
    private Long applicationId;
    private Long managerId;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Long createUserId;
}
