package wit.zalicz.pap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("wit.zalicz.pap")
public class PapApplication {

	public static void main(String[] args) {
		SpringApplication.run(PapApplication.class, args);
	}

}
