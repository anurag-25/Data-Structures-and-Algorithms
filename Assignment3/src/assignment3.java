import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class assignment3 {

	public static void main (String args[]) throws InterruptedException, NotFoundException{
		
		BufferedReader reader;
		

		 try {
			 reader = new BufferedReader(new FileReader(args[2]) ); 
			 MyHashTable_ <Pair_ <String, String> , Student > hashT = null;
			 if (args[1].equals("DH")) {
				 hashT = new HashTableDH <Pair_ <String, String> , Student > (Integer.parseInt(args[0]) );
			 }
			 else if ( args[1].equals("SCBST") ){
				 hashT = new HashTableSCBST <Pair_ <String, String> , Student > (Integer.parseInt(args[0]) );
			 }
			 String line = "Start"; 
			 
			 while (line != null) {
					line = reader.readLine();
					if (line != null) {
					    String[] tokens = line.split(" ");
					    
					    if (tokens[0].equals("insert") ){
					    	
					    	Pair_ <String, String > p = new Pair_ <String, String >  (tokens[1], tokens[2]);
					    	Student s = new Student (tokens[1], tokens[2], tokens[3], tokens[4], tokens[5] );
					    	System.out.println(hashT.insert(p, s));
					    }
					    
					    else if (tokens[0].equals("update")){
					    	Pair_ <String, String > p = new Pair_ <String, String >  (tokens[1], tokens[2]);
					    	Student s = new Student (tokens[1], tokens[2], tokens[3], tokens[4], tokens[5] );
					    	try{
					    		int i=hashT.update(p,s);
					    		if (i==0) System.out.println("E");
					    		else System.out.println(i);
					    	}
					    	catch (Exception NotFoundException){
					    		System.out.println("E");
					    	}
					    }
					    
					    else if (tokens[0].equals("delete")){
					    	Pair_ <String, String > p = new Pair_ <String, String >  (tokens[1], tokens[2]);
					    	try	{
					    		int i=hashT.delete(p);
					    		if ( i==0 ) System.out.println("E");
					    		else System.out.println(i);
					    	}
					    	catch (Exception NotFoundException){
					    		System.out.println("E");
					    	}
					    }
					    
					    else if (tokens[0].equals("contains")){
					    	Pair_ <String, String > p = new Pair_ <String, String >  (tokens[1], tokens[2]);
					    	if (hashT.contains(p)==true ) System.out.println("T");
					    	else System.out.println("F");
					    }
					    
					    else if (tokens[0].equals("get")){
					    	Pair_ <String, String > p = new Pair_ <String, String >  (tokens[1], tokens[2]);
					    	try{
					    		Student s = hashT.get(p);
						    	System.out.println(s.toString());
					    	}
					    	catch (Exception NotFoundException){
					    		System.out.println("E");
					    	}
					    }
					    else if (tokens[0].equals("address")){
					    	Pair_ <String, String > p = new Pair_ <String, String >  (tokens[1], tokens[2]);
					    	try{
						    	System.out.println(hashT.address(p));
					    	}
					    	catch (Exception NotFoundException){
					    		System.out.println("E");
					    	}
					    }
					}
			 }
			 reader.close();
		} 
		 
		 catch (IOException e) {
			e.printStackTrace();
		}
	}
}
