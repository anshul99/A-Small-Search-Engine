public class SearchResult implements Comparable<SearchResult>
{
	private PageEntry pe;
	private float rel;
	public SearchResult(PageEntry p, float r)
	{
		pe = p;
		rel = r;
	}
	public PageEntry getPageEntry()
	{
		return pe;
	}
	public float getRelevance()
	{
		return rel;
	}
	@Override
	public int compareTo(SearchResult otherObject)
	{
		if (rel > otherObject.getRelevance())
			return 1;
		if (rel < otherObject.getRelevance())
			return -1;
		return 0;
	}
	
	
}
