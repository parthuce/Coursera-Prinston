package com.parth.sort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

	private final LineSegment[] lineSegments;

	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException("null argument to constructor");
		}

		for (Point point : points) {
			if (point == null)
				throw new IllegalArgumentException("One of the point in points array is null");
		}
		
		Point[] newPoints = points.clone();
		Arrays.sort(newPoints);
		for (int i = 0; i < newPoints.length - 1; i++) {
			if (newPoints[i].compareTo(newPoints[i + 1]) == 0) {
				throw new IllegalArgumentException("Duplicated entries in given points");
			}
		}

		List<LineSegment> listSegments = new LinkedList<>();

		for (int i = 0; i < newPoints.length - 3; i++) {
			for (int j = i + 1; j < newPoints.length - 2; j++) {
				double slopeIJ = newPoints[i].slopeTo(newPoints[j]);
				for (int k = j + 1; k < newPoints.length - 1; k++) {
					double slopeIK = newPoints[i].slopeTo(newPoints[k]);
					if (slopeIJ == slopeIK) {
						for (int l = k + 1; l < newPoints.length; l++) {
							double slopeIL = newPoints[i].slopeTo(newPoints[l]);
							if (slopeIJ == slopeIL) {
								listSegments.add(new LineSegment(newPoints[i], newPoints[l]));
							}
						}
					}
				}
			}
		}
		
		this.lineSegments = listSegments.toArray(new LineSegment[listSegments.size()]);
	}

	// the number of line segments
	public int numberOfSegments() {
		return lineSegments.length;
	}

	// the line segments
	public LineSegment[] segments() {
		return lineSegments.clone();
	}
	
	   /**
     * Simple client provided by Princeton University.
     */
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
