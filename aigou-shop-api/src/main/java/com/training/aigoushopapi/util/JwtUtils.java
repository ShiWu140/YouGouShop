package com.training.aigoushopapi.util;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/**
 * JWT工具类
 */
public class JwtUtils {

    public static final String JWT_KEY_USERNAME = "username";
    public static final int EXPIRE_MINUTES = 120;

    /**
     * 私钥加密token
     */
    public static String generateToken(String username, PrivateKey privateKey, int expireMinutes) {

        return Jwts.builder()
                .claim(JWT_KEY_USERNAME, username)
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 从token解析用户
     */
    public static String getUsernameFromToken(String token, PublicKey publicKey) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String username = (String) body.get(JWT_KEY_USERNAME);
        return username;
    }

    public static void main(String[] args) {
        //生成jwt
        String token = JwtUtils.generateToken("zhangsan", RsaUtils.privateKey, EXPIRE_MINUTES);
        System.out.println(token);
        //解析jwt
        String username = JwtUtils.getUsernameFromToken(token, RsaUtils.publicKey);
        System.out.println(username);
    }

    public static boolean isTokenExpired(String token, PublicKey publicKey) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException ex) {
            // 如果抛出过期异常，则返回 true
            return true;
        }
    }

    /**
     * 刷新Token（在Token过期后生成新的Token）
     */
    public static String refreshToken(String token, PublicKey publicKey, PrivateKey privateKey, int expireMinutes) {
        // 获取原始用户名
        String username = getUsernameFromToken(token, publicKey);
        // 生成新的Token
        return generateToken(username, privateKey, expireMinutes);
    }
}
