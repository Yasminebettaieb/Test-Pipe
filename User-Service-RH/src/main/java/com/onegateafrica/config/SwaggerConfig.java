package com.onegateafrica.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().description("Gestion des users").title("Users infos Rest Apis").build())
                .groupName("Rest Apis v1")
                .select().apis(RequestHandlerSelectors.basePackage("com.onegateafrica")).paths(PathSelectors.ant("Rh/v1"+"/**")).build();

    }
}

