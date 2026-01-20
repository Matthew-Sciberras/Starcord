package com.starcord.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication() // Remove eventually, temporary fix exclude = {DataSourceAutoConfiguration.class }
public class StarcordApplication {
	public static void main(String[] args) {
		SpringApplication.run(StarcordApplication.class, args);
	}
}
