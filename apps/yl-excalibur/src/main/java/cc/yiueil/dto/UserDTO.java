package cc.yiueil.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDTO implements Serializable {
    private String guid;
    private String username;
    private String email;
    private Long balance;
    private Long signDays;
    private LocalDateTime createTime;
}
