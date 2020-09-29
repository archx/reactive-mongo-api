package com.example.reactive.config

import io.swagger.annotations.Api
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.server.ServerWebExchange
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

/**
 * SwaggerConfig
 *
 * UI http://localhost:9000/swagger-ui/
 *
 * @author archx
 * @since 2020/9/29 10:17
 */
@Configuration
@EnableOpenApi
class SwaggerConfig : WebFluxConfigurer {

    // ------------------------------------
    // |    S W A G G E R  C O N F I G    |
    // ------------------------------------
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .ignoredParameterTypes(ServerWebExchange::class.java)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(Api::class.java)
                .and(RequestHandlerSelectors.basePackage("com.example.reactive.controller")))
            .paths(PathSelectors.any())
            .build()
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/swagger-ui/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
            .resourceChain(false)
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Reactive Mongodb APIs")
            .description("Powered by Spring WebFlux")
            .version("1.0")
            //.contact(Contact("archx", "http://github.com/archx", "archxchu@gmail.com"))
            .build()
    }
}