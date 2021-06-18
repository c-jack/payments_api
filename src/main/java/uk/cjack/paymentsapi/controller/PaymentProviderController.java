package uk.cjack.paymentsapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.cjack.paymentsapi.provider.PaymentProvider;
import uk.cjack.paymentsapi.repository.PaymentProviderRepository;

import java.util.List;
import java.util.Optional;

/**
 * API Controller for Payment Providers
 */
@RestController
@RequestMapping("/api")
public class PaymentProviderController
{

    @Autowired
    PaymentProviderRepository paymentProviderRepository;

    /**
     * Returns all payment providers currently persisted
     */
    @GetMapping("/provider")
    public ResponseEntity<List<PaymentProvider>> getAllPaymentProviders() {
        try {
            final List<PaymentProvider> all = paymentProviderRepository.findAll();
            return new ResponseEntity<>(all, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns the {@link PaymentProvider} with the matching ID
     *
     * @param id the PaymentProvider ID value to look up
     * @return matching record (if exists)
     */
    @GetMapping("/payment/{id}")
    public ResponseEntity<PaymentProvider> getPaymentProviderById(@PathVariable("id") final String id) {
        final Optional<PaymentProvider> byId = paymentProviderRepository.findById(id);
        if (byId.isPresent()) {
            return new ResponseEntity<>(byId.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a Payment Provider
     *
     * @param paymentProvider the payment provider to persist
     * @return persisted provider object (with ID)
     */
    @PostMapping("/provider")
    public ResponseEntity<PaymentProvider> createPaymentProvider(@RequestBody final PaymentProvider paymentProvider) {
        try {
            final PaymentProvider savedCard = paymentProviderRepository.save(paymentProvider);
            return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates a payment provider
     *
     * @param id          the id of the card to update
     * @param paymentProvider the details of the card to update
     * @return updated payment card object
     */
    @PutMapping("/payment/{id}")
    public ResponseEntity<PaymentProvider> updatePaymentProvider(@PathVariable("id") String id, @RequestBody PaymentProvider paymentProvider) {
        Optional<PaymentProvider> existingProvider = paymentProviderRepository.findById(id);

        if (existingProvider.isPresent()) {
            PaymentProvider existingPaymentProvider = existingProvider.get();
            existingPaymentProvider.setProviderName(paymentProvider.getProviderName());
            existingPaymentProvider.setWsdl(paymentProvider.getWsdl());
            existingPaymentProvider.setCredentials(paymentProvider.getCredentials());
            existingPaymentProvider.setSchema(paymentProvider.getSchema());
            return new ResponseEntity<>(paymentProviderRepository.save(existingPaymentProvider), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a payment provider with the matching ID
     *
     * @param id the ID to look up
     * @return request response
     */
    @DeleteMapping("/payment/{id}")
    public ResponseEntity<HttpStatus> deletePaymentProvider(@PathVariable("id") final String id) {
        try {
            paymentProviderRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes all payment providers (useful for testing, but might not want to leave it in...)
     *
     * @return request response
     */
    @DeleteMapping("/provider")
    public ResponseEntity<HttpStatus> deleteAllPaymentProviders() {
        try {
            paymentProviderRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
