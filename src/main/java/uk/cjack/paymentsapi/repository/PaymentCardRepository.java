package uk.cjack.paymentsapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.cjack.paymentsapi.model.PaymentCard;

/**
 * Payment Card Repository
 * Can have some custom bits added, but otherwise just defines the Mongo implementation
 */
public interface PaymentCardRepository extends MongoRepository<PaymentCard, String> {

}
