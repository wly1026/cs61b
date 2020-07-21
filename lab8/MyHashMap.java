import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int m;      // table size
    private int n;
    private double lf;     // loadFactor;
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
        table = new Node[m];
    }

    @Override
    public void clear() {
        m = 16;
        lf = 0.75;
        n = 0;
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
        return n;
    }

    @Override
    public void put(K key, V value) {
        if (size() >= m * lf ) {
            resize(2 * m);
        }
        int i = hash(key);
        if (table[i] == null) {
            table[i] = new Node(key, value, null);
            n++;
        } else if (containsKey(key)) {
            table[i].getNode(key).value = value;
        } else {
            table[i].putNode(key, value);
            n++;
        }
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keySets = new HashSet<>();
        for(int i = 0; i < m; i++) {
            Node h = table[i];
            while(h != null) {
                keySets.add((K)h.key);
                h = h.next;
            }
        }
        return keySets;
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        } else {
            V returnV = get(key);
           int i = hash(key);

           Node purposeNode = table[i].getNode(key);
           if (purposeNode.next != null) {
               purposeNode.value = purposeNode.next.value;
               purposeNode.key = purposeNode.next.key;
               purposeNode.next =purposeNode.next.next;
           } else {
               Node lastNode = table[i];
               Node deleNode = lastNode.next;
               if (deleNode == null) {
                   table[i] = null;
               } else {
                   while(deleNode.next != null) {
                       lastNode = lastNode.next;
                       deleNode = lastNode.next;
                   }
                   lastNode.next = null;
               }
           }
           return returnV;
        }
    }

    @Override
    public V remove(K key, V value) {
        if (!containsKey(key) || get(key) != value) {
            throw new IllegalArgumentException();
        } else {
            return remove(key);
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    private void resize(int x) {
        MyHashMap<K, V> temp = new MyHashMap<>(x);
        for(K key : keySet()) {
            temp.put(key, get(key));
        }
        this.n = temp.n;
        this.m = temp.m;
        this.table = temp.table;
    }

//    public static void main(String[] args) {
//        MyHashMap<String,Integer> a = new MyHashMap<>(5);
//        for (int i = 0; i < 10; i++) {
//            a.put("hi" + i, 1);
//        }
//        a.put("hi" + 1, 2);
//    }
}
