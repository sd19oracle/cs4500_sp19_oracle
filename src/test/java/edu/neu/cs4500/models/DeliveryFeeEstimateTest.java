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
	Estimate estimate3;
	Estimate estimate4;
	Frequency daily = Frequency.DAILY;
	Frequency weekday = Frequency.WEEKDAY;
	Frequency monthly = Frequency.MONTHLY;
	Frequency onetime = Frequency.ONETIME;
	Frequency holiday = Frequency.HOLIDAY;
	Frequency weekend = Frequency.WEEKEND;

	List<DeliveryFee> fees = new ArrayList<>();

	DeliveryFee fee1 = new DeliveryFee(20, Frequency.ONETIME, true, 0);
	DeliveryFee fee2 = new DeliveryFee(0.1f, Frequency.MONTHLY, false, 0);
	DeliveryFee fee3 = new DeliveryFee(0.2f, Frequency.WEEKDAY, false, 0);
	DeliveryFee fee4 = new DeliveryFee(10, Frequency.WEEKDAY, true, 0);
	DeliveryFee fee5 = new DeliveryFee(30, Frequency.WEEKEND, true, 0);
	DeliveryFee fee6 = new DeliveryFee(0.5f, Frequency.HOLIDAY, false, 0);


	@BeforeEach
	void setUp() {
		this.estimate1 = new Estimate(100, this.daily, this.weekday, this.fees);
		this.estimate2 = new Estimate(300, this.onetime, this.onetime, this.fees);
		this.estimate3 = new Estimate(500, this.onetime, this.weekend, this.fees);
		this.estimate4 = new Estimate(1000, this.onetime, this.holiday, this.fees);
		this.fees.clear();
	}

	@Test
	void testFullComplexityFees() {
		this.fees.add(this.fee1);
		this.fees.add(this.fee2);
		this.fees.add(this.fee3);
		this.fees.add(this.fee4);
		assertEquals(30, this.estimate1.getFees(0));
		assertEquals(20, this.estimate2.getFees(0));
	}

	@Test
	void testEstimateSubtractsFees() {
		this.fees.add(this.fee1);
		this.fees.add(this.fee2);
		this.fees.add(this.fee3);
		this.fees.add(this.fee4);
		assertEquals(100 + 30, this.estimate1.getEstimate(new ArrayList<>(), 0));
		assertEquals(300 + 20, this.estimate2.getEstimate(new ArrayList<>(), 0));
	}

	@Test
	void testFlatFees() {
		this.fees.add(this.fee1);
		this.fees.add(this.fee4);
		this.fees.add(this.fee5);
		assertEquals(300 + 20, this.estimate2.getEstimate(new ArrayList<>(), 0));
		assertEquals(100 + 10, this.estimate1.getEstimate(new ArrayList<>(), 0));
		assertEquals(500 + 30, this.estimate3.getEstimate(new ArrayList<>(), 0));
	}

	@Test
	void testFeeFrequency() {
		this.fees.add(this.fee1);
		this.fees.add(this.fee2);
		this.fees.add(this.fee3);
		this.fees.add(this.fee5);
		this.fees.add(this.fee6);
		assertEquals(100 + 20, this.estimate1.getEstimate(new ArrayList<>(), 0));
		assertEquals(300 + 20, this.estimate2.getEstimate(new ArrayList<>(), 0));
		assertEquals(500 + 30, this.estimate3.getEstimate(new ArrayList<>(), 0));
		assertEquals(1000 + 500, this.estimate4.getEstimate(new ArrayList<>(), 0));
	}

	@Test
	void testPercentageFees() {
		this.fees.add(this.fee2);
		this.fees.add(this.fee3);
		this.fees.add(this.fee6);
		assertEquals(100 + 20, this.estimate1.getEstimate(new ArrayList<>(), 0));
		assertEquals(1000 + 500, this.estimate4.getEstimate(new ArrayList<>(), 0));
	}
}
