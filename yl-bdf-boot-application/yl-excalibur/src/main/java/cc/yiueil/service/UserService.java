package cc.yiueil.service;

import cc.yiueil.dto.UserDTO;
import cc.yiueil.dto.UserRegDTO;

public interface UserService {

    UserDTO createUser(UserRegDTO userDto);

    UserDTO getUserByGuid(String guid);

    void updateUser(String guid, UserDTO userDTO);
}
