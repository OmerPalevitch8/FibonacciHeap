/** omer palevitch
 * 206840126
 *
 *
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
    private HeapNode min;
    private int size = 0;
    private int sum_marked = 0;
    private int sum_cuts = 0;
   /**
    * public boolean isEmpty()
    *
    * Returns true if and only if the heap is empty.
    *   
    */
    public boolean isEmpty()

    {
    	return min == null;
    }
		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    * The added key is assumed not to already belong to the heap.  
    * 
    * Returns the newly created node.
    */
    public HeapNode insert(int key)
    {    
    	return new HeapNode(key); // should be replaced by student code
    }

   /**
    * public void deleteMin()
    *
    * Deletes the node containing the minimum key.
    *
    */
    public void deleteMin()
    {
     	//if min is the only one
        if((min.child==null)&(min.right==null))
        {
            min = null;
            this.size--1;
            return;
        }
        else
        {
             this.size--1;

        }


     	
    }

   /**
    * public HeapNode findMin()
    *
    * Returns the node of the heap whose key is minimal, or null if the heap is empty.
    *
    */
    public HeapNode findMin()
    {
    	if(this.isEmpty())
        {
            return null;
        }
        else
        {
           return this.min;
        }
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Melds heap2 with the current heap.
    *
    */
    public void meld (FibonacciHeap heap2)
    {

    }

   /**
    * public int size()
    *
    * Returns the number of elements in the heap.
    *   
    */
    public int size()
    {

        return this.size;
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
    * (Note: The size of of the array depends on the maximum order of a tree.)  
    * 
    */
    public int[] countersRep()
    {
    	int[] arr = new int[100];
        return arr; //	 to be replaced by student code
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap.
	* It is assumed that x indeed belongs to the heap.
    *
    */
    public void delete(HeapNode x) 
    {    
    	return; // should be replaced by student code
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {    
    	x.key -= delta;
        if(x.parent==null)
        {
            if(x.key<min.key)
            {
                min = x;
            }
        }
        else
        {
            if(x.key<x.parent.key)
            {
                cascading(x);
            }
        }
    }

   /**
    * public int nonMarked() 
    *
    * This function returns the current number of non-marked items in the heap
    */
    public int nonMarked() 
    {    
        return this.size - this.sum_marked;
    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * 
    * In words: The potential equals to the number of trees in the heap
    * plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
        return -234; // should be replaced by student code
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the
    * run-time of the program. A link operation is the operation which gets as input two
    * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
    * tree which has larger value in its root under the other tree.
    */
    public static int totalLinks()
    {    
    	return -345; // should be replaced by student code
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the
    * run-time of the program. A cut operation is the operation which disconnects a subtree
    * from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return this.sum_cuts;
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k) 
    *
    * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
    * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
    *  
    * ###CRITICAL### : you are NOT allowed to change H. 
    */
    public static int[] kMin(FibonacciHeap H, int k)
    {    
        int[] arr = new int[100];
        return arr; // should be replaced by student code
    }

    public void cut(HeapNode node)
    {
        this.sum_cuts++;
        //needs to add condition for root
        HeapNode parent = node.parent;
        node.parent=null;
        if(node.marked)
        {
            node.marked = false;
            sum_marked--;

        }
        parent.rank--;
        if(node.right==null)
        {
            parent.child=null;
        }
        else
        {
            parent.child=node.right;
            node.left.right=node.right;
            node.right.left = node.left;
        }
    }
    public void cascading-cut(HeapNode node)
    {
        HeapNode parent = node.parent;
        cut(node)
        if(parent!=null)
        {
            if(!parent.marked)
            {
                parent.marked=true;
                this.sum_marked++;
            }
            else
            {
                cascading-cut(parent);
            }
        }

    }

    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in another file. 
    *  
    */
    public static class HeapNode
   {
       public int key;
       public HeapNode child;
       public HeapNode parent;
       public HeapNode left;
       public HeapNode right;
       public boolean marked;
       public int rank;

       public HeapNode getChild() {
           return this.child;
       }

       public HeapNode getParent() {
           return this.parent;
       }

       public HeapNode getLeft() {
           return this.left;
       }

       public HeapNode getRight() {
           return this.right;
       }

       public boolean isMarked() {
           return this.marked;
       }



    	public HeapNode(int key) {
    		this.key = key;
    	}


    	public int getKey() {
    		return this.key;
    	}
    }
}
