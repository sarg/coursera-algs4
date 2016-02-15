import java.util.Arrays;

public class FastCollinearPoints {
    LineSegment[] segments;

    private Point min(Point p1, Point p2) {
        return p1.compareTo(p2) < 0 ? p1 : p2;
    }

    private Point max(Point p1, Point p2) {
        return p1.compareTo(p2) > 0 ? p1 : p2;
    }

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        int N = points.length;
        Point[][] segmentList = new Point[N][1];
        int segmentCount = 0;

        Arrays.sort(points);
        Point[] sorted = new Point[N];
        for (int i = 0; i < N; i++) {
            sorted[i] = points[i];
            segmentList[i][0] = points[i];
        }

        for (int i = 0; i < N; i++) {
            Point p = points[i];
            Arrays.sort(sorted, p.slopeOrder());

            int startIdx = 0;
            for (int j = 1; j < N; j++) {
                if (p.slopeTo(sorted[j]) == p.slopeTo(sorted[startIdx]))
                    if (j != N - 1) continue;

                if (j - startIdx >= 3) {
                    Arrays.sort(sorted, startIdx, j);
                    segmentCount += newSegment(
                            segmentList,
                            min(p, sorted[startIdx]),
                            max(p, sorted[j - 1])
                    );
                }

                startIdx = j;
            }
        }

        segments = new LineSegment[segmentCount];
        int j = 0;
        for (Point[] p : segmentList) {
            for (int i = 1; i < p.length; i++) {
                if (p[i] != null)
                    segments[j++] = new LineSegment(p[0], p[i]);
            }
        }
    }

    private int newSegment(Point[][] points, Point p, Point q) {
        int lo = 0;
        int hi = points.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (p.compareTo(points[mid][0]) < 0) hi = mid - 1;
            else if (p.compareTo(points[mid][0]) > 0) lo = mid + 1;
            else {
                points[mid] = insertNewSegment(points[mid], q);

                return points[mid][points[mid].length-1] == null ? 0 : 1;
            }
        }

        return 0;
    }

    private Point[] insertNewSegment(Point[] points, Point p) {
        Point[] newP;
        boolean noAdd = false;
        if (points[points.length - 1] == null) {
            newP = points;
            for (int i = 0; i < points.length; i++) {
                if (points[i] == p) {
                    noAdd = true;
                    break;
                }
            }
        } else {
            newP = new Point[points.length + 1];
            for (int i = 0; i < points.length; i++) {
                newP[i] = points[i];
                if (points[i] == p)
                    noAdd = true;
            }
        }

        if (!noAdd)
            newP[newP.length-1] = p;

        return newP;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }
}