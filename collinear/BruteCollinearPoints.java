import edu.princeton.cs.algs4.LinkedStack;

import java.util.Arrays;

/**
 * Brute force. Write a program BruteCollinearPoints.java that examines 4
 * points at a time and checks whether they all lie on the same line segment,
 * returning all such line segments. To check whether the 4 points p, q, r,
 * and s are collinear, check whether the three slopes between p and q,
 * between p and r, and between p and s are all equal.
 */
public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        LinkedStack<LineSegment> segmentStack = new LinkedStack<>();

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                if (p == q)
                    throw new IllegalArgumentException();

                double slope = p.slopeTo(q);
                for (int k = j + 1; k < points.length; k++) {
                    Point r = points[k];
                    if (slope != p.slopeTo(r))
                        continue;

                    for (int l = k + 1; l < points.length; l++) {
                        Point s = points[l];
                        if (slope == p.slopeTo(s)) {
                            Point min = min(p, min(q, min(r, s)));
                            Point max = max(p, max(q, max(r, s)));

                            segmentStack.push(new LineSegment(min, max));
                        }
                    }
                }
            }
        }

        segments = new LineSegment[segmentStack.size()];
        while (!segmentStack.isEmpty()) {
            segments[segmentStack.size() - 1] = segmentStack.pop();
        }
    }

    private Point min(Point p1, Point p2) {
        return p1.compareTo(p2) < 0 ? p1 : p2;
    }

    private Point max(Point p1, Point p2) {
        return p1.compareTo(p2) > 0 ? p1 : p2;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] sgl = new LineSegment[segments.length];
        for (int i = 0; i < segments.length; i++) {
            sgl[i] = segments[i];
        }
        return sgl;
    }
}