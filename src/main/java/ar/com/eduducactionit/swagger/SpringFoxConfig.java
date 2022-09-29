package ar.com.eduducactionit.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

	@Bean
	public Docket createRestApi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))//cuales son las clases que voy a documentar
				.paths(PathSelectors.any())
				.build();
		return docket;
				
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.contact(new Contact("carlos", "http://sitio.com", "email@email.com"))
				.description("Proyecto Rest final, Bootcamp EducactionIt")
				.title("SpringBoot + JWT + Swagger ")
				.build();
	}
}
