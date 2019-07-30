import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments = new LineSegment[0];

    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        checkNull(points);
        checkDuplicate(points);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point p0 = points[i];
                        Point p1 = points[j];
                        Point p2 = points[k];
                        Point p3 = points[l];
                        if (isSameLine(p0, p1, p2, p3)) {
                            Point[] tuple = new Point[]{p0, p1, p2, p3};
                            Arrays.sort(tuple);
                            LineSegment[] newSegments = new LineSegment[segments.length + 1];
                            for (int m = 0; m < segments.length; m++) {
                                newSegments[m] = segments[m];
                            }
                            newSegments[segments.length] = new LineSegment(tuple[0], tuple[3]);
                            this.segments = newSegments;
                        }
                    }
                }
            }
        }
        ;
    }

    private boolean isSameLine(Point a, Point b, Point c, Point d) {
        double slcopeb = a.slopeTo(b);
        double slcopec = a.slopeTo(c);
        double slcoped = a.slopeTo(d);
        return (slcopeb == slcopec && slcopec == slcoped);
    }

    private void checkDuplicate(Point[] points) {
        if (points.length > 0) {
            Point[] newPoints = new Point[points.length];
            for (int i = 0; i < points.length; i++) {
                newPoints[i] = points[i];
            }
            Arrays.sort(newPoints);
            for (int i = 1; i < newPoints.length; i++) {
                if (newPoints[i - 1].compareTo(newPoints[i]) == 0) {
                    throw new IllegalArgumentException("duplicate points");
                }
            }
        }
    }

    private void checkNull(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        } else {
            for (Point p : points) {
                if (p == null) {
                    throw new NullPointerException();
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.segments;
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
