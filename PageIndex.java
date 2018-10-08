public class PageIndex
{
	private MyLinkedList<WordEntry> l = new MyLinkedList<>();
	public WordEntry searchByWord(String word)
	{
		MyLinkedList<WordEntry>.Node n = l.getHead();
		while (n != null)
		{
			WordEntry we = n.data();
			String s = we.getWord();
			if (s.equals(word))
				return n.data();
			n = n.next();
		}
		return null;
	}
	public void addPositionForWord(String str, Position p)
	{
		WordEntry we = searchByWord(str);
		if (we != null)
		{
			we.addPosition(p);
		}
		else
		{
			WordEntry wordent = new WordEntry(str);
			wordent.addPosition(p);
			l.addRear(wordent);
		}
	}
	public MyLinkedList<WordEntry> getWordEntries()
	{
		return l;
	}
}
