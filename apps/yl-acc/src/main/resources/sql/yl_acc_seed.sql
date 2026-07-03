-- yl_acc 基础种子数据
-- 适用场景: yl_acc schema 下表结构和序列已经存在, 但业务数据为空
-- 建议执行方式: psql -d <db> -f apps/yl-acc/src/main/resources/sql/yl_acc_seed.sql
-- 默认管理员:
--   login_name = admin
--   password   = Admin@123456
-- 首次登录后请尽快修改密码

begin;

create schema if not exists yl_acc;

create sequence if not exists yl_acc.s_org start with 1 increment by 1;
create sequence if not exists yl_acc.s_user start with 1 increment by 1;
create sequence if not exists yl_acc.s_role start with 1 increment by 1;
create sequence if not exists yl_acc.s_permission start with 1 increment by 1;
create sequence if not exists yl_acc.s_application start with 1 increment by 1;
create sequence if not exists yl_acc.s_application_manager start with 1 increment by 1;
create sequence if not exists yl_acc.s_function start with 1 increment by 1;
create sequence if not exists yl_acc.s_user_role start with 1 increment by 1;
create sequence if not exists yl_acc.s_role_permission start with 1 increment by 1;
create sequence if not exists yl_acc.s_role_function start with 1 increment by 1;
create sequence if not exists yl_acc.s_user_org start with 1 increment by 1;

insert into yl_acc.org (
    id, guid, name, code, type, description, parent_id, create_time, modify_time, create_user_id
)
select
    1, 'org-root-0001', '平台根机构', 'ROOT', '单位', '系统初始化默认根机构', 0, now(), now(), 1
where not exists (
    select 1 from yl_acc.org where id = 1
);

insert into yl_acc."user" (
    id, guid, user_name, login_name, password, phone_number, email, avatar_url, signature, state,
    create_user_id, create_time, modify_time, extend_property1, extend_property2, extend_property3, extend_property4
)
select
    1, 'user-admin-0001', '系统管理员', 'admin', 'Admin@123456', '13800000000', 'admin@local',
    null, 'Built by seed script', '正常', 1, now(), now(), null, null, null, null
where not exists (
    select 1 from yl_acc."user" where id = 1
);

insert into yl_acc."role" (
    id, guid, name, description, create_time, modify_time, create_user_id, parent_id
)
select
    1, 'role-admin-0001', '系统管理员', 'yl-acc 默认超级管理员角色', now(), now(), 1, 0
where not exists (
    select 1 from yl_acc."role" where id = 1
);

insert into yl_acc.application (
    id, guid, name, description, url, icon_url, status, create_time, modify_time, create_user_id
)
select
    1, 'app-yl-acc-0001', '访问控制中心', 'yl-acc 默认管理应用', '/yl-acc', 'setting', '启用', now(), now(), 1
where not exists (
    select 1 from yl_acc.application where id = 1
);

insert into yl_acc.application_manager (
    id, guid, application_id, manager_id, create_time, modify_time, create_user_id
)
select
    1, 'app-manager-0001', 1, 1, now(), now(), 1
where not exists (
    select 1 from yl_acc.application_manager where id = 1
);

insert into yl_acc.user_org (
    id, guid, user_id, org_id, create_time, modify_time, create_user_id
)
select
    1, 'user-org-0001', 1, 1, now(), now(), 1
where not exists (
    select 1 from yl_acc.user_org where id = 1
);

insert into yl_acc.user_role (
    id, guid, user_id, role_id, create_time, modify_time, create_user_id
)
select
    1, 'user-role-0001', 1, 1, now(), now(), 1
where not exists (
    select 1 from yl_acc.user_role where id = 1
);

insert into yl_acc."permission" (
    id, guid, name, right_name, description, create_time, modify_time, create_user_id, parent_id
)
values
    (1, 'perm-root-0001', '访问控制中心权限', 'acc.root', 'yl-acc 权限根节点', now(), now(), 1, 0),
    (2, 'perm-user-0001', '用户管理', 'acc.user.manage', '用户增删改查和链接管理权限', now(), now(), 1, 1),
    (3, 'perm-role-0001', '角色权限管理', 'acc.role.manage', '角色、授权和角色成员维护权限', now(), now(), 1, 1),
    (4, 'perm-app-0001', '应用管理', 'acc.application.manage', '应用、功能和应用授权维护权限', now(), now(), 1, 1),
    (5, 'perm-org-0001', '机构管理', 'acc.org.manage', '机构和机构成员维护权限', now(), now(), 1, 1),
    (6, 'perm-account-0001', '账户中心', 'acc.account.manage', '个人账户维护权限', now(), now(), 1, 1),
    (7, 'perm-debug-0001', '调试能力', 'acc.debug.access', '访问调试接口权限', now(), now(), 1, 1)
on conflict (id) do nothing;

insert into yl_acc.role_permission (
    id, guid, role_id, permission_id, create_time, modify_time, create_user_id
)
values
    (1, 'role-perm-0001', 1, 1, now(), now(), 1),
    (2, 'role-perm-0002', 1, 2, now(), now(), 1),
    (3, 'role-perm-0003', 1, 3, now(), now(), 1),
    (4, 'role-perm-0004', 1, 4, now(), now(), 1),
    (5, 'role-perm-0005', 1, 5, now(), now(), 1),
    (6, 'role-perm-0006', 1, 6, now(), now(), 1),
    (7, 'role-perm-0007', 1, 7, now(), now(), 1)
on conflict (id) do nothing;

insert into yl_acc."function" (
    id, guid, name, right_name, description, url, type, method, parent_id, application_id, create_time, modify_time, create_user_id
)
values
    (100, 'func-root-0100', '访问控制中心', 'acc.menu.root', 'yl-acc 功能树根节点', '/yl-acc', 'menu', 'GET', 0, 1, now(), now(), 1),
    (110, 'func-user-0110', '用户管理', 'acc.menu.user', '用户管理菜单', '/yl-acc/bdf-boot/orup/getUser', 'menu', 'GET', 100, 1, now(), now(), 1),
    (120, 'func-role-0120', '角色权限', 'acc.menu.role', '角色权限菜单', '/yl-acc/bdf-boot/orup/getRoleTree', 'menu', 'GET', 100, 1, now(), now(), 1),
    (130, 'func-app-0130', '应用管理', 'acc.menu.application', '应用管理菜单', '/yl-acc/bdf-boot/orup/getAllApplicationList', 'menu', 'GET', 100, 1, now(), now(), 1),
    (140, 'func-org-0140', '机构管理', 'acc.menu.org', '机构管理菜单', '/yl-acc/bdf-boot/orup/getOrgTree', 'menu', 'GET', 100, 1, now(), now(), 1),
    (150, 'func-account-0150', '账户中心', 'acc.menu.account', '账户信息菜单', '/yl-acc/bdf-boot/orup/currentUser', 'menu', 'GET', 100, 1, now(), now(), 1),
    (160, 'func-tool-0160', '系统工具', 'acc.menu.tool', '调试和工具菜单', '/yl-acc/bdf-boot/user/test3', 'menu', 'GET', 100, 1, now(), now(), 1),

    (1001, 'func-orup-login', '用户登录', 'acc.orup.login', '登录接口', '/yl-acc/bdf-boot/orup/login', 'api', 'POST', 150, 1, now(), now(), 1),
    (1002, 'func-orup-refresh', '刷新令牌', 'acc.orup.refreshToken', '刷新 token', '/yl-acc/bdf-boot/orup/refreshToken', 'api', 'GET', 150, 1, now(), now(), 1),
    (1003, 'func-orup-register', '用户注册', 'acc.orup.register', '注册接口', '/yl-acc/bdf-boot/orup/register', 'api', 'POST', 150, 1, now(), now(), 1),
    (1004, 'func-orup-passchg', '修改密码', 'acc.orup.passwordChange', '修改当前用户密码', '/yl-acc/bdf-boot/orup/passwordChange', 'api', 'POST', 150, 1, now(), now(), 1),
    (1005, 'func-orup-mailchg', '修改邮箱', 'acc.orup.mailChange', '修改当前用户邮箱', '/yl-acc/bdf-boot/orup/mailChange', 'api', 'POST/GET', 150, 1, now(), now(), 1),
    (1006, 'func-orup-security', '账号安全等级', 'acc.orup.getAccountSecurityLevel', '查看密码强度', '/yl-acc/bdf-boot/orup/getAccountSecurityLevel', 'api', 'GET', 150, 1, now(), now(), 1),
    (1007, 'func-orup-phonechg', '修改手机号', 'acc.orup.phoneNumberChange', '修改当前用户手机号', '/yl-acc/bdf-boot/orup/phoneNumberChange', 'api', 'POST', 150, 1, now(), now(), 1),
    (1008, 'func-orup-logout', '用户登出', 'acc.orup.logout', '退出登录', '/yl-acc/bdf-boot/orup/logout', 'api', 'POST', 150, 1, now(), now(), 1),
    (1009, 'func-orup-current', '当前用户', 'acc.orup.currentUser', '获取当前用户', '/yl-acc/bdf-boot/orup/currentUser', 'api', 'GET', 150, 1, now(), now(), 1),
    (1010, 'func-orup-perms', '当前用户权限', 'acc.orup.permissions', '获取当前用户权限', '/yl-acc/bdf-boot/orup/permissions', 'api', 'GET', 150, 1, now(), now(), 1),
    (1011, 'func-orup-roles', '当前用户角色', 'acc.orup.roles', '获取当前用户角色', '/yl-acc/bdf-boot/orup/roles', 'api', 'GET', 150, 1, now(), now(), 1),

    (1101, 'func-user-add', '添加用户', 'acc.user.add', '创建用户', '/yl-acc/bdf-boot/orup/addUser', 'api', 'POST', 110, 1, now(), now(), 1),
    (1102, 'func-user-modify', '修改用户', 'acc.user.modify', '修改用户信息', '/yl-acc/bdf-boot/orup/modifyUser', 'api', 'POST', 110, 1, now(), now(), 1),
    (1103, 'func-user-suspend', '挂起用户', 'acc.user.suspend', '挂起用户', '/yl-acc/bdf-boot/orup/suspendUser', 'api', 'POST', 110, 1, now(), now(), 1),
    (1104, 'func-user-suspend-batch', '批量挂起用户', 'acc.user.suspend.batch', '批量挂起用户', '/yl-acc/bdf-boot/orup/suspendUserByIds', 'api', 'POST', 110, 1, now(), now(), 1),
    (1105, 'func-user-delete', '删除用户', 'acc.user.delete', '删除单个用户', '/yl-acc/bdf-boot/orup/delUser', 'api', 'POST', 110, 1, now(), now(), 1),
    (1106, 'func-user-delete-batch', '批量删除用户', 'acc.user.delete.batch', '批量删除用户', '/yl-acc/bdf-boot/orup/delUserByIds', 'api', 'POST', 110, 1, now(), now(), 1),
    (1107, 'func-user-get', '查询用户', 'acc.user.get', '根据 ID 获取用户', '/yl-acc/bdf-boot/orup/getUser', 'api', 'GET', 110, 1, now(), now(), 1),
    (1108, 'func-link-list', '用户链接列表', 'acc.link.list', '获取用户链接列表', '/yl-acc/bdf-boot/orup/getUserLinks', 'api', 'GET', 110, 1, now(), now(), 1),
    (1109, 'func-link-add', '添加用户链接', 'acc.link.add', '添加用户链接', '/yl-acc/bdf-boot/orup/addLink', 'api', 'POST', 110, 1, now(), now(), 1),
    (1110, 'func-link-modify', '修改用户链接', 'acc.link.modify', '修改用户链接', '/yl-acc/bdf-boot/orup/modifyLink', 'api', 'POST', 110, 1, now(), now(), 1),
    (1111, 'func-link-delete', '删除用户链接', 'acc.link.delete', '删除用户链接', '/yl-acc/bdf-boot/orup/delLink', 'api', 'POST', 110, 1, now(), now(), 1),

    (1201, 'func-role-add', '添加角色', 'acc.role.add', '添加角色', '/yl-acc/bdf-boot/orup/addRole', 'api', 'POST', 120, 1, now(), now(), 1),
    (1202, 'func-role-get', '查询角色', 'acc.role.get', '根据 ID 查询角色', '/yl-acc/bdf-boot/orup/getRole', 'api', 'GET', 120, 1, now(), now(), 1),
    (1203, 'func-role-tree', '角色树', 'acc.role.tree', '获取角色树', '/yl-acc/bdf-boot/orup/getRoleTree', 'api', 'GET', 120, 1, now(), now(), 1),
    (1204, 'func-role-update', '更新角色', 'acc.role.update', '更新角色', '/yl-acc/bdf-boot/orup/updateRole', 'api', 'POST', 120, 1, now(), now(), 1),
    (1205, 'func-role-delete', '删除角色', 'acc.role.delete', '删除角色', '/yl-acc/bdf-boot/orup/deleteRole', 'api', 'POST', 120, 1, now(), now(), 1),
    (1206, 'func-role-user-add', '添加角色用户', 'acc.role.user.add', '向角色添加用户', '/yl-acc/bdf-boot/orup/addRoleUser', 'api', 'POST', 120, 1, now(), now(), 1),
    (1207, 'func-role-user-delete', '移除角色用户', 'acc.role.user.delete', '从角色移除用户', '/yl-acc/bdf-boot/orup/delRoleUser', 'api', 'POST', 120, 1, now(), now(), 1),

    (1301, 'func-app-list', '应用列表', 'acc.application.list', '获取所有应用', '/yl-acc/bdf-boot/orup/getAllApplicationList', 'api', 'GET', 130, 1, now(), now(), 1),
    (1302, 'func-app-status', '修改应用状态', 'acc.application.status', '修改应用状态', '/yl-acc/bdf-boot/orup/updateApplicationStatus', 'api', 'POST', 130, 1, now(), now(), 1),
    (1303, 'func-app-add', '添加应用', 'acc.application.add', '添加应用', '/yl-acc/bdf-boot/orup/addApplication', 'api', 'POST', 130, 1, now(), now(), 1),
    (1304, 'func-app-modify', '修改应用', 'acc.application.modify', '修改应用', '/yl-acc/bdf-boot/orup/modifyApplication', 'api', 'POST', 130, 1, now(), now(), 1),
    (1305, 'func-app-delete', '删除应用', 'acc.application.delete', '删除应用', '/yl-acc/bdf-boot/orup/delApplication', 'api', 'POST', 130, 1, now(), now(), 1),
    (1306, 'func-app-manager-add', '添加应用管理员', 'acc.application.manager.add', '添加应用管理员', '/yl-acc/bdf-boot/orup/addApplicationManager', 'api', 'POST', 130, 1, now(), now(), 1),
    (1307, 'func-app-func-tree', '应用功能树', 'acc.application.function.tree', '获取应用功能树', '/yl-acc/bdf-boot/orup/getApplicationFunctionTree', 'api', 'GET', 130, 1, now(), now(), 1),
    (1308, 'func-app-user-func', '用户应用功能树', 'acc.application.user.function.tree', '获取当前用户可见功能树', '/yl-acc/bdf-boot/orup/getUserFunctions', 'api', 'GET', 130, 1, now(), now(), 1),
    (1309, 'func-app-func-add', '添加应用功能', 'acc.application.function.add', '添加应用功能', '/yl-acc/bdf-boot/orup/addApplicationFunction', 'api', 'POST', 130, 1, now(), now(), 1),
    (1310, 'func-app-func-modify', '修改应用功能', 'acc.application.function.modify', '修改应用功能', '/yl-acc/bdf-boot/orup/modifyApplicationFunction', 'api', 'POST', 130, 1, now(), now(), 1),
    (1311, 'func-app-func-delete', '删除应用功能', 'acc.application.function.delete', '删除应用功能', '/yl-acc/bdf-boot/orup/delApplicationFunction', 'api', 'POST', 130, 1, now(), now(), 1),
    (1312, 'func-app-auth', '应用功能授权', 'acc.application.authorization', '角色功能授权', '/yl-acc/bdf-boot/orup/applicationAuthorization', 'api', 'POST', 130, 1, now(), now(), 1),

    (1401, 'func-org-add', '创建机构', 'acc.org.add', '创建机构', '/yl-acc/bdf-boot/orup/addOrg', 'api', 'POST', 140, 1, now(), now(), 1),
    (1402, 'func-org-tree', '机构树', 'acc.org.tree', '获取机构树', '/yl-acc/bdf-boot/orup/getOrgTree', 'api', 'GET', 140, 1, now(), now(), 1),
    (1403, 'func-org-get', '查询机构', 'acc.org.get', '根据 ID 查询机构', '/yl-acc/bdf-boot/orup/getOrg', 'api', 'GET', 140, 1, now(), now(), 1),
    (1404, 'func-org-update', '更新机构', 'acc.org.update', '更新机构信息', '/yl-acc/bdf-boot/orup/updateOrg', 'api', 'POST', 140, 1, now(), now(), 1),
    (1405, 'func-org-delete', '删除机构', 'acc.org.delete', '删除机构', '/yl-acc/bdf-boot/orup/deleteOrg', 'api', 'POST', 140, 1, now(), now(), 1),
    (1406, 'func-org-user-add', '添加机构用户', 'acc.org.user.add', '向机构添加用户', '/yl-acc/bdf-boot/orup/addOrgUser', 'api', 'POST', 140, 1, now(), now(), 1),
    (1407, 'func-org-user-delete', '移除机构用户', 'acc.org.user.delete', '从机构移除用户', '/yl-acc/bdf-boot/orup/delOrgUser', 'api', 'POST', 140, 1, now(), now(), 1),

    (1601, 'func-tool-test3', '数据源测试', 'acc.tool.test3', '访问 UserController.test3', '/yl-acc/bdf-boot/user/test3', 'api', 'GET', 160, 1, now(), now(), 1)
on conflict (id) do nothing;

insert into yl_acc.role_function (
    id, guid, role_id, function_id, create_time, modify_time, create_user_id
)
select
    row_number() over (order by src.function_id),
    'role-func-' || lpad(row_number() over (order by src.function_id)::text, 4, '0'),
    1,
    src.function_id,
    now(),
    now(),
    1
from (
    values
        (100), (110), (120), (130), (140), (150), (160),
        (1001), (1002), (1003), (1004), (1005), (1006), (1007), (1008), (1009), (1010), (1011),
        (1101), (1102), (1103), (1104), (1105), (1106), (1107), (1108), (1109), (1110), (1111),
        (1201), (1202), (1203), (1204), (1205), (1206), (1207),
        (1301), (1302), (1303), (1304), (1305), (1306), (1307), (1308), (1309), (1310), (1311), (1312),
        (1401), (1402), (1403), (1404), (1405), (1406), (1407),
        (1601)
) as src(function_id)
where not exists (
    select 1
    from yl_acc.role_function rf
    where rf.role_id = 1
      and rf.function_id = src.function_id
);

select setval('yl_acc.s_org', greatest((select coalesce(max(id), 1) from yl_acc.org), 1), true);
select setval('yl_acc.s_user', greatest((select coalesce(max(id), 1) from yl_acc."user"), 1), true);
select setval('yl_acc.s_role', greatest((select coalesce(max(id), 1) from yl_acc."role"), 1), true);
select setval('yl_acc.s_permission', greatest((select coalesce(max(id), 1) from yl_acc."permission"), 1), true);
select setval('yl_acc.s_application', greatest((select coalesce(max(id), 1) from yl_acc.application), 1), true);
select setval('yl_acc.s_application_manager', greatest((select coalesce(max(id), 1) from yl_acc.application_manager), 1), true);
select setval('yl_acc.s_function', greatest((select coalesce(max(id), 1) from yl_acc."function"), 1), true);
select setval('yl_acc.s_user_role', greatest((select coalesce(max(id), 1) from yl_acc.user_role), 1), true);
select setval('yl_acc.s_role_permission', greatest((select coalesce(max(id), 1) from yl_acc.role_permission), 1), true);
select setval('yl_acc.s_role_function', greatest((select coalesce(max(id), 1) from yl_acc.role_function), 1), true);
select setval('yl_acc.s_user_org', greatest((select coalesce(max(id), 1) from yl_acc.user_org), 1), true);

commit;
