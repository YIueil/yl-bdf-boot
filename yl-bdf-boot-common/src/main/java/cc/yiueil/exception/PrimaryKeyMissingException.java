package cc.yiueil.exception;

/**
 * PrimaryKeyMissingException 主键缺失异常
 * 通常发生于 <删改查>
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/3/18 17:41
 */
public class PrimaryKeyMissingException extends RuntimeException{
    public PrimaryKeyMissingException() {
        super();
    }

    public PrimaryKeyMissingException(String message) {
        super(message);
    }

    public PrimaryKeyMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrimaryKeyMissingException(Throwable cause) {
        super(cause);
    }

    protected PrimaryKeyMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
