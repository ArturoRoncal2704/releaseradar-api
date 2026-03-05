package com.arturo.releaseradarapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReleaseradarApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReleaseradarApiApplication.class, args);
    }

}
