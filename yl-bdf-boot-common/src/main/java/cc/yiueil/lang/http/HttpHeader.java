package cc.yiueil.lang.http;

/**
 * HttpHeader http头类型定义
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/1/18 16:32
 */
public interface HttpHeader {
    String CONTENT_TYPE = "Content-Type";
    String AUTHORIZATION = "Authorization";
    String USERAGENT = "User-Agent";
    String COOKIE = "Cookie";

    /**
     * 常用浏览器标识
     */
    String USERAGENT_DEFAULT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)";

    /**
     * Content-Type 可选值常量定义
     */
    String CONTENT_TYPE_DEFAULT_VALUE = "application/x-www-form-urlencoded";
    String CONTENT_TYPE_JSON_VALUE = "application/json";
    String CONTENT_TYPE_XML_VALUE = "application/xml";
    String CONTENT_TYPE_TEXT_VALUE = "text/plain";
    String CONTENT_TYPE_MULTIPART_VALUE = "multipart/form-data";

}