package com.springboot.blog.springbootblogrestapi;

import com.springboot.blog.springbootblogrestapi.entity.Role;
import com.springboot.blog.springbootblogrestapi.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Blog App REST APIs",
                description = "Spring Boot Blog APP REST API's Documentation",
                version = "v1.0.0",
                contact = @Contact(
                        name = "Sashi",
                        email ="sarsheyy@gmail.com",
                        url = "https://www.google.com"
                ),
                license = @License(
                        name="Apache 2.0",
                        url="https://www.google.com.sg"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Blog App Documentation",
                url="https://github.com/sashitharan/spring-blog-app"
        )
)
public class SpringbootBlogRestApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
    }

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {

        Role admin = new Role();
        admin.setName("ROLE_ADMIN");
        roleRepository.save(admin);

        Role user = new Role();
        user.setName("ROLE_USER");
        roleRepository.save(user);

    }
}
