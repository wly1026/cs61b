import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private static final int R = 128;
    private Node root;

    private static class Node {
        private boolean isKey;
        private Node[] next = new Node[R];
    }

    public MyTrieSet() {
        root = new Node();
        root.isKey = false;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean contains(String key) {
        Node current = root;
        if (current == null) {
            return false;
        }
        for (int i = 0; i < key.length(); i++) {
            if (current.next[key.charAt(i)] == null) {
                return false;
            }
            current = current.next[key.charAt(i)];
            if (i == key.length() - 1 && current.isKey) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(String key) {
        Node current = root;
        for (int i = 0; i < key.length(); i++) {
            if (current.next[key.charAt(i)] == null) {
                current.next[key.charAt(i)] = new Node();
            }
            current = current.next[key.charAt(i)];
            current.isKey = (i == key.length() - 1);
        }
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> returnL = new LinkedList<>();
        Node current = root;
        for(int i = 0; i < prefix.length(); i++) {
            if (current.next[prefix.charAt(i)] == null) {
                break;
            }
            current = current.next[prefix.charAt(i)];
        }
        collect(prefix, returnL, current);
        return returnL;
    }

    public void collect(String s, List<String> x, Node n) {
        if (n.isKey) {
            x.add(s);
        }
        for (int i = 0; i < R; i ++) {
            if (n.next[i] != null) {
                collect(s + (char) i, x, n.next[i]);
            }
        }
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        int i = 'c';
        System.out.println(i);
        System.out.println((char) 99);
    }
}
