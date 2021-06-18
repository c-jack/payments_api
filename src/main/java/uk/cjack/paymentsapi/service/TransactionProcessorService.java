package uk.cjack.paymentsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uk.cjack.paymentsapi.model.Transaction;
import uk.cjack.paymentsapi.provider.PaymentProcessor;
import uk.cjack.paymentsapi.repository.TransactionRepository;

import java.util.List;

/**
 * Transaction Processor Service
 * <p>
 * Processes transactions
 */
@Component
public class TransactionProcessorService
{

    @Autowired
    TransactionRepository transactionRepository;

    /**
     * Processes Pending Transactions
     */
    @Scheduled(fixedRate = 10000)
    public void processPendingTransactions()
    {
        final List<Transaction> pendingTransactions = transactionRepository.findAllByStatus(Transaction.Status.PENDING);

        for (final Transaction transaction : pendingTransactions)
        {
            try
            {
                final PaymentProcessor paymentProcessor = transaction.getPaymentProcessor();
                paymentProcessor.processTransaction(transaction);

            } catch (final Exception e)
            {
                // handle exception
            }
        }
    }

}
