package cc.yiueil2.convert;

import cc.yiueil2.dto.UserDTO;
import cc.yiueil2.dto.UserLoginDTO;
import cc.yiueil2.dto.UserRegDTO;
import cc.yiueil2.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserConvert {
    void dtoMapEntity(UserDTO userDTO, @MappingTarget UserEntity userEntity);

    void entityMapDto(UserEntity userEntity, @MappingTarget UserDTO userDTO);

    UserEntity toUserEntity(UserDTO userDTO);

    UserEntity toUserEntity(UserLoginDTO userLoginDTO);

    UserEntity toUserEntity(UserRegDTO userRegDTO);

    UserDTO toUserDTO(UserEntity userEntity);
}
