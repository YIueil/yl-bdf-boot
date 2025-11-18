package cc.yiueil.service.impl;

import cc.yiueil.entity.PermissionEntity;
import cc.yiueil.entity.RoleEntity;
import cc.yiueil.entity.UserEntity;
import cc.yiueil.repository.PermissionRepository;
import cc.yiueil.repository.RoleRepository;
import cc.yiueil.repository.UserRepository;
import cc.yiueil.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UserServiceImpl
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/8/1 22:54
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionEntity> getUserPermissions(UserEntity user) {
        return permissionRepository.findPermissionsByUserId(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleEntity> getUserRoles(UserEntity user) {
        return roleRepository.findRolesByUserId(user.getId());
    }
}
