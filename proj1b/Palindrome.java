public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> returnDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            returnDeque.addLast(word.charAt(i));
        }
        return returnDeque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> p = wordToDeque(word);
        while (p.size() > 1) {
            Character front = Character.toLowerCase(p.removeFirst());
            Character back = Character.toLowerCase(p.removeLast());
            if (!front.equals(back)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> p = wordToDeque(word);
        while (p.size() > 1) {
            Character front = p.removeFirst();
            Character back = p.removeLast();
            if (!cc.equalChars(front, back)) {
                return false;
            }
        }
        return true;
    }

}
