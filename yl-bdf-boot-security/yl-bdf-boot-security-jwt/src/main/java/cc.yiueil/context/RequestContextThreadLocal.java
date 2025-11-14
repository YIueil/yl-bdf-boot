package cc.yiueil.context;

import cc.yiueil.dto.UserDto;
import cc.yiueil.util.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * RequestContextThreadLocal 请求上下文ThreadLocal对象, 包含了用户的基本信息以及参数信息
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/3/31 3:25
 */
public class RequestContextThreadLocal {
    private static final ThreadLocal<UserDto> USER_THREAD_LOCAL = new ThreadLocal<>();

    private static final ThreadLocal<Map<String, Object>> CONTEXT_MAP_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUser(UserDto userDto) {
        USER_THREAD_LOCAL.set(userDto);
    }

    public static UserDto getUser() {
        return USER_THREAD_LOCAL.get();
    }

    public static void setParams(String key, Object value) {
        Map<String, Object> contextMap = CONTEXT_MAP_THREAD_LOCAL.get();
        if (contextMap == null) {
            contextMap = new HashMap<>(16);
        }
        contextMap.put(key, value);
        CONTEXT_MAP_THREAD_LOCAL.set(contextMap);
    }

    /**
     * 通过map设置上下文参数
     * @param map map
     */
    public static void setParamsFromMap(Map<String, ?> map) {
        Map<String, Object> contextMap = CONTEXT_MAP_THREAD_LOCAL.get();
        if (contextMap == null) {
            contextMap = new HashMap<>(16);
        }
        contextMap.putAll(map);
        CONTEXT_MAP_THREAD_LOCAL.set(contextMap);
    }

    /**
     * 获取请求上下文参数
     * @param key 参数键
     * @return 参数值
     */
    public static Object getParams(String key) {
        Map<String, Object> contextMap = CONTEXT_MAP_THREAD_LOCAL.get();
        if (MapUtils.isNotEmpty(contextMap)) {
            return contextMap.get(key);
        }
        return null;
    }

    public static void clear() {
        CONTEXT_MAP_THREAD_LOCAL.remove();
    }
}