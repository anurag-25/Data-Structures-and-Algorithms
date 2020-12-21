package PriorityQueue;

import java.util.ArrayList;
import java.util.List;

public class PQNode<T extends Comparable> {
	public T element;
	ArrayList<T> FIFOlist;
	public PQNode (T element) {
		this.element = element;
		FIFOlist = new ArrayList<T> ();
	}
	
	public int compareTo(PQNode<T> pqNode)	{
		if (element.compareTo(pqNode.element) <0) return -1;
		else if (element.compareTo(pqNode.element) >0) return 1;
		else return 0;
	}
	public String toString () {
		return element.toString();
	}
	
	public ArrayList<T> getFIFOList (){
		return FIFOlist;
	}
	public T getElement (){
		return element;
	}
}
