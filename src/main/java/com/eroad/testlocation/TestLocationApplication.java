package com.eroad.testlocation;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class TestLocationApplication {
	public static void main(String[] args) {
		for (String arg : args) {
			System.out.println(arg);
		}
		SpringApplication application = new SpringApplication(TestLocationApplication.class);
		application.run(args);
	}
}
