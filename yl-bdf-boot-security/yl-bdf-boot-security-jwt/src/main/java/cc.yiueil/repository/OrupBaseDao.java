package cc.yiueil.repository;

import cc.yiueil.context.RequestContextThreadLocal;
import cc.yiueil.data.impl.JpaBaseDao;
import cc.yiueil.dto.UserDto;
import cc.yiueil.lang.instance.HasOwn;
import org.springframework.stereotype.Service;


/**
 * OrupBaseDao orup专用的baseDao, 提供对于用户信息的注入功能
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/4/4 1:50
 */
@Service
public class OrupBaseDao extends JpaBaseDao {
    @Override
    public void generatorCreateUser(Object entity) {
        if (entity instanceof HasOwn) {
            HasOwn ownEntity = (HasOwn) entity;
            if (ownEntity.getCreateUserId() == null) {
                UserDto user = RequestContextThreadLocal.getUser();
                if (user == null) {
                    return;
                }
                ownEntity.setCreateUserId(user.getId());
            }
        }
    }
}
