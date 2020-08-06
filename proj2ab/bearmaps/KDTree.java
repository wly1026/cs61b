package bearmaps;

import com.sun.javaws.jnl.ExtDownloadDesc;

import java.util.Comparator;
import java.util.List;

public class KDTree implements PointSet{

    private class Node extends Point implements Comparator<Point> {

        Node left, right;
        String dominant;
        double upOfX;
        double downOfX;
        double upOfY;
        double downOfY;

        public Node(double x, double y, String dominant) {
            super(x, y);
            this.dominant = dominant;
            upOfX = Double.MAX_VALUE;
            downOfX = Double.MIN_VALUE;
            upOfY = Double.MAX_VALUE;
            downOfY = Double.MIN_VALUE;
        }

        public Node(double x, double y, String dominant, double xdown, double xup, double ydown, double yup) {
            super(x, y);
            this.dominant = dominant;
            downOfX = xdown;
            upOfX = xup;
            downOfY = ydown;
            upOfY = yup;
        }

        @Override
        public int compare(Point o1, Point o2) {
            if (dominant == "x") {
                if (o1.getX() > o2.getX()) {
                    return 1;
                } else if (o1.getX() == o2.getX()) {
                    return 0;
                } else {
                    return -1;
                }
            }
            if (dominant == "y") {
                if (o1.getY() >= o2.getY()) {
                    return 1;
                } else if (o1.getY() == o2.getY()) {
                    return 0;
                } else {
                    return -1;
                }
            }
            return 0;
        }
    }

    Node root;

    public KDTree(List<Point> points) {
        for (Point i : points) {
            this.insert(i.getX(), i.getY());
        }
    }

//    private Node helpNearest(Node n, Point goal, Node best) {
//        if (n == null) {
//            return best;
//        }
//        if (Point.distance(goal, n) < Point.distance(goal, best)) {
//            best = n;
//        }
//        best = helpNearest(n.left, goal, best);
//        best = helpNearest(n.right, goal, best);
//        return best;
//    }

    private Point bestBadSidePoint(Node badSide, Point goal) {
        if (badSide == null) {
            return new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        }
        double x;
        double y;
        if (goal.getX() >= badSide.downOfX && goal.getX() <= badSide.upOfX) {
            x = goal.getX();
            if (Math.abs(goal.getY() - badSide.upOfY) < Math.abs(goal.getY() - badSide.downOfY)) {
                y = badSide.upOfY;
            } else {
                y = badSide.downOfY;
            }
        } else if (goal.getY() >= badSide.downOfY && goal.getY() <= badSide.upOfY) {
            y = goal.getY();
            if (Math.abs(goal.getX() - badSide.upOfX) < Math.abs(goal.getX() - badSide.downOfX)) {
                x = badSide.upOfX;
            } else {
                x = badSide.downOfX;
            }
        } else {
            if (Math.abs(goal.getY() - badSide.upOfY) < Math.abs(goal.getY() - badSide.downOfY)) {
                y = badSide.upOfY;
            } else {
                y = badSide.downOfY;
            }
            if (Math.abs(goal.getX() - badSide.upOfX) < Math.abs(goal.getX() - badSide.downOfX)) {
                x = badSide.upOfX;
            } else {
                x = badSide.downOfX;
            }
        }
        return new Point(x, y);
    }

    private Node helpNearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(goal, n) < Point.distance(goal, best)) {
            best = n;
        }
        Node goodSide;
        Node badSide;
        if (n.compare(n, goal) > 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = helpNearest(goodSide, goal, best);
        if (Point.distance(bestBadSidePoint(badSide, goal), goal) < Point.distance(best, goal)) {
            best = helpNearest(badSide, goal, best);
        }
        return best;
    }

    @Override
    public Point nearest(double x, double y) {
        Node n = helpNearest(root, new Point(x, y), new Node(Double.MAX_VALUE, Double.MAX_VALUE, "x"));
        return new Point(n.getX(), n.getY());
    }

    private Node helpInsert(double x, double y, Node n, String d, double xdown, double xup, double ydown, double yup) {
        if (n == null) {
            n = new Node(x, y, d, xdown, xup, ydown, yup);
//            if (d == "x") {
//                n.left.upOfX = Math.min(x, n.upOfX);
//                n.right.downOfX = Math.max(x, n.downOfX);
//            } else if (d == "y") {
//                n.left.upOfY = Math.min(y, n.upOfY);
//                n.right.downOfY = Math.max(y, n.downOfY);
//            }
            return n;
        } else if (n.getX() == x && n.getY() == y) {
            return n;
        } else if (n.dominant == "x" && x >= n.getX()) {
            n.right = helpInsert(x, y, n.right, "y", Math.max(n.getX(), xdown), xup, ydown, yup);
        } else if (n.dominant == "x" && x < n.getX()) {
            n.left = helpInsert(x, y, n.left, "y", xdown, Math.min(n.getX(), xup), ydown, yup);
        } else if (n.dominant == "y" && y >= n.getY()) {
            n.right = helpInsert(x, y, n.right, "x", xdown, xup, Math.max(n.getY(), ydown), yup);
        } else if (n.dominant == "y" && y < n.getY()) {
            n.left = helpInsert(x, y, n.left, "x", xdown, xup, ydown, Math.min(n.getY(), yup));
        }
        return n;
    }

    public void insert(double x, double y) {
        root = helpInsert(x, y, root, "x", Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE);
    }


    public static void main(String[] agrs) {
//        KDTree mytree = new KDTree();
//        mytree.insert(2,3);
//        mytree.insert(4,2);
//        mytree.insert(4,2);
//        mytree.insert(4,5);
//        mytree.insert(3,3);
//        mytree.insert(1,5);
//        mytree.insert(4,4);
        System.out.println((int)(-0.0001));
    }
}
