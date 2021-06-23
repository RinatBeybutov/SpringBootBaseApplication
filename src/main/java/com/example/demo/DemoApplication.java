package com.example.demo;

import com.example.demo.DTO.UserKafka;
import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
@EnableKafka
public class DemoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

	@KafkaListener(topics = "users")
	public static void messageListener(String record) throws IOException {

		UserKafka userKafka = new Gson().fromJson(record, UserKafka.class);
		FileWriter fileWriter = new FileWriter("src/main/resources/activityLog.txt", true);
		String text = "User - " + userKafka.getName() + " is now " +
				userKafka.getStatus() + ", " + new Date().toString() + "\n";
		fileWriter.write(text);
		fileWriter.flush();

	}
}
