package cc.yiueil.model.entity;

import cc.yiueil.lang.instance.BaseEntity;
import cc.yiueil.lang.instance.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 21:13
 * @version 1.0 用户实体
 * @see cc.yiueil.model.dto.UserDto
 * @see cc.yiueil.model.vo.UserVo
 */
@Getter
@Setter
@Entity
@Table(name = "user", schema = "yl_acc")
public class UserEntity implements User<Long>, BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "generator")
    @SequenceGenerator(name = "generator", schema = "yl_acc", sequenceName = "s_user", allocationSize = 1)
    private Long id;
    private String guid;
    @Column(unique = true)
    private String userName;
    private String loginName;
    private String password;
    private String phoneNumber;
    private String email;
    @Column(columnDefinition = "TEXT")
    private String avatarUrl;
    private String signature;
    private String state;
    private Long createUserId;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private String extendProperty1;
    private String extendProperty2;
    private String extendProperty3;
    private String extendProperty4;
}
