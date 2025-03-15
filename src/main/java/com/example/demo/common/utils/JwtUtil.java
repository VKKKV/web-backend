package com.example.demo.common.utils;

import com.example.demo.common.exception.TradeException;
import com.example.demo.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * 利用开源工具java-jwt，获取jwt令牌。写成工具类，方便被调用。
 * 这里要熟悉jwt的格式，格式为 header.payload.signature
 */
public class JwtUtil {
    private static final long tokenExpiration = 60 * 60 * 1000L;//令牌过期时长 1h
    private static final SecretKey tokenSignKey = Keys.hmacShaKeyFor("M0PKKI6pYGVWWfDZw90a0lTpGYX1d4AQ".getBytes());//签名秘钥，用字节数组转换成有效秘钥。

    /**
     * 根据userId和username获取token
     *
     * @param userId
     * @param username
     * @return
     */
    public static String createToken(Integer userId, String username) {
        return Jwts.builder()
                .subject("USER_INFO")
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("username", username)
                .signWith(tokenSignKey)
                .compact();
    }

    /**
     * 校验前端传入的jwt token合法性，解析出payload。
     *
     * @param token
     * @return
     */
    public static Claims parseToken(String token) {
        //判断token是否为null
        if (token == null) {
            throw new TradeException(ResultCodeEnum.ADMIN_LOGIN_AUTH);//未登录异常
        }

        try {
//            //jwt解析器
//            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(tokenSignKey).build();
//            //返回payload
//            //return jwtParser.parseClaimsJws(token).getBody();//解析token验证签名后，返回payload，即userId、username
//            Jws<Claims> jwsClaims = jwtParser.parseClaimsJws(token);//解析token，得到jws（带有签名的jwt）
//            return jwsClaims.getBody();

            // api update in jwt 0.12.0
            return Jwts.parser()
                    .verifyWith(tokenSignKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            throw new TradeException(ResultCodeEnum.TOKEN_EXPIRED);//token过期
        } catch (JwtException e) {
            throw new TradeException(ResultCodeEnum.TOKEN_INVALID);//token非法
        }
    }

    /**
     * 因为除了登录接口之外的所有接口都需要token，
     * 临时生成长期token，配置knife4j，用来测试接口。
     * web-admin
     * header字段：access-token
     * token值：eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVU0VSX0lORk8iLCJleHAiOjE3NTMxOTY2MDQsInVzZXJJZCI6MSwidXNlcm5hbWUiOiJjaGVuY2hhb2ZlbmcifQ.uap0kemljOhntLRYzx5LGHpM_kbgFheG6M9Ra0CXOhc
     * <p>
     * web-app
     * token值：eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVU0VSX0lORk8iLCJleHAiOjE3NTMyNzU4MTMsInVzZXJJZCI6OCwidXNlcm5hbWUiOiIxNTcyMjkyMjg2MiJ9.dML-IqPwgHh8e6TtxyhdhnRNg04jv3VEf7eyL67nOEU
     *
     * @param args
     */
    public static void main(String[] args) {
        //web-admin
        //String token = JwtUtil.createToken(1L, "chenchaofeng");
        //web-app
        //String token = JwtUtil.createToken(8L, "15722922862");
        //System.out.println(token);
    }
}