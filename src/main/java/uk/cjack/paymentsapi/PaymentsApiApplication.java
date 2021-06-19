/*
 * Copyright (C) Chris Jackson (github.com/c-jack) - All Rights Reserved
 * Unauthorised copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Chris Jackson <chris@cjack.uk>,  2021.
 */

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

