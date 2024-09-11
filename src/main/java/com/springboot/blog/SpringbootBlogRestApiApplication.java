package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spting Boot Blog App Rest APIs",
				description = "spring boot blog app rest apis documentation.",
				version = "v1.0",
				contact = @Contact(
						name = "Manoj",
						email = "manojhc110@gmail.com",
						url = "https://www.javaguides.net"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.javaguides.net/license"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation",
				url = "https://github.com/manoj-H-C/springboot-blog-rest-api"
		)
)
public class SpringbootBlogRestApiApplication {
    @Bean
	public ModelMapper modelMapper(){
		return  new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}
