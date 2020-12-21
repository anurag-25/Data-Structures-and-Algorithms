public class HashEntry <K,T> {
	
	public K key;
	public T obj;
	public HashEntry (K key, T obj){
		this.key = key;
		this.obj = obj;
	}
	
	public HashEntry(){
		key = null;
		obj = null;
	}

	public K getKey() {
		// TODO Auto-generated method stub
		return this.key;
	}
}
