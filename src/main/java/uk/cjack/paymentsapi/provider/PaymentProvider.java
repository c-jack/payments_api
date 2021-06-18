package uk.cjack.paymentsapi.provider;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Document(collection = "paymentCards")
public class PaymentProvider {

    @Id
    private String id;

    @Field
    @JsonProperty("type")
    private String _class;

    /**
     *  Name of the provider
     */
    @Field
    private String providerName;

    /**
     * the WSDL/Endpoint for the payment provider
     */
    @Field
    private String wsdl;

    /**
     * credentials required by the payment provider
     */
    @Field
    private Map<String, String> credentials;

    /**
     * the field mapping schema for the payment provider
     */
    @Field
    private Map<String, String> schema;



    /**
     * Constructor for the Persistence flow
     *
     * @param id        the ID of the PaymentCard document
     * @param _class     the class name
     */
    @PersistenceConstructor
    public PaymentProvider(final String id,
                           final String _class,
                           final String providerName,
                           final String wsdl,
                           final Map<String, String> credentials,
                           final Map<String, String> schema)
    {
        this.id = id;
        this._class = _class;
        this.providerName = providerName;
        this.wsdl = wsdl;
        this.credentials = credentials;
        this.schema = schema;
    }

    /**
     * Constructor for creating a new Payment Provider
     */
    @JsonCreator
    public PaymentProvider(final String providerName,
                           final String wsdl,
                           final Map<String, String> credentials,
                           final Map<String, String> schema)
    {
        this.providerName = providerName;
        this.wsdl = wsdl;
        this.credentials = credentials;
        this.schema = schema;
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
     * Gets providerName
     *
     * @return value of providerName
     */
    public String getProviderName()
    {
        return providerName;
    }

    /**
     * Sets providerName
     *
     * @param providerName value of providerName
     */
    public void setProviderName(final String providerName)
    {
        this.providerName = providerName;
    }

    /**
     * Gets wsdl
     *
     * @return value of wsdl
     */
    public String getWsdl()
    {
        return wsdl;
    }

    /**
     * Sets wsdl
     *
     * @param wsdl value of wsdl
     */
    public void setWsdl(final String wsdl)
    {
        this.wsdl = wsdl;
    }

    /**
     * Gets credentials
     *
     * @return value of credentials
     */
    public Map<String, String> getCredentials()
    {
        return credentials;
    }

    /**
     * Sets credentials
     *
     * @param credentials value of credentials
     */
    public void setCredentials(final Map<String, String> credentials)
    {
        this.credentials = credentials;
    }

    /**
     * Gets schema
     *
     * @return value of schema
     */
    public Map<String, String> getSchema()
    {
        return schema;
    }

    /**
     * Sets schema
     *
     * @param schema value of schema
     */
    public void setSchema(final Map<String, String> schema)
    {
        this.schema = schema;
    }
}
