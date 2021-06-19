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
import uk.cjack.paymentsapi.model.Transaction;
import uk.cjack.paymentsapi.provider.PaymentProcessor;
import uk.cjack.paymentsapi.repository.PaymentCardRepository;
import uk.cjack.paymentsapi.repository.TransactionRepository;

import java.time.LocalDate;
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
            final PaymentProcessor paymentProcessor = transaction.getPaymentProcessor();
            final PaymentCard card = transaction.getCard();
            if (card.getId() == null)
            {
                final PaymentCard paymentCard = paymentCardRepository.save(card);
                transaction.setCard(paymentCard);
            }

            // check processor implementation and respond if invalid?

            // set/override status to 'pending'
            transaction.setStatus(Transaction.Status.PENDING);
            final Transaction savedTransaction = transactionRepository.save(transaction);
            return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
        } catch (final Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Cancels a transaction with the matching ID.
     * If the transaction is 'complete', a refund will be processed.
     *
     * @param id the ID to look up
     * @return request response
     */
    @GetMapping("/transaction/refund/{id}")
    public ResponseEntity<Transaction> cancelTransaction(@PathVariable("id") final String id)
    {
        try
        {
            final Transaction result;
            Optional<Transaction> byId = transactionRepository.findById(id);

            if (byId.isPresent())
            {
                final Transaction transaction = byId.get();

                if (transaction.getStatus().equals(Transaction.Status.COMPLETE))
                {
                    // refund - cancel the existing
                    transaction.setStatus(Transaction.Status.CANCELLED);

                    // build a new refund transaction
                    final Transaction refund = new Transaction();
                    refund.setTransactionType(Transaction.Type.REFUND);
                    refund.setPaymentProcessor(transaction.getPaymentProcessor());
                    refund.setCard(transaction.getCard());
                    refund.setStatus(Transaction.Status.PENDING);
                    refund.setAmount(transaction.getAmount());
                    refund.setCustomerRef(transaction.getCustomerRef());
                    refund.setPaymentDate(LocalDate.now());
                    refund.setProductRef(transaction.getProductRef());
                    refund.getAudit().add("Refund for " + transaction.getId());

                    // save the refund
                    result = transactionRepository.save(refund);

                    // save the cancelled one
                    transactionRepository.save(transaction);

                } else if (transaction.getStatus().equals(Transaction.Status.PENDING))
                {
                    // No need to refund - just cancel
                    transaction.setStatus(Transaction.Status.CANCELLED);
                    result = transactionRepository.save(transaction);
                } else
                {
                    result = transaction;
                }
                return new ResponseEntity<>(result, HttpStatus.OK);
            }

            // TODO exception?

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (final Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
