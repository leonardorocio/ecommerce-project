package com.ecommerce.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ecommerce.backend.services")
@ComponentScan(basePackages = "com.ecommerce.backend.mapper")
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class })
public class SpringBootHardwareEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHardwareEcommerceApplication.class, args);
	}

}
