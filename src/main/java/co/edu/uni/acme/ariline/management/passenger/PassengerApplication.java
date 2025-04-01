package co.edu.uni.acme.ariline.management.passenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import co.edu.uni.acme.aerolinea.commons.configuration.ResourceServerConfig;

@SpringBootApplication(scanBasePackages = "co.edu.uni.acme.aerolinea")
@EntityScan("co.edu.uni.acme.aerolinea")
@ComponentScan(basePackages = {"co.edu.uni.acme.aerolinea"})
@Import(ResourceServerConfig.class)
public class PassengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassengerApplication.class, args);
	}

}
