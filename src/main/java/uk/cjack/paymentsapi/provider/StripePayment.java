package uk.cjack.paymentsapi.provider;

import uk.cjack.paymentsapi.model.PaymentCard;
import uk.cjack.paymentsapi.model.Transaction;

import java.util.HashMap;
import java.util.Map;

/**
 * Example implementation of a Stripe Payment
 */
public class StripePayment extends PaymentProvider implements PaymentProcessor
{
    /**
     * Constructor for the Persistence flow
     *
     * @param endpoint
     * @param credentials
     */
    public StripePayment(final String endpoint,
                         final Map<String, String> credentials)
    {
        super(endpoint, credentials);
    }

    /**
     * @param transaction
     */
    @Override
    public void processTransaction(final Transaction transaction) throws Exception
    {
        final String result;

        if (transaction.getTransactionType() == Transaction.Type.PROCESS)
        {
            final Map<String, Object> schema = getProcessSchema(transaction);
            result = processPendingTransaction(String.class, schema);

        } else if (transaction.getTransactionType() == Transaction.Type.REFUND)
        {
            final Map<String, Object> schema = getCancelSchema(transaction);
            result = cancelTransaction(String.class, schema);
        }
        else
        {
            // add an exception class for an incompatible/missing type
            throw new Exception();
        }



        // Store the result with a timestamp if not in the returned String
        transaction.getAudit().add( result );

        // Change the status based on the outcome?
        if(result.contains("success"))
        {
            transaction.setStatus(Transaction.Status.COMPLETE);
        }
        else if(result.contains("failed"))
        {
            transaction.setStatus(Transaction.Status.FAILED);
        }

        // otherwise try again next time.  this would perhaps need a ceiling to prevent recursive attempts

    }

    /**
     * Builds the schema object for the Stripe payment processing action
     *
     * @param transaction the {@link Transaction} containing all the information to build the request
     * @return the schema
     */
    private Map<String, Object> getProcessSchema(final Transaction transaction)
    {
        final Map<String, Object> schema = new HashMap<>();
        final Map<String, Object> paymentCard = new HashMap<>();

        final PaymentCard card = transaction.getCard();
        paymentCard.put("address", card.getAddress());
        paymentCard.put("expiry", card.getExpiryDate());
        paymentCard.put("last4", card.getLast4());
        // etc

        schema.put("customer", transaction.getCustomerRef());
        schema.put("amount", transaction.getAmount());
        schema.put("currency", "GBP");
        schema.put("paymentCard", paymentCard);
        // etc

        return schema;
    }

    /**
     * Builds the schema object for the Stripe payment refund action
     *
     * @param transaction the {@link Transaction} containing all the information to build the request
     * @return the schema
     */
    private Map<String, Object> getCancelSchema(final Transaction transaction)
    {
        // for a shortcut, let's assume the object is the same for cancellations
        return getProcessSchema(transaction);
    }
}
