package pers.fancy.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import pers.fancy.cloud.search.core.annotation.EnableESTools;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 搜索模块启动类
 *
 * @author LiLiChai
 */
@EnableESTools
@EnableDiscoveryClient
@EnableSwagger2
@EnableHystrix
@EnableCircuitBreaker
@EnableTurbine
@EnableScheduling
@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy
public class SearchApplication {


    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

    @Bean
    public Docket dcsApi() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        ApiInfo apiInfo = apiInfoBuilder.title("搜索引擎")
                .description("搜索引擎")
                .version("1.0.0")
                .contact(new Contact("fancy", "", "SmartShuShu@163.com"))
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select().apis(RequestHandlerSelectors.basePackage("pers.fancy.cloud.search.controller"))
                .build();
    }
}
