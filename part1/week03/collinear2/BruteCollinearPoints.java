/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int count;
    private LineSegment[] segment;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points

        checkPoints(points);

        Point[] myPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(myPoints);

        count = 0;
        segment = new LineSegment[1];

        int len = myPoints.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    for (int m = k + 1; m < len; m++) {
                        Point p = myPoints[i], q = myPoints[j], r = myPoints[k], s = myPoints[m];
                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
                            incCount(p, s);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() { // the number of line segments
        return count;
    }

    public LineSegment[] segments() { // the line segments
        return Arrays.copyOf(segment, count);
    }

    private void checkPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points point to null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("");
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Illegal pointer pointing at points");
                }
            }
        }
    }

    private void incCount(Point p, Point s) {
        if (count >= segment.length) {
            LineSegment[] temp = segment;
            segment = new LineSegment[2 * count];
            for (int i = 0; i < count; i++) {
                segment[i] = temp[i];
            }
        }
        segment[count] = new LineSegment(p, s);
        count++;
    }


    public static void main(String[] args) {
        Point[] points = new Point[3];
        points[0] = new Point(2, 4);
        points[1] = new Point(1, 2);
        points[2] = null;

        // // read the n points from a file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // Point[] points = new Point[n];
        // for (int i = 0; i < n; i++) {
        //     int x = in.readInt();
        //     int y = in.readInt();
        //     points[i] = new Point(x, y);
        // }
        //
        // // draw the points
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
