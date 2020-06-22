public class LinkedListDeque<T> implements Deque<T> {
    private class Node {
        private T item;
        private Node prev;
        private Node next;

        private Node(Node p, T t, Node n) {
            prev = p;
            item = t;
            next = n;
        }
    }

    private int size;
    private Node sentinel;

    // create an empty Deque.
    public LinkedListDeque() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public LinkedListDeque(LinkedListDeque other) {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        for (int i = 0; i < other.size; i++) {
            addLast((T) other.get(i));
        }
    }

//    public LinkedListDeque(T x){
//        size=1;
//        sentinel=new Node(null, null, null);
//        sentinel.next=new Node(null, x, null);
//        sentinel.prev=sentinel.next;
//        sentinel.next.prev=sentinel;
//        sentinel.next.next=sentinel;
//    }

    @Override
    public void addFirst(T item) {
        size += 1;
        Node first = new Node(null, item, null);
        sentinel.next.prev = first;
        first.prev = sentinel;
        first.next = sentinel.next;
        sentinel.next = first;
    }

    @Override
    public void addLast(T item) {
        size += 1;
        Node last = new Node(null, item, null);
        sentinel.prev.next = last;
        last.prev = sentinel.prev;
        sentinel.prev = last;
        last.next = sentinel;
    }

    //public boolean isEmpty() {
//        return (size == 0);
//    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node currentNode = sentinel;
        for (int i = 0; i < size; i++) {
            currentNode = currentNode.next;
            System.out.print(currentNode.item + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (!isEmpty()) {
            size -= 1;
        }
        T toRemove = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return toRemove;
    }

    @Override
    public T removeLast() {
        if (!isEmpty()) {
            size -= 1;
        }
        T toRemove = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        return toRemove;
    }

    @Override
    public T get(int index) {
        Node p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    public T getRecursiveHelper(Node p, int index) {
        if (index == 0) {
            return p.item;
        } else {
            return getRecursiveHelper(p.next, index - 1);
        }
    }

    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }
}
