package br.com.thiagoodev.blogapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@SpringBootConfiguration
@EnableJpaAuditing
@EnableScheduling
public class BlogapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogapiApplication.class, args);
	}

}
