package edu.neu.cs4500.models;

/**
 * Created by Michael Goodnow on 2019-02-23.
 */

public class DeliveryFee {

	private float fee;
	private Frequency frequency;
	private boolean flat;
	private ProgressiveRate progRate; // This can be null to indicate this fee is not progressive

	// when progressiveRate is 0, it is not a progressive fee 
	public DeliveryFee(float fee, Frequency frequency, boolean flat, ProgressiveRate progressiveRate) {
		this.fee = fee;
		this.frequency = frequency;
		this.flat = flat;
		this.progRate = progressiveRate;
	}

	public float getFee(int distance) {
		float feeOut = fee;
		if (progRate != null) {
			feeOut = (distance > progRate.getThreshold()) ? fee * (float) Math.ceil((double)(distance - progRate.getThreshold()) / (double) progRate.getRate()) : 0.0f;
		}
		return feeOut;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public boolean isFlat() {
		return flat;
	}

	public void setFlat(boolean flat) {
		this.flat = flat;
	}
	
	public ProgressiveRate getPR() {
		return this.progRate;
	}
	
	public void setPR(ProgressiveRate pr) {
		this.progRate = pr;
	}
}
