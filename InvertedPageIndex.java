public class InvertedPageIndex
{
	private MyLinkedList<PageEntry> plist;
	private MyHashTable ht;
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
	public MySet<PageEntry> getPagesWhichContainWord(String str)
	{
		MySet<PageEntry> pset = new MySet<>();
		WordEntry we = ht.search(str);
		if (str.equals("function"))
		{
			MyLinkedList<Position> l = we.getAllPositionsForThisWord();
			MyLinkedList<Position>.Node n = l.getHead();
			while (n != null)
			{
				n = n.next();
			}
		}
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
}
