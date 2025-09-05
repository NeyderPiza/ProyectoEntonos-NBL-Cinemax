package com.uis.entornos.servicio;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // 1. Clave Secreta: Es como la contraseña para firmar y verificar los tokens.
    // DEBE ser larga y compleja. En un proyecto real, se guarda fuera del código.
    // Puedes generar una en: https://www.allkeysgenerator.com/ (256-bit)
    private static final String SECRET_KEY = "NDI0NzNkNzM1NzY3NTU2YzQ5NGU0MjQ5MmQ0NDZiNzU3MzU4MmQzMTUxNDE1MzY4Nzc0MjZlNjQ1MTVmN2I0Mw==";

    // 2. Método para generar un token JWT
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
            .builder()
            .setClaims(extraClaims) // Información adicional (claims)
            .setSubject(user.getUsername()) // El "sujeto" del token (el email del usuario)
            .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de creación
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Caduca en 24 horas
            .signWith(getKey(), SignatureAlgorithm.HS256) // Firma el token con la clave secreta
            .compact();
    }

    // 3. Método para obtener la clave de firma
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 4. Método para obtener el username (email) del token
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    // 5. Método para validar si un token es correcto
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 6. Métodos privados auxiliares para procesar el token
    private Claims getAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
