import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("Cac"));
        assertTrue(palindrome.isPalindrome("c"));
        assertTrue(palindrome.isPalindrome("refer"));
        assertTrue(palindrome.isPalindrome("noon"));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        OffByOne cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("Flake", cc));
        assertTrue(palindrome.isPalindrome("Zyzy", cc));
        assertFalse(palindrome.isPalindrome("Xyz", cc));
        assertFalse(palindrome.isPalindrome("noon", cc));
    }

    @Test
    public void testIsPalindromeOffBy5() {
        OffByN cc = new OffByN(5);
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("bidding", cc));
        assertTrue(palindrome.isPalindrome("month", cc));
        assertFalse(palindrome.isPalindrome("flake", cc));
    }
}     //Uncomment this class once you've created your Palindrome class.
