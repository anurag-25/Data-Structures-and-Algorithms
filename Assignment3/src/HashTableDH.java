	import java.lang.Math; 

public class HashTableDH <K extends Comparable <K>,T> implements MyHashTable_<K, T> {
	
	private int hashTableSize;
	private int currentSize;
	private HashEntry<K,T> [] table;

	
	@SuppressWarnings("unchecked")
	HashTableDH (int hashTableSize){
		this.currentSize = 0;
		this.hashTableSize= hashTableSize;
		table = new HashEntry [this.hashTableSize];
	}
	
	public static long h1(String str, int hashtableSize) { 
	    long hash = 5381; 
	    for (int i = 0; i < str.length(); i++) { 
	        hash = ((hash << 5) + hash) + str.charAt(i); 
	    } 
	    return Math.abs(hash) % hashtableSize; 
	}
	public static long h2(String str, int hashtableSize) { 
	    long hash = 0; 
	    for (int i = 0; i < str.length(); i++) { 
	        hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash; 
	    } 
	    return Math.abs(hash) % (hashtableSize - 1) + 1; 
	}
	
	// Insert new object with given key 
	   public int insert(K key, T obj) {
		   int steps=0;

			   int slot = (int) h1(key.toString() , this.hashTableSize);
			   steps++;
			   int offset = (int) h2(key.toString() , this.hashTableSize);
			   while (table[slot] != null ){
				   slot = (slot+offset)%hashTableSize;
				   steps++;
			   }
			   table[slot] = new HashEntry<K, T> (key, obj);
			   this.currentSize++;		   
		return steps;
	} 
	 
	   // Update object for given key 
	   public int update(K key, T obj) {
		   if (contains (key) ){
			   int steps = 0;
			   int slot = (int) h1(key.toString() , this.hashTableSize);
			   int offset = (int) h2(key.toString() , this.hashTableSize);
			   
			   while (true){
					  if (table[slot] == null ) {
						  steps++;
						  slot = (slot+offset) % hashTableSize;
						  continue;
					  }
					  else {
						  if (key.toString().equals (table[slot].key.toString() )) {
							  steps++;
							  table[slot].obj = obj;
							  return steps;
						  }
						  else{
							  steps++;
							  slot = (slot+offset) % hashTableSize;
						  }
					  }
				  }
		   }
		   return 0;
	} 
	 
	   // Delete object for given key 
	   public int delete(K key) {
		   int steps = 0;
		   if (contains (key) ){
			   int slot = (int) h1(key.toString() , this.hashTableSize);
			   int offset = (int) h2(key.toString() , this.hashTableSize);
			   
			   while (true){
					  if (table[slot] == null ) {
						  steps++;
						  slot = (slot+offset) % hashTableSize;
						  continue;
					  }
					  else {
						  if (key.toString().equals (table[slot].key.toString() )) {
							  steps++;
							  table[slot]= null;
							  return steps;
						  }
						  else{
							  steps++;
							  slot = (slot+offset) % hashTableSize;
						  }
					  }
				  }
		   }
		   return steps;
	} 
	 
	   // Does an object with this key exist? 
	   public boolean contains(K key) {
		   int slot = (int) h1(key.toString() , this.hashTableSize);
		   int offset = (int) h2(key.toString() , this.hashTableSize);
		   int i=0;
		   while (i<hashTableSize){
				  if (table[slot] == null ){
					  slot = (slot+offset) % hashTableSize;
					  continue;
				  }
				  else {
					  if (key.toString().equals (table[slot].key.toString() )) {
						  return true;
					  }
					  else {
						  slot = (slot+offset) % hashTableSize;
						  i++;
					  }
				  }
			  }
		   return false;
	} 
	 
	   // Return the object with given key 
	   public T get(K key) throws NotFoundException {
		    if (contains (key) ){
		    	int slot = (int) h1(key.toString() , this.hashTableSize);
				   int offset = (int) h2(key.toString() , this.hashTableSize);
				   
				  while (true){
					  if (table[slot] == null ) {
						  slot = (slot+offset) % hashTableSize;
						  continue;
					  }
					  else {
						  if (key.toString().equals (table[slot].key.toString() )) {
							  return table[slot].obj;
						  }
						  else slot = (slot+offset) % hashTableSize;
					  }
				  }
		    }
		    else return null;
	} 
	 
	   // ”Address” of object with given key (explained below) 
	   public String address(K key) throws NotFoundException {
		   if (contains (key) ){
			   int slot = (int) h1(key.toString() , this.hashTableSize);
			   int offset = (int) h2(key.toString() , this.hashTableSize);
			   
			   while (true){
					  if (table[slot] == null ) {
						  slot = (slot+offset) % hashTableSize;
						  continue;
					  }
					  else {
						  if (key.toString().equals (table[slot].key.toString() )) {
							  return Integer.toString(slot);
						  }
						  else slot = (slot+offset) % hashTableSize;
					  }
				  }
		   }
		   else return "E";
		   
	} 
}
