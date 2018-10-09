public class MySet<X>
{
	private MyLinkedList<X> l = new MyLinkedList<>();
	public MyLinkedList<X> list()
	{
		return l;
	}
	public boolean IsMember(X element)
	{
		if (l.search(element) == -1)
			return false;
		else
			return true;
	}
	public int getSize()
	{
		return l.getSize();
	}
	public void addElement(X element) throws Exception
	{
		if (IsMember(element) == false)
		{
			l.addRear(element);
		}
		else
			throw new Exception();
	}
	public MySet<X> union(MySet<X> otherSet)
	{
		MySet<X> s = new MySet<>();
		MyLinkedList<X> l2 = otherSet.list();
		MyLinkedList<X>.Node n1 = l.getHead();
		MyLinkedList<X>.Node n2 = l2.getHead();
		while (n1 != null)
		{
			try
			{
				s.addElement(n1.data());
			}
			catch (Exception e)
			{}
			n1 = n1.next();
		}
		while (n2 != null)
		{
			try
			{
				s.addElement(n2.data());
			}
			catch (Exception e)
			{}
			n2 = n2.next();
		}
		return s;
	}
	public MySet<X> intersection(MySet<X> otherSet)
	{
		MySet<X> s = new MySet<>();
		MyLinkedList<X>.Node n1 = l.getHead();
		while(n1 != null)
		{
			if (otherSet.IsMember(n1.data()))
			{
				try
				{
					s.addElement(n1.data());
				}
				catch(Exception e)
				{}
			}
			n1 = n1.next();
		}
		return s;
	}
}