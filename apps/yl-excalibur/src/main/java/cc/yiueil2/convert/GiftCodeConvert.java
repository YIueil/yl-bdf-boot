package cc.yiueil2.convert;

import cc.yiueil2.dto.GiftCodeDTO;
import cc.yiueil2.entity.GiftCodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GiftCodeConvert {
    void dtoMapEntity(GiftCodeDTO giftCodeDTO, @MappingTarget GiftCodeEntity giftCodeEntity);

    void entityMapDto(GiftCodeEntity giftCodeEntity, @MappingTarget GiftCodeDTO giftCodeDTO);

    GiftCodeEntity toGiftCodeEntity(GiftCodeDTO giftCodeDTO);

    GiftCodeDTO toGiftCodeDTO(GiftCodeEntity giftCodeEntity);
}
