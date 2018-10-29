import java.util.ArrayList;

public class MySort 
{
	public ArrayList<SearchResult> sortThisList(MySet<SearchResult> listOfSortableEntries)
	{
		MyLinkedList<SearchResult> l = listOfSortableEntries.list();
		MyLinkedList<SearchResult>.Node n = l.getHead();
		ArrayList<SearchResult> arr = new ArrayList<>(l.getSize());
		while(n != null)
		{
			arr.add(n.data());
			n = n.next();
		}
		for(int i=0;i<arr.size()-1;i++)
		{
			SearchResult min = arr.get(i);
			int ind = i;
			for(int j=i+1;j<arr.size();j++)
			{
				int comp = arr.get(j).compareTo(min);
				if (comp == 1)
				{
					min = arr.get(j);
					ind = j;
				}
			}
			SearchResult temp = arr.get(ind);
			arr.set(ind,arr.get(i));
			arr.set(i,temp);
		}
		return arr;
	}
}
