package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app-jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    //    generate jwt token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return token;

    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get username from JWT token
//    public String getUsername(String token){
//
//        return Jwts.parser()
//                .verifyWith((SecretKey) key())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload()
//                .getSubject();
//    }

    //    get username from JWT token
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key()) // Use the signing key here
                .parseClaimsJws(token) // Change to parseClaimsJws for JWT
                .getBody(); // Get the payload (claims)

        return claims.getSubject(); // Extract the username
    }

//    validate jwt token
    public boolean validateToken(String token) {
      try{
          Jwts.parser()
                  .setSigningKey(key())
                  .parseClaimsJws(token);
          return  true;
      }catch (MalformedJwtException malformedJwtException){
          throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
      }catch (ExpiredJwtException expiredJwtException){
          throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
      }catch (UnsupportedJwtException unsupportedJwtException){
          throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
      }catch (IllegalArgumentException illegalArgumentException){
          throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Jwt claims string is null or empty");
      }
    }
}
