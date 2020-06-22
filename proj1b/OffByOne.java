public class OffByOne implements CharacterComparator{

    @Override
    public boolean equalChars(char x, char y) {
        x = Character.toLowerCase(x);
        y = Character.toLowerCase(y);
        int differ = (x - y);
        return Math.abs(differ) == 1;
    }
}
