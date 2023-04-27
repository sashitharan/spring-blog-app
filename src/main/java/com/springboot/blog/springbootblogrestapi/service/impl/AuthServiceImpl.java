package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Role;
import com.springboot.blog.springbootblogrestapi.entity.User;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPIException;
import com.springboot.blog.springbootblogrestapi.payload.LoginDTO;
import com.springboot.blog.springbootblogrestapi.payload.RegisterDTO;
import com.springboot.blog.springbootblogrestapi.repository.RoleRepository;
import com.springboot.blog.springbootblogrestapi.repository.UserRepository;
import com.springboot.blog.springbootblogrestapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.springboot.blog.springbootblogrestapi.AppConfiguration.SecurityConfig.passwordEncoder;
import static com.springboot.blog.springbootblogrestapi.utils.AppConstants.ROLE_USER;

@Service
public class AuthServiceImpl implements AuthService {


    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        // spring security context holder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User Logged in Successfully.";
    }

    @Override
    public String register(RegisterDTO registerDTO) {

        //check if username exists in database
        if(userRepository.existsByUsername(registerDTO.getUsername())||(userRepository.existsByEmail(registerDTO.getEmail()))){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Username or Email exists in database!");
        }

        //user class, pass all the things from existing dto to user object
        User user = new User();
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roleSet = new HashSet<>();
        Role userRole = roleRepository.findByName(ROLE_USER).get();
        roleSet.add(userRole);
        user.setRoles(roleSet);
        userRepository.save(user);
        return "User Registered Successfully";
    }
}
