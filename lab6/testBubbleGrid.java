import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class testBubbleGrid {
    int[][] grid = new int[][]{{1, 1, 0}, {1, 0, 0}, {1, 1, 0}, {1, 1, 1}};
    int[][] darts = new int[][]{{2, 2}, {2, 0}};
    BubbleGrid bg = new BubbleGrid(grid);

    int[][] grid2 = new int[][]{{1, 0, 0, 0}, {1, 1, 1, 0}};
    int[][] darts2 = new int[][]{{1, 0}};
    BubbleGrid bg2 = new BubbleGrid(grid2);

    int[][] grid3 = new int[][]{{1, 0, 0, 0}, {1, 1, 0, 0}};
    int[][] darts3 = new int[][]{{1, 1}, {1, 0}};
    BubbleGrid bg3 = new BubbleGrid(grid3);

    @Test
    public void testUnionBubble() {
        UnionFind pos = bg.unionBubble(grid);
        int[] a = pos.parent;
        System.out.println(Arrays.toString(a));
        assertEquals(bg.sizeUnionBubble,9);
    }

    @Test
    public void testPopBubbles() {
        int[] exp = new int[]{0, 4};
        int[] act = bg.popBubbles(darts);
        assertArrayEquals(exp, act);

        int[] exp2 = new int[]{2};
        int[] act2 = bg2.popBubbles(darts2);
        assertArrayEquals(exp2, act2);

        int[] exp3 = new int[]{0, 0};
        int[] act3 = bg3.popBubbles(darts3);
        assertArrayEquals(exp3, act3);
    }
}
