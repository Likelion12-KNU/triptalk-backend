package com.example.triptalk.util;

import com.example.triptalk.exception.TokenException;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;


@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final int ACCESS_TOKEN_EXPIRATION_SEC=60*30;  //30분
    @Value("${com.tenius.jwt.secret}")
    private String jwtSecret;


    /**
     * Access Token 생성 함수
     * @param authentication 인증 정보
     * @return access token
     */
    public String getAccessToken(Authentication authentication){
        return generateTokenFromUsername(authentication.getName(), ACCESS_TOKEN_EXPIRATION_SEC);
    }

    /**
     * 토큰 문자열에서 username을 추출하는 함수
     * @param token 토큰 문자열
     * @return username
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * JWT 생성 함수
     * @param username 유저 이름
     * @param seconds 유효 시간
     * @return 토큰 문자열
     */
    private String generateTokenFromUsername(String username, int seconds) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusSeconds(seconds).toInstant()))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰 검증 함수
     * @param token 토큰
     * @return 검증 결과
     * @throws TokenException
     */
    public boolean validateToken(String token) throws TokenException {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException e) {
            throw new TokenException(TokenException.TOKEN_ERROR.MALFORM);
        } catch (ExpiredJwtException e) {
            throw new TokenException(TokenException.TOKEN_ERROR.EXPIRED);
        } catch(SignatureException signatureException){
            throw new TokenException(TokenException.TOKEN_ERROR.BADSIGN);
        } catch (UnsupportedJwtException e) {
            throw new TokenException(TokenException.TOKEN_ERROR.UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            throw new TokenException(TokenException.TOKEN_ERROR.UNACCEPT);
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
