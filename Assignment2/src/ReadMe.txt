Assignment 2
Anurag Chaudhary
2018EE10448

Classes used in this assignment, with explanation of implementations of methods, where ever required:

1. Bases: It contains all the predefined interfaces and abstract classes for other classes.
2. Item: It was pre-defined.
3. Node: In getPriority() methods, priority is returned, and in getValue() method, value is returned.

4. Queue: It has following methods:
	
	A. Constructor 
		public Queue(int capacity) {    
    		this.capacity = capacity;
    		queue = new Node [this.capacity];
    		currentSize =0;
    		front = -1;
    		rear = -1;
   		}

		It is used to initialize the front and rear variables which will act as index for my queue.
		Also, queue is implemented using an array of size 'capacity'.
	B. isEmpty(): Returns true if cuurentSize of queue is zero.
	C. isFull(): Returns true if cuurentSize of queue is equal to capacity
	D. size(): returns currentSize of queue.
	E. enqueue(Node<V> item): If queue is full, it does nothing. Else, rear is increased by one and 
			the node 'item' is stored at this index and cuurentSize is increased by one. Also,
			if front is -1, which indicates that queue is empty, then front is put equal to
			rear.
	F. dequeue (): If queue is empty, it does nothing. Else, element at front index is stored in variable
			dequeElement, then front element is set to null, and front is increased by one.
			Finally, dequeElement is returned. currentSize is decreased by one.

5. PriorityQueue: It has the constructor, isEmpty(), isFull(), size(), and enqueue(Node<V> item) methods same as queue.
	 However, dequeue is changed as follows:
	If queue is empty, it does nothing. 
	a. Else, a variable maxPriority stores the priority of element at front index,and variable maxIndex is initialized
	   to zero. 
	b. Then, using a for loop over the PriorityQueue, maxPriority changes to the lowest value (which corresponds to
	   maximum Priority) and maxIndex changes to the corresponding index of element with maxPriority. 
	c. Then element at maxIndex is stored in dequeElement and rest all elements are shifted left.
	d. The last element is put to null. rear and currentSize are decreased by one.
	
6. Buyer:
	A. Constructor: All the protected varibales in class BuyerBase in class Bases are initialized in the constructor.
	B. buy():
		a. As soon as thread enters this method, is acquires the lock and prevents any other thread from entering.
		b. Then, if the catalog (shared priority queue between sellers adn buyers) is empty, it waits for the 
		   seller to add items to it.
		c. Else, it dequeues an element from the catalog, and prints its priority in the format- "Consumed <Priority>"
		   and notifies the seller if it is waiting due to the catalog being full.
		d. Finally it releases the lock.
	
6. Seller: 
	A. Constructor: All the protected varibales in class SellerBase in class Bases are initialized in the constructor.
	B. sell():
		a. As soon as thread enters this method, is acquires the lock and prevents any other thread from entering.
		   Then it dequeues an element from inventory (shared queue between sellers).
		b. Then, if the catalog is empty, it waits for the seller to add items to it.
		c. Else, it enqueues an element into the catalog, and notifies the buyer if it is waiting due to the 
		   catalog being empty.
		d. Finally it releases the lock.

7. Assignment2Driver:
	Only two things are implemented by us in this class, rest all is pre-defined.
	A. All the Items are added from ArrayList 'list' to Queue 'inventory' using for loop.
	B. Multiple buyer and seller threads are initialized, on objects of buyers[] array, using for loops.
	   Objects in buyers[] array, have been used to initialize sleepTime and iteration varibales in BuyerBase,
	   using setSleepTime() and setIteration() methods.

















