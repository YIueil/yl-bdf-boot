package cc.yiueil2.convert;

import cc.yiueil2.dto.UserPickGiftCodeDTO;
import cc.yiueil2.entity.UserPickGiftCodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserPickGiftCodeConvert {
    void dtoMapEntity(UserPickGiftCodeDTO dto, @MappingTarget UserPickGiftCodeEntity entity);

    void entityMapDto(UserPickGiftCodeEntity userPickGiftCodeEntity, @MappingTarget UserPickGiftCodeDTO userPickGiftCodeDTO);

    UserPickGiftCodeDTO toDto(UserPickGiftCodeEntity userPickGiftCodeEntity);

    UserPickGiftCodeEntity toEntity(UserPickGiftCodeDTO dto);
}
