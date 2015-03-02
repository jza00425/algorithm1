/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {
    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope(); // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    private class BySlope implements Comparator<Point> {
        public int compare(Point v, Point w) {
            double a = slopeTo(v);
            double b = slopeTo(w);
            if (a < b)
                return -1;
            else if (a > b)
                return +1;
            else
                return 0;
        }
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        double yDiff = (double)(that.y - this.y);
        double xDiff = (double)(that.x - this.x);
        if ((xDiff == 0.0) && (yDiff == 0.0))
            return Double.NEGATIVE_INFINITY;
        if (xDiff == 0.0)
            return Double.POSITIVE_INFINITY;
        if (yDiff == 0.0)
            return +0.0;
        
        double slope = yDiff / xDiff;
        return slope;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        
    }
}
