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
import uk.cjack.paymentsapi.model.Transaction;
import uk.cjack.paymentsapi.repository.PaymentCardRepository;
import uk.cjack.paymentsapi.repository.TransactionRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Stub of a test class for {@link TransactionController}
 */
@WebMvcTest(TransactionController.class)
class TransactionControllerTest extends ControllerTestUtils
{

    /**
     * Base URI for the Transaction Endpoint
     */
    private static final String API_BASE = "/api/transaction";

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private PaymentCardRepository paymentCardRepository;

    /**
     * An example test to demonstrate testing the transaction endpoint, creating a new {@link Transaction}
     * <p>
     * Generally this could be fleshed out to poke at validation, checking returned error codes.
     *
     * @throws Exception from the MockMVC
     */
    @Test
    public void shouldCreateTransaction() throws Exception
    {

        final String json = readJson("transaction.json");

        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(paymentCardRepository.save(Mockito.any(PaymentCard.class))).thenAnswer(i -> i.getArguments()[0]);

        final ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders
                .post(API_BASE)
                .contentType(APPLICATION_JSON)
                .content(json));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerRef").value("cust123"))
                .andExpect(jsonPath("$.productRef").value("prod123"));
    }
}