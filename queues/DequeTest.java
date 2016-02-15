import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DequeTest {

    Deque<Integer> d;

    @Before
    public void before() {
        d = new Deque<>();
    }

    @Test
    public void testEmpty() {
        assertTrue(d.isEmpty());
        assertEquals(0, d.size());
    }

    @Test(expected = NullPointerException.class)
    public void testNPE() {
        d.addFirst(null);
    }

    @Test
    public void testAddFirstRemoveLast() {
        d.addFirst(1);
        assertEquals(1, d.size());
        assertEquals(1, (int) d.removeLast());
        testEmpty();
    }

    @Test
    public void testAdd2() {
        d.addFirst(1);
        d.addFirst(2);
        assertEquals(2, d.size());
        assertEquals(1, (int) d.removeLast());
        assertEquals(2, (int) d.removeLast());
        testEmpty();
    }

    @Test
    public void testIter() {
        assertFalse(d.iterator().hasNext());

        d.addFirst(1);
        d.addFirst(2);
        Iterator<Integer> iter = d.iterator();
        assertTrue(iter.hasNext());
        assertEquals(2, (int) iter.next());
        assertEquals(1, (int) iter.next());

        assertFalse(iter.hasNext());
    }

    @Test
    public void test2() {
        testAdd2();
        testAdd2();
    }


}