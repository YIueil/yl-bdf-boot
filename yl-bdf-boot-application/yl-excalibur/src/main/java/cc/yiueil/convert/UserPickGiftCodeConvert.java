package cc.yiueil.convert;

import cc.yiueil.dto.UserPickGiftCodeDTO;
import cc.yiueil.entity.UserPickGiftCodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserPickGiftCodeConvert {
    void dtoMapEntity(UserPickGiftCodeDTO dto, @MappingTarget UserPickGiftCodeEntity entity);

    void entityMapDto(UserPickGiftCodeEntity userPickGiftCodeEntity, @MappingTarget UserPickGiftCodeDTO userPickGiftCodeDTO);

    UserPickGiftCodeDTO toDto(UserPickGiftCodeEntity userPickGiftCodeEntity);

    UserPickGiftCodeEntity toEntity(UserPickGiftCodeDTO dto);
}
