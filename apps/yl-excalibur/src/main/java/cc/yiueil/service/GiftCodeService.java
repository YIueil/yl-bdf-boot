package cc.yiueil.service;

import cc.yiueil.dto.GiftCodeDTO;
import cc.yiueil.dto.UserPickGiftCodeDTO;

import java.util.List;

public interface GiftCodeService {
    GiftCodeDTO createGiftCode(GiftCodeDTO giftCodeDTO);

    GiftCodeDTO getGiftCodeByGuid(String guid);

    GiftCodeDTO updateGiftCode(String guid, GiftCodeDTO giftCodeDTO);

    void deleteGiftCode(String guid);

    UserPickGiftCodeDTO claimGiftCode(String guid, String userGuid);

    List<GiftCodeDTO> getAllGiftCodes();
}
