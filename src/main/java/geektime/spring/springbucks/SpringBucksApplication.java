package geektime.spring.springbucks;

import geektime.spring.springbucks.integration.Barista;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ImportResource;

@EnableCaching(proxyTargetClass = true)
@SpringBootApplication
@ImportResource(locations = "classpath:config/beans.xml")
@EnableBinding(Barista.class)
public class SpringBucksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

}

