/** omer palevitch
 * 206840126
 **Elad Shaba
 **207909409
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
    private HeapNode min;
    private int size = 0;
    private int numOfTrees;
    private int sum_marked = 0;
    private int sum_cuts = 0;
    private int sum_links = 0;
    public HeapNode first;
    public HeapNode last;

    public FibonacciHeap() {
        this.numOfTrees = 0;
        this.sum_marked = 0;
        sum_cuts = 0;
        this.size = 0;
        sum_links = 0;
    }
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
       this.numOfTrees++;
       this.size++;
       HeapNode Node = new HeapNode(key);

       if (isEmpty()) {// If empty we'll make the new root it's minimum
           this.min = Node;
           last=Node;
           first=Node;
           Node.left=Node;
           Node.right=Node;
           return Node;
       }
       // if not empty
       Node.right = last;
       last.left=Node;
       last=Node;
       Node.left=first;
       first.right=Node;

       if (key < this.min.getKey()) {
           this.min = Node;
       }
       return Node;
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

    public int max_rank()
    {
        int max = min.rank;
        HeapNode node = min.right;
        while(node!=min)
        {
            if(node.rank>max)
            {
                max = node.rank;
            }
            node = node.right;
        }
        return max;
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
    	int[] arr = new int[max_rank() + 1];
        if(isEmpty())
        {
            return arr;
        }
        arr[min.rank]++;
        HeapNode node = min.right;
        while(node!=min)
        {
            arr[node.rank]++;
            node = node.right;
        }
        return arr;


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
       // thought about using Integer.Min_value?
       int minKeyVal = min.getKey();
       int delta = x.getKey() - minKeyVal + 1;
       decreaseKey(x, delta);
       deleteMin();
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


    // TODO: 10/01/2023 decide if to delete this function
    public int count_trees()
    {
        if(isEmpty())
        {
            return 0;
        }
        else
        {
            int num_of_trees = 1;
            HeapNode node = min.right;
            while(node!=min)
            {
                num_of_trees++;
                node = node.right;
            }
            return num_of_trees;
        }
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
       return this.numOfTrees+2*this.sum_marked;
   }

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
    	return this.sum_links;
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
         int[] arr = new int[k];//changed from 100 to k?
         if(k==0 || H.size()==0) {
             int[]list= new int[0];
         }
         HeapNode curr=H.min;
         FibonacciHeap kminHeap=new FibonacciHeap();
         kminHeap.insert(curr.getKey());
         kminHeap.first.location=curr;
         for(int i=0;i<k;i++) {
             arr[i]=kminHeap.min.getKey();
             if(kminHeap.min.location.child!=null) {
                 HeapNode Child=kminHeap.min.location.child;
                 HeapNode Flag=null;
                 while(kminHeap.min.location.child!=Flag) {
                     kminHeap.insert(Child.getKey());
                     kminHeap.first.location=Child;
                     Flag=Child.right;
                     Child=Child.right;
                 }
             }
             kminHeap.deleteMin();
         }
         return arr;
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
       public boolean marked = false;
       public int rank=0;
       public HeapNode first;
       public HeapNode last;
       public HeapNode location;

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
