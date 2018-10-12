public class MyLinkedList<T>
{
	class Node
    {
        private T data;
        private Node next;
        public Node(T obj)
        {
            data = obj;
            next = null;
        }
        public T data()
        {
        	return data;
        }
        public Node next()
        {
        	return next;
        }
    }
    private Node head;
    private int size;
    public MyLinkedList()
    {
    	head = null;
    	size = 0;
    }
    public Node getHead()
    {
    	return head;
    }
    public int getSize()
    {
    	return size;
    }
    public boolean find(T obj)
    {
    	Node n = head;
        while(n != null)
        {
            if (n.data.equals(obj))
            	return true;
            n = n.next;
        }
        return false;
    }
    public boolean isEmpty()
    {
        return size==0;
    }
    public void addRear(T data)
    {
        
    	Node node = new Node(data);
    	if (isEmpty())
    	{
    		head = node;
    	}
    	else
    	{
    		Node n = head;
    		while(n.next != null)
    			n = n.next;
    		n.next = node;
    	}
    	size++;
    }
    public void remove(T obj)
    {
        Node n = head;
        if (n.data == obj)
        {
        	head = n.next;
        	size--;
        }
        else
        {
        	while(n.next != null)
        	{
        		if (n.next.data == obj)
        		{
        			n.next = n.next.next;
        			size--;
        			return;
        		}
        		n = n.next;
        	}
        	if (n.data == obj)
        	{
    			n.next = n.next.next;
    			size--;
    		}
        }
    }
    /*public void print()
    {
    	Node n = head;
		while(n != null)
		{
			System.out.print(n.data+" ");
			n = n.next;
		}
		System.out.println();
    }*/
}
