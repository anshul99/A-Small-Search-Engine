public class MyHashTable 
{
	private int tablesize = 100;
	@SuppressWarnings("unchecked")
	private MyLinkedList<WordEntry>[] table = new MyLinkedList[tablesize];
	public MyHashTable()
	{
		for(int i=0;i<tablesize;i++)
		{
			table[i] = new MyLinkedList<>();
		}
	}
	private int getHashIndex(String str)
	{
		int key = 0;
		for(int i=0;i<str.length();i++)
			key += (int)str.charAt(i);
		key = key%tablesize;
		return key;
	}
	public void addPositionsForWord(WordEntry w)
	{
		String word = w.getWord();
		int key = getHashIndex(word);
		MyLinkedList<WordEntry>.Node n = table[key].getHead();
		while (n != null)
		{
			WordEntry we = n.data();
			if (we.getWord().equals(word))
			{
				we.addPositions(w.getAllPositionsForThisWord());
				return;
			}
			n = n.next();
		}
		table[key].addRear(w);
	}
	public WordEntry search(String str)
	{
		int key = getHashIndex(str);
		MyLinkedList<WordEntry> l = table[key];
		MyLinkedList<WordEntry>.Node n = l.getHead();
		while (n != null)
		{
			WordEntry we = n.data();
			if (we.getWord().equals(str))
			{
				return we; 
			}
			n = n.next();
		}
		return null;
	}
}
