package com.seeu.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.seeu.domains.JWTToken;

import java.util.Date;

public class JWTUtil {
    private static final String SECRET = resolveSecret();
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    private static String resolveSecret() {
        String secret = System.getenv("JWT_SECRET");
        if (secret != null && !secret.isBlank()) {
            return secret;
        }
        return "seeu-jwt-secret-only-for-demo";
    }
    private static final long ACCESS_EXPIRY_MS = 15 * 60 * 1000L;
    private static final long REFRESH_EXPIRY_MS = 7L * 24 * 60 * 60 * 1000;

    private JWTUtil() {
    }

    public static String generateAccessToken(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withClaim("type", "access")
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_EXPIRY_MS))
                .sign(ALGORITHM);
    }

    public static String generateRefreshToken(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withClaim("type", "refresh")
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_EXPIRY_MS))
                .sign(ALGORITHM);
    }

    public static DecodedJWT validateToken(String token) throws JWTVerificationException {
        return JWT.require(ALGORITHM).build().verify(token);
    }

    public static JWTToken refreshToken(String refreshToken) throws JWTVerificationException {
        DecodedJWT jwt = validateToken(refreshToken);
        if (!"refresh".equals(jwt.getClaim("type").asString())) {
            throw new JWTVerificationException("Invalid token type");
        }
        String subject = jwt.getSubject();
        return new JWTToken(generateAccessToken(subject), refreshToken);
    }
}
