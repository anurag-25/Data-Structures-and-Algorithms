public class Pair_ <F extends Comparable <F> , S extends Comparable <S> > implements Comparable <Pair_ <F, S> > {
	
	private F first;
	private S second;
	
	Pair_ (F first, S second)
	{
		this.first =first;
		this.second = second;
	}
	
	public String toString (){
		return first.toString()+second.toString();
	}
	
	public int compareTo(Pair_ <F, S> pair){
		if (this.first.compareTo(pair.first) <0) return -1;
		else if (this.first.compareTo(pair.first) >0) return 1;
		else {													//first element is equal
																//So, now compare second element 
			if (this.second.compareTo(pair.second) <0) return -1;
			else if (this.second.compareTo(pair.second) >0) return 1;
			else return 0;
		}
	}
}
