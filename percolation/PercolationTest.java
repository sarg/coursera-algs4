import edu.princeton.cs.algs4.In;
import org.junit.Assert;
import org.junit.Test;

public class PercolationTest {

    @Test
    public void testOpen() throws Exception {
        Percolation p = new Percolation(5);
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                Assert.assertFalse(p.isOpen(i, j));
                p.open(i, j);
                Assert.assertTrue(p.isOpen(i, j));
            }
        }
    }


    @Test
    public void testBack() {
        testBackwash("input20.txt", 231, 18, 1);
        testBackwash("input10.txt", 56, 9, 1);
        testBackwash("input50.txt", 1412, 22, 28);
    }

    @Test
    public void testOpen1() {
//        testOpenSite("input6.txt", 18);
        testOpenSite("input1.txt", 1);
    }

    public void testOpenSite(String fn, int iter) {
        In in = new In(fn);      // input file
        int N = in.readInt();         // N-by-N percolation system

        Percolation perc = new Percolation(N);
        int x = 0;
        while (!in.isEmpty() && x < iter) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            Assert.assertTrue(perc.isOpen(i, j));
            x++;
        }
        Assert.assertTrue(perc.percolates());
    }


    public void testBackwash(String fn, int iter, int ii, int jj) {
        In in = new In(fn);      // input file
        int N = in.readInt();         // N-by-N percolation system

        Percolation perc = new Percolation(N);
        int x = 0;
        while (!in.isEmpty() && x < iter) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            x++;
        }

        Assert.assertFalse(perc.isFull(ii, jj));
    }

    @Test
    public void testIsOpen() throws Exception {
    }

    @Test
    public void testIsFull() throws Exception {
        Percolation p = new Percolation(2);
        Assert.assertFalse(p.isFull(1, 1));
        p.open(1, 1);
        Assert.assertTrue(p.isFull(1, 1));

        Assert.assertFalse(p.isFull(2, 1));
        p.open(2, 1);
        Assert.assertTrue(p.isFull(2, 1));

        Assert.assertTrue(p.percolates());
    }

    @Test
    public void testPercolates() throws Exception {
        Percolation p = new Percolation(5);
        for (int i = 1; i <= 5; i++) {
            p.open(1, i);
        }

        Assert.assertFalse(p.percolates());

        for (int i = 1; i < 5; i++) {
            p.open(i, 1);
            Assert.assertFalse(Integer.toString(i), p.percolates());
        }

        p.open(5, 1);
        Assert.assertTrue(p.percolates());
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testRow15() {
        new Percolation(10).open(15, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCol15() {
        new Percolation(10).open(1, 15);
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testCol0() {
        new Percolation(10).open(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstr() {
        new Percolation(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstr1() {
        new Percolation(-5);
    }
}