
public class SinglyLinkedList<E> {
	
	public class Node {
		int i;
		E value;
		Node next = null; 
		
		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		public Node(int i, E value) {
			this.i = i;
			this.value = value;
			
		}
		
		
	}
	
	Node head;
	Node tail;
	int size = 0 ;
	public SinglyLinkedList() {
		head = new Node(-1, null);
		
	}
	
	
	public boolean add(E e) {
		if ( size == 0 ) {
			
			Node next = new Node(size, e);
			head.setNext(next);
			tail = next;
			size++;
		}
		else {
			Node next = new Node(size, e);
			size++;
			tail.setNext(next);
			tail = next;
		}
		return true;
		
	}
	public E get(int index) {
		if(index >= size) {
			throw new IllegalArgumentException();
		}
		Node interator = head;
		while (while )
	}
	
	
}
