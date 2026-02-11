package com.fors.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.fors")
@EnableJpaRepositories(basePackages = "com.fors.persistence")
@EntityScan(basePackages = "com.fors.persistence.entity")
public class DocPipeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocPipeApplication.class, args);
    }
}
