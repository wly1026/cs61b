/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
//        In in = new In("../library-sp19/data/words.txt");
        Palindrome palindrome = new Palindrome();
//        OffByOne cc = new OffByOne();
//        OffByN cn = new OffByN(5);

//        while (!in.isEmpty()) {
//            String word = in.readString();
            //original palindrome finder.
//            if (word.length() >= minLength && palindrome.isPalindrome(word)) {
//                System.out.println(word);
//            }

            // OffByOne palindrome finder.
//            if (word.length() >= minLength && palindrome.isPalindrome(word,cc)) {
//                System.out.println(word);
//            }

            //OffByN palindrome finder.
//            if (word.length() >= minLength && palindrome.isPalindrome(word,cn)) {
//                System.out.println(word);
//            }

        //for what n are there the most palindromes in English?
//        int n = 0;
//        int numOfPatN = 0;
//        for (int i = 1; i < 26; i++ ) {
//            In in = new In("../library-sp19/data/words.txt");
//            OffByN cn = new OffByN(i);
//            int numOfp = 0;
//            while (!in.isEmpty()) {
//                String word = in.readString();
//                if (word.length() >= minLength && palindrome.isPalindrome(word,cn)) {
//                    numOfp += 1;
//                }
//            }
//            if (numOfp > numOfPatN) {
//                n = i;
//                numOfPatN = numOfp;
//            }
//            in.close();
//        }
//
//        System.out.println("There are most palindromes for " + n);
//        In in = new In("../library-sp19/data/words.txt");
//        OffByN cn = new OffByN(n);
//        while (!in.isEmpty()) {
//             String word = in.readString();
//             if (word.length() >= minLength && palindrome.isPalindrome(word,cn)) {
//                System.out.println(word);
//             }
//        }

        //what is the longest offByN palindrome for any N?
        In in = new In("../library-sp19/data/words.txt");
        int n = 0;
        int lengthOfP = 0;
        String w = null;
        int nOfW = 0 ;
        while (!in.isEmpty()) {
            String word = in.readString();
            int lengthOfW = word.length();
            for (int i = 1; i < 26; i++ ) {
                OffByN cn = new OffByN(i);
                if (word.length() >= minLength && palindrome.isPalindrome(word,cn)) {
                    nOfW = i;
                    if (lengthOfW > lengthOfP) {
                        lengthOfP = lengthOfW;
                        n = nOfW;
                        w = word;
                        break;
                    }
                }
            }
        }
        System.out.println(w + " is the longest Palindrome for " + n);


    }
} //Uncomment this class once you've written isPalindrome.