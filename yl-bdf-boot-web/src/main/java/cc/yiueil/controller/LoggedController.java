package cc.yiueil.controller;

import cc.yiueil.context.RequestContextThreadLocal;
import cc.yiueil.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

/**
 * LoggedController
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2022/7/3 20:25
 */
public interface LoggedController extends BaseController {

    /**
     * 获取以及登录的用户信息
     * @param request 请求体
     * @return 用户信息
     */
    default UserDto getUser(HttpServletRequest request) {
        return RequestContextThreadLocal.getUser();
    }
}