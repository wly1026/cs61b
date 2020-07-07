public class BubbleGrid {
    int[][] grid;
    int sizeUnionBubble;

    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        UnionFind pos = unionBubble(grid);
        sizeUnionBubble = pos.sizeOf(0);
    }

    // change grid index to pos ( 1~capacity)
    public int gridToPos(int m, int n) {
        int c = grid[0].length;
        return m*c+n+1;
    }

    // whether the left pos is 1;
    public boolean leftBubble(int m , int n) {
        if (n > 0) {
            return grid[m][n-1] == 1;
        }
        return false;
    }

    // whether the above pos is 1;
    public boolean aboveBubble(int m , int n) {
        if (m > 0) {
            return grid[m-1][n] == 1;
        }
        return false;
    }

    // return a new unionFind upon 1-0 grid。
    public UnionFind unionBubble(int[][] grid) {
        UnionFind pos = new UnionFind(grid.length*grid[0].length+1);
        for (int m = 0; m < grid.length; m++) {
            for (int n = 0; n < grid[0].length; n++) {
                if (grid[m][n] == 1) {
                    if (m == 0) {
                        pos.union(0,gridToPos(m, n));
                    } else {
                        if (leftBubble(m, n)) {
                            pos.union(gridToPos(m, n-1),gridToPos(m, n));
                        }
                        if (aboveBubble(m, n)) {
                            pos.union(gridToPos(m-1, n), gridToPos(m, n));
                        }
                    }
                }
            }
        }
        return pos;
    }

    public int[] popBubbles(int[][] darts) {
        int[] fallBubbles = new int[darts.length];
        for (int i = 0; i < darts.length; i++) {
            int row = darts[i][0];
            int column = darts[i][1];

            if (grid[row][column] == 0) {
                fallBubbles[i] = 0;
            } else {
                grid[row][column] = 0;
                UnionFind posAfterDart = unionBubble(grid);
                int sizeAfterDart = posAfterDart.sizeOf(0);

                fallBubbles[i] = sizeUnionBubble - sizeAfterDart -1;
                sizeUnionBubble = sizeAfterDart;
            }
        }
        return fallBubbles;
    }
}

