package uk.cjack.paymentsapi.provider;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Payment Provider
 *
 * Stores the generic/common attributes of a payment provider implementation
 */
public class PaymentProvider
{

    private static final String PROCESS = "/payment";
    private static final String CANCEL = "/cancel";

    /**
     * the WSDL/Endpoint for the payment provider
     */
    private String endpoint;

    /**
     * credentials required by the payment provider
     */
    private Map<String, String> credentials;

    /**
     * Constructor for the Persistence flow
     */
    public PaymentProvider(final String endpoint,
                           final Map<String, String> credentials)
    {
        this.endpoint = endpoint;
        this.credentials = credentials;
    }

    /**
     * Gets wsdl
     *
     * @return value of wsdl
     */
    public String getEndpoint()
    {
        return endpoint;
    }

    /**
     * Sets wsdl
     *
     * @param endpoint value of wsdl
     */
    public void setEndpoint(final String endpoint)
    {
        this.endpoint = endpoint;
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
     * Process the transaction
     *
     * @param responseType
     * @param payload
     */
    public <T> T processPendingTransaction(final Class<?> responseType,
                                   final Map<String, Object> payload)
    {
        RestTemplate restTemplate = new RestTemplate();

        return (T) restTemplate.getForObject(this.endpoint + PROCESS, responseType, payload);
    }
    /**
     * Cancel the transaction
     *
     * @param responseType
     * @param payload
     */
    public <T> T cancelTransaction(final Class<?> responseType,
                                   final Map<String, Object> payload)
    {
        RestTemplate restTemplate = new RestTemplate();

        return (T) restTemplate.getForObject(this.endpoint + CANCEL, responseType, payload);
    }
}
