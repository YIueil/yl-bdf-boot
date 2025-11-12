package cc.yiueil2.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPickGiftCodeDTO {
    private String guid;

    private String fkUserGuid;

    private String fkGiftCodeGuid;

    private LocalDateTime pickTime;

}
