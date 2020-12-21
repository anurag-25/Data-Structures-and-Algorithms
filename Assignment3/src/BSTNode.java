public class BSTNode <K extends Comparable <K>, T> {
	K key;
    BSTNode<K, T> left;
    BSTNode<K, T> right;
    T obj;
    
    public BSTNode(K key, T obj) {
		this.key = key;
		this.obj= obj;
		this.left = null;;
		this.right= null;
	}
    public BSTNode(){
    	this.key= null;
    	obj=null;
		this.left = null;;
		this.right= null;
    }

}
