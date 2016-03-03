import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdIn;

import static org.junit.Assert.*;

/**
 * Created by sarg on 03.03.16.
 */
public class KdTreeTest {

    @org.junit.Test
    public void testInsert() throws Exception {
        KdTree kdTree = new KdTree();

        Point2D p = new Point2D(0.5, 0.5);
        Point2D p1 = new Point2D(0.5, 0.15);

        kdTree.insert(p);
        assertTrue(kdTree.contains(p));
        assertEquals(1, kdTree.size());

        kdTree.insert(p1);
        assertTrue(kdTree.contains(p1));
        assertEquals(2, kdTree.size());

        kdTree.insert(p1);
        assertTrue(kdTree.contains(p1));
        assertEquals(2, kdTree.size());

        kdTree.draw();
        StdIn.readString();

    }

    @org.junit.Test
    public void testDraw() throws Exception {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));

        kdTree.draw();
        StdIn.readString();
    }
}