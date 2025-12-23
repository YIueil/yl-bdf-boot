package cc.yiueil.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPickGiftCodeDTO {
    private String guid;

    private String fkUserGuid;

    private String fkGiftCodeGuid;

    private LocalDateTime pickTime;

}
