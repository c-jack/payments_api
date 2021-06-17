package uk.cjack.paymentsapi.model

import spock.lang.Specification

/**
 * Test Class for {@link PaymentCard}
 */
class PaymentCardTest extends Specification {

    private PaymentCard paymentCard;

    /**
     * Pre-Test setup
     */
    void setup() {
        paymentCard = new PaymentCard();
    }

    /**
     * Simple test to assert that the getter and setter for Address does its job
     */
    def "The Address value can be set"() {
        given: "The address value is null"
        paymentCard.getAddress() == null

        when: "I set a value using the setter"
        paymentCard.setAddress(new Address())

        then: "the value is no longer null"
        paymentCard.getAddress() != null
    }

    /**
     * Simple test to assert that the getter and setter for Brand does its job
     */
    def "The Brand value can be set"() {
        given: "The brand lue is null"
        paymentCard.getBrand() == null

        when: "I set a value using the setter"
        paymentCard.setBrand(PaymentCard.Brand.AMEX)

        then: "the value is no longer null"
        paymentCard.getBrand() == PaymentCard.Brand.AMEX
    }

    /**
     * Simple test to assert that the getter and setter for Last4 does its job
     */
    def "The 'Last4' value can be set"() {
        given: "The last4 value is null"
        paymentCard.getLast4() == null

        when: "I set a value using the setter"
        paymentCard.setLast4("1234")

        then: "the value is no longer null"
        paymentCard.getLast4() == "1234"
    }

    /**
     * Simple test to assert that the getter and setter for Expiry Date does its job when
     * provided with a string value
     */
    def "The 'expiryDate' value can be set using a string argument"() {
        given: "The expiryDate value is null"
        paymentCard.getExpiryDate() == null

        when: "I set a value using the String argument setter"
        paymentCard.setExpiryDate(new PaymentCard.ExpiryDate("01/2022"))

        then: "the month and year values are correct set"
        paymentCard.getExpiryDate().getMonth() == "01"
        paymentCard.getExpiryDate().getYear() == "2022"
    }

    /**
     * Simple test to assert that the getter and setter for Expiry Date does its job when
     * provided with month and year arguments
     */
    def "The 'expiryDate' value can be set using month and year arguments"() {
        given: "The expiryDate value is null"
        paymentCard.getExpiryDate() == null

        when: "I set a value using the month and year argument setter"
        paymentCard.setExpiryDate(new PaymentCard.ExpiryDate("01", "2022"))

        then: "the month and year values are correct set"
        paymentCard.getExpiryDate().getMonth() == "01"
        paymentCard.getExpiryDate().getYear() == "2022"
    }
    /**
     * Simple test to assert that miscellaneous attributes can be set
     */
    def "The 'attributes' value can be set using a Map of values"() {
        given: "The attributes value is null"
        paymentCard.getAttributes() == null

        when: "I set a value using the setter"
        final Map someAttributes = new HashMap();
        someAttributes.put("key", "value")
        paymentCard.setAttributes(someAttributes)

        then: "the attributes are set and retrievable"
        paymentCard.getAttributes().size() == 1
        paymentCard.getAttributes().get("key") == "value"
    }
}
