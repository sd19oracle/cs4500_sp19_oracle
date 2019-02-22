package edu.neu.cs4500.models;

import java.util.List;

//import javax.persistence.*;

//@Entity
//@Table(name="estimates")
public class Estimate {

    //@Id
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private float estimate;
    private float baseprice;
    private Frequency baseFrequency;
    private boolean subscription;
    private Frequency subscriptionFrequency;
    private Frequency deliveryFrequency;

    public Estimate(float estimate, float baseprice, Frequency baseFrequency, 
            boolean subscription, Frequency subscriptionFrequency, Frequency deliveryFrequency) {
        this.estimate = estimate;
        this.baseprice = baseprice;
        this.baseFrequency = baseFrequency;
        this.subscription = subscription;
        this.subscriptionFrequency = subscriptionFrequency;
        this.deliveryFrequency = deliveryFrequency;
    }
            
    public Integer getId() {
        return id;
    }

    public float getEstimate() {
        return estimate;
    }

    public float getBaseprice() {
        return baseprice;
    }

    public Frequency getBaseFrequency() {
        return baseFrequency;
    }

    public boolean getSubscription() {
        return subscription;
    }

    public Frequency getSubscriptionFrequency() {
        return subscriptionFrequency;
    }

    public Frequency getDeliveryFrequency() {
        return deliveryFrequency;
    }

    public void setEstimate(float estimate) {
        this.estimate = estimate;
    }

    public void setBaseprice(float baseprice) {
        this.baseprice = baseprice;
    }

    public void setBaseFrequency(Frequency baseFrequency) {
        this.baseFrequency = baseFrequency;
    }

    public void setSubscription(boolean subscription) {
        this.subscription = subscription;
    }

    public void setSubscriptionFrequency(Frequency subscriptionFrequency) {
        this.subscriptionFrequency = subscriptionFrequency;
    }

    public void setDeliveryFrequency(Frequency deliveryFrequency) {
        this.deliveryFrequency = deliveryFrequency;
    }
}
