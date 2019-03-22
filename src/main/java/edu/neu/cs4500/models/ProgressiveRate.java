package edu.neu.cs4500.models;

// Describes a rate for a DeliveryFee, fee is handled by the DeliveryFee itself
public class ProgressiveRate {
	private int rate;
	private int threshold;
	
	public ProgressiveRate(int rate, int threshold) {
		this.setRate(rate);
		this.setThreshold(threshold);
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}	

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
}
