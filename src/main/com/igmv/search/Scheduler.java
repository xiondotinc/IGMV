package main.com.igmv.search;


public class Scheduler {

	private final int k, limit;

	private final double lambda;

	public Scheduler(int k, double lam, int limit) {
		this.k = k;
		this.lambda = lam;
		this.limit = limit;
	}

	public Scheduler() {
		this.k = 100;
		this.lambda = 0.00045;
		this.limit = 100000;
	}

	public double getTemp(int t) {
		if (t < limit) {
			double res = k * Math.exp((-1) * lambda * t);
			return res;
		} else {
			return 0.0;
		}
	}
}
