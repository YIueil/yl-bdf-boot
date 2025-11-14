package cc.yiueil.service;

import cc.yiueil.dto.*;
import cc.yiueil.vo.PasswordStrengthVo;

import java.util.List;

/**
 * 核心ORUP接口
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/5/30 22:17
 */
public interface OrupService {
    /**
     * 注册用户
     *
     * @param registerUser 新注册用户
     * @return 新注册成功的用户
     */
    UserDto registerUser(UserDto registerUser);

    /**
     * 修改密码
     *  @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void passwordChange(String oldPassword, String newPassword);

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return 用户实体信息
     */
    UserDto getUser(Long userId);

    /**
     * 添加用户
     *
     * @param userDto     用户DTO
     * @return 用户实体信息
     */
    UserDto addUser(UserDto userDto);

    /**
     * 修改用户信息
     *
     * @param userDto     用户实体
     * @return 编辑后的用户信息
     */
    UserDto modifyUser(UserDto userDto);

    /**
     * 删除用户信息
     *  @param userId      用户id
     *
     */
    void delUser(Long userId);

    /**
     * 批量删除用户信息
     *  @param userIds     用户id集合
     *
     */
    void delUserByIds(List<Long> userIds);

    /**
     * 挂起用户
     *  @param userId      用户id
     *
     */
    void suspendUser(Long userId);

    /**
     * 批量挂起用户
     *  @param userIds     用户id集合
     *
     */
    void suspendUserByIds(List<Long> userIds);

    /**
     * 根据id查找用户
     *
     * @param id 用户id
     * @return 用户数据传输类
     */
    UserDto findUserById(Long id);

    /**
     * 根据登陆名查找用户
     *
     * @param loginName 用户登陆名
     * @return 用户数据传输类
     */
    UserDto findUserByLoginName(String loginName);

    /**
     * 获取用户所有权限
     *
     * @return 权限集合
     */
    List<PermissionDto> getUserPermissions();

    /**
     * 获取用户所有角色
     *
     * @return 角色集合
     */
    List<RoleDto> getUserRoles();

    /**
     * 获取所有应用集合
     *
     * @return 应用集合
     */
    List<ApplicationDto> getAllApplications();

    /**
     * 添加应用
     *
     * @param applicationDto 应用Dto
     * @return 新增的应用
     */
    ApplicationDto addApplication(ApplicationDto applicationDto);

    /**
     * 修改应用
     *
     * @param applicationDto 应用
     * @return applicationDto 应用Dto
     */
    ApplicationDto modifyApplication(ApplicationDto applicationDto);

    /**
     * 修改应用状态
     *
     * @param applicationId 应用id
     * @param status        状态
     */
    void modifyApplicationStatus(Long applicationId, String status);

    /**
     * 给应用批量设置应用管理员
     *
     * @param applicationId 应用id
     * @param userIds       用户id集合
     */
    void addApplicationManager(Long applicationId, List<Long> userIds);

    /**
     * 添加应用功能
     *
     * @param functionDto 应用功能Dto
     * @return 新添加的应用功能
     */
    FunctionDto addApplicationFunction(FunctionDto functionDto);

    /**
     * 应用功能授权
     *
     * @param functionId  应用功能id
     * @param roleIds     角色id集合
     */
    void applicationAuthorization(Long functionId, List<Long> roleIds);

    /**
     * 删除应用
     *  @param applicationId 应用id
     *
     */
    void delApplication(Long applicationId);

    /**
     * 获取应用的所有应用功能
     *
     * @param applicationId 应用id
     * @return 应用下的素有应用功能集合
     */
    List<FunctionDto> getApplicationFunctionList(Long applicationId);

    /**
     * 删除应用功能
     *  @param functionId  应用功能id
     *
     */
    void delFunction(Long functionId);

    /**
     * 添加机构
     *
     * @param orgDto 机构Dto
     * @return 添加好的机构Dto
     */
    OrgDto addOrganization(OrgDto orgDto);

    /**
     * 获取机构列表
     *
     * @return 机构列表
     */
    List<OrgDto> getOrgList();

    /**
     * 通过id获取机构
     *
     * @param id 机构id
     * @return 机构Dto
     */
    OrgDto findOrgById(Long id);

    /**
     * 修改机构实体
     *
     * @param orgDto 机构Dto
     * @return 修改后的机构dto
     */
    OrgDto modifyOrg(OrgDto orgDto);

    /**
     * 通过id删除机构
     *
     * @param id   删除机构
     */
    void delOrgById(Long id);

    /**
     * 添加机构用户
     *  @param orgId   机构id
     * @param userIds 用户id集合
     */
    void addOrgUser(Long orgId, List<Long> userIds);

    /**
     * 添加角色
     *
     * @param roleDto 角色Dto
     * @return 新增的RoleDto
     */
    RoleDto addRole(RoleDto roleDto);


    /**
     * 获取角色
     *
     * @param id 角色id
     * @return RoleDto
     */
    RoleDto findRoleById(Long id);

    /**
     * 获取角色集合
     *
     * @return RoleList
     */
    List<RoleDto> getRoleList();

    /**
     * 获取角色集合
     *
     * @param functionId   功能id权限加载
     * @param permissionId 权限id权限加载
     * @return RoleList
     */
    List<RoleDto> getRoleList(Long functionId, Long permissionId);

    /**
     * 修改角色
     *
     * @param roleDto roleDto
     * @return 修改后的RoleDto
     */
    RoleDto modifyRole(RoleDto roleDto);

    /**
     * 删除角色
     *
     * @param roleId 角色id
     */
    void delRole(Long roleId);

    /**
     * 向角色中添加用户
     *
     * @param roleId  角色id
     * @param userIds 用户id集合
     */
    void addRoleUser(Long roleId, List<Long> userIds);

    /**
     * 移除角色中的用户
     *  @param roleId  角色id
     * @param userIds 用户id集合
     */
    void delRoleUser(Long roleId, List<Long> userIds);

    /**
     * 修改应用功能
     *
     * @param functionDto 功能dto
     * @return 修改后的 FunctionDto
     */
    FunctionDto modifyFunction(FunctionDto functionDto);

    /**
     * 移除机构用户
     *  @param orgId   机构id
     * @param userIds 用户集合
     */
    void delOrgUser(Long orgId, List<Long> userIds);

    /**
     * 获取用户应用功能
     *
     * @param applicationId 应用id
     * @return 用户当前应用具备的应用功能
     */
    List<FunctionDto> getUserFunctions(Long applicationId);

    /**
     * 获取用户链接集合
     *
     * @return 链接集合
     */
    List<LinkDto> getUserLinks();

    /**
     * 创建用户链接
     *
     * @param linkDto 用户链接
     * @return 创建后的 link 链接
     */
    LinkDto addLink(LinkDto linkDto);

    /**
     * 编辑用户链接
     *
     * @param linkDto 用户链接
     * @return 修改后的用户链接
     */
    LinkDto modifyLink(LinkDto linkDto);

    /**
     * 删除用户链接
     *
     * @param linkId      链接实体id
     */
    void delLink(Long linkId);

    /**
     * 修改用户手机号
     *
     * @param newPhoneNumber 新手机号码
     */
    void phoneNumberChange(String newPhoneNumber);

    /**
     * 获取密码健壮性等级
     *
     * @return 密码健壮性视图对象
     */
    PasswordStrengthVo getAccountSecurityLevel();

    /**
     * 修改用户邮箱
     *
     * @param userId         用户id
     * @param newMailAddress 新邮件地址
     */
    void userMailChange(Long userId, String newMailAddress);
}
