package cc.yiueil2.entity;

import cc.yiueil.lang.instance.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "t_user_sign")
public class UserSignEntity implements BaseEntity<Long>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "generator")
    @SequenceGenerator(name = "userSignEntityGenerator", sequenceName = "s_user_sign", allocationSize = 1)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "fk_user_guid")
    private String fkUserGuid;

    @Column(name = "sign_in_date")
    private LocalDate signInDate;

    @Column(name = "sign_in_time")
    private LocalDateTime signInTime;

    @Column(name = "location")
    private String location;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "modify_time")
    private LocalDateTime modifyTime;
}
