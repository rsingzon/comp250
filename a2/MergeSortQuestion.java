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
    	int indexMiddle = firstThird + 1;
    	int indexRight = secondThird + 1;
    	int tempIndex = start;
    	
    	while(tempIndex <= stop){
    		
    		//Check if the indices are within bounds, then
    		//insert the smallest value into the temp array
    		
    		int left;
    		int middle;
    		int right;
    		
    		boolean leftInBounds = indexLeft <= firstThird;
    		boolean middleInBounds = indexMiddle <= secondThird;
    		boolean rightInBounds = indexRight <= stop;
    		
    		//All of the values are valid, find the smallest of the three
    		if(leftInBounds && middleInBounds && rightInBounds){
       			left = A[indexLeft];
       			middle = A[indexMiddle];
       			right = A[indexRight];

       			if(left <= middle && left <= right){
    				tempArray[tempIndex] = left;
    				indexLeft++;
    			}
    			else if(middle <= right){
    				tempArray[tempIndex] = middle;
    				indexMiddle++;
    			}
    			else{
    				tempArray[tempIndex] = right;
    				indexRight++;
    			}
    		}
    		
    		//Left index is out of bounds
    		else if(middleInBounds && rightInBounds){
    			middle = A[indexMiddle];
       			right = A[indexRight];
    			
    			if(middle <= right){
    				tempArray[tempIndex] = middle;
    				indexMiddle++;
    			}
    			else{
    				tempArray[tempIndex] = right;
    				indexRight++;
    			}
    		}
    		
    		//Middle index is out of bounds
    		else if(leftInBounds && rightInBounds){
    			left = A[indexLeft];
    			right = A[indexRight];
    			
    			if(left <= right){
    				tempArray[tempIndex] = left;
    				indexLeft++;
    			}
    			else{
    				tempArray[tempIndex] = right;
    				indexRight++;
    			}
    		}
    		
    		//Right index is out of bounds
    		else if(leftInBounds && middleInBounds){
    			left = A[indexLeft];
    			middle = A[indexMiddle];
    			
    			if(left <= middle){
    				tempArray[tempIndex] = left;
    				indexLeft++;
    			}
    			else{
    				tempArray[tempIndex] = middle;
    				indexMiddle++;
    			}
    		}
    		
    		//Left and middle out of bounds
    		else if(rightInBounds){
				tempArray[tempIndex] = A[indexRight];
				indexRight++;
    		}
    		
    		//Left and right out of bounds
    		else if(middleInBounds){
				tempArray[tempIndex] = A[indexMiddle];
				indexMiddle++;
    		}
    		
    		//Middle and right out of bounds
    		else{
				tempArray[tempIndex] = A[indexLeft];
				indexLeft++;
    		}
    		
    		tempIndex++;
    	}
    	
    	//Place the sorted values back into the original array
    	for(int i = start; i <= stop; i++){
    		A[i] = tempArray[i];
    	}
    }

    // sorts A[start...stop]
    public static void mergeSortThreeWay(int A[], int start, int stop) {

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
    		
    		mergeSortThreeWay(A, start, firstThird);
    		mergeSortThreeWay(A, firstThird + 1, secondThird);
    		mergeSortThreeWay(A, secondThird + 1, stop);   
    		mergeThreeWay(A, start, firstThird, secondThird, stop);
    	}
    }

    
    public static void main (String args[]) throws Exception {
       
		int myArray[] = {8,22,46,32,213,19,21,3,5,7,9,2,3,5,5,8,12,13,34,54,232,9000,14,51}; // an example array to be sorted. You'll need to test your code with many cases, to be sure it works.
    	
    	//Step 1: Call mergeSort on the entire array
    	//Step 2: The mergeSort splits the array into three parts
    	//Step 3: Each third is recursively sorted
    	//Step 4: Each of the small parts are merged back into one array
    	
		mergeSortThreeWay(myArray,0,myArray.length-1);
		System.out.println("Sorted array is:");
		for (int i=0;i<myArray.length;i++) {
		    System.out.print(myArray[i] + " ");
		}
    }
}
	