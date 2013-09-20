//STUDENT1_NAME: Ryan Singzon
//STUDENT1_ID: 260397455

//COMP 250: Introduction to Computer Science
//Fall 2013

import java.io.*;    
import java.util.*;

class studentList {
    int studentID[];
    int numberOfStudents;
    String courseName;
    
    // A constructor that reads a studentList from the given fileName and assigns it the given courseName
    public studentList(String fileName, String course) {
	String line;
	int tempID[]=new int[4000000]; // this will only work if the number of students is less than 4000000.
	numberOfStudents=0;
	courseName=course;
	BufferedReader myFile;
	try {
	    myFile = new BufferedReader(new FileReader( fileName ) );	

	    while ( (line=myFile.readLine())!=null ) {
		tempID[numberOfStudents]=Integer.parseInt(line);
		numberOfStudents++;
	    }
	    studentID=new int[numberOfStudents];
	    for (int i=0;i<numberOfStudents;i++) {
		studentID[i]=tempID[i];
	    }
	} catch (Exception e) {System.out.println("Can't find file "+fileName);}
	
    }
    

    // A constructor that generates a random student list of the given size and assigns it the given courseName
    public studentList(int size, String course) {
	int IDrange=2*size;
	studentID=new int[size];
	boolean[] usedID=new boolean[IDrange];
	for (int i=0;i<IDrange;i++) usedID[i]=false;
	for (int i=0;i<size;i++) {
	    int t;
	    do {
		t=(int)(Math.random()*IDrange);
	    } while (usedID[t]);
	    usedID[t]=true;
	    studentID[i]=t;
	}
	courseName=course;
	numberOfStudents=size;
    }
    
    // Returns the number of students present in both lists L1 and L2
    public static int intersectionSizeNestedLoops(studentList L1, studentList L2) {
    	
    	int intersectSize = 0;
    	
    	//Loop through the names in the first list and the second list
    	for(int i = 0; i < L1.numberOfStudents; i++){
    		for(int j = 0; j < L2.numberOfStudents; j++){
    			
    			//If the names match, then increment the count
    			if(L1.studentID[i] == L2.studentID[j]){
    				intersectSize++;
    			}
    		}
    	}
    	return intersectSize;
    }
    
    
    // This algorithm takes as input a sorted array of integers called mySortedArray, the number of elements it contains, and the student ID number to look for
    // It returns true if the array contains an element equal to ID, and false otherwise.
    public static boolean myBinarySearch(int mySortedArray[], int numberOfStudents, int ID) {
    	
    	int leftIndex = 0;
    	int rightIndex = numberOfStudents;
    	
    	//While the right index is greater than the left, search for the ID in the sorted array
    	while(rightIndex > leftIndex + 1){
    		
    		//Find the centre of the array
    		int middle = (int)Math.ceil((leftIndex + rightIndex)/2);
    		
    		//If the index of the middle of the array is greater than the ID, search the left half of the array
    		if(mySortedArray[middle] > ID){
    			rightIndex = middle;
    		}
    		
    		//Else, search the right half of the array
    		else{
    			leftIndex = middle;
    		}
    	}
    	
    	//Return true if the ID from one array is in the other array
    	if(mySortedArray[leftIndex] == ID){
    		return true;
    	}
    	
   		return false;
    }
    
    //Takes two lists of student IDs and finds the number of common IDs in both lists using a binary search
    public static int intersectionSizeBinarySearch(studentList L1, studentList L2) {
    	
    	int intersectSize = 0;
    	
    	//Sort the array to be passed to the binary search method
    	Arrays.sort(L2.studentID);
    	
    	//Loop through the first list and perform a binary search to see if any of the IDs in the first list appear in the second
    	for(int i = 0; i < L1.numberOfStudents; i++){
    		if(myBinarySearch(L2.studentID, L2.numberOfStudents, L1.studentID[i])){
    			intersectSize++;
    		}
    	}
    	
    	return intersectSize;
    }
    
    
    public static int intersectionSizeSortAndParallelPointers(studentList L1, studentList L2) {

    	//Initialize variables
    	int intersectSize = 0;
    	int pointerA = 0;
    	int pointerB = 0;
    	
    	//Sort the arrays of studentIDs
    	Arrays.sort(L1.studentID);
    	Arrays.sort(L2.studentID);
    	
    	while(pointerA < L1.numberOfStudents && pointerB < L2.numberOfStudents){
    		if(L1.studentID[pointerA] == L2.studentID[pointerB]){
    			intersectSize++;
    			pointerA++;
    			pointerB++;
    		}
    		else if(L1.studentID[pointerA] < L2.studentID[pointerB]){
    			pointerA++;
    		}
    		else{
    			pointerB++;
    		}
    	}
    	return intersectSize;
    }
    
    
    public static int intersectionSizeMergeAndSort(studentList L1, studentList L2) {
	
    	int intersectSize = 0;
    	int pointer = 0;
    	int[] mergedList = new int[L1.numberOfStudents + L2.numberOfStudents];
    	
    	//Insert the IDs of the students into the merged array
    	for(int i = 0; i < L1.numberOfStudents; i++){
    		mergedList[i] = L1.studentID[i];
    	}
    	for(int i = 0; i < L2.numberOfStudents; i++){
    		mergedList[i + L1.numberOfStudents] = L2.studentID[i];
    	}
    	
    	Arrays.sort(mergedList);
    	
    	//Check if two adjacent entries in the array are equal.  If so, increment the intersect size
    	while(pointer < (L1.numberOfStudents + L2.numberOfStudents -1) ){
    		if(mergedList[pointer] == mergedList[pointer + 1]){
    			intersectSize++;
    			pointer = pointer + 2;
    		}
    		else{
    			pointer++;
    		}
    	}
    	
    	return intersectSize;
    }
    
    /* The main method */
    /* Write code here to test your methods, and evaluate the running time of each. 2013f */
    /* This method will not be marked */
    public static void main(String args[]) throws Exception {
		studentList firstList;
		studentList secondList;
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("experimentalData.txt", true)));
		
		out.println("Binary search");
		
		long programTime = System.currentTimeMillis();
		System.out.println("Start time: " + programTime);
		
		//Create a loop which runs the algorithm on different sized lists
		for(int power = 0; power < 9; power++){
			
			int listSize = (int)Math.pow(2,  power);
			out.println("LIST SIZE: " + listSize);
			
			// repeat the process a certain number of times, to make more accurate average measurements.
			for (int rep=0;rep<1000;rep++) {
				
				
				firstList=new studentList(listSize, "COMP250 - Introduction to Computer Science"); 
				secondList=new studentList(listSize, "MATH240 - Discrete Mathematics"); 
				
				// get the time before starting the intersections
				long startTime = System.nanoTime();
				
				// run the intersection method
				int intersect = studentList.intersectionSizeBinarySearch(firstList, secondList);
				
				// get the time after the intersection
				long endTime = System.nanoTime();
				//out.println("List size: "+listSize+ "\tTime: "+ (endTime-startTime)/1000.0 + " nanoseconds \t Intersect: " + intersect);
				out.println((endTime-startTime)/1000.0);
			}
		}
		
		out.close();
		
		System.out.println("Done");
		System.out.println("Process took " + (System.currentTimeMillis() - programTime)/1000.0 + " s");
		
		
//		    int intersectionMerge = studentList.intersectionSizeMergeAndSort(firstList, secondList);
//		    int intersectionPointers = studentList.intersectionSizeSortAndParallelPointers(firstList, secondList);
//		    int intersectionNested=studentList.intersectionSizeNestedLoops(firstList,secondList);
//		    System.out.println("Binary: "+intersectionBinary+"\t Merge: "+intersectionMerge+"\t Pointers: "+intersectionPointers+"\t Nested: "+intersectionNested);
    }
}


