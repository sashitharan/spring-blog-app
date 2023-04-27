package com.springboot.blog.springbootblogrestapi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorEncoder {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("sashi"));
        System.out.println(passwordEncoder.encode("admin"));

//        $2a$10$1Xlj0sQy8b4xuEUAzrdMguSsiR1FtgrBU5kqoo41I3K8tt16zveB6
//        $2a$10$elOLX.pMQ6RIlKGZ5EjTNekRX3ZrLzBPj9bMSA5XXMX2ICDYEfvh6

    }
}
