package com.springboot.blog.springbootblogrestapi.security;

import com.springboot.blog.springbootblogrestapi.exception.BlogAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    //utility method to generate JWT Token
    public String generateToken(Authentication authentication){
        String username = authentication.getName(); //get username or email when they login

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        //create tokens
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
        return token;

    }

    private Key key(){
        //decode key and return key
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //Get username from JWT Token
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        return username;

    }



    //Validate JWT Token
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        }catch (MalformedJwtException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid JWT token");
        }catch (ExpiredJwtException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Expired JWT Token");
        }catch (UnsupportedJwtException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Unsupported JWT Token");
        }catch (IllegalArgumentException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"JWT claims string is empty");
        }

    }


}
