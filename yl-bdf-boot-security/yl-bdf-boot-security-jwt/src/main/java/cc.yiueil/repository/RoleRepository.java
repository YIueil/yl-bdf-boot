package cc.yiueil.repository;

import cc.yiueil.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * RoleRepository
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/8/1 23:27
 */
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    /**
     * 根据用户id查询所有角色
     * @param userId 用户id
     * @return 角色集合
     */
    @Query("select r from RoleEntity r left join UserRoleEntity ur on ur.roleId = r.id where ur.userId = :userId")
    List<RoleEntity> findRolesByUserId(@Param(value = "userId") Long userId);
}
