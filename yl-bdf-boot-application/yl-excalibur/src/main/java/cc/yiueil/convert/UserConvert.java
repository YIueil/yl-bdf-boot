package cc.yiueil.convert;

import cc.yiueil.dto.UserDTO;
import cc.yiueil.dto.UserLoginDTO;
import cc.yiueil.dto.UserRegDTO;
import cc.yiueil.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserConvert {
    @Mapping(target = "guid", ignore = true)
    void dtoMapEntity(UserDTO userDTO, @MappingTarget UserEntity userEntity);

    @Mapping(target = "guid", ignore = true)
    void entityMapDto(UserEntity userEntity, @MappingTarget UserDTO userDTO);

    UserEntity toUserEntity(UserDTO userDTO);

    UserEntity toUserEntity(UserLoginDTO userLoginDTO);

    UserEntity toUserEntity(UserRegDTO userRegDTO);

    UserDTO toUserDTO(UserEntity userEntity);
}
