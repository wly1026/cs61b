import org.junit.Test;

import static org.junit.Assert.*;

public class testUnionFind {


    @Test
    public void testUF() {
        UnionFind test = new UnionFind(9);
        test.union(3,2);
        test.union(2,1);
        test.union(7,5);
        test.union(8,4);
        test.union(2,7);
        test.find(3);
        test.union(6,0);
        test.union(4,6);
        test.union(3,6);
        test.find(8);
        test.find(6);

        assertEquals(9,test.sizeOf(2));
        assertEquals(5,test.parent(7));
        assertEquals(2,test.parent(6));
        assertEquals(2,test.parent(4));
    }
}
