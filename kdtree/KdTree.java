import edu.princeton.cs.algs4.LinkedBag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class KdTree {
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
    }

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    private Node split(Node src, Point2D p, boolean vert, int cmp) {
        Node n = new Node();
        n.p = p;

        if (vert) {
            if (cmp < 0) {
                // left
                n.rect = new RectHV(src.rect.xmin(), src.rect.ymin(), src.p.x
                        (), src.rect.ymax());
            } else {
                // right
                n.rect = new RectHV(src.p.x(), src.rect.ymin(), src.rect.xmax
                        (), src.rect.ymax());
            }
        } else {
            if (cmp < 0) {
                // bottom
                n.rect = new RectHV(src.rect.xmin(), src.rect.ymin(), src
                        .rect.xmax(), src.p.y());
            } else {
                // top
                n.rect = new RectHV(src.rect.xmin(), src.p.y(), src.rect.xmax
                        (), src.rect.ymax());
            }
        }

        return n;
    }

    private static int cmp(Point2D p1, Point2D p2, boolean vert) {
        int cmp = vert
                ? Double.compare(p1.x(), p2.x())
                : Double.compare(p1.y(), p2.y());

        if (cmp == 0) {
            cmp = vert
                    ? Double.compare(p1.y(), p2.y())
                    : Double.compare(p1.x(), p2.x());

            if (cmp != 0)
                return 1;
        }

        return cmp;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (root == null) {
            root = new Node();
            root.rect = new RectHV(0, 0, 1, 1);
            root.p = p;
            size++;
            return;
        }

        Node cur = root;
        boolean vert = true;

        while (true) {
            int cmp = cmp(p, cur.p, vert);
            if (cmp < 0) {
                if (cur.lb == null) {
                    cur.lb = split(cur, p, vert, cmp);
                    size++;
                    break;
                }

                cur = cur.lb;
            } else if (cmp > 0) {
                if (cur.rt == null) {
                    cur.rt = split(cur, p, vert, cmp);
                    size++;
                    break;
                }

                cur = cur.rt;
            } else break;

            vert = !vert;
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node cur = root;
        boolean vert = true;

        while (cur != null) {
            int cmp = cmp(p, cur.p, vert);
            if (cmp < 0)
                cur = cur.lb;
            else if (cmp > 0)
                cur = cur.rt;
            else
                return true;

            vert = !vert;
        }

        return false;
    }

    private void _draw(Node n, boolean vert) {
        if (n == null)
            return;

        if (vert) {
            StdDraw.setPenColor(Color.red);
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        } else {
            StdDraw.setPenColor(Color.blue);
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }

        StdDraw.setPenColor(Color.black);
        StdDraw.filledCircle(n.p.x(), n.p.y(), 0.01);

        _draw(n.lb, !vert);
        _draw(n.rt, !vert);
    }

    // draw all points to standard draw
    public void draw() {
        _draw(root, true);
    }

    private void _range(Node n, RectHV rect, LinkedBag<Point2D> bag) {
        if (n == null || !n.rect.intersects(rect))
            return;

        if (rect.contains(n.p))
            bag.add(n.p);

        _range(n.lb, rect, bag);
        _range(n.rt, rect, bag);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        LinkedBag<Point2D> bag = new LinkedBag<>();
        _range(root, rect, bag);
        return bag;
    }

    private double min(double a, double b) {
        return a > b ? b : a;
    }

    private static class NearestDto {
        private double dist;
        private Point2D p;

        public NearestDto(double dist, Point2D p) {
            this.dist = dist;
            this.p = p;
        }
    }

    private NearestDto _nearest(Node n, boolean vert, NearestDto dto,
                                Point2D p) {
        if (n == null || n.rect.distanceSquaredTo(p) > dto.dist)
            return dto;

        int cmp = cmp(p, n.p, vert);

        NearestDto newNearest;
        double newDist = n.p.distanceSquaredTo(p);
        if (newDist < dto.dist) {
            newNearest = new NearestDto(newDist, n.p);
        } else {
            newNearest = dto;
        }

        if (cmp < 0) {
            newNearest = _nearest(n.lb, !vert, newNearest, p);
            newNearest = _nearest(n.rt, !vert, newNearest, p);
        } else {
            newNearest = _nearest(n.rt, !vert, newNearest, p);
            newNearest = _nearest(n.lb, !vert, newNearest, p);
        }

        return newNearest;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty())
            return null;

        return _nearest(root, true, new NearestDto(root.p.distanceSquaredTo
                (p), root.p), p).p;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}