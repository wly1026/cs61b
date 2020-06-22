public class OffByN implements CharacterComparator {
    private int n;
    public OffByN(int N) {
        n = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int differ = x - y;
        return Math.abs(differ) == n;
    }
}
