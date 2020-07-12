package com.parth.BST;

import java.util.LinkedList;
import java.util.Queue;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

	Node root = null;
	int size = 0;

	private static class Node {
		public Node(Point2D p) {
			this.p = p;
		}

		private Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree
	}

	public KdTree() {

	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException("Point cannot be null");
		if (!contains(p)) {
			Node node = new Node(p);
			if (root == null) {
				node.rect = new RectHV(0, 0, 1, 1);
				root = node;
				size++;
				return;
			}
			insert(root, p, 2);
		}
	}

	private void insert(Node node, Point2D p, int level) {
		int mod = level % 2;
		if (mod == 0) {
			int comp = Double.compare(p.x(), node.p.x());
			if (comp < 0) {
				if (node.lb == null) {
					node.lb = new Node(p);
					node.lb.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
					size++;
				} else {
					insert(node.lb, p, ++level);
				}
			} else {
				if (node.rt == null) {
					node.rt = new Node(p);
					node.rt.rect = new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
					size++;
				} else {
					insert(node.rt, p, ++level);
				}
			}
		} else {
			int comp = Double.compare(p.y(), node.p.y());
			if (comp < 0) {
				if (node.lb == null) {
					node.lb = new Node(p);
					node.lb.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
					size++;

				} else {
					insert(node.lb, p, ++level);
				}
			} else {
				if (node.rt == null) {
					node.rt = new Node(p);
					node.rt.rect = new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
					size++;

				} else {
					insert(node.rt, p, ++level);
				}
			}
		}
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException("Point cannot be null");
		Node node = root;
		int level = 1;
		while (node != null) {
			int mod = level % 2;
			if (mod == 0) {
				int comp = Double.compare(p.y(), node.p.y());
				if (comp == 0 && p.compareTo(node.p) == 0) {
					return true;
				} else if (comp < 0) {
					node = node.lb;
				} else {
					node = node.rt;
				}
			} else {
				int comp = Double.compare(p.x(), node.p.x());
				if (comp == 0 && p.compareTo(node.p) == 0) {
					return true;
				} else if (comp < 0) {
					node = node.lb;
				} else {
					node = node.rt;
				}
			}
			level++;
		}
		return false;
	}

	public void draw() {
		// draw all points to standard draw
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new IllegalArgumentException("Rect cannot be null");
		Queue<Point2D> queue = new LinkedList<>();
		range(root, rect, queue);
		return queue;
	}

	private void range(Node node, RectHV rect, Queue<Point2D> queue) {
		if (node == null || !node.rect.intersects(rect)) return;
		
		if (rect.contains(node.p)) {
			queue.add(node.p);
		}
		range(node.lb, rect, queue);
		range(node.rt, rect, queue);
	}

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException("Point cannot be null");
		if(root == null) return null;
		return nearest(root, p, root.p, 1);
		// a nearest neighbor in the set to point p; null if the set is empty
	}

	private Point2D nearest(Node node, Point2D p, Point2D closestPoint, int level) {
		if(node == null) return closestPoint;
		
		if(node.rect.distanceSquaredTo(p) < closestPoint.distanceSquaredTo(p)) {
			if(node.p.distanceSquaredTo(p) < closestPoint.distanceSquaredTo(p)) {
				closestPoint = node.p;
			}
			int mod = level % 2;
			if (mod == 1) {
				if (p.x() <= node.p.x()) {
					closestPoint = nearest(node.lb, p, closestPoint, ++level);
					closestPoint = nearest(node.rt, p, closestPoint, ++level);
				} else {
					closestPoint = nearest(node.rt, p, closestPoint, ++level);
					closestPoint = nearest(node.lb, p, closestPoint, ++level);
				}
			} else {
				if (p.y() <= node.p.y()) {
					closestPoint = nearest(node.lb, p, closestPoint, ++level);
					closestPoint = nearest(node.rt, p, closestPoint, ++level);
				} else {
					closestPoint = nearest(node.rt, p, closestPoint, ++level);
					closestPoint = nearest(node.lb, p, closestPoint, ++level);
				}
			}
		}
		return closestPoint;
	}

	public static void main(String[] args) {
		KdTree tree1 = new KdTree();
		tree1.insert(new Point2D(0.7, 0.2));
		tree1.insert(new Point2D(0.5, 0.4));
		tree1.insert(new Point2D(0.2, 0.3));
		tree1.insert(new Point2D(0.4, 0.7));
		tree1.insert(new Point2D(0.9, 0.6));

		System.out.println(tree1.contains(new Point2D(0.4, 0.7)));

		System.out.println("Complted! Please verify");
		
		 	KdTree tree = new KdTree();
	        assert tree.isEmpty();
	        assert tree.size() == 0;

	        tree.insert(new Point2D(0.5, 0.5));
	        tree.insert(new Point2D(0.5, 0.9));
	        tree.insert(new Point2D(0.25, 0.4));
	        tree.insert(new Point2D(0.75, 0.6));
	        tree.insert(new Point2D(0.15, 0.25));
	        tree.insert(new Point2D(0.35, 0.85));
	        tree.insert(new Point2D(0.05, 0.75));
	        System.out.println( !tree.isEmpty());
	        System.out.println(tree.size());
	        System.out.println( tree.size() == 7);
	        
	        
	        System.out.println("matching");
	        System.out.println(tree.contains(new Point2D(0.5, 0.5)));
	        System.out.println(tree.contains(new Point2D(0.5, 0.9)));
	        System.out.println( tree.contains(new Point2D(0.25, 0.4)));
	        System.out.println( tree.contains(new Point2D(0.75, 0.6)));
	        System.out.println(tree.contains(new Point2D(0.15, 0.25)));
	        System.out.println(tree.contains(new Point2D(0.35, 0.85)));
	        System.out.println(tree.contains(new Point2D(0.05, 0.75)));
	        //System.out.println(!tree.contains(new Point2D(0.25, 0.74)));
	        System.out.println(!tree.contains(new Point2D(0.24, 0.75)));
	}
}
