public class InvertedPageIndex
{
	private MyLinkedList<PageEntry> plist = new MyLinkedList<>();
	public void addPage(PageEntry p)
	{
		plist.addRear(p);
	}
	public MySet<PageEntry> getPagesWhichContainWord(String str)
	{
		MySet<PageEntry> pset = new MySet<>();
		MyLinkedList<PageEntry>.Node n = plist.getHead();
		while (n != null)
		{
			PageIndex pi = n.data().getPageIndex();
			WordEntry we = pi.searchByWord(str);
			if (we != null)
			{
				MyLinkedList<Position> l = we.getAllPositionsForThisWord();
				MyLinkedList<Position>.Node n2 = l.getHead();
				while (n2 != null)
				{
					try
					{
						pset.addElement(n2.data().getPageEntry());
					} 
					catch (Exception e)
					{}
					n2 = n2.next();
				}
			}
			n = n.next();
		}
		return pset;
	}
}
