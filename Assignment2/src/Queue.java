// This class implements the Queue
public class Queue<V> implements QueueInterface<V>{

    //TODO Complete the Queue implementation
	// I have made a circular queue
    private NodeBase<V>[] queue;
    private int capacity, currentSize, front, rear;
	
    @SuppressWarnings("unchecked")
	public Queue(int capacity) {    
    		this.capacity = capacity;
    		queue = new Node [this.capacity];
    		currentSize =0;
    		front = -1;
    		rear = -1;
    }

    public boolean isEmpty() {
    	return (currentSize == 0);
    }
	
    public boolean isFull() {
    	return (currentSize == capacity);
    }

    public void enqueue(Node<V> node) {
    	 if (isFull())
    	 {
    		 System.out.print("");
    	 }
    	 else 
    	 {
    		 rear = (rear+1)%capacity;
    		 queue[rear]= node;
    		 currentSize++;
    		 if (front == -1)
    		 {
    			 front=rear;
    		 }
    	 }
    }

    public NodeBase<V> dequeue() {
    	NodeBase <V> dequeElement = null;
    	if (isEmpty())
    	{
    		System.out.print("");
    	}
    	
    	else
    	{
    		dequeElement= queue[front];
    		queue[front] = null;
    		front = (front+1) % capacity;
    		currentSize--;
    	}
    	return dequeElement;
    }
    
    public int size() {
        return currentSize;
    }
}

