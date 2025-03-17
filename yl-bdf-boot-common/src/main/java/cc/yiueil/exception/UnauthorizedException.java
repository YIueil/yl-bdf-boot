package cc.yiueil.exception;

/**
 * UnauthorizedException 未授权访问异常
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:51
 * @version 1.0
 */
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
