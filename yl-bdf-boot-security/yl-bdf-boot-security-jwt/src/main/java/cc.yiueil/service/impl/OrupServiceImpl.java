package cc.yiueil.service.impl;

import cc.yiueil.context.RequestContextThreadLocal;
import cc.yiueil.data.impl.JpaBaseDao;
import cc.yiueil.dto.*;
import cc.yiueil.entity.*;
import cc.yiueil.enums.UserStateEnum;
import cc.yiueil.exception.BusinessException;
import cc.yiueil.exception.PrimaryKeyMissingException;
import cc.yiueil.exception.ResourceNotFoundException;
import cc.yiueil.query.instance.DynamicQueryInst;
import cc.yiueil.repository.*;
import cc.yiueil.service.OrupService;
import cc.yiueil.service.SearchService;
import cc.yiueil.util.BeanUtils;
import cc.yiueil.util.ObjectUtils;
import cc.yiueil.util.PasswordUtils;
import cc.yiueil.vo.PasswordStrengthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 核心ORUP服务
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/5/30 22:14
 */
@Service
public class OrupServiceImpl implements OrupService {
    @Autowired
    @Qualifier("orupBaseDao")
    JpaBaseDao baseDao;

    @Autowired
    SearchService searchService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    FunctionRepository functionRepository;

    @Autowired
    RoleFunctionRepository roleFunctionRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserOrgRepository userOrgRepository;

    @Autowired
    OrgRepository orgRepository;

    @Autowired
    LinkRepository linkRepository;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UserDto registerUser(UserDto registerUser) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(registerUser.getUserName());
        userEntity.setLoginName(registerUser.getLoginName());
        userEntity.setPassword(registerUser.getPassword());
        userEntity.setPhoneNumber(registerUser.getPhoneNumber());
        userEntity.setExtendProperty1(registerUser.getExtendProperty1());
        userEntity.setExtendProperty2(registerUser.getExtendProperty2());
        userEntity.setExtendProperty3(registerUser.getExtendProperty3());
        userEntity.setExtendProperty4(registerUser.getExtendProperty4());
        userEntity.setCreateTime(LocalDateTime.now());
        return BeanUtils.copyProperties(baseDao.save(userEntity), new UserDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void passwordChange(String oldPassword, String newPassword) {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        UserEntity userEntity = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
        baseDao.findById(UserEntity.class, userEntity.getId()).ifPresent(user -> {
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                baseDao.save(user);
            } else {
                throw new BusinessException("旧密码和原密码不符");
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUser(Long userId) {
        return BeanUtils.copyProperties(
                baseDao.findById(UserEntity.class, userId).orElseThrow(() -> new ResourceNotFoundException("用户不存在")),
                new UserDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UserDto addUser(UserDto userDto) {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        UserEntity userEntity = BeanUtils.copyProperties(userDto, new UserEntity());
        userEntity.setCreateUserId(currentUser.getId());
        baseDao.save(userEntity);
        return BeanUtils.copyProperties(userEntity, new UserDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UserDto modifyUser(UserDto userDto) {
        UserEntity userEntity = userRepository.findById(userDto.getId()).orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
        UserEntity modifyUser = BeanUtils.copyProperties(userDto, userEntity, true);
        baseDao.save(modifyUser);
        return BeanUtils.copyProperties(modifyUser, new UserDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delUser(Long userId) {
        baseDao.deleteById(UserEntity.class, userId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delUserByIds(List<Long> userIds) {
        userRepository.deleteAllById(userIds);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void suspendUser(Long userId) {
        baseDao.findById(UserEntity.class, userId).ifPresent(userEntity -> {
            userEntity.setState(UserStateEnum.SUSPEND.getState());
            baseDao.save(userEntity);
        });
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void suspendUserByIds(List<Long> userIds) {
        Iterable<UserEntity> userEntityIterable = userRepository.findAllById(userIds);
        for (UserEntity userEntity : userEntityIterable) {
            userEntity.setState(UserStateEnum.SUSPEND.getState());
        }
        userRepository.saveAll(userEntityIterable);
    }

    @Override
    public UserDto findUserById(Long id) {
        return BeanUtils.copyProperties(
                userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("用户不存在")),
                new UserDto()
        );
    }

    @Override
    public UserDto findUserByLoginName(String loginName) {
        return BeanUtils.copyProperties(
                userRepository.findUserEntityByLoginName(loginName).orElseThrow(() -> new ResourceNotFoundException("用户不存在")),
                new UserDto()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDto> getUserPermissions() {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        List<PermissionEntity> permissionEntityList = permissionRepository.findPermissionsByUserId(currentUser.getId());
        return permissionEntityList.stream()
                .map(permissionEntity -> BeanUtils.copyProperties(permissionEntity, new PermissionDto()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getUserRoles() {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        List<RoleEntity> roleEntityList = roleRepository.findRolesByUserId(currentUser.getId());
        return roleEntityList.stream()
                .map(roleEntity -> BeanUtils.copyProperties(roleEntity, new RoleDto()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDto> getAllApplications() {
        Iterable<ApplicationEntity> applicationEntities = applicationRepository.findAll();
        return StreamSupport.stream(applicationEntities.spliterator(), false)
                .map(applicationEntity -> BeanUtils.copyProperties(applicationEntity, new ApplicationDto()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ApplicationDto addApplication(ApplicationDto applicationDto) {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        ApplicationEntity newApplicationEntity = baseDao.save(BeanUtils.copyProperties(applicationDto, new ApplicationEntity()));
        newApplicationEntity.setCreateUserId(currentUser.getId());
        return BeanUtils.copyProperties(newApplicationEntity, new ApplicationDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ApplicationDto modifyApplication(ApplicationDto applicationDto) {
        ApplicationEntity applicationEntity = baseDao.save(BeanUtils.copyProperties(applicationDto, new ApplicationEntity()));
        return BeanUtils.copyProperties(applicationEntity, new ApplicationDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void modifyApplicationStatus(Long applicationId, String status) {
        ApplicationEntity applicationEntity = baseDao.findById(ApplicationEntity.class, applicationId).orElseThrow(() -> new ResourceNotFoundException("应用不存在"));
        applicationEntity.setStatus(status);
        baseDao.save(applicationEntity);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addApplicationManager(Long applicationId, List<Long> userIds) {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        for (Long userId : userIds) {
            ApplicationManagerEntity applicationManagerEntity = new ApplicationManagerEntity();
            applicationManagerEntity.setApplicationId(applicationId);
            applicationManagerEntity.setManagerId(userId);
            applicationManagerEntity.setCreateUserId(currentUser.getId());
            baseDao.save(applicationManagerEntity);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public FunctionDto addApplicationFunction(FunctionDto functionDto) {
        FunctionEntity functionEntity = BeanUtils.copyProperties(functionDto, new FunctionEntity());
        FunctionEntity newFunctionEntity = baseDao.save(functionEntity);
        return BeanUtils.copyProperties(newFunctionEntity, new FunctionDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public FunctionDto modifyFunction(FunctionDto functionDto) {
        if (functionDto.getId() == null) {
            throw new RuntimeException("修改接口传入的实体需要具有id");
        }
        FunctionEntity functionEntity = functionRepository.findById(functionDto.getId()).orElseThrow(() -> new ResourceNotFoundException("功能不存在"));
        FunctionEntity modifyFunctionEntity = BeanUtils.copyProperties(functionDto, functionEntity, true);
        return BeanUtils.copyProperties(baseDao.save(modifyFunctionEntity), new FunctionDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delFunction(Long functionId) {
        baseDao.deleteById(FunctionEntity.class, functionId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void applicationAuthorization(Long functionId, List<Long> roleIds) {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        // 树结构保存, 先清除整个的功能下的权限然后重新复制
        roleFunctionRepository.removeRoleFunctionEntitiesByFunctionId(functionId);
        List<RoleFunctionEntity> roleFunctionEntityList = roleIds.stream()
                .map(roleId -> {
                    RoleFunctionEntity roleFunctionEntity = new RoleFunctionEntity();
                    roleFunctionEntity.setFunctionId(functionId);
                    roleFunctionEntity.setRoleId(roleId);
                    roleFunctionEntity.setCreateUserId(currentUser.getId());
                    return roleFunctionEntity;
                })
                .collect(Collectors.toList());
        baseDao.batchSave(roleFunctionEntityList);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delApplication(Long applicationId) {
        baseDao.deleteById(ApplicationEntity.class, applicationId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FunctionDto> getApplicationFunctionList(Long applicationId) {
        List<FunctionEntity> functionEntities = functionRepository.findFunctionEntitiesByApplicationId(applicationId);
        return functionEntities.stream()
                .map(functionEntity -> BeanUtils.copyProperties(functionEntity, new FunctionDto()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FunctionDto> getUserFunctions(Long applicationId) {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("applicationId", applicationId);
        parameters.put("userId", currentUser.getId());
        DynamicQueryDto dynamicQueryDto = new DynamicQueryDto("dynamicsql", "application.xml", "getUserApplicationFunctions");
        DynamicQueryInst dynamicQueryInst = searchService.buildQueryInst(dynamicQueryDto, parameters);
        return baseDao.sqlAsEntity(dynamicQueryInst.getSql(), parameters, FunctionDto.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public OrgDto addOrganization(OrgDto orgDto) {
        OrgEntity orgEntity = BeanUtils.copyProperties(orgDto, new OrgEntity());
        return BeanUtils.copyProperties(baseDao.save(orgEntity), new OrgDto());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrgDto> getOrgList() {
        Iterable<OrgEntity> orgEntityList = orgRepository.findAll();
        return StreamSupport.stream(orgEntityList.spliterator(), false)
                .map(orgEntity -> BeanUtils.copyProperties(orgEntity, new OrgDto()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrgDto findOrgById(Long id) {
        return BeanUtils.copyProperties(orgRepository.findById(id), new OrgDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public OrgDto modifyOrg(OrgDto orgDto) {
        if (orgDto.getId() == null) {
            throw new PrimaryKeyMissingException("修改接口传入的实体需要具有id");
        }
        OrgEntity orgEntity = orgRepository.findById(orgDto.getId()).orElseThrow(() -> new ResourceNotFoundException("机构不存在"));
        OrgEntity modifyOrgEntity = BeanUtils.copyProperties(orgDto, orgEntity);
        return BeanUtils.copyProperties(baseDao.save(modifyOrgEntity), new OrgDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delOrgById(Long id) {
        orgRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addOrgUser(Long orgId, List<Long> userIds) {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        List<UserOrgEntity> userOrgEntities = userIds.stream()
                .map(userId -> {
                    UserOrgEntity userOrgEntity = new UserOrgEntity();
                    userOrgEntity.setUserId(userId);
                    userOrgEntity.setCreateUserId(currentUser.getId());
                    userOrgEntity.setOrgId(orgId);
                    return userOrgEntity;
                }).collect(Collectors.toList());
        baseDao.batchSave(userOrgEntities);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RoleDto addRole(RoleDto roleDto) {
        RoleEntity roleEntity = BeanUtils.copyProperties(roleDto, new RoleEntity());
        return BeanUtils.copyProperties(baseDao.save(roleEntity), new RoleDto());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDto findRoleById(Long id) {
        RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("角色不存在"));
        return BeanUtils.copyProperties(roleEntity, new RoleDto());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getRoleList(Long functionId, Long permissionId) {
        if (ObjectUtils.isAllNull(functionId, permissionId)) {
            return getRoleList();
        }
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("functionId", ObjectUtils.defaultIfNull(functionId, -1));
        parameters.put("permissionId", ObjectUtils.defaultIfNull(permissionId, -1));
        DynamicQueryDto dynamicQueryDto = new DynamicQueryDto("dynamicsql", "role.xml", "getRoleListWithAuth");
        DynamicQueryInst dynamicQueryInst = searchService.buildQueryInst(dynamicQueryDto, parameters);
        return baseDao.sqlAsEntity(dynamicQueryInst.getSql(), dynamicQueryInst.getParameters(), RoleDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getRoleList() {
        Iterable<RoleEntity> roleEntityIterable = roleRepository.findAll();
        return StreamSupport.stream(roleEntityIterable.spliterator(), false)
                .map(roleEntity -> BeanUtils.copyProperties(roleEntity, new RoleDto()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RoleDto modifyRole(RoleDto roleDto) {
        if (roleDto.getId() == null) {
            throw new PrimaryKeyMissingException("修改接口传入的实体需要具有id");
        }
        RoleEntity roleEntity = roleRepository.findById(roleDto.getId()).orElseThrow(() -> new ResourceNotFoundException("角色不存在"));
        RoleEntity modifyRoleEntity = BeanUtils.copyProperties(roleDto, roleEntity, true);
        return BeanUtils.copyProperties(baseDao.save(modifyRoleEntity), new RoleDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addRoleUser(Long roleId, List<Long> userIds) {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        List<UserRoleEntity> userRoleEntities = userIds.stream().map(userId -> {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(userId);
            userRoleEntity.setRoleId(roleId);
            userRoleEntity.setCreateUserId(currentUser.getId());
            return userRoleEntity;
        }).collect(Collectors.toList());
        baseDao.batchSave(userRoleEntities);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delRoleUser(Long roleId, List<Long> userIds) {
        userRoleRepository.removeUserRoleEntitiesByRoleIdAndUserIdIn(roleId, userIds);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delOrgUser(Long orgId, List<Long> userIds) {
        userOrgRepository.removeUserOrgEntitiesByOrgIdAndUserIdIn(orgId, userIds);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkDto> getUserLinks() {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        List<LinkEntity> linkEntityList = linkRepository.findLinksByUserId(currentUser.getId());
        return linkEntityList.stream()
                .map(linkEntity -> BeanUtils.copyProperties(linkEntity, new LinkDto()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public LinkDto addLink(LinkDto linkDto) {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        LinkEntity linkEntity = BeanUtils.copyProperties(linkDto, new LinkEntity());
        linkEntity.setUserId(currentUser.getId());
        return BeanUtils.copyProperties(baseDao.save(linkEntity), new LinkDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public LinkDto modifyLink(LinkDto linkDto) {
        LinkEntity linkEntity = linkRepository.findById(linkDto.getId()).orElseThrow(() -> new ResourceNotFoundException("链接不存在"));
        LinkEntity modifyLinkEntity = BeanUtils.copyProperties(linkDto, linkEntity);
        return BeanUtils.copyProperties(baseDao.save(modifyLinkEntity), new LinkDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delLink(Long linkId) {
        baseDao.deleteById(LinkEntity.class, linkId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void phoneNumberChange(String newPhoneNumber) {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        UserEntity userEntity = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
        userEntity.setPhoneNumber(newPhoneNumber);
        baseDao.save(userEntity);
    }

    @Override
    public PasswordStrengthVo getAccountSecurityLevel() {
        UserDto currentUser = RequestContextThreadLocal.getUser();
        PasswordStrengthVo passwordStrengthVo = new PasswordStrengthVo();
        UserEntity userEntity = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
        passwordStrengthVo.setPasswordStrengthLevel(PasswordUtils.checkPasswordStrength(userEntity.getPassword()));
        passwordStrengthVo.setExpectedCrackingTime(PasswordUtils.estimateCrackTime(userEntity.getPassword()));
        return passwordStrengthVo;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void userMailChange(Long userId, String newMailAddress) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
        userEntity.setEmail(newMailAddress);
        baseDao.save(userEntity);
    }
}
