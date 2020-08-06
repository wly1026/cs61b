package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {

    List<Point> points;

    public NaivePointSet(List<Point> points){
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Point nearestPoint = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        double distanceToNearest = Double.MAX_VALUE;
        for (Point i : points) {
            double iToTarget = Point.distance(i, target);
            if (iToTarget < distanceToNearest) {
                nearestPoint = i;
                distanceToNearest = iToTarget;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        ArrayList<Point> a = new ArrayList<>();
        a.add(p1);
        a.add(p2);
        a.add(p3);

        NaivePointSet nn = new NaivePointSet(a);
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY()); // evaluates to 4.4
    }
}
