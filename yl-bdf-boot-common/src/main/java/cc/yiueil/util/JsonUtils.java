package cc.yiueil.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * JSONUtils json工具类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:19
 * @version 1.0
 */
@Slf4j
public class JsonUtils {
    private static class SingletonRegistry {
        private static final ObjectMapper OBJECT_MAPPER;
        static {
            OBJECT_MAPPER = new ObjectMapper();
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 忽略不能反序列化的字段
            OBJECT_MAPPER.findAndRegisterModules(); // 注册 jsr 扩展
        }
    }

    /**
     * 单例适用ObjectMapper, 提高性能, 注意配置不变线程安全, 使用过程中不能修改配置
     * @return objectMapper实例
     */
    public static ObjectMapper getInstance() {
        return SingletonRegistry.OBJECT_MAPPER;
    }

    public static String toJsonString(Object obj) {
        try {
            return getInstance().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException();
    }

    public static <T> T parse(Class<T> clazz, String jsonStr) {
        try {
            return getInstance().readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException();
    }

}
