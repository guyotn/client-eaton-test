package mock;

import java.util.Random;

public class Mock {
	int min = 0;
	int max = 1000;
	Random random = new Random();
	
	public Mock() {
		
	}
	
	public Mock (int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		this.min = min;
		this.max = max;
	}
	
	public int getRandom() {
		return random.nextInt((max - min) + 1) + min;
	}
	
	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}
	
	public void setMin(int min) {
		if (min > this.max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		this.min = min;
	}
	
	public void setMax(int max) {
		if (this.min > max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		this.max = max;
	}

	public void setMinMax(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		this.min = min;
		this.max = max;
	}
}
