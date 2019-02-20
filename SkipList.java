import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;




public class SkipList<E> implements List<E> {


	private static class Node<E> { 
		E item;
		Node<E> forward;
		Node<E> down;
		int distance;
		Node(E element){
			this.item = element;
		}
	    
	}	
	

	private Node<E> head;
	private int listHeight = 1;
	private int size = 0;
	private Random r;
	private final double p = 0.5;
	private final int MAX_LEVEL = 20;
	

	
	
	public SkipList() {
		head = new Node<> (null);
		head.distance = 1;
		head.forward = null;
		r = new Random();
	}
	
	    /**
     * Appends the specified element to the end of this list (optional
     * operation).
     *
     * <p>Lists that support this operation may place limitations on what
     * elements may be added to this list.  In particular, some
     * lists will refuse to add null elements, and others will impose
     * restrictions on the type of elements that may be added.  List
     * classes should clearly specify in their documentation any restrictions
     * on what elements may be added.
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *         prevents it from being added to this list
     */
	@Override
	public boolean add(E arg0) {
		add(this.size(), arg0);
		return true;
	}
	private int randomLevel() {
		int lvl = 1;
		while (r.nextDouble() < p && lvl < MAX_LEVEL)
			lvl = lvl +1;
		return lvl;
	}

	    /**
     * Inserts the specified element at the specified position in this list
     * (optional operation).  Shifts the element currently at that position
     * (if any) and any subsequent elements to the right (adds one to their
     * indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws NullPointerException if the specified element is null and
     *         this list does not permit null elements
     * @throws IllegalArgumentException if some property of the specified
     *         element prevents it from being added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
	@Override
	public void add(int index, E e) {
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index +", Size: " + size);
		}
		
		int lvl = randomLevel();
		if (lvl > listHeight) {
			for(int i = lvl; i > listHeight; i--) {
				Node<E> node = new Node<>(null);
				node.down = head;
				node.forward = null;
				node.distance = size + 1;
				head = node;
			}
			listHeight = lvl;
		}
		int pos = 0;
		int currentLevel = listHeight;
		Node<E> lastInserted = null;
		for(Node<E> x = head; x != null; x = x.down, currentLevel--) {
			
			while (pos + x.distance <= index) {
				pos = pos + x.distance;
				x = x.forward;

			}
			if(currentLevel > lvl) {
				x.distance = x.distance + 1;
			}
			else {
				Node<E> y = new Node<>(e);
				Node<E> z = x.forward;
				y.forward = z;
				x.forward = y;
				
				y.distance = pos + x.distance - index;
				x.distance = index + 1 - pos;
				if(lastInserted != null) {
					lastInserted.down = y;
				}
				lastInserted = y;
			}
		}
		size++;
	}
	
    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
	@Override
	public E get(int index) {
		int pos = 0;
		if(index >= size) {
			throw new IndexOutOfBoundsException("index not within List");
		}
		
		for(Node<E> x = head; x != null; x = x.down) {
			
			while (pos + x.distance <= index) {
				pos = pos + x.distance;
				x = x.forward;				
			}
			if(pos == index) {
				Node<E> ret = null;
				for(Node<E> y = x; y!= null; y = y.down)
					ret = y;
				ret = ret.forward;
				return ret.item;
			}
		}
		return null;
		
	}
	    /**
     * Returns the number of elements in this list.  If this list contains
     * more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of elements in this list
     */
	@Override
	public int size() {
		return size;
	}

	private String toString1() {
		StringBuffer sb = new StringBuffer();
        sb.append("SkipList of height: " + listHeight + " Size: "+ size +"\n");
        for (Node<E> node = head; node != null; node = node.down){
                for (Node<E> node2 = node; node2 != null; node2 = node2.forward)
                        sb.append("(" + node2.item + ")-" + node2.distance + "- ");
                sb.append("\n");
        }
        return sb.toString();
		
	}
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("[");
		Node<E> iterator = null;
		for(Node<E> x = head; x != null; x = x.down)
			iterator = x;
		int i = 0;
		iterator = iterator.forward;
		for(Node<E> x = iterator; x != null; x = x.forward, i++) {

			if(i == size - 1) {
				ret.append(x.item + "]");
			}
			else {
				ret.append(x.item + ", ");
			}
		}
		return ret.toString();
	}
	
	
	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public int indexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lastIndexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E remove(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E set(int arg0, E arg1) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<E> subList(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

