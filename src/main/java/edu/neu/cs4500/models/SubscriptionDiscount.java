package edu.neu.cs4500.models;

//import javax.persistence.*;

//@Entity
//@Table(name="subscription_discount")
public class SubscriptionDiscount {

    //@Id
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private float discount;
    private Frequency frequency;
    private boolean flat;

    public SubscriptionDiscount(float discount, Frequency frequency, boolean flat) {
        this.discount = discount;
        this.frequency = frequency;
        this.flat = flat;
    }

    public SubscriptionDiscount() {
    
    }
    
    public Integer getId() {
        return id;
    }

    public float getDiscount() {
        return discount;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public boolean isFlat() {
        return flat;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public void setFlat(boolean flat) {
        this.flat = flat;
    }
}
