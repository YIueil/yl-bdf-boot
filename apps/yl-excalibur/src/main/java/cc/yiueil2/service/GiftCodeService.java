package cc.yiueil2.service;

import cc.yiueil2.dto.GiftCodeDTO;
import cc.yiueil2.dto.UserPickGiftCodeDTO;

import java.util.List;

public interface GiftCodeService {
    GiftCodeDTO createGiftCode(GiftCodeDTO giftCodeDTO);

    GiftCodeDTO getGiftCodeByGuid(String guid);

    GiftCodeDTO updateGiftCode(String guid, GiftCodeDTO giftCodeDTO);

    void deleteGiftCode(String guid);

    UserPickGiftCodeDTO claimGiftCode(String guid, String userGuid);

    List<GiftCodeDTO> getAllGiftCodes();
}
