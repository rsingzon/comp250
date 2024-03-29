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
    	//Obtain all urls in the internet and iterate through all of them
    	Iterator<String> allUrls = internet.getVertices().iterator();
    	
    	//Re-compute the page rank until the numbers have converged (ie. 100 times)
    	int iteration = 0;
    	while( iteration < 100 ){
    		allUrls = internet.getVertices().iterator();
    		
    		//Set the page rank for each URL in the internet
    		while( allUrls.hasNext() ){
        		String url = allUrls.next();

        		//Set pageranks to 1 for first iteration
        		if(iteration == 0){
        			internet.setPageRank(url, 1);  	
        		} 
        		
        		// For subsequent iterations, calculate the page rank of the URL based on the
        		// number of pages that are linked to it and the page ranks of those URLs
        		else{
        			Iterator<String> linksIntoUrl = internet.getEdgesInto(url).iterator();
        			double pageRank = 0;
        			
        			while( linksIntoUrl.hasNext() ){
        				String linkedUrl = linksIntoUrl.next();
        				pageRank += internet.getPageRank(linkedUrl) / internet.getOutDegree(linkedUrl);
        			}
        			
        			pageRank = 0.5 + 0.5 * pageRank; 
            		internet.setPageRank(url, pageRank);
        		}
        	}
    		iteration++;
    	}
    } // end of computePageRanks
    
	
    /* Returns the URL of the page with the high page-rank containing the query word
       Returns the String "" if no web site contains the query.
       This method can only be called after the computePageRanks method has been executed.
       Start by obtaining the list of URLs containing the query word. Then return the URL 
       with the highest pageRank.
       This method should take about 25 lines of code.
    */
    String getBestURL(String query) {
    	String bestUrl = "";
    	double highestPageRank = 0;

    	// Get the urls of all the vertices in the internet and search them for the query
    	Iterator<String> allUrls = internet.getVertices().iterator();
    	while( allUrls.hasNext() ){
    		String url = allUrls.next();
    		Iterator<String> wordsInUrl = wordIndex.get(url).iterator();
    		
    		//Search the list of words contained on each page
    		while( wordsInUrl.hasNext() ){
    			String word = wordsInUrl.next();
    		
    			//Convert both the query and the words to lowercase to make the search 
    			//case insensitive
    			if( word.toLowerCase().contains(query.toLowerCase()) ){

    				//If the url contains the query AND its page rank is higher than the current
    				//highest page rank, then set that URL to the new best URL
    				if( internet.getPageRank(url) > highestPageRank ){
    					highestPageRank = internet.getPageRank(url);
    					bestUrl = url;
    				}
    			}
    		}
    	}
		return bestUrl;
    } // end of getBestURL
    
    
	
    public static void main(String args[]) throws Exception{		
		searchEngine mySearchEngine = new searchEngine();
		
		// to debug your program, start with.
		//mySearchEngine.traverseInternet("http://www.cs.mcgill.ca/~blanchem/250/a.html");
		
		// When your program is working on the small example, move on to
		mySearchEngine.traverseInternet("http://www.cs.mcgill.ca");
		
		// this is just for debugging purposes. REMOVE THIS BEFORE SUBMITTING
		mySearchEngine.computePageRanks();
//		System.out.println(mySearchEngine);
		
		BufferedReader stndin = new BufferedReader(new InputStreamReader(System.in));
		String query;
		do {
		    System.out.print("Enter query: ");
		    query = stndin.readLine();
		    if ( query != null && query.length() > 0 ) {
		    String bestSite = mySearchEngine.getBestURL(query);
			System.out.println("Best site = " + bestSite);
			System.out.println("Page rank = " + mySearchEngine.internet.getPageRank(bestSite));
		    }
		} while (query!=null && query.length()>0);				
    } // end of main
}
