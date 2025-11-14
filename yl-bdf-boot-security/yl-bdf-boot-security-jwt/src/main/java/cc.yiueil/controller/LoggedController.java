package cc.yiueil.controller;


import cc.yiueil.dto.UserDto;
import com.auth0.jwt.interfaces.Claim;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    UserDto getUser(HttpServletRequest request);

    /**
     * 获取上下文哈希表: 适用于publicToken
     * @param request 请求体
     * @return 上下文哈希表
     */
    Map<String, Object> getContextMap(HttpServletRequest request);
}
