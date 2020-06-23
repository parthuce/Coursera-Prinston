package com.parth.sort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/** Alternate solution can be found at  https://github.com/mincong-h/algorithm-princeton/blob/master/collinear/FastCollinearPoints.java */

public class FastCollinearPoints {
	
	private final LineSegment[] lineSegments;

	// finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points) {
		
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
		for (int i = 0; i < newPoints.length; i++) {
			
			Point origin = newPoints[i];
			Point[] sortedBySlope = newPoints.clone();
			Arrays.sort(sortedBySlope, origin.slopeOrder());
			
			int count = 1;
			Point initial = null;
			for (int j = 0; j < sortedBySlope.length - 1; j++) {
				
				if (sortedBySlope[j].slopeTo(origin) == sortedBySlope[j + 1].slopeTo(origin))
                {
                    count++;
                    if (count == 2)
                    {
                    	initial = sortedBySlope[j];
                        count++;
                    }
                    else if (count >= 4 && j + 1 == sortedBySlope.length - 1)
                    {
                        if (origin.compareTo(initial) < 0)
                        {
                        	listSegments.add(new LineSegment(origin, sortedBySlope[j + 1]));
                        }
                        count = 1;
                    }
                }
                else if (count >= 4)
                {
                    if (origin.compareTo(initial) < 0)
                    {
                    	listSegments.add(new LineSegment(origin, sortedBySlope[j]));
                    }
                    count = 1;
                }
                else
                {
                    count = 1;
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
     * Simple client ()
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
