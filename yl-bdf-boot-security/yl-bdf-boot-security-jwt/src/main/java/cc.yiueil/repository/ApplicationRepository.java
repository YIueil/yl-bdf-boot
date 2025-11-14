package cc.yiueil.repository;

import cc.yiueil.entity.ApplicationEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * ApplicationRepository
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/9/10 4:06
 */
public interface ApplicationRepository extends CrudRepository<ApplicationEntity, Long> {
}