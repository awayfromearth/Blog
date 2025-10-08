package com.cm.weblog.jwt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 * 封装 JWT 相关的功能
 */
@Component
public class JwtTokenHelper implements InitializingBean {
    // 签发人
    @Value("${jwt.issuer}")
    private String issuer;

    // 秘钥
    private Key key;

    // 解析器
    private JwtParser jwtParser;

    // 过期时间
    @Value("${jwt.tokenExpireTime}")
    private Long tokenExpireTime;

    /**
     * 解码 application.yml 配置文件中的 secret 字段 为秘钥
     * @param base64Key secret 的base64编码
     */
    @Value("${jwt.secret}")
    public void setBase64Key(String base64Key) {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
    }

    /**
     * 初始化解析器
     * @throws Exception 异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        jwtParser = Jwts.parserBuilder().requireIssuer(issuer)
                .setSigningKey(key).setAllowedClockSkewSeconds(10)
                .build();
    }

    /**
     * 编译 Token
     * @param username 用户名
     * @return 根据用户名加密生成的 Token 且一小时后失效
     */
    public String generateToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusMinutes(tokenExpireTime);

        return Jwts.builder().setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key)
                .compact();
    }

    /**
     * 解析 Token
     * @param token Token
     * @return 解码 Token 中的信息
     */
    public Jws<Claims> parseToken(String token) {
        try {
            return jwtParser.parseClaimsJws(token);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Token 不可用", e);
        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("Token 失效", e);
        }
    }

    /**
     * 校验 Token 是否可用
     * @param token Token
     */
    public void validateToken(String token) {
        jwtParser.parseClaimsJws(token);
    }

    /**
     * 根据 Token 获取用户名
     * @param token Token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     * 生成一个 Base64 编码的安全秘钥
     * @return 安全秘钥
     */
    private static String generateBase64Key() {
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) {
        String key = generateBase64Key();
        System.out.println("key: " + key);
    }
}
