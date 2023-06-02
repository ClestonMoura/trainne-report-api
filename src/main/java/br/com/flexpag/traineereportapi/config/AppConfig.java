package br.com.flexpag.traineereportapi.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Principais configurações da API para conexão com S3,
 * jdbcTemplate e SpringDoc
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    private static final String ACCESS_KEY = "AKIARSBKZNOICKRXVGPF";
    private static final String SECRET_KEY = "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";



    public AWSCredentials credentials() {
        return new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(
                        new AWSStaticCredentialsProvider(credentials()))
                .withRegion(Regions.SA_EAST_1)
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Trainee Report API")
                        .description("API que simula um sistema de relatórios. O sistema tem interação com a API de pagmentos e tem funcionalidades de gerar relatório de pagamentos e envio para AWS S3.")
                        .contact(new Contact().name("Cleston Tavares de Moura Filho").email("cleston.filho@flexpag.com")));
    }

}
