package uk.cjack.paymentsapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.cjack.paymentsapi.model.PaymentCard;
import uk.cjack.paymentsapi.model.Transaction;
import uk.cjack.paymentsapi.provider.PaymentProvider;
import uk.cjack.paymentsapi.repository.PaymentCardRepository;
import uk.cjack.paymentsapi.repository.PaymentProviderRepository;
import uk.cjack.paymentsapi.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

/**
 * API Controller for Transactions
 */
@RestController
@RequestMapping("/api")
public class TransactionController
{

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PaymentCardRepository paymentCardRepository;

    @Autowired
    PaymentProviderRepository paymentProviderRepository;

    /**
     * Returns all transactions currently persisted
     */
    @GetMapping("/transaction")
    public ResponseEntity<List<Transaction>> getAllTransactions()
    {
        try
        {
            final List<Transaction> all = transactionRepository.findAll();
            return new ResponseEntity<>(all, HttpStatus.CREATED);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns the {@link Transaction} with the matching ID
     *
     * @param id the Transaction ID value to look up
     * @return matching record (if exists)
     */
    @GetMapping("/transaction/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") final String id)
    {
        final Optional<Transaction> byId = transactionRepository.findById(id);
        if (byId.isPresent())
        {
            return new ResponseEntity<>(byId.get(), HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates a Transaction.
     * <p>
     * If a {@link PaymentCard} is provided (e.g. not an ID) then this will also be persisted.
     * If an ID is provided for an existing payment card, a reference will be used.
     *
     * @param transaction the transaction to persist
     * @return persisted card object (with ID)
     */
    @PostMapping("/transaction")
    public ResponseEntity<Transaction> processTransaction(@RequestBody final Transaction transaction)
    {
        try
        {
            final PaymentProvider paymentProvider = transaction.getPaymentProvider();
            final PaymentCard card = transaction.getCard();
            if (card.getId() == null)
            {
                final PaymentCard paymentCard = paymentCardRepository.save(card);
                transaction.setCard(paymentCard);
            }

            if (paymentProvider.getId() == null
                    && paymentProvider.getProviderName() != null)
            {
                final Optional<PaymentProvider> providerName = paymentProviderRepository.findPaymentProviderByProviderName(paymentProvider.getProviderName());
                if( providerName.isPresent())
                {
                    transaction.setPaymentProvider(providerName.get());
                }

            }
            final Transaction savedCard = transactionRepository.save(transaction);
            return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Cancels a transaction with the matching ID
     *
     * @param id the ID to look up
     * @return request response
     */
    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<HttpStatus> cancelTransaction(@PathVariable("id") final String id)
    {
        try
        {
            //TODO
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
