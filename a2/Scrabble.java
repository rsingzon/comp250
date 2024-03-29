//STUDENT1_NAME: Ryan Singzon
//STUDENT1_ID: 260397455

//COMP 250: Introduction to Computer Science
//Assignment 2
//Fall 2013

import java.util.*;
import java.io.*;

public class Scrabble {

    static HashSet<String> myDictionary; // this is where the words of the dictionary are stored

    // you should not need to change this method
    // Reads dictionary from file
    public static void readDictionaryFromFile(String fileName) throws Exception {
        myDictionary=new HashSet<String>();

        BufferedReader myFileReader = new BufferedReader(new FileReader(fileName) );

        String word;
        while ((word=myFileReader.readLine())!=null) myDictionary.add(word);

	myFileReader.close();
    }



    /* Arguments: 
        char availableLetters[] : array of characters containing the letters that remain available
        String wordToDate : Word assembled to date
     Behavior:
        Prints out all English words that start with wordToDate, combined with any number (including zero) of letters from availableLetters 
     Returns:
        Nothing
     */
    
    public static void printValidWords(char availableLetters[], String WordToDate) {
    	String newWordToDate;
    	int length = availableLetters.length;

    	//Base case: availableLetters only has one letter
    	//Add the letter to WordToDate, and finish
    	if(length == 1){  
    		newWordToDate = WordToDate + availableLetters[0];
    		if(myDictionary.contains(newWordToDate)){
    			System.out.println(newWordToDate);
    		}
    	}
    	
    	//Permute the available letters and print any valid words
    	else{
    		char[] newLetters = new char[length - 1];

    		//Add each letter to WordToDate, then recursively call function 
    		for(int i=0; i<length; i++){
    			boolean letterRemoved = false;
    			char letterAdded = availableLetters[i];
    			int index = 0;

    			newWordToDate = WordToDate + letterAdded;
    			
    			//Create a new array, removing the letter that was used
    			for (int j = 0; index<newLetters.length; j++){
    				if(letterAdded == availableLetters[j] && letterRemoved == false){
    					letterRemoved = true;
    				}
    				else{
    					newLetters[index] = availableLetters[j];
    					index++;
    				}
    			}
    			if(myDictionary.contains(newWordToDate)){
    				System.out.println(newWordToDate);
    			}
    			printValidWords(newLetters, newWordToDate);
    		}
    	}
    }
    
    
    /* main method
        You should not need to change anything here.
     */
    public static void main (String args[]) throws Exception {
       
	// First, read the dictionary
	try {
	    readDictionaryFromFile("englishDictionary.txt");
        }
        catch(Exception e) {
            System.out.println("Error reading the dictionary: "+e);
        }
        
        
        // Ask user to type in letters
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in) );
        char letters[]; 
        do {
            System.out.println("Enter your letters (no spaces or commas):");
            
            letters = keyboard.readLine().toCharArray();

	    // now, enumerate the words that can be formed
            printValidWords(letters, "");
        } while (letters.length!=0);

        keyboard.close();
        
    }
}