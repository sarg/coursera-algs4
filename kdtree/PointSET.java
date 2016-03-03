import edu.princeton.cs.algs4.*;

import java.util.Iterator;

public class PointSET {
    private SET<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : pointSet) {
            p.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        LinkedStack<Point2D> ret = new LinkedStack<>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                ret.push(p);
            }
        }
        return ret;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D from) {
        if (isEmpty())
            return null;

        Iterator<Point2D> i = pointSet.iterator();
        Point2D minDistPoint = i.next();
        double min = minDistPoint.distanceSquaredTo(from);
        while (i.hasNext()) {
            Point2D cur = i.next();
            double d = cur.distanceSquaredTo(from);
            if (min > d) {
                min = d;
                minDistPoint = cur;
            }
        }

        return minDistPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
