package uk.cjack.paymentsapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.cjack.paymentsapi.model.PaymentCard;
import uk.cjack.paymentsapi.repository.PaymentCardRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
class PaymentControllerTest
{

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private PaymentCardRepository paymentCardRepository;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {

       String request = "{" +
               "    \"address\": {" +
               "        \"city\": \"Chippenham\"," +
               "        \"country\": \"England\"," +
               "        \"line1\": \"1 High Street\"," +
               "        \"line2\": null," +
               "        \"state\": \"Wiltshire\"," +
               "        \"zip\": \"SN15 1AB\"," +
               "        \"attributes\": {" +
               "            \"misc\": \"banana\"" +
               "        }" +
               "    }," +
               "    \"brand\": \"AMEX\"," +
               "    \"last4\": \"1234\"," +
               "    \"expiryDate\": {" +
               "        \"month\": \"01\"," +
               "        \"year\": \"2022\"" +
               "    }," +
               "    \"attributes\": {" +
               "        \"cvc\" : \"123\"" +
               "    }" +
               "}";


        Mockito.when(paymentCardRepository.save(Mockito.any(PaymentCard.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/payment").contentType(APPLICATION_JSON).content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address.city").value("Chippenham"))
                .andExpect(jsonPath("$.brand").value("AMEX"));
    }
}