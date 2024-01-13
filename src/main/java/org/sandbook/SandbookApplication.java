package org.sandbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.sandbook")
public class SandbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(SandbookApplication.class, args);
	}

}
