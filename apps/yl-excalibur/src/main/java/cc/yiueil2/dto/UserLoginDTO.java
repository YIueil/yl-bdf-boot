package cc.yiueil2.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable {
    private String loginName;
    private String password;
}
