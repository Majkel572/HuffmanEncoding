package pl.edu.pw.ee;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MinHeapTest {
    private MinHeap<Double> heap;

    @Before
    public void init() {
        heap = new MinHeap<Double>(100);
    }

    @Test
    public void should_returnNull_whenPopEmpty() {
        assertNull(heap.pop());
    }

    @Test
    public void should_putSomething_whilePutIsCalled() {
        heap.put(1.1);
        heap.put(2.2);
        assert true;
    }

    @Test
    public void should_properlyHeapify_whenPutBiggerThenSmaller() {
        heap.put(12.4);
        heap.put(5.2);
        Double firstExpected = 5.2;
        Double secondExpected = 12.4;
        assertEquals(firstExpected, heap.pop());
        assertEquals(secondExpected, heap.pop());
    }

    @Test
    public void should_heapifyProperly_whenPutSmallerThenBigger() {
        heap.put(5.2);
        heap.put(12.4);
        Double firstExpected = 5.2;
        Double secondExpected = 12.4;
        assertEquals(firstExpected, heap.pop());
        assertEquals(secondExpected, heap.pop());
    }

    @Test
    public void should_putProperly_whenPutIdentical() {
        heap.put(5.0);
        heap.put(5.0);
        Double expected = 5.0;
        assertEquals(expected, heap.pop());
        assertEquals(expected, heap.pop());
    }

    @Test
    public void should_putProperly() {
        MinHeap<Integer> mh = new MinHeap<>(10);
        for (int i = 0; i < 10; i++) {
            mh.put(i);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(i, mh.pop());
        }
    }
}
