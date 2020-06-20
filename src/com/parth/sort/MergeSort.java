package com.parth.sort;

import java.util.Arrays;

public class MergeSort {

	public static void main(String[] args) {
		int[] array = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13, 5, 10, 8,2,3,4,50};
		Arrays.sort(array);
		for (int i : array) {
			System.out.println(i);
		}
	}
	
	public static void sort(Comparable[] a) {
		sort(a, a.clone(), 0 , a.length);
	}

	private static void sort(Comparable[] a, Comparable[] aux, int low, int high) {
		
		if(low <= high) return;
		int mid = (low + high) >>> 1; // Add zero on MSB (left most bit) and discards LSB
		sort(a, aux, low, mid);
		sort(a, aux, mid, high);
		if(a[mid-1].compareTo(a[mid]) <= 0) return;
		merge(a, aux, low, mid, high);
	}

	private static void merge(Comparable[] a, Comparable[] aux, int low, int mid, int high) {
		
		for (int k = low, i = low, j = high; k < high; k++ ) {
			if(j >= high || i < mid && aux[i].compareTo(aux[j]) <= 0)
				a[k] = aux[i++];
			else 
				a[k] = aux[j++];
		}
	}
	
}
