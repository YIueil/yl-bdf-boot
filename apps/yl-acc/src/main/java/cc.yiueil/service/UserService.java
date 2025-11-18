package cc.yiueil.service;

import cc.yiueil.entity.PermissionEntity;
import cc.yiueil.entity.RoleEntity;
import cc.yiueil.entity.UserEntity;

import java.util.List;

/**
 * UserService 用户操作接口
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/8/1 22:59
 */
public interface UserService {
    /**
     * 获取所有用户
     * @return 用户集合
     */
    Iterable<UserEntity> getAllUser();

    /**
     * 获取用户所有权限
     * @param user 操作用户
     * @return 权限集合
     */
    List<PermissionEntity> getUserPermissions(UserEntity user);

    /**
     * 获取用户所有角色
     * @param user 操作用户
     * @return 角色集合
     */
    List<RoleEntity> getUserRoles(UserEntity user);
}
