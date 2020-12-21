package Trie;

import java.util.ArrayList;
import java.util.List;

public class Trie<T> implements TrieInterface {

	private TrieNode<T> root;
	
	public Trie (){
		this.root = new TrieNode<T> ();
	}
	
	static int longestWord =0;

	@Override
	public boolean insert(String word, Object value) {
		if( search(word) != null) return false;
		if (word == null) return false;
		if(word.length()> longestWord ) longestWord = word.length();
		TrieNode<T> current = root;
		for (int i=0; i< word.length(); i++)	{
			int indexOfChild = word.charAt(i)-32;
			if ( current.children [indexOfChild] == null) {
				current.children [indexOfChild] = new TrieNode <T> (word.charAt(i));
			}
			
			current = current.children[indexOfChild];
		}
		
		current.isLeaf = true;
		current.person = (T) value;
		return true;
	}

	@Override
	public TrieNode search(String word) {
		if (word == null) return null;
		TrieNode <T> current = root;
		for (int i=0; i< word.length(); i++)	{
			int indexOfChild = word.charAt(i)-32;
			if (current == null) return null;
			current = current.children[indexOfChild];
		}
		if (current.isLeaf == true ) return current;
		return null;
	}

	@Override
	public TrieNode startsWith(String prefix) {
		if (prefix == null) return null;
		TrieNode <T> current = root;
		for (int i=0; i< prefix.length(); i++)	{
			int indexOfChild = prefix.charAt(i)-32;
			if (current == null) return null;
			current = current.children[indexOfChild];
		}
		return current;
	}

	@Override
	public void printTrie(TrieNode trieNode) {
		if (trieNode.isLeaf) System.out.println(trieNode.person);
		if ( hasChildren(trieNode) == false ) return;
		for (int i=0; i< 95; i++)	{
			if (trieNode.children[i] != null) printTrie (trieNode.children[i] );
		}
	}



	@Override
	public boolean delete(String word) {
		if (search (word) == null) {
			return false;
		}
		if (word == null) return false;
		delete(root, word, 0);
		return true;
	}
	
			private boolean hasChildren( TrieNode <T> node )	{
				for (int i=0; i<95; i++)	{
					if (node.children[i]!= null) return true;
				}
				return false;
			}
			
			TrieNode<T> delete( TrieNode<T> node, String word, int level) 
			{ 
			    if (node==null) 
			        return null; 
			  
			    if (level == word.length()) { 
			        if (node.isLeaf) 
			            node.isLeaf = false; 
			        if (hasChildren(node) == false) { 
			            node = null;
			        } 
			        return node; 
			    } 
		
			    int indexOfChild = word.charAt(level) - 32; 
			    node.children[indexOfChild] =  
			          delete( node.children[indexOfChild], word, level + 1); 
		
			    if (hasChildren(node)==false && node.isLeaf == false) { 
			        node =null;
			    } 
			    return node; 
			}
	@Override
	public void print() {
		System.out.println("-------------");
		System.out.println("Printing Trie");
		for (int i=1; i<= longestWord+1; i++)	{
			printLevel(i);
		}
		System.out.println("-------------");
	}
	
	static List<Character> listOfLevel = new ArrayList<Character>();
	@Override
	public void printLevel(int level) {
		System.out.print("Level "+ level + ": ");
		printLevel(root, 1, level);
		sort (listOfLevel);
		for (int i=0; i<listOfLevel.size(); i++)	{
			if (i==listOfLevel.size()-1 ) {
				System.out.print(listOfLevel.get(i));
				break;
			}
			String s =",";
			System.out.print(listOfLevel.get(i) + s);
		}
		listOfLevel.clear();
		System.out.println();
	}
		private void sort(List<Character> list) {
		for (int i=0; i<list.size(); i++ )	{
			for (int j=i; j<list.size(); j++ )	{
				if (list.get(j).compareTo(list.get(i)) <0)	{
					char temp = list.get(j);
					list.set(j, list.get(i) );
					list.set(i, temp);
				}
			}
		}
	}

		private void printLevel (TrieNode <T> node, int currentLevel, int levelToBePrinted){
			
			for (int i=0; i<95; i++)	{
				if ( node.children[i]!=null ){
					if (currentLevel == levelToBePrinted ){
						if (i!=0) listOfLevel.add(node.children[i].ch ) ;
					}
					else printLevel (node.children [i], currentLevel+1, levelToBePrinted) ;
				}
			}
			
		}
}