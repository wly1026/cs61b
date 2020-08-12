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

    public Queue<String> createIncreaseLengthStringQueue() {
        Queue<String> tasils = new Queue<>();
        tasils.enqueue("Joe");
        tasils.enqueue("Omar");
        tasils.enqueue("Itai");
        tasils.enqueue("");
        tasils.enqueue("Apple");
        tasils.enqueue("Cocacola");
        return tasils;
    }

    public Queue<Integer> createIntQueue() {
        Queue<Integer> tai = new Queue<>();
        tai.enqueue(10);
        tai.enqueue(1);
        tai.enqueue(12);
        return tai;
    }

    public Queue<Integer> createSameItems() {
        Queue<Integer> sameItem = new Queue<>();
        for (int i = 0; i <10; i++) {
            sameItem.enqueue(10);
        }
        return sameItem;
    }

    public Queue<Integer> createIntQueueGradeScope() {
        Queue<Integer> t = new Queue<>();
        t.enqueue(0);
        t.enqueue(0);
        t.enqueue(2);
        t.enqueue(8);
        t.enqueue(4);
        t.enqueue(8);
        t.enqueue(3);
        t.enqueue(9);
        t.enqueue(4);
        t.enqueue(5);
        return t;
    }

    @Test
    public void testQuickSort() {
        Queue<String> sortedTas = QuickSort.quickSort(createStringQueue());
        assertTrue(isSorted(sortedTas));

        Queue<Integer> sortedTai = QuickSort.quickSort(createIntQueue());
        assertTrue(isSorted(sortedTai));

        Queue<Integer> sortedTaiAgain = QuickSort.quickSort(sortedTai);
        assertTrue(isSorted(sortedTaiAgain));

        Queue<Integer> sortedT = QuickSort.quickSort(createIntQueueGradeScope());
        assertTrue(isSorted(sortedT));
    }

    @Test
    public void testMergeSort() {
        Queue<String> sortedTas = MergeSort.mergeSort(createStringQueue());
        assertTrue(isSorted(sortedTas));

        Queue<String> sortedtasils = MergeSort.mergeSort(createIncreaseLengthStringQueue());
        assertTrue(isSorted(sortedtasils));

        Queue<Integer> sortedTai = MergeSort.mergeSort(createIntQueue());
        assertTrue(isSorted(sortedTai));

        Queue<Integer> sortedSameItem = MergeSort.mergeSort(createSameItems());
        assertTrue(isSorted(sortedSameItem));
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
