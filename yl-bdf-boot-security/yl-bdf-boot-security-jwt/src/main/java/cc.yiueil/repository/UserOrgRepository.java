package cc.yiueil.repository;

import cc.yiueil.entity.UserOrgEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * UserOrgEntityRepository
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/10/10 11:59
 */
public interface UserOrgRepository extends CrudRepository<UserOrgEntity, Long> {
    /**
     * 删除用户机构关联
     * @param userIds 用户ids
     * @param orgId 机构id
     */
    void removeUserOrgEntitiesByOrgIdAndUserIdIn(@Param("orgId") Long orgId, @Param("userIds") List<Long> userIds);
}