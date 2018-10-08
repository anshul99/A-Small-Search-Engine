public class MyHashTable 
{
	private int tablesize = 100;
	@SuppressWarnings("unchecked")
	private MyLinkedList<Entry>[] table = new MyLinkedList[tablesize];
	class Entry
	{
		private String w;
		private MyLinkedList<Position> l;
		public Entry(String word, MyLinkedList<Position> list)
		{
			w = word;
			l = list;
		}
		public String getStringName()
		{
			return w;
		}
		public MyLinkedList<Position> getPosList()
		{
			return l;
		}
	}
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
	public void addPositionsForWords(WordEntry w)
	{
		String word = w.getWord();
		MyLinkedList<Position> pos = w.getAllPositionsForThisWord();
		int key = getHashIndex(word);
		Entry entry = new Entry(word,pos);
		table[key].addRear(entry);
	}
}
