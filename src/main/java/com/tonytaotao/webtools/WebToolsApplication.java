package com.tonytaotao.webtools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tonytaotao
 */
@SpringBootApplication
@RestController
public class WebToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebToolsApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

}
