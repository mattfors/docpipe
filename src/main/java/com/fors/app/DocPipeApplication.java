package com.fors.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.fors")
public class DocPipeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocPipeApplication.class, args);
    }
}
