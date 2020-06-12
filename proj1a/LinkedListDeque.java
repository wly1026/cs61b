
public class LinkedListDeque<T> {
    public class Node {
        T item;
        Node prev;
        Node next;

        public Node(Node p, T t, Node n) {
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

    public void addFirst(T item) {
        size += 1;
        Node first = new Node(null, item, null);
        sentinel.next.prev = first;
        first.prev = sentinel;
        first.next = sentinel.next;
        sentinel.next = first;
    }

    public void addLast(T item) {
        size += 1;
        Node last = new Node(null, item, null);
        sentinel.prev.next = last;
        last.prev = sentinel.prev;
        sentinel.prev = last;
        last.next = sentinel;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node currentNode = sentinel;
        for (int i = 0; i < size; i++) {
            currentNode = currentNode.next;
            System.out.print(currentNode.item + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        size -= 1;
        sentinel.prev.next = sentinel.next;
        sentinel.next.prev = sentinel.prev;
        Node first = sentinel;
        sentinel = sentinel.next;
        return first.item;
    }

    public T removeLast() {
        size -= 1;
        Node last = sentinel.prev;
        last.prev.next = sentinel;
        sentinel.prev = last.prev;
        return last.item;
    }

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
