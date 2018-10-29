import java.util.ArrayList;

public class AVLTree
{
	class Node
	{
		private Position data;
		private Node left,right,parent;
		private int ht;
		public Node(Position obj)
		{
			data = obj;
			ht = 0;
			left = null;
			right = null;
			parent = null;
		}
		public boolean balanced()
		{
			if (left == null)
				return Math.abs(1+right.ht) <= 1;
			if (right == null)
				return Math.abs(1+left.ht) <= 1;
			return Math.abs(left.ht-right.ht) <= 1;
		}
		public Position data()
		{
			return data;
		}
		public int height()
		{
			return ht;
		}
	}
	private Node root;
	public AVLTree()
	{
		root = new Node(null);
	}
	public Node root()
	{
		return root;
	}
	public void updateHeight(Node n)
	{
		int h1 = -1, h2 = -1;
		if (n.left != null)
			h1 = n.left.ht;
		if (n.right != null)
			h2 = n.right.ht;
		n.ht = 1 + Math.max(h1,h2);
	}
	public void insert(Position pos)
	{
		Node n = new Node(pos);
		if (root.data != null)
		{
			Node n2 = root;
			while (n2 != null)
			{
				if (n.data.getWordIndex() > n2.data.getWordIndex())
				{
					if (n2.right == null)
					{
						n2.right = n;
						n.parent = n2;
						break;
					}
					else
						n2 = n2.right;
				}
				else
				{
					if (n2.left == null)
					{
						n2.left = n;
						n.parent = n2;
						break;
					}
					else
						n2 = n2.left;
				}
			}
			n = n.parent;
			while (n != null)
			{
				int h1 = -1, h2 = -1;
				if (n.left != null)
					h1 = n.left.ht;
				if (n.right != null)
					h2 = n.right.ht;
				n.ht = 1 + Math.max(h1,h2);
				boolean left = true;
				if (n.parent != null)
				{
					if (n.parent.left != null && n.parent.left.equals(n))
						left = true;
					else
						left = false;
				}
				if (!n.balanced())
				{
					Node x = new Node(null);
					Node y = new Node(null);
					Node z = n;
					if (h1 > h2)
					{
						y = z.left;	
					}
					else
					{
						y = z.right;
					}
					if (y.left != null && y.right != null)
					{
						x = (y.left.ht>y.right.ht)?y.left:y.right;
					}
					else
					{
						if (y.left == null)
							x = y.right;
						if (y.right == null)
							x = y.left;
					}
					if (y.equals(z.right))
					{
						if (x.equals(y.left))
						{
							z.right = x.left;
							y.left = x.right;
							x.left = z;
							x.right = y;
							x.parent = z.parent;
							z.parent = x;
							y.parent = x;
							updateHeight(z);
							n = x;
						}
						else if (x.equals(y.right))
						{
							z.right = y.left;
							y.left = z;
							y.parent = z.parent;
							z.parent = y;
							updateHeight(z);
							n = y;
							
						}
					}
					else if (y.equals(z.left))
					{
						if (x.equals(y.right))
						{
							y.right = x.left;
							z.left = x.right;
							x.left = y;
							x.right = z;
							x.parent = z.parent;
							y.parent = x;
							z.parent = x;
							updateHeight(z);
							n = x;
						}
						else if (x.equals(y.left))
						{
							z.left = y.right;
							y.right = z;
							y.parent = z.parent;
							z.parent = y;
							updateHeight(z);
							n = y;
						}
					}
					if (n.parent != null)
					{
						if (left)
							n.parent.left = n;
						else
							n.parent.right = n;
					}
					else
						root = n;
				}
				n = n.parent;
			}
		}
		else
		{
			root = n;
		}
	}
	public ArrayList<Position> getInorder(ArrayList<Position> arr, Node n)
	{
		if (n.left != null)
	        arr = getInorder(arr,n.left);
	    arr.add(n.data);
	    if (n.right != null)
	        arr = getInorder(arr,n.right);
	    return arr;
	}
	public boolean find(Position pos)
	{
		Node n = root;
		while (n != null)
		{
			if (pos.getWordIndex() == n.data.getWordIndex())
				return true;
			else if (pos.getWordIndex() > n.data.getWordIndex())
			{
				if (n.right == null)
				{
					return false;
				}
				else
				{
					n = n.right;
				}
			}
			else
			{
				if (n.left == null)
				{
					return false;
				}
				else
				{
					n = n.left;
				}
			}
		}
		return false;
	}
}
