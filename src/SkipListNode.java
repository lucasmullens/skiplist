
public class SkipListNode<T> {
	private SkipListNode<T> up;
	private SkipListNode<T> down;
	private SkipListNode<T> left;
	private SkipListNode<T> right;
	private T value;
	
	public SkipListNode(T value){
		this.setValue(value);
	}
	public SkipListNode<T> getUp() {
		return up;
	}
	public void setUp(SkipListNode<T> up) {
		this.up = up;
	}
	public SkipListNode<T> getDown() {
		return down;
	}
	public void setDown(SkipListNode<T> down) {
		this.down = down;
	}
	public SkipListNode<T> getLeft() {
		return left;
	}
	public void setLeft(SkipListNode<T> left) {
		this.left = left;
	}
	public SkipListNode<T> getRight() {
		return right;
	}
	public void setRight(SkipListNode<T> right) {
		this.right = right;
	}
	public T getValue() {
		 //used for timing/debugging
		 SkipList.readCount++;
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
}
