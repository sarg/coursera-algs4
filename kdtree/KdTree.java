import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
    }

    Node root;
    int size;

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

    private void split(Node src, Point2D p, boolean vert) {
        int cmp = cmp(src.p, p, vert);

        Node n = new Node();
        n.p = p;

        if (vert) {
            if (cmp < 0) {
                n.rect = new RectHV(src.rect.xmin(), src.rect.ymin(), src.p.x(), src.rect.ymax());
            } else {
                n.rect = new RectHV(src.p.x(), src.rect.ymin(), src.rect.xmax(), src.rect.ymax());
            }
        } else {
            if (cmp < 0) {
                n.rect = new RectHV(src.rect.xmin(), src.rect.ymin(), src.rect.xmax(), src.p.y());
            } else {
                n.rect = new RectHV(src.rect.xmin(), src.p.y(), src.rect.xmax(), src.rect.ymax());
            }
        }
    }

    public static int cmp(Point2D p1, Point2D p2, boolean vert) {
        int cmp = vert
                ? Double.compare(p1.x(), p2.x())
                : Double.compare(p1.y(), p2.y());

        if (cmp == 0) cmp = p1.compareTo(p2);
        return cmp;
    }

    private boolean insert(Node cur, Point2D t, boolean vert) {
        int cmp = cmp(cur.p, t, vert);

        if (cmp < 0) {
            if (cur.lb == null) {

            } else insert(cur.lb, t, !vert);
        } else if (cmp > 0) {
            if (cur.rt == null) {
            } else insert(cur.rt, t, !vert);
        }

        return false;
    }


    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (root == null) {
            root = new Node();
            root.rect = new RectHV(0, 0, 1, 1);
            root.p = p;
            return;
        }

        Node cur = root;
        boolean vert = false;

        int cmp = Node.cmp(cur.p, p, vert);

        if (cmp < 0) return cur.lb == null ? this : lb.find(t, !vert);
        else if (cmp > 0) return rt == null ? this : rt.find(t, !vert);
        else return this;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node node = root.find(p, false);
        return node.p.equals(p);
    }

    // draw all points to standard draw
    public void draw() {
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
