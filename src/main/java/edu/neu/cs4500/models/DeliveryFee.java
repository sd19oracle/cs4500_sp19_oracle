package edu.neu.cs4500.models;

/**
 * Created by Michael Goodnow on 2019-02-23.
 */

public class DeliveryFee {

	private float fee;
	private Frequency frequency;
	private boolean flat;
	private int progressiveRate; // Basic representation of a progressive rate. As of now, is only a multiplier onto the fee when calculating fees that change with distance travelled.

	// when progressiveRate is 0, it is not a progressive fee 
	public DeliveryFee(float fee, Frequency frequency, boolean flat, int progressiveRate) {
		this.fee = fee;
		this.frequency = frequency;
		this.flat = flat;
		this.progressiveRate = progressiveRate;
	}

	public float getFee(int distance) {
		float feeOut = fee;
		if (progressiveRate > 0) {
			feeOut = progressiveRate * distance * fee;
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
	
	public int getPR() {
		return this.progressiveRate;
	}
	
	public void setPR(int pr) {
		this.progressiveRate = pr;
	}
}
