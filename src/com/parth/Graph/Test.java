package com.parth.Graph;

public class Test {

	// Driver method
	public static void main(String args[]) {
		int[] nums = {5,6,6,7,7,7,8,8,8,8,9,10};
		
		/* int i = 0;
		int n = nums.length;
		while (i < n - 1) {
			if (nums[i] == nums[i + 1]) {
				for (int j = i + 1; j < n - 1; j++) {
					nums[j] = nums[j + 1];
				}
				nums[n - 1] = 0;
				n--;
			} else {
				i++;
			}
		} */
		
		    int i = 0;
		    for (int j = 1; j < nums.length; j++) {
		        if (nums[j] != nums[i]) {
		            i++;
		            nums[i] = nums[j];
		        }
		    }
		    System.out.println(i + 1);
		    
		    for (int a : nums) {
		    	System.out.println(a);
		    }
		    
		    String str = "";
		    str.indexOf("", 5);
		    
			char c = 'A';
System.out.println(c - 65);
		}
}
