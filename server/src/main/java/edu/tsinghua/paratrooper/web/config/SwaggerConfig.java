package edu.tsinghua.paratrooper.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${paratrooper.header}")
    private String tokenHeader;

    @Bean
    public Docket createRestApi() {

        ParameterBuilder authPar = new ParameterBuilder();
        List<Parameter> headers = new ArrayList<Parameter>();
        authPar.name(tokenHeader).description("授权信息")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        headers.add(authPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("edu.tsinghua.paratrooper"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(headers);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("伞兵通信模拟API")
                //创建人
                .contact(new Contact("cuiods", "http://www.paratrooper.com", ""))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }

}
