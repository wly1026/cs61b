package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    ArrayList<PriorityNode> nodes;
    private int n;

    public ArrayHeapMinPQ() {
        nodes = new ArrayList<>();
        nodes.add(0,null);
        n = 0;
    }

    private class PriorityNode {
        private T item;
        private double priority;

        private PriorityNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                PriorityNode other = (PriorityNode) o;
                return other.item.equals(this.item);
            }
        }
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new NoSuchElementException(item + "has been added");
        }
        nodes.add(new PriorityNode(item, priority));
        n++;
        swim(n);
    }

    void swim(int i) {
        while (i > 1 && nodes.get(i).priority < nodes.get(i/2).priority) {
            swap(i, i / 2);
            i = i / 2;
        }
    }

    void sink(int i) {
        while (2 * i <= n) {
            int j = 2 * i;
            if (j < n && nodes.get(j).priority > nodes.get(j + 1).priority) {
                j = j + 1;
            }
            if (nodes.get(j).priority < nodes.get(i).priority) {
                swap(i, j);
                i = j;
            } else {
                break;
            }
        }
    }

    private void swap(int x, int y) {
        T tempItem = nodes.get(x).item;
        double tempPriority = nodes.get(x).priority;

        nodes.get(x).item = nodes.get(y).item;
        nodes.get(x).priority = nodes.get(y).priority;

        nodes.get(y).item = tempItem;
        nodes.get(y).priority = tempPriority;
    }

    @Override
    public boolean contains(T item) {
        return nodes.contains(new PriorityNode(item,0));
    }

    @Override
    public T getSmallest() {
        if (n == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return nodes.get(1).item;
    }

    @Override
    public T removeSmallest() {
        if (n == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        T returnItem = nodes.get(1).item;
        swap(1, n);
        nodes.remove(n);
        n--;
        sink(1);
        return returnItem;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int i = nodes.indexOf(new PriorityNode(item, priority));
        nodes.get(i).priority = priority;
        swim(i);
        sink(i);
    }

    public static void main(String[] args) {
        ArrayList a = new ArrayList();
        a.add('a');
        a.add(1);
        a.add(0.99);
        System.out.println(a.get(2));
    }

    @Test
    public void addTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ();
        pq.add("apple", 2.00);
        pq.add("pear", 1.00);
        PriorityNode exp = new ArrayHeapMinPQ.PriorityNode("pear", 1.00);
        assertEquals(pq.nodes.get(1), exp);
    }
}
