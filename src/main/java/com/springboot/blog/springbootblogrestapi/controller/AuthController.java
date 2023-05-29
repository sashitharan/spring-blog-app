package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.JWTAuthResponseDTO;
import com.springboot.blog.springbootblogrestapi.payload.LoginDTO;
import com.springboot.blog.springbootblogrestapi.payload.RegisterDTO;
import com.springboot.blog.springbootblogrestapi.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

//    Build Login REST API

    @PostMapping(value={"/login","/signin"})
    public ResponseEntity<JWTAuthResponseDTO>login(@RequestBody LoginDTO loginDTO){
        String token = authService.login(loginDTO);

        JWTAuthResponseDTO jwtAuthResponseDTO = new JWTAuthResponseDTO();
        jwtAuthResponseDTO.setAccessToken(token);
        System.out.println(jwtAuthResponseDTO.toString());
        return ResponseEntity.ok(jwtAuthResponseDTO); //return jwt token response in the api
    }



//    Build Register REST API

    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String>register(@RequestBody RegisterDTO registerDTO){
        String response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }




}
