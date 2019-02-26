package edu.neu.cs4500.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryFeeEstimateTest {
	Estimate estimate1;
	Estimate estimate2;
	Frequency daily = Frequency.DAILY;
	Frequency weekday = Frequency.WEEKDAY;
	Frequency monthly = Frequency.MONTHLY;
	Frequency onetime = Frequency.ONETIME;

	List<DeliveryFee> fees = new ArrayList<>();

	DeliveryFee fee1 = new DeliveryFee(20, Frequency.ONETIME, true);
	DeliveryFee fee2 = new DeliveryFee(0.1f, Frequency.MONTHLY, false);
	DeliveryFee fee3 = new DeliveryFee(0.2f, Frequency.WEEKDAY, false);
	DeliveryFee fee4 = new DeliveryFee(10, Frequency.WEEKDAY, true);


	@BeforeEach
	void setUp() {
		this.estimate1 = new Estimate(100, this.daily, this.weekday, this.fees);
		this.estimate2 = new Estimate(300, this.onetime, this.onetime, this.fees);
		this.fees.clear();
	}

	@Test
	void testFullComplexityFees() {
		this.fees.add(this.fee1);
		this.fees.add(this.fee2);
		this.fees.add(this.fee3);
		this.fees.add(this.fee4);
		assertEquals(30, this.estimate1.getFees());
		assertEquals(20, this.estimate2.getFees());
	}

	@Test
	void testEstimateSubtractsFees() {
		this.fees.add(this.fee1);
		this.fees.add(this.fee2);
		this.fees.add(this.fee3);
		this.fees.add(this.fee4);
		assertEquals(100 + 30, this.estimate1.getEstimate(new ArrayList<>()));
		assertEquals(300 + 20, this.estimate2.getEstimate(new ArrayList<>()));
	}
}
