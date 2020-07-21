import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int m;      // table size
    private double lf;     // loadFactor;
    private HashSet<K> keySets;
    private Node<K, V>[] table;

    private class Node<K, V> {
        private K key;
        private V value;
        private Node next;

        private Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        };
        private Node(){};

        private Node<K, V> getNode(K key) {
            if (this.key.equals(key)) {
                return this;
            }
            if (this.next == null) {
                return null;
            }
            return next.getNode(key);
        }

        private void putNode(K key, V value) {
            if (next == null){
                next = new Node(key, value, null);
            } else {
                next.putNode(key, value);
            }
        }
    }

    public MyHashMap() {
        this(16, 0.75);
    }
    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }
    public MyHashMap(int initialSize, double loadFactor) {
        m = initialSize;
        lf = loadFactor;
        keySets = new HashSet();
        table = new Node[m];
    }

    @Override
    public void clear() {
        m = 16;
        lf = 0.75;
        keySets.clear();
        table = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int i = hash(key);
        if (table != null && table[i] != null && table[i].getNode(key) != null) {
            return table[i].getNode(key).value;
        }
        return null;
    }

    @Override
    public int size() {
        return keySets.size();
    }

    @Override
    public void put(K key, V value) {
        if (size() >= m * lf ) {
            resize(2 * m);
        }
        int i = hash(key);
        if (table[i] == null) {
            table[i] = new Node(key, value, null);
            keySets.add(key);
        } else if (containsKey(key)) {
            table[i].getNode(key).value = value;
        } else {
            table[i].putNode(key, value);
            keySets.add(key);
        }
    }

    @Override
    public Set<K> keySet() {
        return keySets;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keySets.iterator();
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    private void resize(int x) {
        MyHashMap<K, V> temp = new MyHashMap<>(x);
        for(K key : keySets) {
            temp.put(key, get(key));
        }
        this.m = temp.m;
        this.table = temp.table;
        this.keySets = temp.keySets;
    }

//    public static void main(String[] args) {
//        MyHashMap<String,Integer> a = new MyHashMap<>(5);
//        for (int i = 0; i < 10; i++) {
//            a.put("hi" + i, 1);
//        }
//        a.put("hi" + 1, 2);
//    }
}
