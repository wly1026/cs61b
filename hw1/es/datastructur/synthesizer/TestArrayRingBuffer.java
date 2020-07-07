package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(4);
        assertTrue(arb.isEmpty());
        arb.enqueue(33.1);
        arb.enqueue(44.8);
        arb.enqueue(62.8);
        arb.enqueue(-3.4);
        assertTrue(arb.isFull());
        assertEquals(33.1,arb.dequeue());
        assertEquals(3,arb.fillCount());
        assertEquals(44.8,arb.peek());
        assertEquals(3,arb.fillCount());
    }

    @Test
    public void iteratorTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(4);
        arb.enqueue(33.1);
        arb.enqueue(44.8);
        arb.enqueue(62.8);
        arb.enqueue(-3.4);

        ArrayRingBuffer arb2 = new ArrayRingBuffer(4);
        arb2.enqueue(33.1);
        arb2.enqueue(44.8);
        arb2.enqueue(62.8);
        arb2.enqueue(-3.4);

        assertTrue(arb.equals(arb2));
    }
}
