//STUDENT1_NAME: Ryan Singzon
//STUDENT1_ID: 260397455

//COMP 250: Introduction to Computer Science
//Assignment 4
//Fall 2013

import java.lang.Math.*;

class expressionTreeNodeSolution {
    private String value;
    private expressionTreeNodeSolution leftChild, rightChild, parent;
    
    expressionTreeNodeSolution() {
        value = null; 
        leftChild = rightChild = parent = null;
    }
    
    // Constructor
    /* Arguments: String s: Value to be stored in the node
                  expressionTreeNodeSolution l, r, p: the left child, right child, and parent of the node to created      
       Returns: the newly created expressionTreeNodeSolution               
    */
    expressionTreeNodeSolution(String s, expressionTreeNodeSolution l, expressionTreeNodeSolution r, expressionTreeNodeSolution p) {
        value = s; 
        leftChild = l; 
        rightChild = r;
        parent = p;
    }
    
    /* Basic access methods */
    String getValue() { return value; }

    expressionTreeNodeSolution getLeftChild() { return leftChild; }

    expressionTreeNodeSolution getRightChild() { return rightChild; }

    expressionTreeNodeSolution getParent() { return parent; }


    /* Basic setting methods */ 
    void setValue(String o) { value = o; }
    
    // sets the left child of this node to n
    void setLeftChild(expressionTreeNodeSolution n) { 
        leftChild = n; 
        n.parent = this; 
    }
    
    // sets the right child of this node to n
    void setRightChild(expressionTreeNodeSolution n) { 
        rightChild = n; 
        n.parent=this; 
    }
    

    // Returns the root of the tree describing the expression s
    // Watch out: it makes no validity checks whatsoever!
    expressionTreeNodeSolution(String s) {
        // check if s contains parentheses. If it doesn't, then it's a leaf
        if (s.indexOf("(")==-1) setValue(s);
        else {  // it's not a leaf

            /* break the string into three parts: the operator, the left operand,
               and the right operand. ***/
            setValue( s.substring( 0 , s.indexOf( "(" ) ) );
            // delimit the left operand 2008
            int left = s.indexOf("(")+1;
            int i = left;
            int parCount = 0;
            // find the comma separating the two operands
            while (parCount>=0 && !(s.charAt(i)==',' && parCount==0)) {
                if ( s.charAt(i) == '(' ) parCount++;
                if ( s.charAt(i) == ')' ) parCount--;
                i++;
            }
            int mid=i;
            if (parCount<0) mid--;

        // recursively build the left subtree
            setLeftChild(new expressionTreeNodeSolution(s.substring(left,mid)));
    
            if (parCount==0) {
                // it is a binary operator
                // find the end of the second operand.F13
                while ( ! (s.charAt(i) == ')' && parCount == 0 ) )  {
                    if ( s.charAt(i) == '(' ) parCount++;
                    if ( s.charAt(i) == ')' ) parCount--;
                    i++;
                }
                int right=i;
                setRightChild( new expressionTreeNodeSolution( s.substring( mid + 1, right)));
        }
    }
    }


    // Returns a copy of the subtree rooted at this node... 2013
    expressionTreeNodeSolution deepCopy() {
        expressionTreeNodeSolution n = new expressionTreeNodeSolution();
        n.setValue( getValue() );
        if ( getLeftChild()!=null ) n.setLeftChild( getLeftChild().deepCopy() );
        if ( getRightChild()!=null ) n.setRightChild( getRightChild().deepCopy() );
        return n;
    }
    
    // Returns a String describing the subtree rooted at a certain node.
    public String toString() {
        String ret = value;
        if ( getLeftChild() == null ) return ret;
        else ret = ret + "(" + getLeftChild().toString();
        if ( getRightChild() == null ) return ret + ")";
        else ret = ret + "," + getRightChild().toString();
        ret = ret + ")";
        return ret;
    } 


    // Returns the value of the expression rooted at a given node
    // when x has a certain value
    double evaluate(double x) {
    	double answer = 0;
    	double leftChild = 0;
    	double rightChild = 0; 
    	String operator = getValue().replaceAll("\\s+","");
    	
    	//When a node has no children, return its value
    	if(getLeftChild() == null && getRightChild() == null){
    		if(operator.equals("x")){
    			answer = x;
    		}
    		else{
    			try{
    				answer = Double.parseDouble(getValue()); 
    			} catch(NumberFormatException e){
    				System.out.println("Invalid number format ["+getValue()+"]");
    				System.exit(0);
    			}
    		}
    		return answer;
    	}
    	
    	//Traverse the nodes on the left side
    	if(this.getLeftChild() != null){
    		leftChild = this.getLeftChild().evaluate(x);
    	}
  
    	//Traverse the nodes on the right side
    	if(this.getRightChild() != null){
    		rightChild = this.getRightChild().evaluate(x);
    	}
    	
    	//Evaluate the expressions
    	//Removed whitespace from strings to allow for spaces in the expression
    	if(operator.equals("add")){
    		answer = leftChild + rightChild;
    	} else if(operator.equals("mult")){
    		answer = leftChild * rightChild;
    	} else if(operator.equals("minus")){
    		answer = leftChild - rightChild;
    	} else if(operator.equals("sin")){
    		answer = Math.sin(leftChild);
    	} else if(operator.equals("cos")){
    		answer = Math.cos(leftChild);
    	} else if(operator.equals("exp") || getValue().equals("e^")){
    		answer = Math.exp(leftChild);
    	} else{
    		System.out.println("Invalid expression ["+getValue()+"]");
    		answer = 0;
    		System.exit(0);
    	}
    	
    	return answer;
    }                
    
    

    /* returns the root of a new expression tree representing the derivative of the
       original expression */
    expressionTreeNodeSolution differentiate() {
    	
    	expressionTreeNodeSolution derivative = this.deepCopy();
    	String operator = getValue().replaceAll("\\s+","");
    	//Base case: node == x
    	if(getValue().replaceAll("\\s+","").equals("x")){
    		return new expressionTreeNodeSolution("1");
    	}
    
    	//Base case: Node is a constant 
    	else{
    	    try { 
    	    	Double.parseDouble(getValue());
    	    	return new expressionTreeNodeSolution("0");
    	    	
    	    } catch(NumberFormatException e) { 
    	    	//Node is not a number
    	    }
    	}
    	    	
    	//Set the value of each node to its derivative
    	if(getValue().replaceAll("\\s+","").equals("add")){
    		derivative.setValue("add("+getLeftChild().differentiate()+","+getRightChild().differentiate()+")");
    		
    	} else if(operator.equals("mult")){
    		derivative.setValue("add(mult("+getLeftChild()+","+getRightChild().differentiate()+"),mult("+getRightChild()+","+getLeftChild().differentiate()+"))sdfsdf");
    		
    	} else if(operator.equals("minus")){
    		derivative.setValue("minus("+getLeftChild().differentiate()+","+getRightChild().differentiate()+")");
    		
    	} else if(operator.equals("sin")){
    		derivative.setValue("mult(cos("+getLeftChild()+"),"+getLeftChild().differentiate()+")");
    		
    	} else if(operator.equals("cos")){
    		derivative.setValue("minus(0,mult(sin("+getLeftChild()+"),"+getLeftChild().differentiate()+"))");
    		
    	} else if(operator.equals("exp") || getValue().equals("e^")){
    		derivative.setValue("mult(exp("+getLeftChild()+"),"+getLeftChild().differentiate()+")");
    	} else{
    		System.out.println("Invalid expression ["+getValue()+"]");
    		System.exit(0);
    	}
        System.out.println("Derivative: "+derivative);
    	return derivative;
    }
        
    
    public static void main(String args[]) {
        expressionTreeNodeSolution e = new expressionTreeNodeSolution("add(x, cos(mult(2,x)))");
        System.out.println(e);
        System.out.println(e.evaluate(1));
        System.out.println(e.differentiate());
   
 }
}
