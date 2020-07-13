import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    Node root;
    int size;

    private class Node {
        K key;
        V value;
        Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Calls containsKey() with a null key");
        }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return get(key, root);
    }

    private V get(K k, Node x) {
        if (k == null) {
            throw new IllegalArgumentException("Calls get() with a null key");
        }
        if (x == null) {
            return null;
        }
        int cmp = k.compareTo(x.key);
        if (cmp > 0) {
            return get(k, x.right);
        }
        if (cmp < 0) {
            return get(k, x.left);
        } else {
            return x.value;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Calls get() with a null key");
        }
        root = put(key, value, root);
    }

    private Node put(K key, V value, Node x) {
        if (x == null) {
            size += 1;
            return new Node(key, value);
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            x.right = put(key, value, x.right);
        } else if (cmp < 0) {
            x.left = put(key, value, x.left);
        } else {
            x.value = value;
        }
        return x;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    // helperMethod!!!
    public void printInOrder(Node x) {
        if (x != null) {
            printInOrder(x.left);
            System.out.println("key: " + x.key + " value: " + x.value);
            printInOrder(x.right);
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> BSTKeySet = new HashSet<>();
        for (K i : this) {
            BSTKeySet.add(i);
        }
        return BSTKeySet;
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            throw new IllegalArgumentException("call remove() with an non-existing key ");
        }
        V returnValue = get(key);
        root = remove(key, root);
        return returnValue;
    }

    private Node remove(K key, Node x) {
        if (x == null) {
            throw new IllegalArgumentException("Calls remove() with a null node");
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            x.right = remove(key, x.right);
        } else if (cmp < 0) {
            x.left = remove(key, x.left);
        } else {
            if (x.left == null && x.right == null) {
                x = null;
            } else if (x.right == null) {
                x = x.left;
            } else if (x.left == null) {
                x = x.right;
            } else {
                Node minRight = min(x.right);
                minRight.left = x.left;
                minRight.right = remove(minRight.key, x.right);
                x = minRight;
            }
            size -= 1;
        }
        return x;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    @Override
    public V remove(K key, V value) {
        if (!containsKey(key)) {
            throw new IllegalArgumentException("call remove() with an non-existing key ");
        }
        if (get(key) == value) {
            return remove(key);
        }
        return null;
    }


    @Override
    public Iterator<K> iterator() {
        return new BSTIterator(root);
    }

    /* !!! use Stack to store nodes: store one node ans left through the end of this node.
    after return this node, check its right node which would be the next to return. **/
    private class BSTIterator implements Iterator<K>{
        private Stack<Node> stack = new Stack<>();
        private BSTIterator(Node x) {
            while (x != null) {
                stack.push(x);
                x = x.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public K next() {
            Node curr = stack.pop();
            if(curr.right != null){
                Node temp = curr.right;
                while(temp != null) {
                    stack.push(temp);
                    temp = temp.left;
                }
            }
            return curr.key;
        }
    }
}

