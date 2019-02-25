package edu.neu.cs4500.models;

import java.util.List;
import java.util.stream.Collectors;

//import javax.persistence.*;

//@Entity
//@Table(name="estimates")
public class Estimate {

    //@Id
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private float estimate;
    private float basePrice;
    private Frequency baseFrequency;
    private boolean subscription;
    private Frequency subscriptionFrequency;
    private Frequency deliveryFrequency;

    public Estimate(float estimate, float basePrice, Frequency baseFrequency,
            boolean subscription, Frequency subscriptionFrequency, Frequency deliveryFrequency) {
        this.estimate = estimate;
        this.basePrice = basePrice;
        this.baseFrequency = baseFrequency;
        this.subscription = subscription;
        this.subscriptionFrequency = subscriptionFrequency;
        this.deliveryFrequency = deliveryFrequency;
    }

    public Estimate() {
        
    }
            
    public Integer getId() {
        return id;
    }

    public float getEstimate() {
        return estimate;
    }

    public float getBasePrice() {
        return basePrice;
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

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
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

    /**
     * Calculate subscription discount of the estimate, for a given discount.
     */
    public float getDiscount(List<SubscriptionDiscount> discounts) {
        float accumulateDiscount = 0.0f;
        for (SubscriptionDiscount discount : discounts) {
            if (discount.getFrequency().equals(this.subscriptionFrequency) && discount.isFlat()) {
                accumulateDiscount += discount.getDiscount();
            }
            if (discount.getFrequency().equals(this.subscriptionFrequency) && !discount.isFlat()) {
                accumulateDiscount = accumulateDiscount + this.baseprice * 0.01f * discount.getDiscount();
            }
        }
        return accumulateDiscount;
    }

    public float getFees(List<DeliveryFee> fees) {
    	float acc = 0f;

    	List<DeliveryFee> applicableFees = fees
				.stream()
				.filter(fee -> fee.getFrequency() == this.deliveryFrequency)
				.collect(Collectors.toList());

    	for (DeliveryFee fee : applicableFees) {
    		if (fee.isFlat()) {
    			acc += fee.getFee();
			} else {
    			acc += this.basePrice * fee.getFee();
			}
		}
    	return acc;
	}
}
