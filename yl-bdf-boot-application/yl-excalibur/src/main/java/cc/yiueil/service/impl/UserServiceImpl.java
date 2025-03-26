package cc.yiueil.service.impl;

import cc.yiueil.convert.UserConvert;
import cc.yiueil.data.impl.JpaBaseDao;
import cc.yiueil.dto.UserDTO;
import cc.yiueil.dto.UserRegDTO;
import cc.yiueil.entity.UserEntity;
import cc.yiueil.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    JpaBaseDao baseDao;

    @Autowired
    UserConvert userConvert;

    @Override
    @Transactional
    public UserDTO createUser(UserRegDTO userDto) {
        UserEntity userEntity = userConvert.toUserEntity(userDto);
        userEntity.setBalance(0L);
        userEntity.setSignDays(0L);
        return userConvert.toUserDTO(baseDao.save(userEntity));
    }

    @Override
    @Transactional
    public UserDTO getUserByGuid(String guid) {
        UserEntity userEntity = baseDao.findByGuid(UserEntity.class, guid).orElseThrow(EntityNotFoundException::new);
        return userConvert.toUserDTO(userEntity);
    }

    @Override
    @Transactional
    public void updateUser(String guid, UserDTO userDTO) {
        UserEntity userEntity = baseDao.findByGuid(UserEntity.class, guid).orElseThrow(EntityNotFoundException::new);
        userConvert.dtoMapEntity(userDTO, userEntity);
        // 最后保存覆盖后的 entity
        baseDao.save(userEntity);
    }
}
