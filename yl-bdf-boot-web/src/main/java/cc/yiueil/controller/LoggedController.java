package cc.yiueil.controller;

/**
 * LoggedController 已登录的控制器
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2025/11/10 16:49
 */
public interface LoggedController extends BaseController{
    default Object getUser() {
        return null;
    }
}
