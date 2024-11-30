package com.example.filmblogproject.application.service;

import com.example.filmblogproject.interfaceAdapters.dto.request.service.RequestByString;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtService {


    public static String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private Set<String> blackList = new HashSet<>();

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // Header'dan Authorization bilgisi alınır
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // "Bearer " kısmını çıkartarak sadece token'ı döndür
            return bearerToken.substring(7); // "Bearer " 7 karakter uzunluğundadır
        }
        return null; // Header'da Authorization yoksa veya format doğru değilse null döndür
    }

    public void addToBlackList(RequestByString request) {
        String token = request.value().substring(7);
        blackList.add(token);
        SecurityContextHolder.clearContext();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }


    public Boolean isTokenBlackListed(RequestByString request) {
        return blackList.contains(request.value());
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    public Boolean validateToken(RequestByString request) {
        try {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(request.value()).getBody();
        return !claims.getExpiration().before(new Date());
    } catch(SignatureException e) {
        // İmza geçersizse
        System.out.println("JWT'nin imzası doğrulanamadı: " + e.getMessage());
    } catch (
    ExpiredJwtException e) {
        // Token süresi dolmuşsa
        System.out.println("JWT'nin süresi dolmuş: " + e.getMessage());
    } catch (Exception e) {
        // Diğer hatalar
        System.out.println("JWT geçersiz: " + e.getMessage());
    }
        return false;
    }

    public String generateToken(String userName,String sessionId, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("sessionId", sessionId);
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}