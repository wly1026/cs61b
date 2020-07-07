package es.datastructur.synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = plusIndex(last);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        int returnIndex = first;
        first = plusIndex(first);
        fillCount -= 1;
        return rb[returnIndex];
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    private int plusIndex(int i) {
        return ((i + 1) % rb.length);
    }

    private int minusIndex(int i) {
        return ((i - 1 + rb.length) % rb.length);
    }

    @Override
    public Iterator<T> iterator() {
        return new BufferIterator();
    }

    private class BufferIterator implements Iterator<T> {
        int wizPos;

        BufferIterator() {
            wizPos = first;
        }

        @Override
        public boolean hasNext() {
            if (!isEmpty()) {
                return wizPos != last;
            }
            return false;
        }

        @Override
        public T next() {
            int returnIndex = wizPos;
            wizPos = plusIndex(wizPos);
            return rb[returnIndex];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> newo = (ArrayRingBuffer<T>) o;
        if (this.fillCount != newo.fillCount) {
            return false;
        }
        for (T item : this) {
            if (item != newo.iterator().next()) {
                return false;
            }
        }
        return true;
    }
}
