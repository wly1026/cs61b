public class ArrayDeque<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
    }

    public ArrayDeque(ArrayDeque other) {
        size = other.size;
        items = (T[]) new Object[other.size];
        System.arraycopy(other.items, 0, items, 0, size);
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
    }

    private void resize(int cap) {
        T[] a = (T[]) new Object[cap];
        int source = plusIndex(nextFirst);
        for (int i = 0; i < size; i++) {
            a[i] = items[source];
            source = plusIndex(source);
        }
        nextFirst = cap - 1;
        nextLast = size;
        items = a;
    }

    private void userFactorAdjust() {
        if ((size < items.length * 0.25) && (items.length >= 16)) {
            resize(4 * size);
        }
    }

    private int minusIndex(int index) {
        if (index == 0) {
            return (items.length - 1);
        } else {
            return (index - 1);
        }
    }

    private int plusIndex(int index) {
        if (index == items.length - 1) {
            return 0;
        } else {
            return (index + 1);
        }
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(2 * size);
        }
        size += 1;
        items[nextFirst] = item;
        nextFirst = minusIndex(nextFirst);
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(2 * size);
        }
        size += 1;
        items[nextLast] = item;
        nextLast = plusIndex(nextLast);
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int p = plusIndex(nextFirst);
        for (int i = 0; i < size; i++) {
            System.out.print(items[p] + " ");
            p = plusIndex(p);
        }
        System.out.println();
    }

    public T removeFirst() {
        size -= 1;
        T first = items[plusIndex(nextFirst)];
        items[plusIndex(nextFirst)] = null;
        nextFirst = plusIndex(nextFirst);
        userFactorAdjust();
        return first;
    }

    public T removeLast() {
        size -= 1;
        T last = items[minusIndex(nextLast)];
        items[minusIndex(nextLast)] = null;
        nextLast = minusIndex(nextLast);
        userFactorAdjust();
        return last;
    }

    public T get(int index) {
        return items[index];
    }

//    public static void main(String args[]){
//        ArrayDeque<String> testArray=new ArrayDeque<>();
//        testArray.addLast("a");
//        testArray.addLast("b");
//        testArray.addFirst("c");
//        testArray.addLast("d");
//        testArray.addLast("e");
//        testArray.addLast("f");
//        testArray.addFirst("g");
//        testArray.addLast("h");
//        testArray.addLast("i");
//        testArray.addLast("j");
//        testArray.addLast("k");
//        testArray.addFirst("l");
//        testArray.addLast("m");
//        testArray.addLast("n");
//        testArray.addLast("o");
//        testArray.addLast("p");
//        testArray.addFirst("q");
//        testArray.addLast("r");
//        testArray.addLast("s");
//        testArray.addLast("g");
//        //testArray.printDeque();
//        for(int i=0; i<12; i++){
//            String a=testArray.removeLast();
//            System.out.println(a+"removed");
//        }
//
//        testArray.removeFirst();
//        testArray.removeFirst();
//    }
}
