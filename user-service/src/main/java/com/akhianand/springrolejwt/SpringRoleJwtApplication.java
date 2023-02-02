package com.akhianand.springrolejwt;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@EnableElasticsearchRepositories("com.akhianand.springrolejwt.es.dao")
@EnableScheduling
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
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	@Bean
	public JSONObject jsonObject()
	{
		return new JSONObject();
	}


}
