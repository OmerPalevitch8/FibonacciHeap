import java.util.Arrays;

/** omer palevitch
 * 206840126
 **Elad Shaba
 **207909409
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
    private HeapNode min;
    private int size = 0;
    private int numOfTrees;
    private int sum_marked = 0;
    private static int sum_cuts = 0;
    private static int sum_links = 0;
    public HeapNode first;
    public HeapNode last;

    public FibonacciHeap() {
        this.numOfTrees = 0;
        this.sum_marked = 0;
        sum_cuts = 0;
        this.size = 0;
        sum_links = 0;
    }

    //O(1)
    public HeapNode getFirst(){ return this.first;}
    //O(1)
    public HeapNode getLast(){ return this.last;}

    //O(1)
    /**
     * public boolean isEmpty()
     * <p>
     * Returns true if and only if the heap is empty.
     */
    //O(1)
    public boolean isEmpty() {
        return min == null;
    }

    /**
     * public HeapNode insert(int key)
     * <p>
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * The added key is assumed not to already belong to the heap.
     * <p>
     * Returns the newly created node.
     */
    public HeapNode insert(int key) {
        this.numOfTrees++;
        this.size++;
        HeapNode Node = new HeapNode(key);

        if (isEmpty()) {// If empty we'll make the new root it's minimum
            this.min = Node;
            last = Node;
            first = Node;
            Node.left = Node;
            Node.right = Node;
            return Node;
        }
        // if not empty
        Node.right = first;
        first.left = Node;
        Node.left = last;
        first=Node;

        if (key < this.min.getKey()) {
            this.min = Node;
        }
        return Node;
    }
    //used for adding node after cut
    public void insert_node(HeapNode Node)
    {
        this.numOfTrees++;
        if (isEmpty()) {// If empty we'll make the new root it's minimum
            this.min = Node;
            last = Node;
            first = Node;
            Node.left = Node;
            Node.right = Node;
            return;
        }
        Node.right = first;
        first.left = Node;
        Node.left = last;
        first=Node;

        if (Node.key < this.min.getKey()) {
            this.min = Node;
        }
        return;
    }


    //O(n) worst case, O(logn) amortiez
    /**
     * public void deleteMin()
     * <p>
     * Deletes the node containing the minimum key.
     */
    public void deleteMin() {
        //if min is the only one
        if ((min.child == null) & (this.numOfTrees == 1)) {
            min = null;
            this.size--;
            this.numOfTrees--;
            this.first = null;
            this.last = null;
            sum_marked = 0;
            return;
        }
        this.size--;
        if (this.numOfTrees == 1) {
            HeapNode tempMin = min;
            HeapNode tempMinRight = min.right;
            HeapNode tempMinLeft = min.left;
            this.first = tempMin.child;
            HeapNode curr = this.first;
            HeapNode min_node = this.first;
            int cnt = 0;
            while (curr.right != this.first) {
                curr.parent = null;
                if (curr.key < min_node.key) {
                    min_node = curr;
                }
                curr = curr.right;
                cnt++;
            }
            if (curr.key < min_node.key) {
                min_node = curr;
            }
            this.min = min_node;
            curr.parent = null;
            this.numOfTrees = cnt + 1;
            consolidate();
            return;
        }
        if (min.child == null) {
            min.right.left = min.left;
            min.left.right = min.right;
            numOfTrees--;
        } else {
            HeapNode child = min.child;
            min.left.right = child;
            child.left.right = min.right;
            min.right.left = child.left;
            child.left = min.left;
        }
        if (this.first == min) {
            if (this.first.child != null) {
                this.first = this.min.child;
            } else {
                this.first = this.first.left;
            }
        }
        HeapNode curr = this.first;
        HeapNode min_node = this.first;
        boolean first = false;
        while (curr!=this.first||first==false) {
            first=true;
            if (curr.marked) {
                curr.marked=false;
                this.sum_marked--;
            }
            if(curr.key<min_node.key)
            {
                min_node = curr;
            }
            curr.parent=null;
            curr=curr.right;
        }
        this.min = min_node;
        consolidate();
        return;
    }





    /**
     * public HeapNode findMin()
     * <p>
     * Returns the node of the heap whose key is minimal, or null if the heap is empty.
     */
    public HeapNode findMin() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.min;
        }
    }
    //O(1)
    /**
     * public void meld (FibonacciHeap heap2)
     * <p>
     * Melds heap2 with the current heap.
     */

    public void meld(FibonacciHeap heap2) {
        if(heap2.isEmpty())
        {
            return;
        }
        if(this.isEmpty())
        {
            this.first = heap2.first;
            this.last = heap2.last;
            this.min = heap2.min;
            this.sum_marked = heap2.sum_marked;
            this.size = heap2.size;
            this.numOfTrees = heap2.numOfTrees;
            return;
        }
        this.first.right = heap2.last;
        heap2.last.left = this.first;
        this.last.left = heap2.first;
        heap2.first.right = this.last;
        this.first = heap2.first;

        if (this.min.key > heap2.min.key) {
            this.min = heap2.min;
        }

        this.size += heap2.size;
        this.numOfTrees += heap2.numOfTrees;
        this.sum_marked += heap2.sum_marked;
        this.sum_links += heap2.sum_links;

    }

    /**
     * public int size()
     * <p>
     * Returns the number of elements in the heap.
     */
    public int size() {

        return this.size;
    }
    //O(n)
    /**
     * public int max_rank()
     * <p>
     * Returns the maximum rank in the heap
     */
    public int max_rank(){
        int max = min.rank;
        HeapNode node = min.right;
        while (node != min) {
            if (node.rank > max) {
                max = node.rank;
            }
            node = node.right;
        }
        return max;
    }

    //O(n)
    /**
     * public int[] countersRep()
     * <p>
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * (Note: The size of the array depends on the maximum order of a tree.)
     */
    public int[] countersRep() {
        if (this.isEmpty()) {
            return new int[0];
        }
        int[] arr = new int[this.max_rank() + 1];
        arr[min.rank]++;
        HeapNode node = min.right;
        while (node != min) {
            arr[node.rank]++;
            node = node.right;
        }
        return arr;


    }

    /**
     * public void delete(HeapNode x)
     * <p>
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     */
    public void delete(HeapNode x) {
        // thought about using Integer.Min_value?
        int minKeyVal = min.getKey();
        int delta = x.getKey() - minKeyVal + 1;
        decreaseKey(x, delta);
        deleteMin();
    }

    //O(n) worst case, O(1) amortiez
    /**
     * public void decreaseKey(HeapNode x, int delta)
     * <p>
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {
        if(this.isEmpty())
        {
            return;
        }
        x.key -= delta;
        if (x.parent == null) {
            if (x.key < min.key) {
                min = x;
            }
        } else {
            if (x.key < x.parent.key) {
                this.cascadingCut(x);
            }
        }
    }

    /**
     * public int nonMarked()
     * <p>
     * This function returns the current number of non-marked items in the heap
     */
    public int nonMarked() {
        return this.size - this.sum_marked;
    }


    //O(1)
    /**
     * public int potential()
     * <p>
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     * <p>
     * In words: The potential equals to the number of trees in the heap
     * plus twice the number of marked nodes in the heap.
     */
    public int potential() {
        return this.numOfTrees + 2 * this.sum_marked;
    }


    /**
     * public static int totalLinks()
     * <p>
     * This static function returns the total number of link operations made during the
     * run-time of the program. A link operation is the operation which gets as input two
     * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
     * tree which has larger value in its root under the other tree.
     */
    public static int totalLinks() {
        return sum_links;
    }

    //O(1)
    /**
     * public static int totalCuts()
     * <p>
     * This static function returns the total number of cut operations made during the
     * run-time of the program. A cut operation is the operation which disconnects a subtree
     * from its parent (during decreaseKey/delete methods).
     */
    public static int totalCuts() {
        return sum_cuts;
    }

    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     * <p>
     * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
     * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
     * <p>
     * ###CRITICAL### : you are NOT allowed to change H.
     */
    public static int[] kMin(FibonacciHeap H, int k) {

        if (k == 0 || H.size() == 0) {
            return new int[0];
        }
        if(k==1||H.size()==1) {
            int[]array=new int[1];
            array[0]=H.min.getKey();
            return array;
        }
        int[] arr = new int[k];//changed from 100 to k?
        HeapNode curr = H.min;
        
        FibonacciHeap kminHeap = new FibonacciHeap();
        HeapNode Inserted = kminHeap.insert(curr.getKey());
        Inserted.location=curr;
        for(int i=0;i<k;i++) {
        	arr[i]=curr.key;
        	kminHeap.deleteMin();
        	if(curr.child!=null) {
        		HeapNode child=curr.child;
        		Inserted=kminHeap.insert(child.key);
        		Inserted.location=child;
        		HeapNode flag=child.right;
        		while(flag!=child) {
        			Inserted=kminHeap.insert(flag.key);
        			Inserted.location=flag;
        			flag=flag.right;
        		}
        	}
        	if(kminHeap.isEmpty()) {
        		break;
        	}
        	curr=kminHeap.findMin().location;
        }
        return arr;
    }
    //O(1)
    public void cut(HeapNode node) {
        if(node.parent==null){
            return;
        }
        this.sum_cuts++;
        HeapNode parent = node.parent;
        parent.rank--;
        if(node.marked)
        {
            node.marked=false;
            sum_marked--;
        }
        if (node.right == node) {
            parent.child = null;
        }
        else
        {
            if(parent.child.key==node.key)
            {
                parent.child = node.right;
            }
            node.left.right = node.right;
            node.right.left=node.left;
        }
        node.parent = null;
        this.insert_node(node);
    }

    //O(n) worst case, O(1) amortiez
    public void cascadingCut(HeapNode node) {
        if(node.parent==null){
            return;
        }
        if(node.parent.marked) {
            while (node.parent != null && node.parent.marked) {
                HeapNode parent = node.parent;
                cut(node);
                node = parent;
            }

            if (node.parent.parent != null) {
                node.parent.marked = true;
                sum_marked++;
            }
        }
        else {
            if(node.parent.parent!=null)
            {
                node.parent.marked = true;
                sum_marked++;
                cut(node);
            }
        }
    }
    //O(n) worst case, O(logn) amortiez
    public void consolidate() {
        if(this.size==0)
        {

            return;
        }
        if(this.size==1){
            this.last = min;
            return;
        }
        HeapNode[] box = new HeapNode[this.size+1];
        HeapNode curr = this.first;
        int start = 0;
        boolean not_first = false;
        while (curr != this.first || not_first==false) {
            not_first=true;
            HeapNode temp_left = curr.left;
            curr.right = null;
            curr.left = null;
            HeapNode temp_curr = curr;
            int temp_rank = curr.rank;
            while (box[temp_rank] != null) {
                temp_curr = connect_same(temp_curr, box[temp_rank]);
                box[temp_rank]= null;
                temp_rank = temp_curr.rank;
            }
            box[temp_rank] = temp_curr;
            curr = temp_left;

        }
        for(int i =0;i<box.length;i++)
        {
           if(box[i]!=null) {
               this.first = box[i];
               this.first.right = this.first;
               this.first.left=this.first;
               start = i;
               break;
           }
        }
        HeapNode lastConnect = this.first;
        HeapNode nextConnect = null;
        for (int i = start+1;i<box.length;i++) {
            if (box[i]!=null) {
                nextConnect=box[i];
                nextConnect.right = this.first;
                nextConnect.left=lastConnect;
                this.first.left=nextConnect;
                lastConnect.right = nextConnect;
                lastConnect=nextConnect;
            }
        }
        if(nextConnect!=null)
        {
            this.last =nextConnect;

        }
        else {
            this.last=this.first;
        }
        HeapNode new_min = new HeapNode(Integer.MAX_VALUE);
        this.numOfTrees = 0;
        for(int i=0;i<box.length;i++) {
            if((box[i]!=null)&&(box[i].key<new_min.key)) {
                new_min = box[i];
            }
            if(box[i]!=null)
            {
                this.numOfTrees++;
            }
        }
        this.min=new_min;
    }
    //O(1)
    public HeapNode connect_same(HeapNode node1, HeapNode node2) {
        sum_links++;
        if(node1.child==null && node2.child==null)
        {
            if(node1.key<node2.key)
            {

                node1.child=node2;
                node2.parent = node1;
                node1.rank=1;
                node2.right = node2;
                node2.left=node2;
                return node1;
            }
            else {
                node2.child=node1;
                node1.parent = node2;
                node2.rank=1;
                node1.right = node1;
                node1.left=node1;
                return node2;
            }
        }
        HeapNode root;
        HeapNode sibling;
        if(node1.key>node2.key)
        {
            root=node2;
            sibling = node1;
        }
        else
        {
            root = node1;
            sibling=node2;
        }

        if(root.child.right==root.child)
        {
            root.child.left = sibling;
            sibling.right=root.child;
            sibling.left=root.child;
            root.child.right=sibling;
            root.child = sibling;
            root.child.parent=root;
        }
        else
        {
            HeapNode temp = root.child;
            root.child=sibling;
            root.child.left=temp.left;
            temp.left.right=root.child;
            temp.left=root.child;
            root.child.right=temp;
            sibling.parent = root;
        }
        root.rank++;
        return root;
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
        public HeapNode location;
        
        public int getRank() {
        	return this.rank;
        }

        //O(1)
        public HeapNode getChild() {
            return this.child;
        }
        //O(1)
        public HeapNode getParent() {
            return this.parent;
        }
        //O(1)
        public HeapNode getPrev() {
            return this.left;
        }
        //O(1)
        public HeapNode getNext() {
            return this.right;
        }
        //O(1)
        public boolean getMarked() {
            return this.marked;
        }
        //O(1)
        public HeapNode(int key) {
            this.key = key;
        }
        //O(1)
        public int getKey() {
            return this.key;
        }
    }

    public static void main(String[] args) {
        FibonacciHeap heap1 = new FibonacciHeap();
        HeapNode a = null;
        int[] val=new int[] {7,6};
        for (int i:val) {
        	a =heap1.insert(i);
        	
        }
        heap1.deleteMin();
        System.out.println(heap1.sum_marked);
        
    }
}



