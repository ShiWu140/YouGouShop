package com.training.aigoushopapi.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;

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
}
