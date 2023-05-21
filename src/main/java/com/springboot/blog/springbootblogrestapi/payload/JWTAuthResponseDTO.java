package com.springboot.blog.springbootblogrestapi.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthResponseDTO {

    private String accessToken;
    private String tokenType = "Bearer";

    @Override
    public String toString() {
        return "JWTAuthResponseDTO{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
