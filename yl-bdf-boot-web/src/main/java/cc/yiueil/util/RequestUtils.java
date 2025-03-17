package cc.yiueil.util;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * RequestUtils httpRequest请求工具类
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/3/20 17:17
 */
public class RequestUtils {
    private static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String UNKNOWN = "unknown";

    /**
     * 获取应用基础地址
     * @param request 请求体
     * @return 获取应用请求地址
     */
    public static String getBaseUrl(HttpServletRequest request) {
        StringBuffer requestUrl = request.getRequestURL();
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        int startIndex = requestUrl.indexOf(requestUri);
        return requestUrl.substring(0, startIndex + contextPath.length());
    }

    /**
     * 获取客户端 IP 地址
     * @param request 请求体
     * @return 客户端 IP 地址
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 判断请求是否为 AJAX 请求
     * @param request 请求体
     * @return 是否ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    /**
     * 判断请求是否为 JSON 请求
     * @param request 请求体
     * @return 是否json请求
     */
    public static boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.startsWith(JSON_CONTENT_TYPE);
    }

    /**
     * 从请求体中获取 JSON 数据
     *
     * @param request 请求体
     * @return jsonStr json字符串
     * @throws IOException io异常
     */
    public static String getJsonStrFromBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        return outputStream.toString("UTF-8");
    }

    /**
     * 获取参数值
     *
     * @param request      request
     * @param name         参数名称
     * @param defaultValue 默认值
     * @return 参数值
     */
    public static Object getParameter(HttpServletRequest request, String name, Object defaultValue) {
        String value = request.getParameter(name);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        return value;
    }
}