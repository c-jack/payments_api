package uk.cjack.paymentsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Class
 */
@SpringBootApplication
@EnableScheduling
public class PaymentsApiApplication {

    /**
     * Main method
     */
    public static void main(String[] args) {
        SpringApplication.run(PaymentsApiApplication.class, args);
    }

}

