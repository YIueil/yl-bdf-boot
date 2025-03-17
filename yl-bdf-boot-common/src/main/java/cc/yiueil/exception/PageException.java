package cc.yiueil.exception;

/**
 * PageException 分页异常类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:51
 * @version 1.0
 */
public class PageException extends RuntimeException{
    public PageException() {
    }

    public PageException(String message) {
        super(message);
    }

    public PageException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageException(Throwable cause) {
        super(cause);
    }

    public PageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
