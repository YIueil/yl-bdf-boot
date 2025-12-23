package cc.yiueil.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable {
    private String loginName;
    private String password;
}
