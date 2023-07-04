package com.ecommerce.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Map.Entry;

@SpringBootApplication
@ComponentScan(basePackages = "com.ecommerce.backend.services")
@ComponentScan(basePackages = "com.ecommerce.backend.mapper")
public class SpringBootHardwareEcommerceApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringBootHardwareEcommerceApplication.class, args);
	}

}
