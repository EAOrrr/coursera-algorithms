/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private static final boolean XCMP = true;
    private static final boolean YCMP = false;

    private int size;
    private Node root;

    private class Node {
        Point2D key;
        Node left;
        Node right;
        boolean cmp;

        Node(Point2D k, boolean c, Node ln, Node rn) {
            key = k;
            left = ln;
            right = rn;
            cmp = c;
        }
    }

    public KdTree() {
        size = 0;
        root = null;
    }


    public boolean isEmpty()                      // is the set empty?
    {
        return size == 0;
    }

    public int size()                         // number of points in the set
    {
        return size;
    }

    public void insert(
            Point2D p)              // add the point to the set (if it is not already in the set)
    {
        validate(p);
        root = insert(root, p, XCMP);
    }

    private Node insert(Node x, Point2D p, boolean c) {
        if (x == null) {
            size++;
            return new Node(p, c, null, null);
        }
        int cmp = compare(x, p);
        if (cmp < 0) x.left = insert(x.left, p, !c);
        else if (cmp > 0) x.right = insert(x.right, p, !c);
        else if (x.key.equals(p)) x = new Node(p, c, x.left, x.right);
        else x.left = insert(x.left, p, !c);
        return x;
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        validate(p);
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) return false;
        int cmp = compare(x, p);
        if (cmp > 0) return contains(x.right, p);
        else if (cmp < 0) return contains(x.left, p);
        else if (x.key.equals(p)) return true;
        return contains(x.right, p) || contains(x.left, p);
    }

    public void draw()                         // draw all points to standard draw
    {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null) return;
        x.key.draw();
        draw(x.left);
        draw(x.right);
    }

    public Iterable<Point2D> range(
            RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) throw new IllegalArgumentException("argument to range is null");
        Queue<Point2D> point2DQueue = new Queue<>();
        range(root, rect, point2DQueue);
        return point2DQueue;
    }

    private void range(Node n, RectHV rect, Queue<Point2D> q) {
        if (n == null) return;
        boolean iterLeft = true, iterRight = true;
        if (rect.contains(n.key)) q.enqueue(n.key);
        else if (n.cmp == XCMP) {
            if (n.key.x() < rect.xmin()) iterLeft = false;
            else if (n.key.x() > rect.xmax()) iterRight = false;
        }
        else {
            if (n.key.y() < rect.ymin()) iterLeft = false;
            else if (n.key.y() > rect.ymax()) iterRight = false;
        }
        if (iterLeft) range(n.left, rect, q);
        if (iterRight) range(n.right, rect, q);
    }

    public Point2D nearest(
            Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        validate(p);
        return nearest(root, p);
    }

    private Point2D nearest(Node n, Point2D p) {
        if (n == null) return null;
        if (n.key.equals(p)) return n.key;
        double dist = p.distanceSquaredTo(n.key);

        double firstDist = Double.POSITIVE_INFINITY, latterDist = Double.POSITIVE_INFINITY;

        double delta;
        if (n.cmp == XCMP) {
            delta = Math.pow(Math.abs(p.x() - n.key.x()), 2);
        }
        else {
            delta = Math.pow(Math.abs(p.y() - n.key.y()), 2);
        }

        Node first, latter;
        Point2D firstNearest, latterNearest;
        if (compare(n, p) < 0) {
            first = n.left;
            latter = n.right;
        }
        else {
            first = n.right;
            latter = n.left;
        }

        firstNearest = nearest(first, p);
        if (firstNearest != null) firstDist = firstNearest.distanceSquaredTo(p);
        if (firstDist < delta) return firstNearest;

        latterNearest = nearest(latter, p);
        if (latterNearest != null) latterDist = latterNearest.distanceSquaredTo(p);

        if (firstDist < dist && firstDist <= latterDist) return firstNearest;
        if (latterDist < dist && latterDist <= firstDist) return latterNearest;

        return n.key;

    }

    private static int compare(Node n, Point2D k) {
        if (n.cmp == XCMP) {
            if (n.key.x() > k.x()) return -1;
            else if (n.key.x() < k.x()) return 1;
            return 0;
        }
        else {
            if (n.key.y() > k.y()) return -1;
            else if (n.key.y() < k.y()) return 1;
            return 0;
        }
    }

    private void validate(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument p is null");
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        StdOut.println(kdtree.nearest(new Point2D(0.21875, 0.28125)));
    }
}

