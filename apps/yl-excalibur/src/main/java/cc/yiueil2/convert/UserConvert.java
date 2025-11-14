package cc.yiueil2.convert;

import cc.yiueil2.dto.UserDTO;
import cc.yiueil2.dto.UserLoginDTO;
import cc.yiueil2.dto.UserRegDTO;
import cc.yiueil2.entity.AppUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserConvert {
    void dtoMapEntity(UserDTO userDTO, @MappingTarget AppUserEntity appUserEntity);

    void entityMapDto(AppUserEntity appUserEntity, @MappingTarget UserDTO userDTO);

    AppUserEntity toUserEntity(UserDTO userDTO);

    AppUserEntity toUserEntity(UserLoginDTO userLoginDTO);

    AppUserEntity toUserEntity(UserRegDTO userRegDTO);

    UserDTO toUserDTO(AppUserEntity appUserEntity);
}
