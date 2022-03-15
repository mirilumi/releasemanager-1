//package com.tset.demo.releasemanager
//
//import org.springframework.context.annotation.Bean
//import org.springframework.stereotype.Component
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
//import springfox.documentation.builders.ApiInfoBuilder
//import springfox.documentation.builders.PathSelectors
//import springfox.documentation.builders.RequestHandlerSelectors
//import springfox.documentation.service.ApiInfo
//import springfox.documentation.service.ApiKey
//import springfox.documentation.service.Contact
//import springfox.documentation.spi.DocumentationType
//import springfox.documentation.spring.web.plugins.Docket
//
//@Component
//class SwaggerConfig(val name: String = "ReleaseManager") : WebMvcConfigurer {
//    @Bean
//    fun api(): Docket {
//        return Docket(DocumentationType.SWAGGER_2)
//            .select()
//            .apis(RequestHandlerSelectors.any())
//            .paths(PathSelectors.any())
//            .build()
//            .apiInfo(apiInfo())
//            .securitySchemes(listOf(basicAuth()))
//            .protocols(hashSetOf("http", "https"))
//    }
//
//    private fun basicAuth(): ApiKey {
//        return ApiKey("Authorization", "Authorization", "header")
//    }
//
//    private fun apiInfo(): ApiInfo {
//        return ApiInfoBuilder().title("REST API ")
//            .description("Api Documentation for $name backend ").termsOfServiceUrl("")
//            .contact(Contact("TSET", "", "info@tset.com"))
//            .license("Apache License Version 2.0")
//            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
//            .version("0.0.1")
//            .build()
//    }
//
//    override fun addViewControllers(registry: ViewControllerRegistry) {
//        registry.addRedirectViewController("/api/v2/api-docs", "/v2/api-docs")
//        registry.addRedirectViewController(
//            "/api/swagger-resources/configuration/ui",
//            "/swagger-resources/configuration/ui"
//        )
//        registry.addRedirectViewController(
//            "/api/swagger-resources/configuration/security",
//            "/swagger-resources/configuration/security"
//        )
//        registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources")
//    }
//
//    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
//        registry.addResourceHandler("/api/swagger-ui.html**")
//            .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html")
//        registry.addResourceHandler("/api/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
//    }
//}