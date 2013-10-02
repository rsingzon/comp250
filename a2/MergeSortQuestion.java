//STUDENT1_NAME: Ryan Singzon
//STUDENT1_ID: 260397455

//COMP 250: Introduction to Computer Science
//Assignment 2
//Fall 2013

import java.util.*;
import java.io.*;


// No need to change anything in this class
class MergeSortQuestion {

    // merges sorted subarrays A[start...firstThird], A[firstThird+1,secondThird], and A[secondThird+1,stop]
    public static void mergeThreeWay(int A[], int start, int firstThird, int secondThird, int stop) {
    	
    	int[] tempArray = new int[A.length];
    	int indexLeft = start;
    	int indexLeftMid = firstThird + 1;
    	int indexRightMid = secondThird + 1;
    	int tempIndex = start;
    	
    	
    	while(tempIndex <= stop){
    		
    		//Check if the indices are within bounds, then
    		//insert the smallest value into the temp array
   			if(	indexLeft <= firstThird 
   					&& A[indexLeft] < A[indexLeftMid] 
   					&& A[indexLeft] < A[indexRightMid]){
   				tempArray[tempIndex] = A[indexLeft];
   				indexLeft++;
   			}	
    		else if(indexLeftMid < secondThird 
    				&& A[indexLeftMid] < A[indexLeft] 
    				&& A[indexLeftMid] < A[indexRightMid]){
    			tempArray[tempIndex] = A[indexLeftMid];
    			indexLeftMid++;
    		}
    		else if(indexRightMid <= stop){
    			tempArray[tempIndex] = A[indexRightMid];
    			indexRightMid++;
    		}
   			System.out.println(tempArray[tempIndex]);
    		tempIndex++;
    		
    	}
    	
    	System.out.println("Sorted Array: ");
    	for(int i = 0; i <= stop; i++){
    		System.out.print(tempArray[i]);
    	}
    	System.out.println();
    }

    // sorts A[start...stop]
    public static void mergeSortThreeWay(int A[], int start, int stop) {

    	System.out.println("Start: " + start);
		System.out.println("Stop: " + stop + "\n");
    	
    	//Base case: The subarray only contains one element
    	if(stop - start == 0){

    	}
   
    	//If the subarray contains two elements, sort it
    	else if(stop - start == 1){
    		if(A[start] > A[stop]){
    			int temp = A[start];
    			A[start] = A[stop];
    			A[stop] = temp;
    		}
    	}    	
    	
    	//If the array contains three or more elements, split up the array
    	else{
    		int firstThird = (stop - start) / 3 + start;
    		int secondThird = (stop - start) * 2 / 3 + start;
    		
    		/*System.out.println("first: " + firstThird);
    		System.out.println("second: " + secondThird);
    		*/
    		System.out.println("START TO FIRST:");
    		mergeSortThreeWay(A, start, firstThird);
    		
    		System.out.println("SECOND TO THIRD");
    		mergeSortThreeWay(A, firstThird + 1, secondThird);
    		
    		System.out.println("THIRD TO STOP");
    		mergeSortThreeWay(A, secondThird + 1, stop);   
    		
    		System.out.println("MERGING: " + start + ", "+firstThird+", "+secondThird+", "+stop);
    		
    		//The values from A[start..firstThird], A[firstThird+1..secondThird], etc must be SORTED
    		mergeThreeWay(A, start, firstThird, secondThird, stop);
    	}
    }

    
    public static void main (String args[]) throws Exception {
       
		//int myArray[] = {8,3,5,7,9,2,3,5,5}; // an example array to be sorted. You'll need to test your code with many cases, to be sure it works.
    	int myArray[] = {7,4,9,8,5,3};
    	
    	//Step 1: Call mergeSort on the entire array
    	//Step 2: The mergeSort splits the array into three parts
    	//Step 3: Each third is recursively sorted
    	//Step 4: Each of the small parts are merged back into one array
    	
		mergeSortThreeWay(myArray,0,myArray.length-1);
		System.out.println("\nArray length: " + myArray.length);
		System.out.println("Sorted array is:");
		for (int i=0;i<myArray.length;i++) {
		    System.out.print(myArray[i]);
		}
    }
}
	