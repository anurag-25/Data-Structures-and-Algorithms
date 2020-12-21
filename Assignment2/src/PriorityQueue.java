 public class PriorityQueue<V> implements QueueInterface<V>{

    private NodeBase<V>[] queue;
    private int capacity, currentSize, front, rear;
	
    //TODO Complete the Priority Queue implementation
    // You may create other member variables/ methods if required.
    @SuppressWarnings("unchecked")
	public PriorityQueue(int capacity) {    
    	this.capacity = capacity;
    	queue = new Node [this.capacity];
		currentSize =0;		
		front=-1;
		rear=-1;
    }

    public int size() {
    	return currentSize;
    }

    public boolean isEmpty() {
    	return (currentSize == 0);
    }
	
    public boolean isFull() {
    	return (currentSize == capacity);
    }

    public void enqueue(Node<V> node) 
    {
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

    // In case of priority queue, the dequeue() should 
    // always remove the element with minimum priority value
    public NodeBase<V> dequeue() {
    	NodeBase <V> dequeElement = null;
    	
    	if (isEmpty())
    	{
    		System.out.print("");
    	}
    	else {
    		int maxPriority = queue[front].getPriority();
    		int maxIndex=0;
    		for (int i=0; i<currentSize; i++)
    		{
    			if (maxPriority> queue[i].getPriority() ) 
    				{
    					maxPriority= queue[i].getPriority();
    					maxIndex=i;
    				}
    		}
    			dequeElement = queue[maxIndex];
    			for (int i=maxIndex; i<currentSize-1; i++)
    			{
    				queue[i]= queue[i+1];
    			}
    			queue[currentSize-1]=null;
    		currentSize--;
    		rear--;
    	}
    	return dequeElement;
    }

    public void display () {
	if (this.isEmpty()) {
            System.out.println("Queue is empty");
	}
	for(int i=0; i<currentSize-1; i++) {
            queue[i+1].show();
	}
    }

}

