package co.edu.uni.acme.airline.passenger.module;

import co.edu.uni.acme.aerolinea.commons.configuration.ResourceServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"co.edu.uni.acme.aerolinea", "co.edu.uni.acme.airline"})
@EntityScan("co.edu.uni.acme.aerolinea")
@ComponentScan(basePackages = {"co.edu.uni.acme.aerolinea", "co.edu.uni.acme.airline"})
@Import(ResourceServerConfig.class) // Solo si usas config de seguridad
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
