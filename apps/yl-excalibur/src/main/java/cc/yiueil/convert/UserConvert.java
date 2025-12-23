package cc.yiueil.convert;

import cc.yiueil.dto.UserDTO;
import cc.yiueil.dto.UserLoginDTO;
import cc.yiueil.dto.UserRegDTO;
import cc.yiueil.entity.AppUserEntity;
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
