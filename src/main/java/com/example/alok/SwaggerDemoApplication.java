package com.example.alok;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

@SpringBootApplication
public class SwaggerDemoApplication {

	@Bean
	CommandLineRunner runner() {
		return args -> {
			String passString = "abcD12cdb23Ughd6aBCD2345678GHderHR234GhDeTjImNbSvCxAzWqP";
			//			String passString = "abc12dbgr";
//						String passString = "12";
//						String passString = "a0bb";
			//			String passString = "a0B";
			//			String passString = "a0B2cD";

			System.out.println(calculatePassword(passString));
		};
	}

	private String calculatePassword(String passString) {
		String[] splitString = passString.split("\\d+");

		Map<String, Integer> treeMap = new TreeMap<>(
				new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						if (o1.length() > o2.length()) {
							return -1;
						} else if(o1.length() < o2.length()) {
							return 1;
						} else {
							return o1.compareTo(o2);
						}
					}
				}
		);

		if (splitString != null) {
			for (String str : splitString) {
				if (str.matches(".*[A-Z]|[A-Z].*")) {
					treeMap.put(str, str.length());
				}
			}
		}

		if (treeMap.isEmpty()) {
			return "-1";
		} else {
			String password = treeMap.entrySet().toArray()[0].toString();
			return password.replaceAll("[=]\\d+","");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(SwaggerDemoApplication.class, args);
	}
}


@Configuration
@EnableSwagger2
class swaggerConfig {


	@Bean
	Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.any())
				.build()
				.forCodeGeneration(true)
				.enableUrlTemplating(true)
				.enable(true);
//				.useDefaultResponseMessages(false);
//				.apiInfo(apiInfo())
//				.securitySchemes(securitySchemes())
//				.securityContext(securityContext());
	}
}

@RestController
@RequestMapping("/api")
class myResource {

	@GetMapping("/name")
	@ApiResponses(value = @ApiResponse(code = 300, message = "Unable to find Name"))
	ResponseEntity<String> getName() {
		return new ResponseEntity("this is my Name",HttpStatus.OK);
	}

}