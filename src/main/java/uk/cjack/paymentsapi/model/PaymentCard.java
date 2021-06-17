package uk.cjack.paymentsapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Payment Card
 * <p>
 * Represents a typical Payment Method/Card
 * Any non-standard fields can be specified as 'attributes'
 */
@Document(collection = "paymentCards")
@JsonPropertyOrder({"id", "_class", "address"})
public class PaymentCard {

    @Id
    private String _id;

    @Field
    @JsonProperty("type")
    private String _class;

    @Field
    private Address address;
    @Field
    private Brand brand;
    @Field
    private String last4;
    @Field
    private ExpiryDate expiryDate;
    @Field
    private Map<String, String> attributes;

    /**
     * Default Public Constructor
     */
    public PaymentCard() {
        // nothing here
    }

    /**
     * Constructor for the Persistence flow
     *
     * @param _id        the ID of the PaymentCard document
     * @param _class     the class name
     * @param address    the address details {@link Address}
     * @param brand      the {@link Brand} of the card
     * @param last4      the last 4 digits of the card
     * @param expiryDate the {@link ExpiryDate} of the card
     * @param attributes any additional fields
     */
    @PersistenceConstructor
    public PaymentCard(final String _id,
                       final String _class,
                       final Address address,
                       final Brand brand,
                       final String last4,
                       final ExpiryDate expiryDate,
                       final Map<String, String> attributes) {
        this._id = _id;
        this._class = _class;
        this.address = address;
        this.brand = brand;
        this.last4 = last4;
        this.expiryDate = expiryDate;
        this.attributes = attributes;
    }

    /**
     * Constructor for creating a new Payment Card
     *
     * @param address    the address details {@link Address}
     * @param brand      the {@link Brand} of the card
     * @param last4      the last 4 digits of the card
     * @param expiryDate the {@link ExpiryDate} of the card
     * @param attributes any additional fields
     */
    @JsonCreator
    public PaymentCard(final Address address,
                       final Brand brand,
                       final String last4,
                       final ExpiryDate expiryDate,
                       final Map<String, String> attributes) {
        this._class = this.getClass().getName();
        this.address = address;
        this.brand = brand;
        this.last4 = last4;
        this.expiryDate = expiryDate;
        this.attributes = attributes;
    }

    /**
     * Constructor
     *
     * @param paymentCardDetails dets
     */
    public PaymentCard(final Map<String, Object> paymentCardDetails) {
        this.brand = Brand.getBrandByCardDescription((String) paymentCardDetails.get("brand"));
        this.last4 = (String) paymentCardDetails.get("last4");
        this.expiryDate = new ExpiryDate((String) paymentCardDetails.get("expiry"));
        this.address = new Address((Map<String, String>) paymentCardDetails.get("address"));
    }


    /**
     * @return Id of the object
     */
    public String getId() {
        return _id;
    }

    /**
     * This could be return from the object, but it is persisted in the DB anyway
     *
     * @return class type
     */
    public String get_class() {
        return _class;
    }

    /**
     * @return {@link Address} value
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the Payment Card's address
     *
     * @param address address object to set
     */
    public void setAddress(final Address address) {
        this.address = address;
    }

    /**
     * @return {@link Brand} value
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     * Sets the Payment Card's brand
     *
     * @param brand brand object to set
     */
    public void setBrand(final Brand brand) {
        this.brand = brand;
    }

    /**
     * @return last 4 digits of the card number
     */
    public String getLast4() {
        return last4;
    }

    /**
     * Sets the last four digits of the card number
     *
     * @param last4 the last four digits of the card number
     */
    public void setLast4(final String last4) {
        this.last4 = last4;
    }

    /**
     * @return expiry date
     */
    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the card
     *
     * @param expiryDate expiry date of the card
     */
    public void setExpiryDate(final ExpiryDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return key/value map of additional values
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Sets any additional misc/provider specific values
     *
     * @param attributes key/value map of additional values
     */
    public void setAttributes(final Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Brand
     * <p>
     * A little enum to restrict the 'brand' of the card
     */
    enum Brand {
        AMEX("American Express"),
        DINERS("Diners Club"),
        DISCOVER("Discover"),
        JCB("JCB"),
        MASTERCARD("MasterCard"),
        UNION_PAY("UnionPay"),
        VISA("Visa"),
        UNKNOWN("Unknown");

        private String description;

        /**
         * Default constructor
         */
        Brand(final String cardDescription) {
            this.description = cardDescription;
        }

        /**
         * Retrieves a matching Enum value
         *
         * @param cardDescription the description to match on
         * @return the matching enum value, if one exists
         */
        public static Brand getBrandByCardDescription(final String cardDescription) {
            List<Brand> matchingBrands = Arrays.stream(Brand.values())
                    .filter(brand1 -> brand1.description.equalsIgnoreCase(cardDescription))
                    .collect(Collectors.toList());

            if (matchingBrands.size() == 1) {
                return matchingBrands.get(0);
            }

            return null;
        }
    }


    /**
     * Class to define the Expiry Date of a card
     */
    static class ExpiryDate {
        private final String month;
        private final String year;

        /**
         * Constructor for persistence.
         * Validation could occur here
         *
         * @param month expiry month
         * @param year  expiry year
         */
        @PersistenceConstructor
        public ExpiryDate(String month, String year) {
            this.month = month;
            this.year = year;
        }

        /**
         * Constructor for passing in a 'xx/xxxx' value.
         * Nice to have
         *
         * @param expiryDate the expiry date in 'xx/xxxx' format
         */
        ExpiryDate(final String expiryDate) {
            String[] split = expiryDate.split("/");
            this.month = split[0];
            this.year = split[1];
        }

        /**
         * @return expiry month
         */
        public String getMonth() {
            return month;
        }

        /**
         * @return expiry year
         */
        public String getYear() {
            return year;
        }
    }
}
