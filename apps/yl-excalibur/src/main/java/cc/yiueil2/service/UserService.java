package cc.yiueil2.service;

import cc.yiueil2.dto.UserDTO;
import cc.yiueil2.dto.UserRegDTO;

public interface UserService {
    UserDTO createUser(UserRegDTO userDto);

    UserDTO getUserByGuid(String guid);

    void updateUser(String guid, UserDTO userDTO);

    void deleteUser(String guid);

    void incrSignDays(String guid);
}
