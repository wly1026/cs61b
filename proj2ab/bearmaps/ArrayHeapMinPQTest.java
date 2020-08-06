package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayHeapMinPQTest {

    // assume add/contains/size work
    @Test
    public void constructTest() {
        ArrayHeapMinPQ<Character> pq = new ArrayHeapMinPQ();
        for (char item = 'a'; item <= 'z'; item++) {
            pq.add(item, item);
            assertTrue(pq.contains(item));
        }
        assertEquals(26, pq.size());
    }

    @Test
    public void addRemoveGetTest() {
        ArrayHeapMinPQ<Character> pq = new ArrayHeapMinPQ();
        String t = "qwertyuiopasdfghjklzxcvbnm";
        for (int i = 0; i < 26; i++) {
            char c = t.charAt(i);
            pq.add(c, c);
        }
        for (char item = 'a'; item <= 'z'; item++) {
            char ac = pq.getSmallest();
            char exp = item;
            assertEquals(ac, exp);
            assertEquals((char)pq.removeSmallest(), exp);
        }
        assertEquals(0, pq.size());
    }

    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ();
        pq.add("apple", 2.00);
        pq.add("pear", 1.00);
        pq.add("peach", 3.00);
        pq.changePriority("apple", 0.1);
        assertEquals(pq.getSmallest(), "apple");
        pq.changePriority("apple", 4.00);
        assertEquals(pq.getSmallest(), "pear");
        pq.changePriority("pear", 5.00);
        assertEquals(pq.getSmallest(), "peach");
    }

    public static void main(String[] args) {
        ArrayHeapMinPQ<String> myPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> naivePQ = new NaiveMinPQ<>();
        int N = 100000;
        Random rd = new Random();

        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < N; i += 1) {
            myPQ.add("mypq"+i, rd.nextInt(100000));
        }
        System.out.println("Add with MyPQ: " + sw1.elapsedTime() +  " seconds.");

        Stopwatch sw2 = new Stopwatch();
        for (int i = 0; i < N; i += 1) {
            naivePQ.add("npq"+i, rd.nextInt(100000));
        }
        System.out.println("Add with NaivePQ: " + sw2.elapsedTime() +  " seconds.");

        Stopwatch sw3 = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            int itemNum = rd.nextInt(N);
            myPQ.changePriority("mypq" + itemNum, rd.nextInt(100000));
        }
        System.out.println("ChangePriority with MyPQ: " + sw3.elapsedTime() +  " seconds.");

        Stopwatch sw4 = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            int itemNum = rd.nextInt(N);
            naivePQ.changePriority("npq" + itemNum, rd.nextInt(100000));
        }
        System.out.println("ChangePriority with NaivePQ: " + sw4.elapsedTime() +  " seconds.");

        Stopwatch sw5 = new Stopwatch();
        for (int i = 0; i < 100000; i += 1) {
            myPQ.removeSmallest();
        }
        System.out.println("RemoveSmallest with MyPQ: " + sw5.elapsedTime() +  " seconds.");

        Stopwatch sw6 = new Stopwatch();
        for (int i = 0; i < 100000; i += 1) {
            naivePQ.removeSmallest();
        }
        System.out.println("RemoveSmallest with NaivePQ: " + sw6.elapsedTime() +  " seconds.");

    }
}
