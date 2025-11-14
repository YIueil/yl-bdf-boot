package cc.yiueil.repository;

import cc.yiueil.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * UserRoleRepository
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/10/10 14:34
 */
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    /**
     * 移除角色内的用户
     * @param roleId 角色id
     * @param userIds 用户ids
     */
    void removeUserRoleEntitiesByRoleIdAndUserIdIn(@Param("roleId") Long roleId, @Param("userIds") List<Long> userIds);
}