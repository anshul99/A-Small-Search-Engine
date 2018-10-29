public class InvertedPageIndex
{
	private MyLinkedList<PageEntry> plist;
	private static MyHashTable ht;
	public InvertedPageIndex()
	{
		plist = new MyLinkedList<>();
		ht = new MyHashTable();
	}
	public void addPage(PageEntry p)
	{
		plist.addRear(p);
		MyLinkedList<WordEntry> l = p.getPageIndex().getWordEntries();
		MyLinkedList<WordEntry>.Node n = l.getHead();
		while (n != null)
		{
			ht.addPositionsForWord(n.data());
			n = n.next();
		}
	}
	public static MySet<PageEntry> getPagesWhichContainWord(String str)
	{
		MySet<PageEntry> pset = new MySet<>();
		WordEntry we = ht.search(str);
		if (we != null)
		{
			MyLinkedList<Position> l = we.getAllPositionsForThisWord();
			
			MyLinkedList<Position>.Node n = l.getHead();
			while (n != null)
			{
				try
				{
					pset.addElement(n.data().getPageEntry());
				} 
				catch (Exception e)
				{}
				n = n.next();
			}
		}
		return pset;
	}
	public static MySet<PageEntry> getPagesWhichContainPhrase(String str[])
	{
		MySet<PageEntry> pset = new MySet<>();
		MySet<PageEntry> ans = new MySet<>();
		pset = getPagesWhichContainWord(str[0]);
		for(int i=1;i<str.length;i++)
		{
			pset = pset.intersection(getPagesWhichContainWord(str[i]));
		}
		MyLinkedList<PageEntry> l = pset.list();
		MyLinkedList<PageEntry>.Node n = l.getHead();
		while (n != null)
		{
			PageEntry pe = n.data();
			if (pe.countPhrase(str) != 0)
			{
				try
				{
					ans.addElement(pe);
				}
				catch (Exception e)
				{}
			}
			n = n.next();
		}
		return ans;
	}
	public PageEntry search(String str)
	{
		MyLinkedList<PageEntry>.Node n = plist.getHead();
		while (n != null)
		{
			if (n.data().getName().equals(str))
				return n.data();
			n = n.next();
		}
		return null;
	}
}
