package com.mrbdi.vehiclerunmiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VehiclerunmilesApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehiclerunmilesApplication.class, args);
	}

}
