import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import sun.text.normalizer.UCharacter;

public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> returnDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++ ){
            returnDeque.addLast(word.charAt(i));
        }
        return returnDeque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> p = this.wordToDeque(word);
        while(p.size() > 1) {
            Character front = Character.toLowerCase(p.removeFirst());
            Character back = (Character.toLowerCase(p.removeLast()));
            if (!front.equals(back)){
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator CC) {
        Deque<Character> p = this.wordToDeque(word);
        while(p.size() > 1) {
            Character front = p.removeFirst();
            Character back = p.removeLast();
            if (!CC.equalChars(front, back)){
                return false;
            }
        }
        return true;
    }

//    public static void main(String args[]){
//        String word = "HELLO WORLD";
//        for (int i=0; i<word.length(); i++ ){
//            System.out.println(word.charAt(i));
//        }
//        Character a = new Character('a');
//        Character b = new Character('A');
//
//        System.out.println(a.equals(b));
//
//    }

}
