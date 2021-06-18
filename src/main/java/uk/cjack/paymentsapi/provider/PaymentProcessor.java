package uk.cjack.paymentsapi.provider;

import uk.cjack.paymentsapi.model.Transaction;

public interface PaymentProcessor
{
    /**
     * Process the Provider's Transaction
     * @param transaction the transaction to process
     */
    void processTransaction(final Transaction transaction) throws Exception;
}
