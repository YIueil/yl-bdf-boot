package cc.yiueil.exception;

import cc.yiueil.enums.ConfigErrorEnum;
import lombok.Getter;

/**
 * 配置相关异常（如缺失、格式错误、无效值等）
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2025/3/18 16:04
 */
@Getter
public final class PropertiesException extends RuntimeException {
    private static final String DEFAULT_MSG_TEMPLATE = "配置项错误 [key=%s] - %s";

    // 单独存储配置项的 key，便于捕获异常后处理
    private final String configKey;

    private final ConfigErrorEnum configErrorEnum;


    /**
     * @param configKey 配置项的 key（如 "app.security.jwt.secret"）
     * @param message   错误详情（如 "未找到配置值"）
     */
    public PropertiesException(String configKey, String message, ConfigErrorEnum configErrorEnum) {
        super(String.format(DEFAULT_MSG_TEMPLATE, configKey, message));
        this.configKey = configKey;
        this.configErrorEnum = configErrorEnum;
    }

    /**
     * @param configKey 配置项的 key
     * @param message   错误详情
     * @param cause     触发此异常的底层原因（如解析异常）
     */
    public PropertiesException(String configKey, String message, Throwable cause, ConfigErrorEnum configErrorEnum) {
        super(String.format(DEFAULT_MSG_TEMPLATE, configKey, message), cause);
        this.configKey = configKey;
        this.configErrorEnum = configErrorEnum;
    }

    /**
     * 适用于仅需传递 key 的简单场景（如配置缺失）
     */
    public PropertiesException(String configKey, ConfigErrorEnum configErrorEnum) {
        this(configKey, "配置项缺失或无效", configErrorEnum);
    }

}