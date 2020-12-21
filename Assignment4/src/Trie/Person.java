package Trie;

public class Person {
	
	private String name;
	private String phoneNumber;
	
    public Person(String name, String phone_number) {
    	this.name = name;
    	this.phoneNumber  = phone_number;
    }

    public String getName() {
        return name;
    }
    
    public String toString(){
    	return("[Name: "+ name + ", Phone=" + phoneNumber + "]");
    }
}
