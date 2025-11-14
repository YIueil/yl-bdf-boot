package cc.yiueil.controller;

import cc.yiueil.annotation.VerifyToken;
import cc.yiueil.constant.OrupRestUrl;
import cc.yiueil.dto.*;
import cc.yiueil.exception.BusinessException;
import cc.yiueil.general.RestUrl;
import cc.yiueil.lang.tree.Tree;
import cc.yiueil.lang.tree.TreeNode;
import cc.yiueil.properties.JwtProperties;
import cc.yiueil.properties.SecurityProperties;
import cc.yiueil.service.OrupService;
import cc.yiueil.util.*;
import cc.yiueil.vo.PasswordStrengthVo;
import cc.yiueil.vo.UserVo;
import com.auth0.jwt.interfaces.Claim;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户账户控制器
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/5/30 22:16
 */
@Tag(name = "ORUP-用户角色账户权限控制")
@Slf4j
@RestController
@RequestMapping(value = RestUrl.BASE_PATH + OrupRestUrl.ORUP)
public class OrupController implements LoggedController {

    final OrupService orupService;

    final JwtProperties jwtProperties;

    final SecurityProperties securityProperties;

    public OrupController(OrupService orupService, JwtProperties jwtProperties, SecurityProperties securityProperties) {
        this.orupService = orupService;
        this.jwtProperties = jwtProperties;
        this.securityProperties = securityProperties;
    }

    /**
     * 获取以及登录的用户信息
     * @param request 请求体
     * @return 用户信息
     */
    public UserDto getUser(HttpServletRequest request) {
        String headerParamsName = jwtProperties.getHeaderParamsName();
        String privateKey = jwtProperties.getPrivateKey();
        String publicToken = jwtProperties.getPublicToken();
        String token = request.getHeader(headerParamsName);
        if (publicToken.equals(token)) {
            UserDto visitor = securityProperties.getVisitor();
            if (visitor == null) {
                throw new BusinessException("未定义游客用户信息");
            }
            return visitor;
        }
        if (StringUtils.isNotEmpty(token)) {
            return JwtUtils.verifyTokenUser(token, privateKey);
        }
        return null;
    }

    /**
     * 获取上下文哈希表: 适用于publicToken
     * @param request 请求体
     * @return 上下文哈希表
     */
    public Map<String, Claim> getContextMap(HttpServletRequest request) {
        String publicToken = request.getParameter("publicToken");
        if (publicToken == null) {
            return null;
        }
        return JwtUtils.verifyTokenToMap(publicToken, jwtProperties.getPrivateKey());
    }

    //region 用户注册登陆

    /**
     * 用户登入
     *
     * @param loginName 登陆名
     * @param password  密码
     * @return 登入结果, 成功则返回 token
     */
    @Operation(summary = "用户登入")
    @PostMapping(value = "login")
    public String login(@RequestParam String loginName,
                        @RequestParam String password
    ) {
        try {
            UserDto userDto = orupService.findUserByLoginName(loginName);
            if (password.equals(userDto.getPassword())) {
                String token = JwtUtils.generateToken(userDto, ParseUtils.getLong(jwtProperties.getExpireSeconds(), 1800L), jwtProperties.getPrivateKey());
                return success(userDto, "登录成功", token);
            }
        } catch (BusinessException e) {
            return fail(e.getMessage());
        }
        return fail("登录失败, 账号或者密码错误");
    }

    /**
     * 刷新token
     *
     * @param request 请求体
     * @return 带有新token的ResultVo
     */
    @Operation(summary = "刷新token")
    @GetMapping(value = "refreshToken")
    public String refreshToken(HttpServletRequest request) {
        UserDto currentUser = getUser(request);
        UserDto user = orupService.getUser(currentUser.getId());
        String newToken = JwtUtils.generateToken(user, ParseUtils.getLong(jwtProperties.getExpireSeconds(), 1800L), jwtProperties.getPrivateKey());
        return success(user, "刷新成功", newToken);
    }

    /**
     * 用户注册
     *
     * @param registerUser 用户信息
     * @return 新注册用户信息
     */
    @Operation(summary = "用户注册")
    @PostMapping(value = "register")
    public String register(@RequestBody UserDto registerUser) {
        try {
            return success(orupService.registerUser(registerUser));
        } catch (Exception e) {
            return fail("用户登陆名已存在");
        }
    }

    /**
     * 用户密码修改
     *
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     * @return 修改结果
     */
    @Operation(summary = "用户密码修改")
    @PostMapping(value = "passwordChange")
    public String passwordChange(@RequestParam String oldPassword, @RequestParam String newPassword) {
        try {
            orupService.passwordChange(oldPassword, newPassword);
            return success(null, "密码修改成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return fail(e.getMessage());
        }
    }

    /**
     * 用户邮件变更
     *
     * @param newMailAddress 新邮件地址
     * @param request        请求体
     * @return 变更结果
     */
    @VerifyToken
    @Operation(summary = "用户邮件变更")
    @RequestMapping(value = "mailChange", method = {RequestMethod.POST, RequestMethod.GET})
    public String mailChange(@RequestParam(required = false) String newMailAddress, HttpServletRequest request) {
        Map<String, Claim> contextMap = getContextMap(request);
        try {
            String mailAddress = ObjectUtils.defaultIfNull(newMailAddress, contextMap.get("newMailAddress").asString());
            Long userId = contextMap.get("userId").asLong();
            if (StringUtils.isBlank(mailAddress)) {
                return fail(null, "没有传入目标邮箱");
            }
            orupService.userMailChange(userId, mailAddress);
            return success();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return fail(null, e.getMessage());
        }
    }

    /**
     * 获取账号安全等级
     *
     * @return 响应体
     */
    @Operation(summary = "获取账号安全等级")
    @GetMapping(value = "getAccountSecurityLevel")
    public String getAccountSecurityLevel() {
        PasswordStrengthVo passwordStrengthVo = orupService.getAccountSecurityLevel();
        return success(passwordStrengthVo);
    }

    /**
     * 用户手机号修改
     */
    @Operation(summary = "用户手机号修改")
    @PostMapping(value = "phoneNumberChange")
    public String phoneNumberChange(@RequestParam String newPhoneNumber) {
        try {
            orupService.phoneNumberChange(newPhoneNumber);
            return success(null, "手机号修改成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return fail(e.getMessage());
        }
    }

    /**
     * 用户登出
     *
     * @param response 响应体
     * @return 登出结果
     */
    @Operation(summary = "用户登出")
    @PostMapping(value = "logout")
    public String logout(HttpServletResponse response) {
        return success();
    }

    /**
     * 获取当前用户信息
     *
     * @param request 请求头
     * @return 获取到用户信息, 未登录则返回游客用户
     */
    @Operation(summary = "获取当前用户信息")
    @GetMapping(value = "currentUser")
    public String currentUser(@RequestParam(defaultValue = "true") Boolean useCache,
                              @RequestParam(defaultValue = "false") Boolean loadPermissions,
                              @RequestParam(defaultValue = "false") Boolean loadRoles,
                              HttpServletRequest request) {
        UserVo userVo = new UserVo();
        UserDto userDto = Optional.ofNullable(getUser(request)).orElseGet(() -> {
            UserDto guest = new UserDto();
            guest.setUserName("游客");
            return guest;
        });
        if (!useCache) {
            userDto = orupService.getUser(userDto.getId());
        }
        if (loadPermissions) {
            userVo.setPermissionDtoList(orupService.getUserPermissions());
        }
        if (loadRoles) {
            userVo.setRoleDtoList(orupService.getUserRoles());
        }
        return success(BeanUtils.copyProperties(userDto, userVo));
    }
    //endregion

    //region 用户管理

    /**
     * 用户管理: 创建用户
     *
     * @param userDto 用户DTO
     * @return 新用户实体
     */
    @Operation(summary = "添加用户")
    @PostMapping(value = "addUser")
    public String addUser(@RequestBody UserDto userDto) {
        UserDto newUser = orupService.addUser(userDto);
        return success(newUser);
    }

    /**
     * 用户管理: 修改用户信息
     *
     * @param userDto 用户DTO
     * @return 新用户实体
     */
    @Operation(summary = "修改用户信息")
    @PostMapping(value = "modifyUser")
    public String modifyUser(@RequestBody UserDto userDto) {
        UserDto newUser = orupService.modifyUser(userDto);
        return success(newUser);
    }

    /**
     * 用户管理: 挂起用户
     *
     * @param userId  用户id
     * @return 新用户实体
     */
    @Operation(summary = "挂起用户")
    @PostMapping(value = "suspendUser")
    public String suspendUser(@RequestParam Long userId) {
        orupService.suspendUser(userId);
        return success();
    }

    /**
     * 批量挂起用户
     *
     * @param userIds 用户id集合
     * @return 请求结果
     */
    @Operation(summary = "批量挂起用户")
    @PostMapping(value = "suspendUserByIds")
    public String suspendUserByIds(@RequestBody List<Long> userIds) {
        orupService.suspendUserByIds(userIds);
        return success();
    }

    /**
     * 用户管理: 删除用户
     *
     * @param userId  用户id
     * @return 新用户实体
     */
    @Operation(summary = "删除用户信息")
    @PostMapping(value = "delUser")
    public String delUser(@RequestParam Long userId) {
        orupService.delUser(userId);
        return success();
    }

    /**
     * 通过id集合批量删除用户
     *
     * @param userIds 用户id集合
     * @return 接口调用结果
     */
    @Operation(summary = "批量删除用户信息")
    @PostMapping(value = "delUserByIds")
    public String delUserByIds(@RequestBody List<Long> userIds) {
        orupService.delUserByIds(userIds);
        return success();
    }

    /**
     * 用户管理: 获取用户信息
     *
     * @param userId  用户id
     * @return 用户实体信息
     */
    @Operation(summary = "获取用户")
    @GetMapping(value = "getUser")
    public String getUser(@RequestParam Long userId) {
        UserDto currentUser = orupService.getUser(userId);
        return success(currentUser);
    }

    /**
     * 获取用户链接列表
     *
     * @return 用户链接集合
     */
    @Operation(summary = "获取用户链接列表")
    @GetMapping(value = "getUserLinks")
    public String getUserLinks() {
        List<LinkDto> linkDtoList = orupService.getUserLinks();
        return success(linkDtoList);
    }

    /**
     * 添加链接
     *
     * @param linkDto 链接实体
     * @return 新增链接实体
     */
    @Operation(summary = "添加用户链接")
    @PostMapping(value = "addLink")
    public String addLink(@RequestBody LinkDto linkDto) {
        return success(orupService.addLink(linkDto));
    }

    /**
     * 修改链接
     *
     * @param linkDto 链接实体
     * @return 修改后的链接
     */
    @Operation(summary = "修改用户链接")
    @PostMapping(value = "modifyLink")
    public String modifyLink(@RequestBody LinkDto linkDto) {
        return success(orupService.modifyLink(linkDto));
    }

    /**
     * 删除链接
     *
     * @param linkId  链接id
     * @return 接口请求结果
     */
    @Operation(summary = "删除用户链接")
    @PostMapping(value = "delLink")
    public String delLink(@RequestParam("id") Long linkId) {
        orupService.delLink(linkId);
        return success();
    }

    //endregion

    //region 角色权限

    @Operation(summary = "添加角色")
    @PostMapping(value = "addRole")
    public String addRole(@RequestBody RoleDto roleDto) {
        return success(orupService.addRole(roleDto));
    }

    @Operation(summary = "查询角色")
    @GetMapping(value = "getRole")
    public String getRole(@RequestParam Long id) {
        return success(orupService.findRoleById(id));
    }

    @Operation(summary = "获取角色树")
    @GetMapping(value = "getRoleTree")
    public String getRoleTree(@RequestParam(required = false) Long functionId, @RequestParam(required = false) Long permissionId) {
        List<RoleDto> roleDtoList = orupService.getRoleList(functionId, permissionId);
        return success(
                TreeUtils.build(roleDtoList.stream().map(roleDto -> {
                    Map<String, Object> extra = MapUtils.entityToMap(roleDto);
                    return new TreeNode<>(roleDto.getId(), roleDto.getParentId(), roleDto.getName(), 1, extra);
                }).collect(Collectors.toList()), 0L)
        );
    }

    @Operation(summary = "更新角色")
    @PostMapping(value = "updateRole")
    public String updateRole(@RequestBody RoleDto roleDto) {
        return success(orupService.modifyRole(roleDto));
    }

    @Operation(summary = "删除角色")
    @PostMapping(value = "deleteRole")
    public String deleteRole(@RequestParam Long roleId) {
        orupService.delRole(roleId);
        return success();
    }

    @Operation(summary = "添加角色用户")
    @PostMapping(value = "addRoleUser")
    public String addRoleUser(@RequestParam Long roleId, @RequestBody List<Long> userIds) {
        orupService.addRoleUser(roleId, userIds);
        return success();
    }

    @Operation(summary = "移除角色用户")
    @PostMapping(value = "delRoleUser")
    public String delRoleUser(@RequestParam Long roleId, @RequestBody List<Long> userIds) {
        orupService.delRoleUser(roleId, userIds);
        return success();
    }

    /**
     * 角色权限: 获取用户所有权限集合
     *
     * @return 权限集合
     */
    @Operation(summary = "获取用户所有权限集合")
    @GetMapping(value = "permissions")
    public String getUserPermissions() {
        return success(orupService.getUserPermissions());
    }

    /**
     * 角色权限: 获取用户所有角色集合
     *
     * @return 角色集合
     */
    @Operation(summary = "获取用户所有角色集合")
    @GetMapping(value = "roles")
    public String getUserRoles() {
        return success(orupService.getUserRoles());
    }
    //endregion

    //region 应用管理

    /**
     * 获取应用集合
     *
     * @return 应用集合
     */
    @Operation(summary = "获取所有应用")
    @GetMapping(value = "getAllApplicationList")
    public String getAllApplications() {
        return success(orupService.getAllApplications());
    }

    @Operation(summary = "修改应用状态")
    @PostMapping(value="updateApplicationStatus")
    public String updateApplicationStatus(Long applicationId, String status){
        orupService.modifyApplicationStatus(applicationId, status);
        return success();
    }

    /**
     * 添加或编辑应用
     *
     * @param applicationDto 应用Dto
     * @return 新添加的应用
     */
    @Operation(summary = "添加应用")
    @PostMapping(value = "addApplication")
    public String addApplication(@RequestBody ApplicationDto applicationDto) {
        return success(orupService.addApplication(applicationDto));
    }

    @Operation(summary = "修改应用")
    @PostMapping(value = "modifyApplication")
    public String modifyApplication(@RequestBody ApplicationDto applicationDto) {
        return success(orupService.modifyApplication(applicationDto));
    }

    @Operation(summary = "删除应用")
    @PostMapping(value = "delApplication")
    public String delApplication(@RequestParam Long applicationId) {
        orupService.delApplication(applicationId);
        return success();
    }

    /**
     * 添加应用管理员
     *
     * @param applicationId 应用id
     * @param userIds       用户id集合
     * @return ResultVo
     */
    @Operation(summary = "添加应用管理员")
    @PostMapping(value = "addApplicationManager")
    public String addApplicationManager(@RequestParam Long applicationId, @RequestBody List<Long> userIds) {
        orupService.addApplicationManager(applicationId, userIds);
        return success();
    }

    /**
     * 获取应用功能列表树
     *
     * @param applicationId 应用id
     * @return ResultVo
     */
    @Operation(summary = "获取应用功能树")
    @GetMapping(value = "getApplicationFunctionTree")
    public String getApplicationFunctionTree(@RequestParam Long applicationId) {
        List<FunctionDto> functionDtoList = orupService.getApplicationFunctionList(applicationId);
        return success(buildFunctionTree(functionDtoList));
    }

    /**
     * 获取用戶应用功能列表树
     *
     * @param applicationId 应用id
     * @return ResultVo
     */
    @Operation(summary = "获取应用功能树")
    @GetMapping(value = "getUserFunctions")
    public String getUserFunctions(@RequestParam Long applicationId) {
        List<FunctionDto> functionDtoList = orupService.getUserFunctions(applicationId);
        return success(buildFunctionTree(functionDtoList));
    }

    private List<Tree<Long>> buildFunctionTree(List<FunctionDto> functionDtoList) {
        return TreeUtils.build(functionDtoList.stream().map(functionDto -> {
            Map<String, Object> extra = new HashMap<>(functionDtoList.size());
            extra.put("id", functionDto.getId());
            extra.put("guid", functionDto.getGuid());
            extra.put("parentId", functionDto.getParentId());
            extra.put("applicationId", functionDto.getApplicationId());
            extra.put("description", functionDto.getDescription());
            extra.put("method", functionDto.getMethod());
            extra.put("rightName", functionDto.getRightName());
            extra.put("name", functionDto.getName());
            extra.put("type", functionDto.getType());
            extra.put("url", functionDto.getUrl());
            return new TreeNode<>(functionDto.getId(), functionDto.getParentId(), functionDto.getName(), 1, extra);
        }).collect(Collectors.toList()), 0L);
    }


    /**
     * 添加应用功能
     *
     * @param functionDto 应用功能数据传输类
     * @return ResultVo
     */
    @Operation(summary = "添加应用功能")
    @PostMapping(value = "addApplicationFunction")
    public String addApplicationFunction(@RequestBody FunctionDto functionDto) {
        return success(orupService.addApplicationFunction(functionDto));
    }

    @Operation(summary = "修改应用功能")
    @PostMapping(value = "modifyApplicationFunction")
    public String modifyApplicationFunction(@RequestBody FunctionDto functionDto) {
        return success(orupService.modifyFunction(functionDto));
    }

    /**
     * 删除应用功能
     *
     * @param functionId 应用功能id
     * @return ResultVo
     */
    @Operation(summary = "删除应用功能")
    @PostMapping(value = "delApplicationFunction")
    public String delApplicationFunction(@RequestParam Long functionId) {
        orupService.delFunction(functionId);
        return success();
    }

    /**
     * 应用功能授权
     *
     * @return ResultVo
     */
    @Operation(summary = "应用功能授权")
    @PostMapping(value = "applicationAuthorization")
    public String applicationAuthorization(@RequestParam Long functionId, @RequestBody List<Long> roleIds) {
        orupService.applicationAuthorization(functionId, roleIds);
        return success();
    }


    //endregion

    //region 机构管理

    @Operation(summary = "创建机构")
    @PostMapping(value = "addOrg")
    public String addOrganization(@RequestBody OrgDto orgDto) {
        return success(orupService.addOrganization(orgDto));
    }

    @Operation(summary = "查询机构列表")
    @GetMapping(value = "getOrgTree")
    public String getOrgTree() {
        List<OrgDto> orgDtoList = orupService.getOrgList();
        return success(
                TreeUtils.build(orgDtoList.stream().map(orgDto -> {
                    Map<String, Object> extra = new HashMap<>(orgDtoList.size());
                    extra.put("id", orgDto.getId());
                    extra.put("guid", orgDto.getGuid());
                    extra.put("parentId", orgDto.getParentId());
                    extra.put("description", orgDto.getDescription());
                    extra.put("name", orgDto.getName());
                    extra.put("code", orgDto.getCode());
                    extra.put("type", orgDto.getType());
                    return new TreeNode<>(orgDto.getId(), orgDto.getParentId(), orgDto.getName(), 1, extra);
                }).collect(Collectors.toList()), 0L)
        );
    }

    @Operation(summary = "根据ID查询机构信息")
    @GetMapping(value = "getOrg")
    public String getOrgById(@RequestParam Long id) {
        OrgDto orgDto = orupService.findOrgById(id);
        return success(orgDto);
    }

    @Operation(summary = "更新机构信息")
    @PostMapping(value = "updateOrg")
    public String updateOrg(@RequestBody OrgDto orgDto) {
        return success(orupService.modifyOrg(orgDto));
    }

    @Operation(summary = "删除机构")
    @PostMapping(value = "deleteOrg")
    public String deleteOrg(@RequestParam Long id) {
        orupService.delOrgById(id);
        return success();
    }

    @Operation(summary = "向机构添加用户")
    @PostMapping(value = "addOrgUser")
    public String addOrgUser(@RequestParam Long orgId, @RequestBody List<Long> userIds) {
        orupService.addOrgUser(orgId, userIds);
        return success();
    }

    @Operation(summary = "移除机构用户")
    @PostMapping(value = "delOrgUser")
    public String delOrgUser(@RequestParam Long orgId, @RequestBody List<Long> userIds) {
        orupService.delOrgUser(orgId, userIds);
        return success();
    }
    //endregion
}
