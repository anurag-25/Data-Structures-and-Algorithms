package RedBlack;

import Util.RBNodeInterface;

import java.util.ArrayList;
import java.util.List;

public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {
	
	T key;
	List <E> listOfPersons ;
	E object;

	public RedBlackNode<T, E> leftChild;
	public RedBlackNode<T, E> rightChild;
	public RedBlackNode<T, E> parent;
	public int colour;
	
	public RedBlackNode() {
		this.key = null;
		this.listOfPersons = new ArrayList <E>() ;
		this.colour = 0;
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
	}
	
	
    @Override
    public E getValue() {
        return object;
    }

    @Override
    public List<E> getValues() {
        return listOfPersons;
    }
    
    public String toString() {
    	return key.toString();
    }
}