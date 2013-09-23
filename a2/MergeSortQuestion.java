import java.util.*;
import java.io.*;


// No need to change anything in this class
class MergeSortQuestion {

    // merges sorted subarrays A[start...firstThird], A[firstThird+1,secondThird], and A[secondThird+1,stop]
    public static void mergeThreeWay(int A[], int start, int firstThird, int secondThird, int stop) 
    {
	// WRITE YOUR CODE HERE!
    }



    // sorts A[start...stop]
    public static void mergeSortThreeWay(int A[], int start, int stop) {
	// WRITE YOUR CODE HERE
    }

    
    public static void main (String args[]) throws Exception {
       
	int myArray[] = {8,3,5,7,9,2,3,5,5,6}; // an example array to be sorted. You'll need to test your code with many cases, to be sure it works.

	mergeSortThreeWay(myArray,0,myArray.length-1);

	System.out.println("Sorted array is:\n");
	for (int i=0;i<myArray.length;i++) {
	    System.out.println(myArray[i]+" ");
	}
    }
}
	