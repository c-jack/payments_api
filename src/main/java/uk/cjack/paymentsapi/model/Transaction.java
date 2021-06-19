package uk.cjack.paymentsapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.cjack.paymentsapi.provider.PaymentProcessor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "transactions")
@JsonPropertyOrder({"id", "_class", "address"})
public class Transaction
{

    @Id
    private String id;

    @Field
    @JsonProperty("type")
    private String _class;

    @Field
    private LocalDate paymentDate;

    @Field
    private PaymentProcessor paymentProcessor;

    @Field
    private Status status;

    @Field
    private Type transactionType;

    @Field
    private String customerRef;

    @Field
    private String productRef;

    @DBRef
    private PaymentCard card;

    @Field
    private long amount;

    @Field
    private List<String> audit = new ArrayList<>();


    /**
     * Default Public Constructor
     */
    public Transaction()
    {
        // nothing here
    }

    /**
     * Constructor for the Persistence flow
     *
     * @param id              the ID of the Transaction document
     * @param _class          the class name
     * @param paymentProcessor the processor of the payment
     * @param paymentDate      the date of the payment
     * @param status          the {@link Status} of the payment
     * @param transactionType the {@link Type} of the transaction
     * @param customerRef     the reference to the customer making the transaction
     * @param productRef      the reference to the product the transaction is for
     * @param card            the {@link PaymentCard} for the transaction
     * @param amount          the amount of the transaction
     * @param audit           any audit notes relating to the transaction
     */
    @PersistenceConstructor
    public Transaction(final String id,
                       final String _class,
                       final PaymentProcessor paymentProcessor,
                       final LocalDate paymentDate,
                       final Status status,
                       final Type transactionType,
                       final String customerRef,
                       final String productRef,
                       final PaymentCard card,
                       final long amount,
                       final List<String> audit)
    {
        this.id = id;
        this._class = _class;
        this.paymentProcessor = paymentProcessor;
        this.paymentDate = paymentDate;
        this.status = status;
        this.transactionType = transactionType;
        this.customerRef = customerRef;
        this.productRef = productRef;
        this.card = card;
        this.amount = amount;
        this.audit = audit;
    }

    /**
     * Constructor for creating a new Transaction
     *
     * @param paymentProcessor the processor of the payment
     * @param paymentDate      the date of the payment
     * @param status           the {@link Status} of the payment
     * @param transactionType  the {@link Type} of the transaction
     * @param customerRef      the reference to the customer making the transaction
     * @param productRef       the reference to the product the transaction is for
     * @param card             the {@link PaymentCard} for the transaction
     * @param amount           the amount of the transaction
     * @param audit            any audit notes relating to the transaction
     */
    @JsonCreator
    public Transaction(final PaymentProcessor paymentProcessor,
                       final LocalDate paymentDate,
                       final Status status,
                       final Type transactionType,
                       final String customerRef,
                       final String productRef,
                       final PaymentCard card,
                       final long amount,
                       final List<String> audit)
    {
        this.paymentProcessor = paymentProcessor;
        this.paymentDate = paymentDate;
        this.status = status;
        this.transactionType = transactionType;
        this.customerRef = customerRef;
        this.productRef = productRef;
        this.card = card;
        this.amount = amount;
        this.audit = audit;
    }

    /**
     * Gets id
     *
     * @return value of id
     */
    public String getId()
    {
        return id;
    }

    /**
     * Gets _class
     *
     * @return value of _class
     */
    public String get_class()
    {
        return _class;
    }

    /**
     * Gets paymentDate
     *
     * @return value of paymentDate
     */
    public LocalDate getPaymentDate()
    {
        return paymentDate;
    }

    /**
     * Sets paymentDate
     *
     * @param paymentDate value of paymentDate
     */
    public void setPaymentDate(final LocalDate paymentDate)
    {
        this.paymentDate = paymentDate;
    }

    /**
     * Gets status
     *
     * @return value of status
     */
    public Status getStatus()
    {
        return status;
    }

    /**
     * Sets status
     *
     * @param status value of status
     */
    public void setStatus(final Status status)
    {
        this.status = status;
    }

    /**
     * Gets type
     *
     * @return value of type
     */
    public Type getTransactionType()
    {
        return transactionType;
    }

    /**
     * Sets type
     *
     * @param transactionType value of type
     */
    public void setTransactionType(final Type transactionType)
    {
        this.transactionType = transactionType;
    }

    /**
     * Gets customerRef
     *
     * @return value of customerRef
     */
    public String getCustomerRef()
    {
        return customerRef;
    }

    /**
     * Sets customerRef
     *
     * @param customerRef value of customerRef
     */
    public void setCustomerRef(final String customerRef)
    {
        this.customerRef = customerRef;
    }

    /**
     * Gets productRef
     *
     * @return value of productRef
     */
    public String getProductRef()
    {
        return productRef;
    }

    /**
     * Sets productRef
     *
     * @param productRef value of productRef
     */
    public void setProductRef(final String productRef)
    {
        this.productRef = productRef;
    }

    /**
     * Gets card
     *
     * @return value of card
     */
    public PaymentCard getCard()
    {
        return card;
    }

    /**
     * Sets card
     *
     * @param card value of card
     */
    public void setCard(final PaymentCard card)
    {
        this.card = card;
    }

    /**
     * Gets amount
     *
     * @return value of amount
     */
    public long getAmount()
    {
        return amount;
    }

    /**
     * Sets amount
     *
     * @param amount value of amount
     */
    public void setAmount(final long amount)
    {
        this.amount = amount;
    }

    /**
     * Gets audit
     *
     * @return value of audit
     */
    public List<String> getAudit()
    {
        return audit;
    }

    /**
     * Sets audit
     *
     * @param audit value of audit
     */
    public void setAudit(final List<String> audit)
    {
        this.audit = audit;
    }

    /**
     * Gets paymentProvider
     *
     * @return value of paymentProvider
     */
    public PaymentProcessor getPaymentProcessor()
    {
        return paymentProcessor;
    }

    /**
     * Sets paymentProcessor
     *
     * @param paymentProcessor value of paymentProcessor
     */
    public void setPaymentProcessor(final PaymentProcessor paymentProcessor)
    {
        this.paymentProcessor = paymentProcessor;
    }

    /**
     * Payment Status
     */
    public enum Status
    {
        COMPLETE,
        PENDING,
        FAILED,
        CANCELLED
    }

    /**
     * Payment Type
     */
    public enum Type
    {
        PURCHASE,
        REFUND
    }
}
