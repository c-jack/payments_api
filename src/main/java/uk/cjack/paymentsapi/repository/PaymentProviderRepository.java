package uk.cjack.paymentsapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.cjack.paymentsapi.provider.PaymentProvider;

import java.util.Optional;

/**
 * Payment Provider Repository
 * Can have some custom bits added, but otherwise just defines the Mongo implementation
 */
public interface PaymentProviderRepository extends MongoRepository<PaymentProvider, String>
{

    /**
     * Find the matching payment provider by the provider name
     */
    Optional<PaymentProvider> findPaymentProviderByProviderName(final String providerName);
}
