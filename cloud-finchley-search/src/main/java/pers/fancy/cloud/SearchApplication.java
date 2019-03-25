package pers.fancy.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;




@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
@EnableHystrix
@EnableCircuitBreaker
@EnableTurbine
@EnableScheduling
@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy

/**
 * @author fancy
 */
public class SearchApplication {

    /**
     * main
     * @param args
     */
    public static void main(String[] args){
        SpringApplication.run(SearchApplication.class,args);
    }

    @Bean
    public Docket dcsApi() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        ApiInfo apiInfo = apiInfoBuilder.title("微服务搜索引擎")
                .description("微服务搜索引擎")
                .version("1.0.0")
                .contact(new Contact("", "", ""))
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select().apis(RequestHandlerSelectors.basePackage("pers.fancy.cloud.search.controller"))
                .build();
    }

}
