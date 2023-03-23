package geektime.spring.springbucks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@EnableCaching(proxyTargetClass = true)
@SpringBootApplication
@ImportResource(locations = "classpath:config/beans.xml")
public class SpringBucksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

}

