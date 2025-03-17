package cc.yiueil.enums;

import lombok.Getter;

/**
 * MessageTypeEnum  消息状态枚举
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/4/5 20:29
 */
@Getter
public enum MessageStateEnum {
    /**
     * unRead: 未读
     * read: 已读
     */
    unRead("未读"), read("已读");

    private final String name;

    MessageStateEnum(String name) {
        this.name = name;
    }
}
