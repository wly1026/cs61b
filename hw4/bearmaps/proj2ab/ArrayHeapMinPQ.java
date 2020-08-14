package bearmaps.proj2ab;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    ArrayList<PriorityNode> nodes;
    HashMap<T, Integer> itemIndex;

    public ArrayHeapMinPQ() {
        nodes = new ArrayList<>();
        itemIndex = new HashMap<>();
        nodes.add(0,null);
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
        itemIndex.put(item, size()+1);
        swim(size());
    }

    void swim(int i) {
        while (i > 1 && nodes.get(i).priority < nodes.get(i/2).priority) {
            swap(i, i / 2);
            i = i / 2;
        }
    }

    void sink(int i) {
        while (2 * i <= size()) {
            int j = 2 * i;
            if (j < size() && nodes.get(j).priority > nodes.get(j + 1).priority) {
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
        PriorityNode temp = nodes.get(x);

        nodes.set(x, nodes.get(y));
        nodes.set(y, temp);

        itemIndex.put(nodes.get(x).item, x);
        itemIndex.put(nodes.get(y).item, y);
    }

    @Override
    public boolean contains(T item) {
        return itemIndex.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (itemIndex.isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return nodes.get(1).item;
    }

    @Override
    public T removeSmallest() {
        if (itemIndex.isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        T returnItem = nodes.get(1).item;
        swap(1, size());
        nodes.remove(size());
        itemIndex.remove(returnItem);
        sink(1);
        return returnItem;
    }

    @Override
    public int size() {
        return itemIndex.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int i = itemIndex.get(item);
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
        Stopwatch s = new Stopwatch();
        System.out.println(s.elapsedTime());
    }

}
