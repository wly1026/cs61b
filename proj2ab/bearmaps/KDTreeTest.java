package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KDTreeTest {

    public List<Point> generateLecturePoints() {
        Point p1 = new Point(2,3);
        Point p2 = new Point(4,2);
        Point p3 = new Point(4,2);
        Point p4 = new Point(4,5);
        Point p5 = new Point(3,3);
        Point p6 = new Point(1,5);
        Point p7 = new Point(4,4);

        List<Point> points = new ArrayList<>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        points.add(p5);
        points.add(p6);
        points.add(p7);
        return points;
    }

    @Test
    public void nearestTest() {
        KDTree myTree = new KDTree(generateLecturePoints());
        Point act = myTree.nearest(0, 7);
        Point exp = new Point(1, 5);
        assertEquals(act, exp);

        Point act2 = myTree.nearest(4, 4.1);
        Point exp2 = new Point(4, 4);
        assertEquals(act2, exp2);

    }

    public List<Point> generateRandomPoints(int N) {
        List<Point> points = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < N; i++) {
            points.add(new Point(r.nextInt(1000), r.nextInt(1000)));
        }
        return points;
    }

    @Test
    public void nearestRandomTest() {
        List<Point> points = generateRandomPoints(100);
        KDTree myTree = new KDTree(points);
        NaivePointSet naiveTree = new NaivePointSet(points);
        Random r = new Random();

        for (int i = 0; i <10; i ++) {
            double x = r.nextInt();
            double y = r.nextInt();
            Point goal = new Point(x, y);
            Point exp = naiveTree.nearest(x, y);
            Point act = myTree.nearest(x, y);
            double expect = Point.distance(goal, exp);
            double actual = Point.distance(goal, act);
            assertTrue(Math.abs(expect - actual) == 0);
        }
    }

    @Test
    public void compareTiming() {
        List<Point> points = generateRandomPoints(100000);
        List<Point> targets = generateRandomPoints(10000);

        KDTree myTree = new KDTree(points);
        NaivePointSet naiveTree = new NaivePointSet(points);

        Stopwatch sw1 = new Stopwatch();
        for (Point i : targets) {
            Point n = myTree.nearest(i.getX(), i.getY());
        }
        System.out.println("Call nearest with KDTree: " + sw1.elapsedTime() +  " seconds.");

        Stopwatch sw2 = new Stopwatch();
        for (Point i : targets) {
            Point n = naiveTree.nearest(i.getX(), i.getY());
        }
        System.out.println("Call nearest with NaivePointSet: " + sw2.elapsedTime() +  " seconds.");
    }
}
