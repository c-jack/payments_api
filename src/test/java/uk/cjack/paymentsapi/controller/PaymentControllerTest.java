/*
 * Copyright (C) Chris Jackson (github.com/c-jack) - All Rights Reserved
 * Unauthorised copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Chris Jackson <chris@cjack.uk>,  2021.
 */

package uk.cjack.paymentsapi.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.cjack.paymentsapi.model.PaymentCard;
import uk.cjack.paymentsapi.repository.PaymentCardRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Stub of a test class for {@link PaymentController}
 */
@WebMvcTest(PaymentController.class)
class PaymentControllerTest extends ControllerTestUtils
{

    /**
     * Base URI for the Payment Endpoint
     */
    private static final String API_BASE = "/api/payment";

    @MockBean
    private PaymentCardRepository paymentCardRepository;

    /**
     * An example test to demonstrate testing the payments endpoint, creating a new {@link PaymentCard}
     * <p>
     * Generally this could be fleshed out to poke at validation, checking returned error codes.
     *
     * @throws Exception from the MockMVC
     */
    @Test
    public void shouldCreatePaymentMethod() throws Exception
    {

        final String json = readJson("payment.json");

        Mockito.when(paymentCardRepository.save(Mockito.any(PaymentCard.class))).thenAnswer(i -> i.getArguments()[0]);

        final ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders
                .post(API_BASE)
                .contentType(APPLICATION_JSON)
                .content(json));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.address.city").value("Chippenham"))
                .andExpect(jsonPath("$.brand").value("AMEX"));
    }
}