package com.postech30.parkingmeter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ParkingmeterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingmeterApplication.class, args);
	}

}
