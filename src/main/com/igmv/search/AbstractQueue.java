package main.com.igmv.search;

import java.util.LinkedList;
import java.util.List;

/**
 * Artificial Intelligence A Modern Approach (2nd Edition): page 71.
 * 
 * The operations on a queue are as follows: 
 * MAKE-QUEUE(element,...) creates a queue with the given element(s). 
 * EMPTY?(queue) returns true only if there are no more elements in the queue. 
 * FIRST(queue) returns the first element of the queue. 
 * REMOVE-FIRST(queue) returns 
 * FIRST(queue) and removes it from the queue. 
 * INSERT(element, queue) inserts an element into the queue and returns
 * the resulting queue. (We will see that different types of queues insert
 * elements in different orders.) 
 * INSERT-ALL(elements, queue) inserts a set of elements into the queue and returns the resulting queue.
 */

public class AbstractQueue<T> implements Queue<T> {
	protected LinkedList<T> linkedList;

	public AbstractQueue() {
		linkedList = new LinkedList<T>();
	}

	public void addToFront(T n) {
		linkedList.addFirst(n);
	}

	public void addToBack(T n) {
		linkedList.addLast(n);
	}

	public void addToFront(List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			addToFront(list.get(list.size() - 1 - i));
		}
	}

	public void addToBack(List<T> list) {
		for (int i = 0; i < list.size(); i++) {
			addToBack(list.get(i));
		}
	}

	public T removeFirst() {
		return (linkedList.removeFirst());
	}

	public T removeLast() {
		return (linkedList.removeLast());
	}

	public T getFirst() {
		return (linkedList.getFirst());
	}

	public T getLast() {
		return (linkedList.getLast());
	}

	public boolean isEmpty() {
		return linkedList.isEmpty();
	}

	public int size() {
		return linkedList.size();
	}

	public List<T> asList() {
		return linkedList;
	}

	public void add(T anItem) {
		throw new RuntimeException("must be implemented by subclasses");
	}

	public void add(List<T> items) {
		throw new RuntimeException("must be implemented by subclasses");
	}

	public T remove() {
		throw new RuntimeException("must be implemented by subclasses");
	}

	public T get() {
		throw new RuntimeException("must be implemented by subclasses");
	}

}