package es.mdef.schedulio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchedulioApplication {
	public static final Logger log = LoggerFactory.getLogger(SchedulioApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SchedulioApplication.class, args);
	}

}
