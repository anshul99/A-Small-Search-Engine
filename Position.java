public class Position
{
	private PageEntry page;
	private int index;
	Position(PageEntry p, int wordIndex)
	{
		page = p;
		index = wordIndex;
	}
	public PageEntry getPageEntry()
	{
		return page;
	}
	public int getWordIndex()
	{
		return index;
	}
}
