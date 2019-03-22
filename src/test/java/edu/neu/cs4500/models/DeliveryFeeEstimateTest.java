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
	Estimate estimate5;
	Frequency daily = Frequency.DAILY;
	Frequency weekday = Frequency.WEEKDAY;
	Frequency monthly = Frequency.MONTHLY;
	Frequency onetime = Frequency.ONETIME;
	Frequency holiday = Frequency.HOLIDAY;
	Frequency weekend = Frequency.WEEKEND;

	List<DeliveryFee> fees = new ArrayList<>();

	DeliveryFee fee1 = new DeliveryFee(20, Frequency.ONETIME, true, null);
	DeliveryFee fee2 = new DeliveryFee(0.1f, Frequency.MONTHLY, false, null);
	DeliveryFee fee3 = new DeliveryFee(0.2f, Frequency.WEEKDAY, false, null);
	DeliveryFee fee4 = new DeliveryFee(10, Frequency.WEEKDAY, true, null);
	DeliveryFee fee5 = new DeliveryFee(30, Frequency.WEEKEND, true, null);
	DeliveryFee fee6 = new DeliveryFee(0.5f, Frequency.HOLIDAY, false, null);
	DeliveryFee fee7 = new DeliveryFee(20, Frequency.DAILY, true, null);
	DeliveryFee fee8 = new DeliveryFee(0.1f, Frequency.DAILY, false, null);

	// $10 for every 5 miles past 50 miles driven
	DeliveryFee pFee1 = new DeliveryFee(10.0f, Frequency.DAILY, true, new ProgressiveRate(5, 50));
	// 1% for every 1 mile past 10 miles driven
	DeliveryFee pFee2 = new DeliveryFee(0.01f, Frequency.DAILY, false, new ProgressiveRate(1, 10));
	


	@BeforeEach
	void setUp() {
		this.estimate1 = new Estimate(100, this.daily, this.weekday, this.fees);
		this.estimate2 = new Estimate(300, this.onetime, this.onetime, this.fees);
		this.estimate3 = new Estimate(500, this.onetime, this.weekend, this.fees);
		this.estimate4 = new Estimate(1000, this.onetime, this.holiday, this.fees);
		this.estimate5 = new Estimate(100, this.onetime, this.daily, this.fees);
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
	
	@Test
	void testProgressiveFeesOnOwn() {
		assertEquals(0, this.pFee1.getFee(30));
		assertEquals(0, this.pFee1.getFee(49));
		assertEquals(0, this.pFee1.getFee(50));
		assertEquals(10, this.pFee1.getFee(51));
		assertEquals(10, this.pFee1.getFee(55));
		assertEquals(20, this.pFee1.getFee(56));
		assertEquals(20, this.pFee1.getFee(60));

		assertEquals(0, this.pFee2.getFee(5));
		assertEquals(0, this.pFee2.getFee(10));
		assertEquals(0.01f, this.pFee2.getFee(11));
		assertEquals(0.02f, this.pFee2.getFee(12));
		assertEquals(0.04f, this.pFee2.getFee(14));
		assertEquals(0.08f, this.pFee2.getFee(18));
	}
	
	@Test
	void testProgressiveFeesInEstimate() {
		this.fees.add(this.pFee1);

		assertEquals(100, this.estimate5.getEstimate(new ArrayList<>(), 30));
		assertEquals(100, this.estimate5.getEstimate(new ArrayList<>(), 50));
		assertEquals(110, this.estimate5.getEstimate(new ArrayList<>(), 51));
		assertEquals(110, this.estimate5.getEstimate(new ArrayList<>(), 55));
		assertEquals(120, this.estimate5.getEstimate(new ArrayList<>(), 56));
		
		this.fees.clear();
		this.fees.add(this.pFee2);

		assertEquals(100, this.estimate5.getEstimate(new ArrayList<>(), 5));
		assertEquals(100, this.estimate5.getEstimate(new ArrayList<>(), 10));
		assertEquals(101, this.estimate5.getEstimate(new ArrayList<>(), 11));
		assertEquals(102, this.estimate5.getEstimate(new ArrayList<>(), 12));
		assertEquals(104, this.estimate5.getEstimate(new ArrayList<>(), 14));
		assertEquals(108, this.estimate5.getEstimate(new ArrayList<>(), 18));
	}
	
	@Test
	void testProgressiveFeesWithFlatFees() {
		this.fees.add(this.pFee1);
		this.fees.add(this.fee7);

		assertEquals(120, this.estimate5.getEstimate(new ArrayList<>(), 30));
		assertEquals(120, this.estimate5.getEstimate(new ArrayList<>(), 50));
		assertEquals(130, this.estimate5.getEstimate(new ArrayList<>(), 51));
		assertEquals(130, this.estimate5.getEstimate(new ArrayList<>(), 55));
		assertEquals(140, this.estimate5.getEstimate(new ArrayList<>(), 56));
	}

	@Test
	void testProgressiveFeesWithPercentageFees() {
		this.fees.add(this.pFee2);
		this.fees.add(this.fee8);

		assertEquals(110, this.estimate5.getEstimate(new ArrayList<>(), 5));
		assertEquals(110, this.estimate5.getEstimate(new ArrayList<>(), 10));
		assertEquals(111, this.estimate5.getEstimate(new ArrayList<>(), 11));
		assertEquals(112, this.estimate5.getEstimate(new ArrayList<>(), 12));
		assertEquals(114, this.estimate5.getEstimate(new ArrayList<>(), 14));
		assertEquals(118, this.estimate5.getEstimate(new ArrayList<>(), 18));
	}
	
	
	
	
	
	
	
	
	
	
}
