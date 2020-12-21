public class HashTableSCBST <K extends Comparable<K> ,T> implements MyHashTable_<K, T> {
	
	private BSTNode<K,T>[] table;
	private int hashTableSize;
	int count = 0;
	
	@SuppressWarnings("unchecked")
	
	public HashTableSCBST (int hashTableSize) {
		this.hashTableSize =hashTableSize;
		table = new BSTNode [this.hashTableSize];
	}
	
	// Insert new object with given key 
	   public int insert(K key, T obj) {
		   count=0;
		   int hash = (int) h1(key.toString(), this.hashTableSize);
		   BSTNode <K, T> root = table[hash];
		   root = insert(root, key, obj);
		   table [hash] = root;
		return count;
	} 
						   private BSTNode<K,T> insert (BSTNode <K,T> node,  K key, T obj){
							   if (node == null){
								   count++;
								   return new BSTNode <K, T> (key, obj);
							   }
							   
							   else if (key.compareTo(node.key) < 0) {
								   count++;
								node.left = insert (node.left, key,  obj);
							   }
							   else if (key.compareTo(node.key) > 0){
								   count++;
								   node.right = insert (node.right, key, obj);
							   }
							   else{
								   count++;
									node.left = insert (node.left, key,  obj);
								   }
							   return node;
						   }
	 
	   // Update object for given key 
	   public int update(K key, T obj) {
		   count=0;
		   int hash = (int) h1(key.toString(), this.hashTableSize);
		   BSTNode <K, T> root = table[hash];
		   root = update(root, key, obj);
		   
		   table [hash] = root;
		return count;
	} 
						   private BSTNode<K,T> update (BSTNode <K,T> node,  K key, T obj){
								   if (key.compareTo(node.key) < 0) {
									   count++;
										node.left = update (node.left, key,  obj);
									   }
								   else if (key.compareTo(node.key) > 0){
									   count++;
									   node.right = update (node.right, key, obj);
								   }
								   else {
									   count++;
									   node.obj = obj;
								   }
								   return node;
							 
						   }
	 
	   // Delete object for given key 
	   public int delete(K key) {
		   count=0;
		   int hash = (int) h1(key.toString(), this.hashTableSize);
		   BSTNode <K, T> root = table[hash];
		   try{
			   root = delete(root, key);
		   }
		   catch (Exception NotFoundEXception ){
		   }
		   finally{
			   table[hash] = root;
		   }
		return count;
	} 
	   
	 
					   private BSTNode<K, T> delete(BSTNode<K, T> node, K key) {
						   if (node == null){
							   count++;
							   return null;
						   }
						   else if (key.compareTo(node.key) < 0) {
							   count++;
								node.left = delete (node.left, key);
						   }
						   else if (key.compareTo(node.key) > 0){
							   count++;
							   node.right = delete (node.right, key);
						   } 
						   else{
							   count++;
							   											//this is the key to be deleted
							   if (node.left ==  null) {
								   count++;
								   return node.right;								//key has only right child
							   }
							   else if (node.right ==  null) {
								   count++;
								   return node.left;								//key has only left child
							   }	
							   else {										//key has both left and right child	
								   		BSTNode <K, T> temporaryNode = node;
								   		node = minimum (temporaryNode.right);			//node is replaced by the smallest child in the right subtree, i.e smallest node larger than it 
								   		node.right = deleteMinimum (temporaryNode.right);	//smallest node's right child is made equal to the original node's right child after deleting this minimum node from its previous place
								   		node.left = temporaryNode.left;				//smallest node's left is replaced by original node's left.
							   }
						   }
						   return node;

					   }

											private BSTNode<K, T> deleteMinimum(BSTNode<K, T> node) { 
												if (node.left ==  null) {
													return node.right;
												}
												else{
													node.left =(deleteMinimum(node.left) );
												}
												return node;
											}
										
											private BSTNode<K, T> minimum(BSTNode<K, T> node) {
												if (node.left == null){
													count++;
													return node;
												}
												else{
													count++;
													return minimum (node.left);
												}
											}

	// Does an object with this key exist? 
	   public boolean contains(K key) {
		   int hash = (int) h1(key.toString(), this.hashTableSize);
		   BSTNode <K, T> root = table[hash];
		   if (contains (root, key) ){
			   return true;
		   }
		   else {
			   return false;
		   }
	} 
	 
									   private boolean contains(BSTNode<K, T> node, K key) {
										   if (node == null) return false;
										   else if (key.compareTo(node.key) < 0) return contains (node.left, key);
										   else if (key.compareTo(node.key) > 0) return contains (node.right, key);
										   else return true;
									}

	// Return the object with given key 
	   public T get(K key) throws NotFoundException {
		   int hash = (int) h1(key.toString(), this.hashTableSize);
		   BSTNode <K, T> root = table[hash];
		   T obj = null;
		   try{
			   obj = get(root, key);
		   }
		   catch (Exception NotFoundEXception ){
			   System.out.println("E");
		   }
		   return obj;
	} 
	 
									   private T get(BSTNode<K, T> node, K key) {
										   if (node == null) return null;
										   else if (key.compareTo(node.key) < 0) return get (node.left, key);
										   else if (key.compareTo(node.key) > 0) return get(node.right, key);
										   else return node.obj; 
											
									}

	// ”Address” of object with given key (explained below) 
	   public String address(K key) throws NotFoundException {
		   String add= "";

		   if (contains(key)){
			   int hash = (int) h1(key.toString(), this.hashTableSize);
			   add = add + hash + "-";
			   BSTNode <K, T> root = table[hash];
			   add = add +address (root, key);
		   }
		   else{
			   add = add + "E";
		   }
			return add;
	} 
	
									   private String address(BSTNode<K, T> node, K key) {
										   String str = "";
										   if (node == null) return str;
										   else if ( key.compareTo (node.key) < 0){
											   str = str+"L"+address(node.left, key);
										   }
										   else if ( key.compareTo (node.key) > 0){
											   str = str+"R"+address(node.right, key);
										   }
										   else return str;
										return str;
									}

	public static long h1(String str, int hashtableSize) { 
		    long hash = 5381; 
		    for (int i = 0; i < str.length(); i++) { 
		        hash = ((hash << 5) + hash) + str.charAt(i); 
		    } 
		    return Math.abs(hash) % hashtableSize; 
		}

}
