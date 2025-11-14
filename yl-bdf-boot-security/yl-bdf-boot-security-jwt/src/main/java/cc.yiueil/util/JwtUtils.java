package cc.yiueil.util;

import cc.yiueil.dto.UserDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/5/30 22:24
 */
@Slf4j
public class JwtUtils {

    /**
     * 生成token, 自定义过期时间 单位: 秒
     *
     * @param user 用户信息
     * @return 根据用户信息生成 jwt token
     */
    public static String generateToken(UserDto user, Long expireSeconds, String tokenSecret) {
        try {
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            ZonedDateTime createTime = ZonedDateTime.now(ZoneId.systemDefault());
            ZonedDateTime expireTime = createTime.plusSeconds(expireSeconds);
            return JWT.create()
                    .withHeader(header)
                    .withClaim("user", JsonUtils.toJsonString(user))
                    .withIssuedAt(Instant.from(createTime))
                    .withExpiresAt(Instant.from(expireTime))
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("generate token occur error, error");
            log.error(e.getMessage(), e);
            return null;
        }
    }


    /**
     * 生成token, 支持自定义过期时间 单位: 秒
     *
     * @param map 哈希表
     * @return 根据map生成的token
     */
    public static String generateToken(Map<String, Object> map, int expireSeconds, String tokenSecret) {
        try {
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            JWTCreator.Builder builder = JWT.create().withHeader(header);
            builderMap(map, builder);
            ZonedDateTime createTime = ZonedDateTime.now(ZoneId.systemDefault());
            ZonedDateTime expireTime = createTime.plusSeconds(expireSeconds);
            return builder
                    .withIssuedAt(Instant.from(createTime))
                    .withExpiresAt(Instant.from(expireTime))
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("generate token occur error, error");
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 检验 jwt token 是否正确
     *
     * @param token 待校验的 jwt token 信息
     * @return 校验结果
     */
    public static UserDto verifyTokenUser(String token, String tokenSecret) {
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        String userInfo = jwt.getClaim("user").asString();
        return JsonUtils.parse(UserDto.class, userInfo);
    }

    /**
     * 检验 jwt token 是否正确
     *
     * @param token 待校验的 jwt token 信息
     * @return 校验结果
     */
    public static DecodedJWT verifyToken(String token, String tokenSecret) {
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    /**
     * 检验 jwt token 是否正确
     *
     * @param token 待校验的 jwt token 信息
     * @return 校验结果
     */
    public static Map<String, Claim> verifyTokenToMap(String token, String tokenSecret) {
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    /**
     * 将map内容添加到builder中
     *
     * @param map     map
     * @param builder JWTCreator.Builder
     */
    private static void builderMap(Map<String, Object> map, JWTCreator.Builder builder) {
        map.forEach((key, value) -> {
            if (value instanceof Boolean) {
                builder.withClaim(key, (Boolean) value);
            } else if (value instanceof Integer) {
                builder.withClaim(key, (Integer) value);
            } else if (value instanceof Long) {
                builder.withClaim(key, (Long) value);
            } else if (value instanceof Double) {
                builder.withClaim(key, (Double) value);
            } else if (value instanceof String) {
                builder.withClaim(key, (String) value);
            } else if (value instanceof Date) {
                builder.withClaim(key, (Date) value);
            } else if (value instanceof Instant) {
                builder.withClaim(key, (Instant) value);
            } else if (value instanceof Map) {
                builder.withClaim(key, (Map<String, ?>) value);
            } else if (value instanceof List) {
                builder.withClaim(key, (List<?>) value);
            } else {
                builder.withClaim(key, JsonUtils.toJsonString(value));
            }
        });
    }
}