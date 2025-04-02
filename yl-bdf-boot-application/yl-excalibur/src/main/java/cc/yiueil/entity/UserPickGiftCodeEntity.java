package cc.yiueil.entity;

import cc.yiueil.lang.instance.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_user_pick_gift_code")
public class UserPickGiftCodeEntity implements BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "generator")
    @SequenceGenerator(name = "userPickGiftCodeEntityGenerator", sequenceName = "s_user_pick_gift_code", allocationSize = 1)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "fk_user_guid")
    private String fkUserGuid;

    @Column(name = "fk_gift_code_guid")
    private String fkGiftCodeGuid;

    @Column(name = "pick_time")
    private LocalDateTime pickTime;

    @Column(name = "pick_result")
    private Boolean pickResult;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "modify_time")
    private LocalDateTime modifyTime;
}
