//STUDENT1_NAME: Ryan Singzon
//STUDENT1_ID: 260397455

//COMP 250: Introduction to Computer Science
//Assignment 5
//Fall 2013

import java.util.*;
import java.io.*;

// This class implements a google-like search engine
public class searchEngine {

    public HashMap<String, LinkedList<String> > wordIndex;      // this will contain a set of pairs (String, LinkedList of Strings)	
    public directedGraph internet;             					// this is our internet graph
    
    
    
    // Constructor initializes everything to empty data structures
    // It also sets the location of the internet files
    searchEngine() {
		// Below is the directory that contains all the internet files
		htmlParsing.internetFilesLocation = "internetFiles";
		wordIndex = new HashMap<String, LinkedList<String> > ();		
		internet = new directedGraph();				
    } // end of constructor2013
    
    
    // Returns a String description of a searchEngine
    public String toString () {
    	return "wordIndex:\n" + wordIndex + "\ninternet:\n" + internet;
    }
    
    
    // This does a graph traversal of the internet, starting at the given url.
    // For each new vertex seen, it updates the wordIndex, the internet graph,
    // and the set of visited vertices.
    
    void traverseInternet(String url) throws Exception {
	/* Hints
	   0) This should take about 50-70 lines of code (or less)
	   1) To parse the content of the url, call
	   htmlParsing.getContent(url), which returns a LinkedList of Strings 
	   containing all the words at the given url. Also call htmlParsing.getLinks(url).
	   and assign their results to a LinkedList of Strings.
	   2) To iterate over all elements of a LinkedList, use an Iterator,
	   as described in the text of the assignment
	   3) Refer to the description of the LinkedList methods at
	   http://docs.oracle.com/javase/6/docs/api/ .
	   You will most likely need to use the methods contains(String s), 
	   addLast(String s), iterator()
	   4) Refer to the description of the HashMap methods at
	   http://docs.oracle.com/javase/6/docs/api/ .
	   You will most likely need to use the methods containsKey(String s), 
	   get(String s), put(String s, LinkedList l).  
	*/
    	
    	// Create a queue of URLs that will be traversed
    	Queue<String> urlQueue = new LinkedList<String>(); 
    	internet.setVisited(url, true);
    	urlQueue.add(url);
    	
    	// Continue traversing the URLs until the queue has been emptied
    	while( !urlQueue.isEmpty() ){
    		
    		//Add the URL at the front of the queue to the internet
    		String currentUrl = urlQueue.remove();
    		internet.addVertex(currentUrl);

    		// Obtain all of the words contained in the website and update word index
        	LinkedList<String> allWords = htmlParsing.getContent(currentUrl);
        	wordIndex.put(currentUrl, allWords);
        	
        	//Get the links contained on the current URL
        	LinkedList<String> allLinks = htmlParsing.getLinks(currentUrl);
        	Iterator<String> i = allLinks.iterator();
        	
        	while( i.hasNext() ){
        		String linkedUrl = i.next();
        		
        		// Always add links out of the current URL
        		internet.addEdge(currentUrl, linkedUrl);
        		
        		// Traverse linked URL if it has not yet been visited
        		if( internet.getVisited(linkedUrl) == false ){
        			internet.setVisited(linkedUrl, true);
        			internet.addVertex(linkedUrl);
            		urlQueue.add(linkedUrl);
        		}
        	}
    	}
    	
    } // end of traverseInternet
    
    
    /* This computes the pageRanks for every vertex in the internet graph.
       It will only be called after the internet graph has been constructed using 
       traverseInternet.
       Use the iterative procedure described in the text of the assignment to
       compute the pageRanks for every vertices in the graph. 
       
       This method will probably fit in about 30 lines.*/
    void computePageRanks() {

    	
	
    } // end of computePageRanks
    
	
    /* Returns the URL of the page with the high page-rank containing the query word
       Returns the String "" if no web site contains the query.
       This method can only be called after the computePageRanks method has been executed.
       Start by obtaining the list of URLs containing the query word. Then return the URL 
       with the highest pageRank.
       This method should take about 25 lines of code.
    */
    String getBestURL(String query) {
	/* WRITE YOUR CODE HERE */

	return null; // remove this
    } // end of getBestURL
    
    
	
    public static void main(String args[]) throws Exception{		
		searchEngine mySearchEngine = new searchEngine();
		
		// to debug your program, start with.
		mySearchEngine.traverseInternet("http://www.cs.mcgill.ca/~blanchem/250/a.html");
		
		// When your program is working on the small example, move on to
		//mySearchEngine.traverseInternet("http://www.cs.mcgill.ca");
		
		// this is just for debugging purposes. REMOVE THIS BEFORE SUBMITTING
		System.out.println(mySearchEngine);
		
		mySearchEngine.computePageRanks();
		
		BufferedReader stndin = new BufferedReader(new InputStreamReader(System.in));
		String query;
		do {
		    System.out.print("Enter query: ");
		    query = stndin.readLine();
		    if ( query != null && query.length() > 0 ) {
			System.out.println("Best site = " + mySearchEngine.getBestURL(query));
		    }
		} while (query!=null && query.length()>0);				
    } // end of main
}
