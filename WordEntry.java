public class WordEntry
{
	private String w;
	private MyLinkedList<Position> l = new MyLinkedList<>();
	public WordEntry(String word)
	{
		w = word;
	}
	public String getWord()
	{
		return w;
	}
	public void addPosition(Position position)
	{
		l.addRear(position);
	}
	public void addPositions(MyLinkedList<Position> positions)
	{
		MyLinkedList<Position>.Node n = positions.getHead();
		while(n != null)
		{
			l.addRear(n.data());
			n = n.next();
		}
	}
	public MyLinkedList<Position> getAllPositionsForThisWord()
	{
		return l;
	}
	public float getTermFrequency(String word)
	{
		float tf = 0;
		int fw = l.getSize();
		MyLinkedList<Position>.Node n = l.getHead();
		PageEntry pe = n.data().getPageEntry();
		int tot = pe.getSize();
		tf = (float)fw/tot;
		return tf;
	}
}