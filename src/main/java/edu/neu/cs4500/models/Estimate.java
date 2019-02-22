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

}
