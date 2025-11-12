package cc.yiueil2.service.impl;

import cc.yiueil2.convert.UserConvert;
import cc.yiueil.data.impl.JpaBaseDao;
import cc.yiueil2.dto.UserDTO;
import cc.yiueil2.dto.UserRegDTO;
import cc.yiueil2.entity.UserEntity;
import cc.yiueil2.entity.UserSignEntity;
import cc.yiueil.exception.BusinessException;
import cc.yiueil2.repository.UserSignRepository;
import cc.yiueil2.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    JpaBaseDao baseDao;

    @Autowired
    UserSignRepository userSignRepository;

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
    @Transactional(readOnly = true)
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

    @Override
    @Transactional
    public void deleteUser(String guid) {
        baseDao.deleteByGuid(UserEntity.class, guid);
    }

    @Override
    @Transactional
    public void incrSignDays(String guid) {
        userSignRepository.findFirstByFkUserGuid(guid).ifPresent(userSignEntity -> {
            throw new BusinessException("今日已签到");
        });
        UserEntity userEntity = baseDao.findByGuid(UserEntity.class, guid).orElseThrow(EntityNotFoundException::new);
        userEntity.setSignDays(userEntity.getSignDays() + 1);
        baseDao.save(userEntity);
        UserSignEntity userSignEntity = getUserSignEntity(userEntity);
        baseDao.save(userSignEntity);
    }

    @NotNull
    private static UserSignEntity getUserSignEntity(UserEntity userEntity) {
        UserSignEntity userSignEntity = new UserSignEntity();
        userSignEntity.setFkUserGuid(userEntity.getGuid());
        userSignEntity.setSignInDate(LocalDate.now());
        userSignEntity.setSignInTime(LocalDateTime.now());
        return userSignEntity;
    }
}
