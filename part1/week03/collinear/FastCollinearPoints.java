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
    private LineSegment[] seg;
    private double[] slopes;

    public FastCollinearPoints(
            Point[] points) { // finds all line segments containing 4 or more points
        pointsCheck(points);
        Arrays.sort(points);
        count = 0;
        seg = new LineSegment[1];
        slopes = new double[1];

        Point[] aux = new Point[points.length];
        for (int i = 0; i < points.length; i++) { // copy points into aux
            aux[i] = points[i];
        }

        for (int i = 0; i < points.length; i++) {
            // sort(aux, points[i].slopeOrder());
            Point p = points[i];
            Arrays.sort(aux, p.slopeOrder());

            int cnt = 0;
            double slope = p.slopeTo(aux[0]);  // sort aux according the slope to points[i]

            for (int j = 1; j < aux.length; j++) {
                if (slope == p.slopeTo(aux[j])) {
                    cnt++;
                    if (j == aux.length - 1) {
                        or (int k = 0; k < count; k++) {
                            if (slope == slopes[k]) {
                                overlap = true;
                                break;
                            }
                        }
                        if (!overlap) {
                            count++;
                            if (count > seg.length) {
                                resize();
                            }
                            seg[count - 1] = new LineSegment(p, aux[j - 1]);
                            slopes[count - 1] = slope;
                    }
                }
                else {
                    if (cnt >= 3) {
                        boolean overlap = false;
                        for (int k = 0; k < count; k++) {
                            if (slope == slopes[k]) {
                                overlap = true;
                                break;
                            }
                        }
                        if (!overlap) {
                            count++;
                            if (count > seg.length) {
                                resize();
                            }
                            seg[count - 1] = new LineSegment(p, aux[j - 1]);
                            slopes[count - 1] = slope;
                            StdOut.println(slope);
                            StdOut.println(seg[count - 1]);
                        }
                    }
                    cnt = 1;
                    slope = p.slopeTo(aux[j]);
                }
            }
        }

        LineSegment[] temp = seg;
        seg = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            seg[i] = temp[i];
        }

    }

    public int numberOfSegments() {
        // the number of line segments
        return count;
    }

    public LineSegment[] segments() { // the line segments
        return seg;
    }

    private void resize() {
        LineSegment[] tempSeg = seg;
        double[] tempSlp = slopes;
        seg = new LineSegment[tempSeg.length * 2];
        slopes = new double[tempSlp.length * 2];
        for (int i = 0; i < tempSeg.length; i++) {
            seg[i] = tempSeg[i];
            slopes[i] = tempSlp[i];
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
