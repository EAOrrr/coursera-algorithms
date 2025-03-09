/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int segmentCount;
    private LineSegment[] seg;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        // if (points == null) throw new IllegalArgumentException("");
        pointsCheck(points);
        segmentCount = 0;
        seg = new LineSegment[1];
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        Point p = points[i], q = points[j], r = points[k], s = points[m];
                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
                            segmentCount++;
                            if (segmentCount > seg.length) resize();
                            seg[segmentCount - 1] = new LineSegment(p, s);
                            StdOut.println(seg[segmentCount - 1]);
                        }
                    }
                }
            }
        }
        LineSegment[] temp = seg;
        seg = new LineSegment[segmentCount];
        for (int i = 0; i < segmentCount; i++) {
            seg[i] = temp[i];
        }

    }

    public int numberOfSegments() { // the number of line segments
        return segmentCount;
    }

    public LineSegment[] segments() { // the line segments
        return seg;
    }

    private void resize() {
        LineSegment[] temp = seg;
        int capacity = seg.length * 2;
        seg = new LineSegment[capacity];
        for (int i = 0; i < temp.length; i++) {
            seg[i] = temp[i];
        }
    }

    private static void pointsCheck(Point[] points) {
        if (points == null) throw new IllegalArgumentException("");
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == null || points[i] == points[j]) {
                    throw new IllegalArgumentException("");
                }
            }
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
