package cc.yiueil.enums;

/**
 * MediaTypeEnum 媒体类型枚举
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/1/22 11:37
 */
public enum MediaTypeEnum {
    /**
     * text/plain：纯文本格式
     * text/html：HTML格式
     * text/xml：XML格式
     * application/json：JSON数据格式
     * application/octet-stream：二进制流数据（如文件下载）
     * image/png：PNG图片格式
     * image/jpeg：JPEG图片格式
     * multipart/form-data：表单数据格式
     */
    TEXT_PLAIN("text/plain"),
    TEXT_HTML("text/html"),
    TEXT_XML("text/xml"),
    APPLICATION_JSON("application/json"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    IMAGE_PNG("image/png"),
    IMAGE_JPEG("image/jpeg"),
    MULTIPART_FORM_DATA("multipart/form-data");

    private final String value;

    MediaTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 获取媒体类型字符串值
     *
     * @return 媒体类型字符串值
     */
    public String getValue() {
        return value;
    }
}