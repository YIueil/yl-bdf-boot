package cc.yiueil.exception;

/**
 * FileUploadException 文件上传异常
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/1/18 12:10
 */
public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }
}
