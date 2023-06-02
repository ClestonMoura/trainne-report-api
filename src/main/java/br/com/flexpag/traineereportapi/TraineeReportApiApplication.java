package br.com.flexpag.traineereportapi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class TraineeReportApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraineeReportApiApplication.class, args);
    }

}
