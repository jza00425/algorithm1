
public class KdTree {
    
    private Node root;
    private int size;
    
    private static class Node {
        private Point2D p;
        private Node lb;
        private Node rt;
        private RectHV rect;
        
        public Node(Point2D a, RectHV b) {
            p = a;
            rect = b;
        }
    }
    
    public KdTree() {
        root = null;
        size = 0;
    }
    
    public boolean isEmpty() {
        return (size == 0);
    }
    
    public int size() {
        return size;
    }
    
    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("null argument in insert");
        root = put(root, p, true, new RectHV(0.0, 0.0, 1.0, 1.0));
    }
    
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("null argument in contains");
        return get(root, p, true) != null;
    }
    
    private Node put(Node x, Point2D p, boolean isX, RectHV c) {
        if (p == null || c == null)
            throw new java.lang.NullPointerException("null argument in put");
        RectHV tmp;
        if (x == null) {
            size++;
            return new Node(p, c);
        }
        if (isX) {
            if (p.x() < x.p.x()) {
                tmp = new RectHV(c.xmin(), c.ymin(), x.p.x(), c.ymax());
                x.lb = put(x.lb, p, false, tmp);
            } else if (p.x() > x.p.x()) {
                tmp = new RectHV(x.p.x(), c.ymin(), c.xmax(), c.ymax());
                x.rt = put(x.rt, p, false, tmp);
            } else {
                if (p.y() != x.p.y()) {
                    tmp = new RectHV(x.p.x(), c.ymin(), c.xmax(), c.ymax());
                    x.rt = put(x.rt, p, false, tmp);
                }
            }
        } else {
            if (p.y() < x.p.y()) {
                tmp = new RectHV(c.xmin(), c.ymin(), c.xmax(), x.p.y());
                x.lb = put(x.lb, p, true, tmp);
            } else if (p.y() > x.p.y()) {
                tmp = new RectHV(c.xmin(), x.p.y(), c.xmax(), c.ymax());
                x.rt = put(x.rt, p, true, tmp);
            } else {
                if (p.x() != x.p.x()) {
                    tmp = new RectHV(c.xmin(), x.p.y(), c.xmax(), c.ymax());
                    x.rt = put(x.rt, p, false, tmp);
                }
            }
        }
        return x;
    }
    
    private Point2D get(Node x, Point2D p, boolean isX) {
        if (x == null) return null;
        if (isX) {
            if (p.x() < x.p.x()) 
                return get(x.lb, p, false);
            else if (p.x() > x.p.x())
                return get(x.rt, p, false);
            else {
                if (p.y() != x.p.y())
                    return get(x.rt, p, false);
                else
                    return x.p;
            }
        } else {
            if (p.y() < x.p.y()) 
                return get(x.lb, p, true);
            else if (p.y() > x.p.y())
                return get(x.rt, p, true);
            else {
                if (p.x() != x.p.x())
                    return get(x.rt, p, false);
                else
                    return x.p;
            }
        }
    }
    
    private void drawHelper(Node a, boolean isX) {
        boolean tmpIsX;
        if (a == null)
            return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        a.p.draw();
        if (isX) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(a.p.x(), a.rect.ymin(), a.p.x(), a.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(a.rect.xmin(), a.p.y(), a.rect.xmax(), a.p.y());
        }
        tmpIsX = !isX;
        drawHelper(a.lb, tmpIsX);
        drawHelper(a.rt, tmpIsX);    
    }
    public void draw() {
        drawHelper(root, true);
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.NullPointerException("null argument in range");
        SET<Point2D> tmpSet = new SET<Point2D>();
        search(root, tmpSet, rect);
        return tmpSet;
    }
    
    private void search(Node a, SET<Point2D> b, RectHV rect) {
        if (a == null)
            return;
        if (rect.contains(a.p))
            b.add(a.p);
        if (rect.intersects(a.rect)) {
            search(a.lb, b, rect);
            search(a.rt, b, rect);
        }
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException("null argument in nearest");
        Point2D big = new Point2D(3.0, 3.0);
        return findNearest(root, p, big);
    }
    
    private Point2D findNearest(Node a, Point2D p, Point2D minP) {
        if (p == null)
            throw new java.lang.NullPointerException("null argument in findNearest");
        Point2D tmpP = minP;
        if (a == null)
            return tmpP;
        if (a.rect.distanceTo(p) < minP.distanceTo(p)) {
            if (a.p.distanceTo(p) < minP.distanceTo(p))
                tmpP = a.p;
            if (a.lb == null && a.rt == null)
                return tmpP;
            else if (a.lb != null && a.rt == null)
                return findNearest(a.lb, p, minP);
            else if (a.lb == null && a.rt != null)
                return findNearest(a.rt, p, minP);
            else {
                if (a.lb.rect.contains(p)) {
                    tmpP = findNearest(a.lb, p, minP);
                    return findNearest(a.rt, p, tmpP);
                } else {
                    tmpP = findNearest(a.rt, p, minP);
                    return findNearest(a.lb, p, tmpP);
                }
            }
        } else
            return tmpP;
    }
    

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
