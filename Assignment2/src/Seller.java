import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Seller<V> extends SellerBase<V> {
	
	@SuppressWarnings("unused")
	private int catalogSize;
	@SuppressWarnings("unused")
	private int sleepTime=1000;;
	
    public Seller (int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog, Queue<V> inventory) {
    	this.lock =lock;
        this.catalog = catalog;
        this.full = full;
        this.empty = empty;
        this.catalogSize = catalogSize;
        this.sleepTime= sleepTime;
        this.inventory = inventory;
    }
   
    public void sell() throws InterruptedException {
    	lock.lock();
	try {
		
			Node<V> current = (Node<V>) inventory.dequeue();
			while (catalog.isFull() )
			{	
				full.await();
			}
			catalog.enqueue(current);
			empty.signalAll();
			
		} catch(Exception e) {
            e.printStackTrace();
		} finally {
            //TODO Complete this block
			lock.unlock();
		}			
    }
}
