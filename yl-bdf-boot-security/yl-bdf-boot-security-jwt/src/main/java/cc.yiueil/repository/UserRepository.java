package cc.yiueil.repository;

import cc.yiueil.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * UserRepository
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:22
 * @version 1.0
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    /**
     * 根据登录名获取用户
     * @param loginName 登陆名
     * @return 用户
     */
    Optional<UserEntity> findUserEntityByLoginName(String loginName);
}