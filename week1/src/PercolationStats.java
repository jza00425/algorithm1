/**
 * Written: 2/8/2015<p>
 * Compilation: javac PercolationStats.java<p>
 * Execution: java PercolationStats sizeofTheSquare timesOfExperiments<p>
 * 
 * Monte Carlo simulation. 
 * To estimate the percolation threshold, consider the following 
 * computational experiment:
 *  Initialize all sites to be blocked.
 *  Repeat the following until the system percolates:
 *  Choose a site (row i, column j) uniformly at random among all blocked sites.
 *  Open the site (row i, column j).
 * The fraction of sites that are opened when the system 
 * percolates provides an estimate of the percolation threshold.
 * 
 * @author tong
 *
 */
public class PercolationStats {
	
	private Percolation[] experiments;
	private double[] cnt;
	private int times;
	private double u, o;
	
	public PercolationStats(int N, int T) {
		int row, column;
		times = T;
		cnt = new double[T];
		experiments = new Percolation[T];
		for (int i = 0; i < T; i++) {
			experiments[i] = new Percolation(N);
			while (!experiments[i].percolates()) {
				cnt[i]++;
				do {
					row = StdRandom.uniform(1, N + 1);
					column = StdRandom.uniform(1, N + 1);
				} while (experiments[i].isOpen(row, column));
				experiments[i].open(row, column);
			}
			cnt[i] = cnt[i] / (N * N);
		}	
	}
	
	public double mean() {
		u = StdStats.mean(cnt);
		return u;
	}
	
	public double stddev() {
		if (times == 1)
			o = Double.NaN;
		else
			o = StdStats.stddev(cnt);
		return o;
	}
	
	public double confidenceLo() {
		if (o == Double.NaN)
			return Double.NaN;
		else
			return u - 1.96 * o /(Math.sqrt(times));
	}
	
	public double confidenceHi() {
		if (o == Double.NaN)
			return Double.NaN;
		else
			return u + 1.96 * o /(Math.sqrt(times));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);
        StdOut.println("mean 	=" + ps.mean());
        StdOut.println("stddev	=" + ps.stddev());
        StdOut.println("95% confidence interval	= " 
        + ps.confidenceLo() +", " + ps.confidenceHi());
        
	}

}
