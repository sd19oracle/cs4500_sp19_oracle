package edu.neu.cs4500.models;

import java.util.List;
import java.util.stream.Collectors;

public class Estimate {

    private float basePrice;
    private Frequency subscriptionFrequency;
    private Frequency deliveryFrequency;
	List<DeliveryFee> fees;

    public Estimate(
			float basePrice,
			Frequency subscriptionFrequency,
			Frequency deliveryFrequency,
			List<DeliveryFee> fees) {
        this.basePrice = basePrice;
        this.subscriptionFrequency = subscriptionFrequency;
        this.deliveryFrequency = deliveryFrequency;
        this.fees = fees;
    }

    public Estimate() {

    }

    // distance affects the progressive fees
    public float getEstimate(List<SubscriptionDiscount> discounts, int distance) {
        return this.basePrice + this.getFees(distance) - this.getDiscount(discounts);
    }

    public float getBasePrice() {
        return basePrice;
    }

    public Frequency getSubscriptionFrequency() {
        return subscriptionFrequency;
    }

    public Frequency getDeliveryFrequency() {
        return deliveryFrequency;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
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
                accumulateDiscount = accumulateDiscount + this.basePrice * 0.01f * discount.getDiscount();
            }
        }
        return accumulateDiscount;
    }

    // distance affects the progressive fees
    public float getFees(int distance) {
    	float acc = 0f;

    	List<DeliveryFee> applicableFees = this.fees
				.stream()
				.filter(fee -> fee.getFrequency() == this.deliveryFrequency)
				.collect(Collectors.toList());

    	for (DeliveryFee fee : applicableFees) {
    		if (fee.isFlat()) {
    			acc += fee.getFee(distance);
			} else {
    			acc += this.basePrice * fee.getFee(distance);
			}
		}
    	return acc;
	}
}
