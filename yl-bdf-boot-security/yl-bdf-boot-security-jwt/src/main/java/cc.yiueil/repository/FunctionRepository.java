package cc.yiueil.repository;

import cc.yiueil.entity.FunctionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * FunctionRepository
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/9/11 21:37
 */
public interface FunctionRepository extends CrudRepository<FunctionEntity, Long> {
    /**
     * 查询functionEntity通过项目id
     *
     * @param applicationId 应用id
     * @return 方法集合
     */
    List<FunctionEntity> findFunctionEntitiesByApplicationId(Long applicationId);
}