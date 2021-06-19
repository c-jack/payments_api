package uk.cjack.paymentsapi.model;

import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Address
 * <p>
 * Used to define an address used on the payment card.
 * The fields have been left as the 'internationalised' names, as most of the payment APIs use the
 * more americanised names, such as 'zip'
 */
public class Address
{
    private String city;
    private String country;
    private String line1;
    private String line2;
    private String state;
    private String zip;
    private Map<String, String> attributes;

    public Address()
    {
    }

    /**
     * Constructor for the Persistence flow
     *
     * @param city       the city/town of the address
     * @param country    the country of the address
     * @param line1      address line 1
     * @param line2      address line 2
     * @param state      the state/county
     * @param zip        the postcode/zip
     * @param attributes any additional metadata for the address (for supporting additional schema data for integration of APIs)
     */
    @PersistenceConstructor
    public Address(final String city,
                   final String country,
                   final String line1,
                   final String line2,
                   final String state,
                   final String zip,
                   final Map<String, String> attributes)
    {
        this.city = city;
        this.country = country;
        this.line1 = line1;
        this.line2 = line2;
        this.state = state;
        this.zip = zip;
        this.attributes = attributes;
    }


    /**
     * Constructor to support {@link Map} argument for creating an {@link Address}
     *
     * @param addressMap a Map of address String values
     */
    public Address(final Map<String, String> addressMap)
    {

        Map<String, String> values = new HashMap<>(addressMap);
        this.city = addressMap.get("city");
        this.country = addressMap.get("country");
        this.line1 = addressMap.get("line1");
        this.line2 = addressMap.get("line2");
        this.state = addressMap.get("state");
        this.zip = addressMap.get("zip");

        values.keySet().removeAll(Arrays.asList("city", "country", "line1", "line2", "state", "zip"));

        attributes = new HashMap<>(values);
    }

    /**
     * Gets city
     *
     * @return value of city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Sets city
     *
     * @param city value of city
     */
    public void setCity(final String city)
    {
        this.city = city;
    }

    /**
     * Gets country
     *
     * @return value of country
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Sets country
     *
     * @param country value of country
     */
    public void setCountry(final String country)
    {
        this.country = country;
    }

    /**
     * Gets line1
     *
     * @return value of line1
     */
    public String getLine1()
    {
        return line1;
    }

    /**
     * Sets line1
     *
     * @param line1 value of line1
     */
    public void setLine1(final String line1)
    {
        this.line1 = line1;
    }

    /**
     * Gets line2
     *
     * @return value of line2
     */
    public String getLine2()
    {
        return line2;
    }

    /**
     * Sets line2
     *
     * @param line2 value of line2
     */
    public void setLine2(final String line2)
    {
        this.line2 = line2;
    }

    /**
     * Gets state
     *
     * @return value of state
     */
    public String getState()
    {
        return state;
    }

    /**
     * Sets state
     *
     * @param state value of state
     */
    public void setState(final String state)
    {
        this.state = state;
    }

    /**
     * Gets zip
     *
     * @return value of zip
     */
    public String getZip()
    {
        return zip;
    }

    /**
     * Sets zip
     *
     * @param zip value of zip
     */
    public void setZip(final String zip)
    {
        this.zip = zip;
    }

    /**
     * Gets attributes
     *
     * @return value of attributes
     */
    public Map<String, String> getAttributes()
    {
        return attributes;
    }

    /**
     * Sets attributes
     *
     * @param attributes value of attributes
     */
    public void setAttributes(final Map<String, String> attributes)
    {
        this.attributes = attributes;
    }
}
