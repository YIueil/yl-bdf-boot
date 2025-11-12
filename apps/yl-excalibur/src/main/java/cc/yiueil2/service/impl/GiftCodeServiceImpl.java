package cc.yiueil2.service.impl;

import cc.yiueil2.convert.GiftCodeConvert;
import cc.yiueil2.convert.UserPickGiftCodeConvert;
import cc.yiueil.data.impl.JpaBaseDao;
import cc.yiueil2.dto.GiftCodeDTO;
import cc.yiueil2.dto.UserPickGiftCodeDTO;
import cc.yiueil2.entity.GiftCodeEntity;
import cc.yiueil2.entity.UserPickGiftCodeEntity;
import cc.yiueil.exception.BusinessException;
import cc.yiueil2.repository.GiftCodeRepository;
import cc.yiueil2.repository.UserPickGiftCodeRepository;
import cc.yiueil2.service.GiftCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftCodeServiceImpl implements GiftCodeService {
    @Autowired
    GiftCodeConvert giftCodeConvert;

    @Autowired
    UserPickGiftCodeConvert userPickGiftCodeConvert;

    @Autowired
    JpaBaseDao baseDao;

    @Autowired
    GiftCodeRepository giftCodeRepository;

    @Autowired
    UserPickGiftCodeRepository userPickGiftCodeRepository;

    @Override
    @Transactional
    public GiftCodeDTO createGiftCode(GiftCodeDTO giftCodeDTO) {
        GiftCodeEntity giftCodeEntity = giftCodeConvert.toGiftCodeEntity(giftCodeDTO);
        return giftCodeConvert.toGiftCodeDTO(baseDao.save(giftCodeEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCodeDTO getGiftCodeByGuid(String guid) {
        return giftCodeConvert.toGiftCodeDTO(baseDao.findByGuid(GiftCodeEntity.class, guid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    @Transactional
    public GiftCodeDTO updateGiftCode(String guid, GiftCodeDTO giftCodeDTO) {
        GiftCodeEntity giftCodeEntity = baseDao.findByGuid(GiftCodeEntity.class, guid).orElseThrow(EntityNotFoundException::new);
        giftCodeConvert.dtoMapEntity(giftCodeDTO, giftCodeEntity);
        return giftCodeConvert.toGiftCodeDTO(baseDao.save(giftCodeEntity));
    }

    @Override
    @Transactional
    public void deleteGiftCode(String guid) {
        baseDao.deleteByGuid(GiftCodeEntity.class, guid);
    }

    @Override
    @Transactional
    public UserPickGiftCodeDTO claimGiftCode(String guid, String userGuid) {
        // 判断是否已经领取过
        if (userPickGiftCodeRepository.existsUserPickGiftCodeEntitiesByFkGiftCodeGuidAndFkUserGuid(guid, userGuid)) {
            throw new BusinessException("已经领取过");
        }
        UserPickGiftCodeEntity userPickGiftCodeEntity = new UserPickGiftCodeEntity();
        userPickGiftCodeEntity.setFkUserGuid(userGuid);
        userPickGiftCodeEntity.setFkGiftCodeGuid(guid);
        userPickGiftCodeEntity.setPickTime(LocalDateTime.now());
        return userPickGiftCodeConvert.toDto(baseDao.save(userPickGiftCodeEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCodeDTO> getAllGiftCodes() {
        return giftCodeRepository
                .findAll()
                .stream()
                .map(giftCodeEntity -> giftCodeConvert.toGiftCodeDTO(giftCodeEntity))
                .collect(Collectors.toList());
    }
}
