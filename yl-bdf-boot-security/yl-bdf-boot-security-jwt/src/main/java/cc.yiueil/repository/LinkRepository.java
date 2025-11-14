package cc.yiueil.repository;

import cc.yiueil.entity.LinkEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * LinkRepository
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/3/2 23:05
 */
public interface LinkRepository extends CrudRepository<LinkEntity, Long> {

    /**
     * 通过用户id查询link集合
     * @param userId 用户id
     * @return 链接集合
     */
    List<LinkEntity> findLinksByUserId(Long userId);
}