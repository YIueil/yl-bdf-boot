package cc.yiueil.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class GiftCodeDTO implements Serializable {
    private String guid;
    private String code;
    private LocalDateTime expireTime;
}
