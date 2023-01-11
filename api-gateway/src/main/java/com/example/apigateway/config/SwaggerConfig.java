package com.example.apigateway.config;


import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final GatewayProperties properties;

    public SwaggerConfig(GatewayProperties properties) {
        this.properties = properties;
    }

    @Bean
    public JsonSerializer jsonSerializer(List<JacksonModuleRegistrar> moduleRegistrars) {
        return new JsonSerializer(moduleRegistrars);
    }

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> properties.getRoutes().stream()
                .map(route -> createResource(route.getId(), getRouteLocation(route), "1.0"))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // You will certainly need to edit this
    private String getRouteLocation(RouteDefinition route) {
        return Optional.ofNullable(route.getPredicates().get(0).getArgs().values().toArray()[0])
                .map(String::valueOf)
                .map(s -> s.replace("*", ""))
                .orElse(null);
    }

    private SwaggerResource createResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/"+name+"/v2/api-docs");
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}