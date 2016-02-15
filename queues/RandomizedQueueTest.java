import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomizedQueueTest {
    RandomizedQueue<Integer> rq;

    @Before
    public void init() {
        rq = new RandomizedQueue<>();
    }

    @Test
    public void test1() {
        rq.enqueue(2);
        assertEquals(2, (int) rq.dequeue());
        rq.enqueue(3);
        assertEquals(3, (int) rq.dequeue());
        rq.enqueue(4);
    }

}