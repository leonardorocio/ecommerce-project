package com.ecommerce.hardware;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.ecommerce.hardware.services")
@ComponentScan(basePackages = "com.ecommerce.hardware.mapper")
public class SpringBootHardwareEcommerceApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext =
		SpringApplication.run(SpringBootHardwareEcommerceApplication.class, args);

//		for (String name: applicationContext.getBeanDefinitionNames()) {
//			if (name.contains("comment")) {
//				System.out.println(name);
//			}
//		}
	}

}
