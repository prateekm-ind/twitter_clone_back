package com.example.twitter.clone;

import com.example.twitter.clone.config.CorsConfig;
import com.example.twitter.clone.config.RedisConfig;
import com.example.twitter.clone.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.twitter.clone.repository")
@Import({SwaggerConfig.class, RedisConfig.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
