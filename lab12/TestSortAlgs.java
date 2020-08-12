import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestSortAlgs {

    public Queue<String> createStringQueue() {
        Queue<String> tas = new Queue<>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        return tas;
    }

    public Queue<Integer> createIntQueue() {
        Queue<Integer> tai = new Queue<>();
        tai.enqueue(10);
        tai.enqueue(1);
        tai.enqueue(12);
        return tai;
    }

    @Test
    public void testQuickSort() {
        Queue<String> sortedTas = QuickSort.quickSort(createStringQueue());
        assertTrue(isSorted(sortedTas));

        Queue<Integer> sortedTai = QuickSort.quickSort(createIntQueue());
        assertTrue(isSorted(sortedTai));

        Queue<Integer> sortedTaiAgain = QuickSort.quickSort(sortedTai);
        assertTrue(isSorted(sortedTaiAgain));
    }

    @Test
    public void testMergeSort() {
        Queue<String> sortedTas = MergeSort.mergeSort(createStringQueue());
        assertTrue(isSorted(sortedTas));

        Queue<Integer> sortedTai = MergeSort.mergeSort(createIntQueue());
        assertTrue(isSorted(sortedTai));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
