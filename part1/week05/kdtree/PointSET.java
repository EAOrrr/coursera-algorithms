import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> point2DSET;

    public PointSET()                          // construct an empty set of points
    {
        point2DSET = new SET<>();
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return point2DSET.isEmpty();
    }

    public int size()                         // number of points in the set
    {
        return point2DSET.size();
    }

    public void insert(
            Point2D p)              // add the point to the set (if it is not already in the set)
    {
        validate(p);
        point2DSET.add(p);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        validate(p);
        return point2DSET.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        for (Point2D p : point2DSET) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(
            RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) throw new IllegalArgumentException("argument to range() is null");
        Queue<Point2D> point2DQueue = new Queue<>();
        for (Point2D p : point2DSET) {
            if (rect.contains(p)) {
                point2DQueue.enqueue(p);
            }
        }
        return point2DQueue;
    }

    public Point2D nearest(
            Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        validate(p);
        Point2D nearest = null;
        double minDist = Double.POSITIVE_INFINITY;
        for (Point2D q : point2DSET) {
            if (p.distanceTo(q) < minDist) {
                minDist = p.distanceTo(q);
                nearest = q;
            }
        }
        return nearest;
    }

    private void validate(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument p is null");
    }

    public static void main(
            String[] args)                  // unit testing of the methods (optional)
    {

    }
}
