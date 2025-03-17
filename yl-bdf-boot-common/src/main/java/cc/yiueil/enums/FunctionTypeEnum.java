package cc.yiueil.enums;

import lombok.Getter;

/**
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 21:14
 * @version 1.0 权限功能分类枚举
 */
@Getter
public enum FunctionTypeEnum {
    /**
     * api: 接口类型权限资源
     * button: 按钮类型权限资源
     * menu: 菜单类型权限资源
     */
    api("接口"), button("按钮"), menu("菜单");
    private final String type;
    FunctionTypeEnum(String type) {
        this.type = type;
    }
}
