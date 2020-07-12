package com.parth.BST;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KDTester {
	public static void main(String[] args) {
		 String filename = args[0];
	        In in = new In(filename);
	        PointSET brute = new PointSET();
	        KdTree kdtree = new KdTree();
	        while (!in.isEmpty()) {
	            double x = in.readDouble();
	            double y = in.readDouble();
	            Point2D p = new Point2D(x, y);
	            kdtree.insert(p);
	            brute.insert(p);
	        }
	        
	        System.out.println(kdtree.nearest(new Point2D(0.81, 0.30)));
	        System.out.println(brute.nearest(new Point2D(0.81, 0.30)));
	        
	        for (Point2D point : kdtree.range(new RectHV(0.0, 0.0, 0.5, 0.5))) {
				System.out.println(point);
			}
	        
	        for (Point2D point : brute.range(new RectHV(0.0, 0.0, 0.5, 0.5))) {
				System.out.println(point);
			}

	}
}
