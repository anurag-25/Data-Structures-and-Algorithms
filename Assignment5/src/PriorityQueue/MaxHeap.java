package PriorityQueue;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap<T extends Comparable> implements PriorityQueueInterface<T> {
	
	private List<PQNode> maxHeap;
	private int currentIndex;
	private int size;
	
	public MaxHeap(){
		size=-1;
		this.currentIndex = -1;
		maxHeap = new ArrayList <PQNode> ();
	}

	@Override
	public void insert(T element) {
		if ( maxHeap.size() == 0 ) {
			currentIndex++;
			size++;
			maxHeap.add(new PQNode(element));
			return;
		}
		for (int i=0; i<maxHeap.size(); i++)	{
			if (maxHeap.get(i).element.compareTo(element ) ==0) {
				maxHeap.get(i).FIFOlist.add(element);
				size++;
				return;
			}
		}
		size++;
		currentIndex++;
		maxHeap.add(new PQNode(element) );
		bubbleUp(currentIndex);
	}

	public void bubbleUp(int current_Index) {
		int parent = (current_Index - 1)/2;
		if ( maxHeap.get(current_Index).compareTo(maxHeap.get(parent) )> 0 ){
			PQNode<T> currentElement = maxHeap.get(current_Index);
			PQNode<T> parentElement = maxHeap.get(parent);
			maxHeap.set(parent, currentElement);
			maxHeap.set(current_Index, parentElement);
			bubbleUp(parent);
		}
	}

	@Override
	public T extractMax() {
		if (maxHeap.size() == 0) return null;
		PQNode<T> maxElement = maxHeap.get(0);
		
		if (maxElement.FIFOlist.size()>0) {
			T elementToBeRemoved = maxElement.element;
			maxElement.element=maxElement.FIFOlist.remove(0);
			size--;
			return elementToBeRemoved;
		}
		
		int indexIfSamePriority = currentIndex;
		PQNode<T> lastElement = maxHeap.get(currentIndex);
		
		maxHeap.set(0, lastElement);
		maxHeap.remove(indexIfSamePriority);

		currentIndex -=1;
		size--;
		bubbleDown(0);
		return maxElement.element;
	}
	
	public void bubbleDown(int parent) {
		int leftChildIndex = 2*parent +1;
		int rightChildIndex = 2*parent +2;
		
		if (maxHeap.size()==0) return;
		PQNode<T> parentElement = maxHeap.get(parent);
		PQNode<T> largestelement = parentElement;
		int largestIndex = parent;
		
		if (currentIndex >= leftChildIndex )	{
			PQNode<T> leftChild = maxHeap.get(leftChildIndex);
			if (maxHeap.get(parent).compareTo(leftChild) < 0 )	{
				largestIndex = leftChildIndex;
				largestelement = leftChild;
			}
		}
		
		if (currentIndex >= rightChildIndex )	{
			PQNode<T> rightChild = maxHeap.get(rightChildIndex);
			if (maxHeap.get(largestIndex).compareTo(rightChild) < 0 )	{
				largestIndex = rightChildIndex;
				largestelement = rightChild;
			}
		}
		maxHeap.set(parent, largestelement);
		maxHeap.set(largestIndex, parentElement);
		if (parent == largestIndex) return;
		bubbleDown (largestIndex);
		return;
	}

	
	public int size(){
		return size+1;
	}
	public T search(String str)	{
		for (int i=0; i<maxHeap.size(); i++){
			if (maxHeap.get(i).toString().compareTo(str) == 0) return (T) maxHeap.get(i).element;
			for (int j=0; j<maxHeap.get(i).FIFOlist.size(); j++ ) {
				if (maxHeap.get(i).FIFOlist.get(j).toString().compareTo(str) == 0) return (T) maxHeap.get(i).FIFOlist.get(j);
			}
		}
		return null;
	}
	public int searchIndex (String str)	{
		for (int i=0; i<maxHeap.size(); i++){
			if (maxHeap.get(i).toString().compareTo(str) == 0) return i;
			for (int j=0; j<maxHeap.get(i).FIFOlist.size(); j++ ) {
				if (maxHeap.get(i).FIFOlist.get(j).toString().compareTo(str) == 0) return i;
			}
		}
		return 0;
	}
	
	public PQNode get (int i){
		return maxHeap.get(i);
	}
	public int numberOfDifferentPriorities()	{
		return maxHeap.size();
	}
}