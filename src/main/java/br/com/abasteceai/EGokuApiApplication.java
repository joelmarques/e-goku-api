package br.com.abasteceai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EGokuApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EGokuApiApplication.class, args);
	}
}