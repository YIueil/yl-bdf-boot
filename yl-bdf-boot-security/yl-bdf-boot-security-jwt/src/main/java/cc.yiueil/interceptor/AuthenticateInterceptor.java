package cc.yiueil.interceptor;

import cc.yiueil.annotation.VerifyToken;
import cc.yiueil.context.RequestContextThreadLocal;
import cc.yiueil.controller.LoggedController;
import cc.yiueil.dto.UserDto;
import cc.yiueil.exception.UnauthorizedException;
import cc.yiueil.properties.JwtProperties;
import cc.yiueil.service.OrupService;
import cc.yiueil.util.JsonUtils;
import cc.yiueil.util.JwtUtils;
import cc.yiueil.util.ParseUtils;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * 应用权限检测拦截器
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/5/30 22:17
 */
@Slf4j
public class AuthenticateInterceptor implements HandlerInterceptor {
    final OrupService orupService;

    final JwtProperties jwtProperties;

    public AuthenticateInterceptor(OrupService orupService, JwtProperties jwtProperties) {
        this.orupService = orupService;
        this.jwtProperties = jwtProperties;
    }

    /**
     * 请求处理之前调用
     *
     * @param request  请求体
     * @param response 响应体
     * @param handler  处理器
     * @return 是否通过拦截器
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            if (handlerMethod.hasMethodAnnotation(VerifyToken.class)) {
                String publicToken = ParseUtils.getString(request.getParameter("publicToken"), null);
                Map<String, Claim> stringClaimMap = verifyPublicToken(publicToken);
                RequestContextThreadLocal.setParamsFromMap(stringClaimMap);
                return stringClaimMap != null;
            } else {
                // 获取Controller类信息
                Object bean = handlerMethod.getBean();
                if (bean instanceof LoggedController) {
                    return verifyToken(request, response);
                }
            }
        }
        return true;
    }

    /**
     * 请求处理完成之后, 视图渲染之前被调用, 如果请求处理过程中发生了异常，该方法可能不会被调用
     *
     * @param request      请求体
     * @param response     响应体
     * @param handler      处理器
     * @param modelAndView 响应体
     * @throws Exception 异常
     */
    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 该方法在整个请求处理完成之后调用, 无论是否发送异常都会调用, 进行资源回收
     *
     * @param request  请求体
     * @param response 响应体
     * @param handler  处理器
     * @param ex       异常响应
     * @throws Exception 异常
     */
    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        // 清理上下文数据防止内存泄漏
        RequestContextThreadLocal.clear();
    }

    /**
     * publicToken校验
     * publicToken 未登录客户端可通过publicToken来实现的接口请求
     *
     * @param publicToken publicToken
     * @return 校验结果
     */
    private Map<String, Claim> verifyPublicToken(String publicToken) {
        return JwtUtils.verifyTokenToMap(publicToken, jwtProperties.getPrivateKey());
    }

    /**
     * token校验
     *
     * @param request  请求体
     * @param response 响应体
     * @return 校验结果
     */
    private boolean verifyToken(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtProperties.getHeaderParamsName();
        String publicToken = jwtProperties.getPublicToken();
        RequestContextThreadLocal.setParams("token", token);
        if (publicToken.equals(token)) {
            return true;
        }
        try {
            DecodedJWT jwt = JwtUtils.verifyToken(token, jwtProperties.getPrivateKey());
            // 将用户放入Thread中
            setUserInfo(jwt);
            // 检查是否续签
            checkNeedReToken(response);
            return true;
        } catch (JWTDecodeException jwtDecodeException) {
            throw new UnauthorizedException("身份校验未通过, 请重新登陆");
        } catch (TokenExpiredException tokenExpiredException) {
            throw new UnauthorizedException("未登陆或授权到期, 请重新登陆");
        }
    }

    /**
     * 判断是否需要续签
     *
     * @param response 响应体
     */
    private void checkNeedReToken(HttpServletResponse response) {
        DecodedJWT jwt = (DecodedJWT) RequestContextThreadLocal.getParams("jwt");
        if (jwt == null) {
            log.error("contextRequestParams is null: jwt");
            return;
        }
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        // 当剩余时间少于三分之一时, 进行续签
        Long jwtExpireSeconds = jwtProperties.getExpireSeconds();
        String jwtHeaderParamsName = jwtProperties.getHeaderParamsName();
        long seconds = ParseUtils.getLong(jwtExpireSeconds, 1800L) / 3;
        ZonedDateTime reTokenZonedDateTime = now.plusSeconds(seconds);
        Instant reTokenInstant = Instant.from(reTokenZonedDateTime);
        Instant expiresInstant = jwt.getExpiresAtAsInstant();
        // 当前时间加三分之一总时间 > jwt过期时间, 则进行续签
        if (reTokenInstant.isAfter(expiresInstant)) {
            UserDto user = orupService.getUser(RequestContextThreadLocal.getUser().getId());
            String newToken = JwtUtils.generateToken(user, ParseUtils.getLong(jwtExpireSeconds, 1800L), jwtProperties.getPrivateKey());
            log.debug("用户续签: {}-{}", user.getUserName(), newToken);
            // 前后端分离, 需要定义前端需要的header
            response.setHeader("Access-Control-Expose-Headers", jwtHeaderParamsName);
            response.setHeader(jwtHeaderParamsName, newToken);
        }
    }

    /**
     * 设置用户信息到线程变量
     *
     * @param jwt jwt
     */
    private void setUserInfo(DecodedJWT jwt) {
        String userInfo = jwt.getClaim("user").asString();
        UserDto userDto = JsonUtils.parse(UserDto.class, userInfo);
        RequestContextThreadLocal.setUser(userDto);
        RequestContextThreadLocal.setParams("jwt", jwt);
    }
}
