package rest;

import fingerprint.interactors.FingerprintInteractor;
import fingerprint.validation.FingerprintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rest.persistence.FingerprintRepositoryImpl;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    @Bean
    public FingerprintInteractor buildService(FingerprintRepositoryImpl repository) {
        return new FingerprintInteractor(repository);
    }

}