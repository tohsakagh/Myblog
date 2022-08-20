package com.gh.myblog_backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gh.myblog_backend.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * myblog_backend
 * 2022/8/15 12:53
 * Origin
 */
@SuppressWarnings("all")
@Slf4j
public class TokenUtil {

    private static final long EXPIRE_TIME = 10 * 60 * 60 * 1000;
    private static final String TOKEN_SECRET = "Hikari20210714";  //密钥盐

    /**
     * 签名生成
     *
     * @param user
     * @return
     */
    public static String sign(User user) {
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userId", user.getId().toString())
                    .withClaim("userType", user.getType())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 签名验证
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            log.info("认证通过：");
            log.info("userId: " + jwt.getClaim("userId").asString());
            log.info("userType: " + jwt.getClaim("userType").asString());
            log.info("过期时间：      " + jwt.getExpiresAt());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 管理员认证
     *
     * @param token
     * @return
     */
    public static boolean adminVerify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            if ("0".equals(jwt.getClaim("userType").asString())) {
                log.info("站主认证通过");
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
