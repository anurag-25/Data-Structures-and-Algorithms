package Trie;


import Util.NodeInterface;


public class TrieNode<T> implements NodeInterface<T> {
	
	T person;
	char ch;
	TrieNode <T> [] children;
	boolean isLeaf;
	
	public TrieNode(){
		this.person = null;
		children = new TrieNode[96];
		isLeaf = false;
		ch='\0';
	}
	
	public TrieNode(T object){
		this.person = object;
		children = new TrieNode[96];
		isLeaf = false;
	}
	public TrieNode(char c){
		children = new TrieNode[96];
		isLeaf = false;
		ch=c;
	}

    @Override
    public T getValue() {
        return person;
    }

}