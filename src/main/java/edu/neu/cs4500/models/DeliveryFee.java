package edu.neu.cs4500.models;

/**
 * Created by Michael Goodnow on 2019-02-23.
 */

public class DeliveryFee {

	private float fee;
	private Frequency frequency;
	private boolean flat;

	public DeliveryFee(float fee, Frequency frequency, boolean flat) {
		this.fee = fee;
		this.frequency = frequency;
		this.flat = flat;
	}

	public float getFee() {
		return fee;
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
}
