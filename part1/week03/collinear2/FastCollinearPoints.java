/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int count;
    private LineSegment[] segment;

    public FastCollinearPoints(
            Point[] points) {     // finds all line segments containing 4 or more points
        checkPoints(points);

        Point[] myPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(myPoints);


        count = 0;
        segment = new LineSegment[1];

        for (int i = 0; i < myPoints.length; i++) {
            Point p = myPoints[i];
            // Arrays.sort(points, i, points.length, p.slopeOrder());
            int cnt = 0;
            Point[] order = Arrays.copyOf(myPoints, myPoints.length);
            Arrays.sort(order, p.slopeOrder());
            double currSlop = Double.NEGATIVE_INFINITY;
            boolean take = true;
            for (int j = 1; j < myPoints.length; j++) {
                if (currSlop == p.slopeTo(order[j])) {
                    cnt++;
                    if (p.compareTo(order[j]) > 0) {
                        take = false;
                    }
                }
                else {
                    if (cnt >= 3 && take) {
                        incCount(p, order[j - 1]);
                    }
                    cnt = 1;
                    currSlop = p.slopeTo(order[j]);
                    if (p.compareTo(order[j]) > 0) take = false;
                    else take = true;
                }
            }
            if (cnt >= 3 && take) {
                incCount(p, order[myPoints.length - 1]);
            }
        }
    }

    public int numberOfSegments() {       // the number of line segments
        return count;
    }

    public LineSegment[] segments() {               // the line segments
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
            LineSegment[] tempSeg = segment;
            segment = new LineSegment[2 * count];
            for (int i = 0; i < count; i++) {
                segment[i] = tempSeg[i];
            }
        }
        segment[count] = new LineSegment(p, s);
        count++;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
