package uk.cjack.paymentsapi.model

import spock.lang.Specification
import uk.cjack.paymentsapi.provider.StripePayment

import java.time.LocalDate

/**
 * Test Class for {@link Transaction}
 */
class TransactionTest extends Specification {

    private Transaction transaction;

    /**
     * Pre-Test setup
     */
    void setup() {
        transaction = new Transaction();
    }

    /**
     * Simple test to assert that the getter and setter for Payment Date does its job
     */
    def "The Payment Date value can be set"() {
        given: "The payment date value is null"
        transaction.getPaymentDate() == null

        when: "I set a value using the setter"
        def now = LocalDate.now()
        transaction.setPaymentDate(now)

        then: "the value is set as expected"
        transaction.getPaymentDate() == now
    }

    /**
     * Simple test to assert that the getter and setter for Payment Processor does its job when
     * provided with a valid value
     */
    def "The Payment Processor value can be set"() {
        given: "The paymentProcessor value is null"
        transaction.getPaymentProcessor() == null

        when: "I set a value using the setter"
        def stripePayment = new StripePayment(null, null)
        transaction.setPaymentProcessor(stripePayment)

        then: "the value is set as expected"
        transaction.getPaymentProcessor().getClass() == StripePayment.class
        transaction.getPaymentProcessor() == stripePayment
    }

    /**
     * Simple test to assert that the getter and setter for Status does its job when
     * provided with a {@link Transaction.Status} value
     */
    def "The 'Status' value can be set"() {
        given: "The status value is null"
        transaction.getStatus() == null

        when: "I set a value using the setter"
        transaction.setStatus(Transaction.Status.COMPLETE)

        then: "the value is set as expected"
        transaction.getStatus() == Transaction.Status.COMPLETE
    }

    /**
     * Simple test to assert that the getter and setter for Transaction Type does its job when
     * provided with a {@link Transaction.Type} value
     */
    def "The 'transactionType' value can be set"() {
        given: "The transactionType value is null"
        transaction.getTransactionType() == null

        when: "I set a value using the setter"
        transaction.setTransactionType(Transaction.Type.PROCESS)

        then: "the value is set as expected"
        transaction.getTransactionType() == Transaction.Type.PROCESS
    }

    /**
     * Simple test to assert that the getter and setter for CustomerRef does its job when
     * provided with a {@link String} value
     */
    def "The 'customerRef' value can be set"() {
        given: "The customerRef value is null"
        transaction.getCustomerRef() == null

        when: "I set a value using the setter"
        def customerRef = "customerRef"
        transaction.setCustomerRef(customerRef)

        then: "the customerRef is now set to the expected value"
        transaction.getCustomerRef() == customerRef
    }

    /**
     * Simple test to assert that the getter and setter for ProductRef does its job when
     * provided with a {@link String} value
     */
    def "The 'productRef' value can be set"() {
        given: "The productRef value is null"
        transaction.getProductRef() == null

        when: "I set a value using the setter"
        def productRef = "productRef"
        transaction.setProductRef(productRef)

        then: "the productRef is now set to the expected value"
        transaction.getProductRef() == productRef
    }

    /**
     * Simple test to assert that the getter and setter for Card does its job when
     * provided with a {@link PaymentCard} value
     */
    def "The 'card' value can be set"() {
        given: "The card value is null"
        transaction.getCard() == null

        when: "I set a value using the setter"
        def card = new PaymentCard()
        transaction.setCard(card)

        then: "the card value is now set to the expected value"
        transaction.getCard() == card
    }

    /**
     * Simple test to assert that the getter and setter for Amount does its job when
     * provided with a {@link Long} value
     */
    def "The 'amount' value can be set"() {
        given: "The amount value is null"
        transaction.getAmount() == 0

        when: "I set a value using the setter"
        def amount = 1000L
        transaction.setAmount(amount)

        then: "the amount value is now set to the expected value"
        transaction.getAmount() == amount
    }

    /**
     * Simple test to assert that Audit entries can be added to the Audit List
     */
    def "An 'audit' value can be added"() {
        given: "The audit value is null"
        transaction.getAudit().size() == 0

        when: "I add a value to the Audit List"
        def note = "some note"
        transaction.getAudit().add(note)

        then: "the amount value is now set to the expected value"
        transaction.getAudit().size() == 1
        transaction.getAudit().get( 0 ) == note
    }
}
