/**
 * Written:2/8/2015<p>
 * Compilation: javac Percolation.java<p>
 * Execution: java Percolation sizeOftheSquare<p>
 * 
 * This program model a percolation system.
 * 
 * @author Tong Zhang
 * 
 *
 */
public class Percolation {
	private WeightedQuickUnionUF a;
    private boolean[][] grid; //an N-by-N grid of sites
    private int size;
    
    // create N-by-N grid, with all sites blocked    
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("N is less then 1");
        size = N;
        grid = new boolean[N][N];
        a = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                grid[i][j] = false;
    }
    
    
    // test row i, column j, if they are out of boundary
    private void boundTest(int i , int j) {
        if (i < 1 || i > size)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j < 1 || j > size)
            throw new IndexOutOfBoundsException("column index i out of bounds");
    }
    
    /**
     * Translate location (i, j) to a one-dimensional position
     * @param i: row number
     * @param j: column number
     * @return: one-dimensional position
     */
    private int xyTo1D(int i, int j) {
        return (i - 1) * size + (j - 1);
    }
    
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        int position1, position2;

        boundTest(i, j);
    	if (grid[i -1][j - 1]) {
    		return;
    	} else {
    		grid[i - 1][j - 1] = true;
    		position1 = xyTo1D(i, j);
    		if (i == 1)
    			a.union(position1, size * size);
    		if (i == size)
    			a.union(position1, size * size + 1);
    		if ((i - 2 >= 0) && grid[i - 2][j - 1]) {
    			position2 = xyTo1D(i - 1, j);
    			a.union(position1, position2);
    		}
    		if ((i <= size - 1) && grid[i][j - 1]) {
    			position2 = xyTo1D(i + 1, j);
    			a.union(position1, position2);
    		}
    		if ((j - 2 >= 0) && grid[i - 1][j - 2]) {
    			position2 = xyTo1D(i, j - 1);
    			a.union(position1, position2);
    		}
    		if ((j <= size - 1) && grid[i - 1][j]) {
    			position2 = xyTo1D(i, j + 1);
    			a.union(position1, position2);
    		}
    	}
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
    	int position1;
    	
    	boundTest(i, j);
    	position1 = xyTo1D(i, j);
    	return a.connected(position1, size * size);
    }
    
    // does the system percolate?
    public boolean percolates() {
    	return a.connected(size * size, size * size + 1);
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
    	boundTest(i, j);
    	return grid[i - 1][j - 1];
    }
    
    public static void main(String[] args) {
        int tmp = Integer.parseInt(args[0]);
        int num1, num2;
        Percolation model = new Percolation(tmp);
        model.open(1, 1);
        model.open(2, 2);
        num1 = model.xyTo1D(1, 2);
        num2 = model.xyTo1D(2, 2);
        StdOut.println(num1);
        StdOut.println(num2);
        StdOut.println(model.a.connected(4, 5));
        StdOut.println("hello, world");     
    }        
}
