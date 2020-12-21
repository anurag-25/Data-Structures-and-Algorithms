package RedBlack;


public class RBTree<T extends Comparable, E> implements RBTreeInterface<T, E>  {
	
	private RedBlackNode<T, E> root = null; 
	public static final int black =0;
	public static final int red = 1;
	
    @Override
    public void insert(T key, E value) {
    	
    	if (search (key).key != null && search(key).listOfPersons!=null ) {
    		RedBlackNode<T, E> node = search (key);
    		node.listOfPersons.add(value);
    		return;
    	}
    	RedBlackNode<T, E> temp = root;
    	RedBlackNode<T, E> node = new RedBlackNode<T, E>() ; 
    	node.key = key;
    	node.object=value;
    	node.listOfPersons.add(value);
    	
    	if (root == null) {
    		root = node;
    		root.colour = black;
    	}
    	else {
    		node.colour = red;
    		while (true) {
    			if ( node.key.compareTo(temp.key)<0 ) {
    				if (temp.leftChild == null) {
    					temp.leftChild = node;
    					node.parent = temp;
    					break;
    				}
    				else {
    					temp = temp.leftChild;
    				}
    			}
    				
    				else if ( node.key.compareTo(temp.key)>0 ) {
    					if (temp.rightChild == null) {
    						temp.rightChild = node;
    						node.parent = temp;
    						break;
    					}
    					else {
    						temp = temp.rightChild;
    					}
    				}
    			}
    			
    		fixUpTree (node);

    		}
    	return;
    	}
				   
	@Override
    public RedBlackNode<T, E> search(T key) {
        RedBlackNode<T, E> node = new RedBlackNode<T, E>();
        node = search (root, key);
        return node;
    }
    
    
    
		    private RedBlackNode<T, E> search(RedBlackNode<T, E> node, T key) {
				if (node == null) {
					RedBlackNode<T, E> nullNode = new RedBlackNode<T, E>();
					nullNode.listOfPersons = null;
					nullNode.key = null;
					return nullNode;
				}
				else if (key.compareTo(node.key)<0) return search (node.leftChild, key);
				else if (key.compareTo(node.key)>0) return search (node.rightChild, key);
				else return node;
			}

	private void leftRightFix (RedBlackNode<T, E> node)	{
		RedBlackNode <T, E> parentNode = node.parent;
		RedBlackNode <T, E> grandParentNode = node.parent.parent;
		grandParentNode.leftChild = node.rightChild;
		parentNode.rightChild = node.leftChild;
		node.parent = grandParentNode.parent;
		node.rightChild = grandParentNode;
		node.leftChild = parentNode;
		parentNode.parent = node;
		if (grandParentNode != root && grandParentNode == grandParentNode.parent.rightChild ) 		grandParentNode.parent.rightChild = node;
		if (grandParentNode != root && grandParentNode == grandParentNode.parent.leftChild ) 		grandParentNode.parent.leftChild = node;

		grandParentNode.parent = node;
		node.colour =  black;
		grandParentNode.colour = red;
		if (grandParentNode == root ) root = node;
	}
	private void leftLeftFix (RedBlackNode<T, E> node)	{
		RedBlackNode <T, E> parentNode = node.parent;
		RedBlackNode <T, E> grandParentNode = node.parent.parent;
		grandParentNode.leftChild = parentNode.rightChild;
		parentNode.rightChild =  grandParentNode;
		parentNode.parent = grandParentNode.parent;
		if (grandParentNode != root && grandParentNode == grandParentNode.parent.rightChild ) grandParentNode.parent.rightChild = parentNode;
		if (grandParentNode != root && grandParentNode == grandParentNode.parent.leftChild ) grandParentNode.parent.leftChild = parentNode;
		
		grandParentNode.parent = parentNode;
		parentNode.colour =  black;
		grandParentNode.colour = red;
		if (grandParentNode == root ) root = parentNode;
	}
	private void rightLeftFix (RedBlackNode<T, E> node)	{
		RedBlackNode <T, E> parentNode = node.parent;
		RedBlackNode <T, E> grandParentNode = node.parent.parent;
		grandParentNode.rightChild = node.leftChild;
		parentNode.leftChild = node.rightChild;
		node.parent = grandParentNode.parent;
		node.rightChild = parentNode;
		node.leftChild = grandParentNode;
		if (grandParentNode != root && grandParentNode == grandParentNode.parent.rightChild ) grandParentNode.parent.rightChild = node;
		if (grandParentNode != root && grandParentNode == grandParentNode.parent.leftChild ) grandParentNode.parent.leftChild = node;
		
		parentNode.parent = node;
		grandParentNode.parent = node;
		node.colour =  black;
		grandParentNode.colour = red;
		if (grandParentNode == root ) root = node;
	}
	private void rightRightFix (RedBlackNode<T, E> node)	{
		RedBlackNode <T, E> parentNode = node.parent;
		RedBlackNode <T, E> grandParentNode = node.parent.parent;
		grandParentNode.rightChild = parentNode.leftChild;
		parentNode.leftChild =  grandParentNode;

		parentNode.parent = grandParentNode.parent;
		if (grandParentNode != root && grandParentNode == grandParentNode.parent.rightChild ) grandParentNode.parent.rightChild = parentNode;
		if (grandParentNode != root && grandParentNode == grandParentNode.parent.leftChild ) grandParentNode.parent.leftChild = parentNode;
		
		grandParentNode.parent = parentNode;
		parentNode.colour =  black;
		grandParentNode.colour = red;
		if (grandParentNode == root ) root = parentNode;
	}
	
	private void fixUpTree (RedBlackNode<T, E> node)	{
		
		if (node == root)	{
			node.colour = black;
			return;
		}
		if (node.parent != null && node.parent.colour == red)	{
			RedBlackNode<T, E> uncle = null;
			if (node.parent == node.parent.parent.leftChild) uncle = node.parent.parent.rightChild;
			else uncle = node.parent.parent.leftChild;
			if (uncle!= null && uncle.colour == red)	{
				uncle.colour = black;
				node.parent.colour = black;
				node.parent.parent.colour = red;
				fixUpTree (node.parent.parent);
			}
			
			else {
				RedBlackNode <T, E> parentNode = node.parent;
				RedBlackNode <T, E> grandParentNode = node.parent.parent;
				if (grandParentNode.leftChild == parentNode && parentNode.leftChild == node ) leftLeftFix (node);
				if (grandParentNode.leftChild == parentNode && parentNode.rightChild == node ) leftRightFix (node);
				if (grandParentNode.rightChild == parentNode && parentNode.leftChild == node ) rightLeftFix (node);
				if (grandParentNode.rightChild == parentNode && parentNode.rightChild == node ) rightRightFix (node);
			}
		}
	}
}