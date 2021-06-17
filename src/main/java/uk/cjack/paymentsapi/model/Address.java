package uk.cjack.paymentsapi.model;

import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Address Object
 *
 * Used to define an address used on the payment card
 */
public class Address {
    private String city;
    private String country;
    private String line1;
    private String line2;
    private String state;
    private String zip;
    private Map<String, String> attributes;

    public Address() {
    }

    @PersistenceConstructor
    public Address(String city, String country, String line1, String line2, String state, String zip, Map<String, String> attributes) {
        this.city = city;
        this.country = country;
        this.line1 = line1;
        this.line2 = line2;
        this.state = state;
        this.zip = zip;
        this.attributes = attributes;
    }

    public Address(final Map<String, String> addressMap) {

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

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getLine1() {
        return line1;
    }

    public Address setLine1(String line1) {
        this.line1 = line1;
        return this;
    }

    public String getLine2() {
        return line2;
    }

    public Address setLine2(String line2) {
        this.line2 = line2;
        return this;
    }

    public String getState() {
        return state;
    }

    public Address setState(String state) {
        this.state = state;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public Address setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public Address setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }
}
