import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Buyer<V> extends BuyerBase<V> {
	
 public Buyer (int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog, int iteration) 
    {
        this.lock =lock;
        this.catalog = catalog;
        this.full = full;
        this.empty = empty;

    }
    public void buy() throws InterruptedException {
    	lock.lock();
		try {
			
			while (catalog.isEmpty()) 
				{
					empty.await();
				}
			Node <V> n = (Node<V>) catalog.dequeue();
	            
		    System.out.print("Consumed "); // DO NOT REMOVE (For Automated Testing)
	            n.show(); // DO NOT REMOVE (For Automated Testing)
	            // ...
	    		full.signalAll();
	    		Thread.sleep(600);
		} catch (Exception e) {
	            e.printStackTrace();
		} finally {
	            //TODO Complete this block
			lock.unlock();
		}
    }
    
}
