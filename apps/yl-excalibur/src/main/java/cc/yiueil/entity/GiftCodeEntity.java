package cc.yiueil.entity;

import cc.yiueil.lang.instance.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "t_gift_code")
public class GiftCodeEntity implements BaseEntity<Long>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "generator")
    @SequenceGenerator(name = "giftCodeEntityGenerator", sequenceName = "s_gift_code", allocationSize = 1)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "code")
    private String code;

    @Column(name = "expire_time")
    private LocalDateTime expireTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "modify_time")
    private LocalDateTime modifyTime;
}
