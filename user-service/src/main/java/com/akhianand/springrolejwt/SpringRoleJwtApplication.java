package com.akhianand.springrolejwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.spring.web.readers.operation.HandlerMethodResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableEurekaClient
public class SpringRoleJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRoleJwtApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

	@Bean
	public WebMvcRequestHandlerProvider webMvcRequestHandlerProvider(
			Optional<ServletContext> context,
			HandlerMethodResolver methodResolver,
			List<RequestMappingInfoHandlerMapping> handlerMappings) {
		List<RequestMappingInfoHandlerMapping> list = new ArrayList<>();
		for (RequestMappingInfoHandlerMapping rh : handlerMappings) {
			if (rh.getClass().getName().contains("RequestMapping")) {
				list.add(rh);
			}
		}
		handlerMappings = list;
		return new WebMvcRequestHandlerProvider(context, methodResolver, handlerMappings);
	}

}
