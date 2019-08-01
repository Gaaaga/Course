import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments = new LineSegment[0];
    private Point[] startPoints = new Point[0];

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        checkDuplicate(points);
        Point[] newPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            newPoints[i] = points[i];
        }
        for (int i = 0; i < points.length; i++) {
            Point a = points[i];
            Arrays.sort(newPoints, a.slopeOrder());
            for (int j = 0; j < points.length - 2; j++) {
                Point b = newPoints[j];
                Point c = newPoints[j + 1];
                Point d = newPoints[j + 2];
                if (b != a && c != a && d != a) {
                    double slope0 = a.slopeTo(b);
                    double slope1 = a.slopeTo(c);
                    double slope2 = a.slopeTo(d);
                    if (slope0 == slope1 && slope1 == slope2) {
                        Point[] tuple = new Point[]{a, b, c, d};
                        Arrays.sort(tuple);
                        if (!hasStartPoint(tuple[0])) {
                            LineSegment newSegment = new LineSegment(tuple[0], tuple[3]);
                            // resize segments array
                            LineSegment[] newSegments = new LineSegment[segments.length + 1];
                            for (int k = 0; k < segments.length; k++) {
                                newSegments[k] = segments[k];
                            }
                            newSegments[segments.length] = newSegment;
                            segments = newSegments;

                            // resize points array
                            Point[] newStartPoint = new Point[startPoints.length + 1];
                            for (int k = 0; k < startPoints.length; k++) {
                                newStartPoint[k] = startPoints[k];
                            }
                            newStartPoint[startPoints.length] = tuple[0];
                            startPoints = newStartPoint;
                        }

                    }
                }
            }
        }

    }


    private boolean hasStartPoint(Point p) {
        for (int k = 0; k < startPoints.length; k++) {
            if (startPoints[k] == p) {
                return true;
            }
        }
        return false;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.segments;
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

        points = new Point[]{new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4),
                new Point(1, -1), new Point(2, -2), new Point(3, -3), new Point(4, -4),};

        collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }

    }
}
