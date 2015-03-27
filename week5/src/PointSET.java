import java.util.ArrayList;


public class PointSET {
    private SET<Point2D> mySet;
    
    public PointSET() {
        mySet = new SET<Point2D>();
    }
    
    public boolean isEmpty() {
        return mySet.isEmpty();
    }
    
    public int size() {
        return mySet.size();
    }
    
    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("null argument in insert");
        mySet.add(p);
    }
    
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("null argument in contains");
        return mySet.contains(p);
    }
    
    public void draw() {
        for (Point2D a: mySet)
            a.draw();
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.NullPointerException("null argument in range");
        SET<Point2D> tmpSet = new SET<Point2D>();
        for (Point2D a: mySet) {
            if (rect.contains(a))
                tmpSet.add(a);
        }
        return tmpSet;    
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("null argument in nearest");
        if (mySet.isEmpty())
            return null;
        double max = Double.MAX_VALUE;
        Point2D tmp = null;
        for (Point2D a: mySet) {
            double tmpDist = a.distanceTo(p);
            if (tmpDist < max) {
                max = tmpDist;
                tmp = a;
            }
        }
        return tmp;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Point2D p = new Point2D(0.5, 0.5);
        Point2D q = new Point2D(0.1, 0.2);
        Point2D r = new Point2D(0.0, 0.0);
        
        RectHV k = new RectHV(0.1, 0.1, 0.8, 0.8);
        
        PointSET m = new PointSET();
//        PointSet n = new PointSet();
//        ArrayList n = new ArrayList<Point2D>();
        Iterable<Point2D> n;
        
        StdOut.println("is it empty? " + m.isEmpty());
        StdOut.println("the size is " + m.size());
        m.insert(p);
        m.insert(q);
        StdOut.println("is it empty after insertion? " + m.isEmpty());
        StdOut.println("the size is after insertion " + m.size());
        StdOut.println("does it contain p?" + m.contains(p));
        StdOut.println("does it contain r?" + m.contains(r));

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(.02);
        m.draw();
        
        n = m.range(k);
        for (Point2D t: n) {
            StdOut.println(t.toString());
        }

        StdOut.println(m.nearest(p).toString());
    }

}
