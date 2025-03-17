package cc.yiueil.enums;

import lombok.Getter;

/**
 * MessageTypeEnum  消息类型枚举
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/4/5 20:29
 */
@Getter
public enum MessageTypeEnum {
    /**
     * user: 用户消息
     * system: 系统消息
     */
    user("用户"), system("系统");

    private final String name;

    MessageTypeEnum(String name) {
        this.name = name;
    }
}
