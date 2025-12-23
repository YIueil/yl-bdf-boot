package cc.yiueil.service.impl;

import cc.yiueil.convert.UserConvert;
import cc.yiueil.data.impl.JpaBaseDao;
import cc.yiueil.dto.UserDTO;
import cc.yiueil.dto.UserRegDTO;
import cc.yiueil.entity.AppUserEntity;
import cc.yiueil.entity.UserSignEntity;
import cc.yiueil.exception.BusinessException;
import cc.yiueil.repository.UserSignRepository;
import cc.yiueil.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier(value = "jpaBaseDao")
    JpaBaseDao baseDao;

    @Autowired
    UserSignRepository userSignRepository;

    @Autowired
    UserConvert userConvert;

    @Override
    @Transactional
    public UserDTO createUser(UserRegDTO userDto) {
        AppUserEntity appUserEntity = userConvert.toUserEntity(userDto);
        appUserEntity.setBalance(0L);
        appUserEntity.setSignDays(0L);
        return userConvert.toUserDTO(baseDao.save(appUserEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByGuid(String guid) {
        AppUserEntity appUserEntity = baseDao.findByGuid(AppUserEntity.class, guid).orElseThrow(EntityNotFoundException::new);
        return userConvert.toUserDTO(appUserEntity);
    }

    @Override
    @Transactional
    public void updateUser(String guid, UserDTO userDTO) {
        AppUserEntity appUserEntity = baseDao.findByGuid(AppUserEntity.class, guid).orElseThrow(EntityNotFoundException::new);
        userConvert.dtoMapEntity(userDTO, appUserEntity);
        // 最后保存覆盖后的 entity
        baseDao.save(appUserEntity);
    }

    @Override
    @Transactional
    public void deleteUser(String guid) {
        baseDao.deleteByGuid(AppUserEntity.class, guid);
    }

    @Override
    @Transactional
    public void incrSignDays(String guid) {
        userSignRepository.findFirstByFkUserGuid(guid).ifPresent(userSignEntity -> {
            throw new BusinessException("今日已签到");
        });
        AppUserEntity appUserEntity = baseDao.findByGuid(AppUserEntity.class, guid).orElseThrow(EntityNotFoundException::new);
        appUserEntity.setSignDays(appUserEntity.getSignDays() + 1);
        baseDao.save(appUserEntity);
        UserSignEntity userSignEntity = getUserSignEntity(appUserEntity);
        baseDao.save(userSignEntity);
    }

    @NotNull
    private static UserSignEntity getUserSignEntity(AppUserEntity appUserEntity) {
        UserSignEntity userSignEntity = new UserSignEntity();
        userSignEntity.setFkUserGuid(appUserEntity.getGuid());
        userSignEntity.setSignInDate(LocalDate.now());
        userSignEntity.setSignInTime(LocalDateTime.now());
        return userSignEntity;
    }
}
