package cc.yiueil.repository;

import cc.yiueil.entity.RoleFunctionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * RoleFunctionRepository 角色功能 repository
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/10/9 17:53
 */
public interface RoleFunctionRepository extends CrudRepository<RoleFunctionEntity, Long> {
    /**
     * 通过functionId删除已有的角色功能关系
     * @param functionId 功能id
     */
    void removeRoleFunctionEntitiesByFunctionId(@Param("functionId") Long functionId);
}