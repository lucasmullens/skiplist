public class SkipList<T extends Comparable<? super T>> {
	SkipListNode<T> head;
	int height = 1;
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
	}
	/**
	 * Removes an item from the SkipList
	 * @param item The item to remove
	 * @return Whether the item was removed or not
	 */
	public boolean remove(T item){
		SkipListNode<T> node = findNodeForValue(item);
		if (node.getValue() == null || !node.getValue().equals(item)){
			return false;
		}
		node.getLeft().setRight(node.getRight());
		if (node.getRight() != null)
			node.getRight().setLeft(node.getLeft());
		return true;
	}
	public boolean contains(T item){
		SkipListNode<T> node = findNodeForValue(item);
		if (node.getValue() == null){
			return false;
		} else {
			return (node.getValue().equals(item));
		}
	}
	/**
	 * Finds the node of a value, or returns the node directly left of where that value would go
	 * @param item Item to find
	 * @return Node of value "item", or the node to the left of where that should go
	 */
	public void clear(){
		head = new SkipListNode<T>(null);
	}
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
}
