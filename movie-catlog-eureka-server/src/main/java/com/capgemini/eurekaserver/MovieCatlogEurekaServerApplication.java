package com.capgemini.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MovieCatlogEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatlogEurekaServerApplication.class, args);
	}

}
