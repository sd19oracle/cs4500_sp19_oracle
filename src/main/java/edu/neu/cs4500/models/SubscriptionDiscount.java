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
}
