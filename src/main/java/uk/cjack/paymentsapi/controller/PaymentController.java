/*
 * Copyright (C) Chris Jackson (github.com/c-jack) - All Rights Reserved
 * Unauthorised copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Chris Jackson <chris@cjack.uk>,  2021.
 */

package uk.cjack.paymentsapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.cjack.paymentsapi.model.PaymentCard;
import uk.cjack.paymentsapi.repository.PaymentCardRepository;

import java.util.List;
import java.util.Optional;

/**
 * API Controller for Payments
 */
@RestController
@RequestMapping("/api")
public class PaymentController
{

    @Autowired
    PaymentCardRepository paymentCardRepository;

    /**
     * Returns all payment cards currently persisted
     */
    @GetMapping("/payment")
    public ResponseEntity<List<PaymentCard>> getAllPaymentCards()
    {
        try
        {
            final List<PaymentCard> all = paymentCardRepository.findAll();
            return new ResponseEntity<>(all, HttpStatus.CREATED);
        }
        catch (final Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns the {@link PaymentCard} with the matching ID
     *
     * @param id the PaymentCard ID value to look up
     * @return matching record (if exists)
     */
    @GetMapping("/payment/{id}")
    public ResponseEntity<PaymentCard> getPaymentCardById(@PathVariable("id") final String id)
    {
        final Optional<PaymentCard> byId = paymentCardRepository.findById(id);
        if (byId.isPresent())
        {
            return new ResponseEntity<>(byId.get(), HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a Payment Card entry
     *
     * @param paymentCard the payment card to persist
     * @return persisted card object (with ID)
     */
    @PostMapping("/payment")
    public ResponseEntity<PaymentCard> createPayment(@RequestBody final PaymentCard paymentCard)
    {
        try
        {
            final PaymentCard savedCard = paymentCardRepository.save(paymentCard);
            return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates a payment card
     *
     * @param id          the id of the card to update
     * @param paymentCard the details of the card to update
     * @return updated payment card object
     */
    @PutMapping("/payment/{id}")
    public ResponseEntity<PaymentCard> updatePaymentCard(@PathVariable("id") String id,
                                                         @RequestBody PaymentCard paymentCard)
    {
        Optional<PaymentCard> existingCard = paymentCardRepository.findById(id);

        if (existingCard.isPresent())
        {
            PaymentCard existingPaymentCard = existingCard.get();
            existingPaymentCard.setBrand(paymentCard.getBrand());
            existingPaymentCard.setExpiryDate(paymentCard.getExpiryDate());
            existingPaymentCard.setLast4(paymentCard.getLast4());
            existingPaymentCard.setAddress(paymentCard.getAddress());
            existingPaymentCard.setAttributes(paymentCard.getAttributes());
            return new ResponseEntity<>(paymentCardRepository.save(existingPaymentCard), HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a payment card with the matching ID
     *
     * @param id the ID to look up
     * @return request response
     */
    @DeleteMapping("/payment/{id}")
    public ResponseEntity<HttpStatus> deletePaymentCard(@PathVariable("id") final String id)
    {
        try
        {
            paymentCardRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes all payment cards (useful for testing, but might not want to leave it in...)
     *
     * @return request response
     */
    @DeleteMapping("/payment")
    public ResponseEntity<HttpStatus> deleteAllPaymentCards()
    {
        try
        {
            paymentCardRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
