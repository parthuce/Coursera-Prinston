package com.parth.BST;

import java.util.LinkedList;
import java.util.Queue;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
	private SET<Point2D> set;
	
	public PointSET() {
		set = new SET<>();
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public int size() {
		return set.size();
	}

	public void insert(Point2D p) {
		if (!set.contains(p)) 
			set.add(p);	
	}

	public boolean contains(Point2D p) {
		return set.contains(p);
	}

	public void draw() {
		 for (Point2D point: set) {
	            point.draw();
	        }
	}

	public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
		Queue<Point2D> queue = new LinkedList<>();
		for (Point2D point: set) {
			if (rect.contains(point)) {
				queue.add(point);
			}
        }
		return queue;
	}

	public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (isEmpty()) return null;

        Point2D nearestPoint = null;

        for (Point2D point: set) {
            if (nearestPoint == null) {
                nearestPoint = point;
                continue;
            }

            if (p.distanceSquaredTo(point) < p.distanceSquaredTo(nearestPoint)) {
                nearestPoint = point;
            }
        }
        return nearestPoint;
	}

	public static void main(String[] args) {
		// unit testing of the methods (optional)
	}
}
