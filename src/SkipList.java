public class SkipList<T extends Comparable<? super T>> {
	private SkipListNode<T> head;
//	private int height = 0;//zero is the bottom row's height
	public static int readCount = 0;
	public SkipList(){
		//create 2 default nodes
		head = new SkipListNode<T>(null);
	}
	public void add(T item){
		SkipListNode<T> newNode = new SkipListNode<T>(item);
		SkipListNode<T> nodeToLeft = findNodeForValue(item);
		newNode.setRight(nodeToLeft.getRight());
		newNode.setLeft(nodeToLeft);
		nodeToLeft.setRight(newNode);
		SkipListNode<T> lastNode = newNode;
		while (coinFlip()){
			//create new node above
			SkipListNode<T> stackedNode = new SkipListNode<T>(item);
			//link vertically
			stackedNode.setDown(lastNode);
			lastNode.setUp(stackedNode);
			//link horizontally...
			//get node to the left (ntl) by traversing the data structure
			SkipListNode<T> ntl = getNodeToLeftOf(stackedNode);
			SkipListNode<T> ntr = ntl.getRight();
			stackedNode.setLeft(ntl);
			stackedNode.setRight(ntr);
			//make other nodes point to this one
			ntl.setRight(stackedNode);
			if (ntr != null){
				ntr.setLeft(stackedNode);
			}
			lastNode = stackedNode;
		}
	}
	/**
	 * Finds a node to the left of a node when there aren't horizontal links.
	 * Algorithm: 
	 * Go down 1, travel left checking if any node has a node above it.
	 * if it does, that node is to the left of our node.
	 * @param node The node we want to find the node to the left of
	 * @return the node to the left of our node
	 */
	private SkipListNode<T> getNodeToLeftOf(SkipListNode<T> node) {
		//go one down
		SkipListNode<T> current = node.getDown();
		//now loop going to the left of the structure, trying to find a node at this level
		while (current.getLeft() != null){//loop all the way to the left
			current = current.getLeft();
			if (current.getUp() != null){
				return current.getUp();
			}
		}
		//if it makes it this far, it has reached the end on the left.
		//we need to create a header node for it.
		SkipListNode<T> newHead = new SkipListNode<T>(null);
		head.setUp(newHead);
		newHead.setDown(head);
		head = newHead;
		return head;
	}
	/**
	 * Removes an item from the SkipList
	 * @param item The item to remove
	 * @return Whether the item was removed or not
	 */
	public boolean remove(T item){
		SkipListNode<T> node = findNodeForValue(item);
		//if the node found holds null or isn't equal to the item, do nothing
		//item not found.
		if (node.getValue() == null || !node.getValue().equals(item)){
			return false;
		}
		boolean top = false;
		while (!top){			
			node.getLeft().setRight(node.getRight());
			if (node.getRight() != null)
				node.getRight().setLeft(node.getLeft());
			if (node.getUp() != null){
				node = node.getUp();//delete all nodes going upward
			} else {
				top = true; //exit
			}
		}
		return true;
	}
	/**
	 * Finds if a value exists in our skip list
	 * @param item the item to search for
	 * @return whether it exists
	 */
	public boolean contains(T item){
		SkipListNode<T> node = findNodeForValue(item);
		if (node.getValue() == null){
			return false;
		} else {
			return (node.getValue().equals(item));
		}
	}

	/**
	 * Clears the list
	 */
	public void clear(){
		head = new SkipListNode<T>(null);
	}
	/**
	 * Finds the node of a value, or returns the node directly left of where that value would go
	 * @param item Item to find
	 * @return Node of value "item", or the node to the left of where that should go
	 */
	private SkipListNode<T> findNodeForValue(T item){
		SkipListNode<T> pointer = head;
		boolean done = false;
		while (!done){
			//if the node to the right doesn't exist, or this item is less than it, move down if possible and continue.
			if (pointer.getRight() == null || pointer.getRight().getValue().compareTo(item) > 0){
				if (pointer.getDown() != null){
					pointer = pointer.getDown();
				} else {
					done = true; //can't move down, insert to the right of this node.
				}
			} else {
				pointer = pointer.getRight();
			}
		}
		return pointer;
	}
	/**
	 * Flips a coin. Returns true 50% of the time.
	 * Used to determine whether to add a node above.
	 * @return whether to add a node above
	 */
	private boolean coinFlip(){
		return (Math.random()>.5);
	}
	/**
	 * Runs a the contains() function and keeps track of the number of reads
	 * Used to see if it is truly O(log(n))
	 * @param item item to test
	 * @return number of reads
	 */
	public int debug(T item) {
		SkipList.readCount = 0;
		contains(item);
		return SkipList.readCount;
	}
}
