public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        int differ = (x - y);
        return Math.abs(differ) == 1;
    }
}
