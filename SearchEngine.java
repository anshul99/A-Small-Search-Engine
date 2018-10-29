import java.util.ArrayList;

public class SearchEngine
{
	private InvertedPageIndex index;
	public SearchEngine()
	{
		index = new InvertedPageIndex();
	}
	@SuppressWarnings("static-access")
	public void performAction(String actionMessage)
	{
		String[] str = actionMessage.split(" ");
		String[] query = new String[str.length-1]; 
		String action = "", x = "" , y = "",orig_x = "";
		if (str[0].equals("queryFindPositionsOfWordInAPage"))
		{
			action = str[0];
			x = str[1];
			orig_x = x;
			y = str[2];
		}
		else if (str[0].equals("queryFindPagesWhichContainWord") || str[0].equals("addPage"))
		{
			action = str[0];
			x = str[1];
			orig_x = x;
		}
		else
		{
			action = str[0];
			for(int i=1;i<str.length;i++)
			{
				String s = str[i].toLowerCase();
				if (s.equals("stacks"))
					query[i-1] = "stack";
				else if (s.equals("structures"))
					query[i-1] = "structure";
				else if (s.equals("applications"))
					query[i-1] = "application";
				else
					query[i-1] = s;
			}
		}
		switch(action)
		{
		case "addPage":
		{
			PageEntry pe;
			try 
			{
				pe = new PageEntry(x);
				index.addPage(pe);
			}
			catch (Exception e)
			{
				System.out.println("No webpage " + x + " found");
			}
		}
			break;
		case "queryFindPagesWhichContainWord":
		{
			x = orig_x.toLowerCase();
			if (x.equals("stacks"))
				x = "stack";
			if (x.equals("structures"))
				x = "structure";
			if (x.equals("applications"))
				x = "application";
			MySet<PageEntry> ps = index.getPagesWhichContainWord(x);
			if (ps.getSize() == 0)
			{
				System.out.println("No webpage contains word " + orig_x);
			}
			else
			{
				MyLinkedList<PageEntry> l = ps.list();
				MyLinkedList<PageEntry>.Node n = l.getHead();
				MySet<SearchResult> res = new MySet<>();
				String[] st = {x};
				while (n != null)
				{
					PageEntry pe = n.data();
					float rel = pe.getRelevanceOfPage(st,false);
					SearchResult sr = new SearchResult(pe,rel);
					try
					{
						res.addElement(sr);
					}
					catch (Exception e)
					{}
					n = n.next();
				}
				MySort sort = new MySort();
				ArrayList<SearchResult> arr = sort.sortThisList(res);
				String ans = "";
				for(int i=0;i<arr.size()-1;i++)
				{
					ans += arr.get(i).getPageEntry().getName() + ", ";
				}
				ans += arr.get(arr.size()-1).getPageEntry().getName();
				System.out.println(ans);
			}
		}
			break;
		case "queryFindPositionsOfWordInAPage":
		{
			if (y.equals(""))
			{
				System.out.println("Invalid action message");
				return;
			}
			PageEntry p = index.search(y);
			if (p != null)
			{ 
				PageIndex pi = p.getPageIndex();
				x = orig_x.toLowerCase();
				if (x.equals("stacks"))
					x = "stack";
				if (x.equals("structures"))
					x = "structure";
				if (x.equals("applications"))
					x = "application";
				WordEntry w = pi.searchByWord(x);
				if (w == null)
				{
					System.out.println("Webpage " + y + " does not contain word " + orig_x);
				}
				else
				{
					MyLinkedList<Position> l = w.getAllPositionsForThisWord();
					MyLinkedList<Position>.Node n = l.getHead();
					String ans = "";
					String res = "";
					for(int i=0;i<l.getSize()-1;i++)
					{
						PageEntry p2 = n.data().getPageEntry();
						int ind = n.data().getWordIndex();
						if (p2.equals(p))
						{
							ans += ind + ", ";
						}
						n = n.next();
					}
					PageEntry p2 = n.data().getPageEntry();
					int ind = n.data().getWordIndex();
					if (p2.equals(p))
					{
						ans += ind;
						res = ans;
					}
					else if (l.getSize() != 1)
					{
						for(int i=0;i<ans.length()-2;i++)
							res += ans.charAt(i);
					}
					System.out.println(res);
				}
			}
			else
			{
				System.out.println("No webpage " + y + " found");

			}
		}
			break;
		case "queryFindPagesWhichContainAllWords":
		{
			MySet<PageEntry> ps = index.getPagesWhichContainWord(query[0]);
			for(int i=1;i<query.length;i++)
			{
				ps = ps.intersection(index.getPagesWhichContainWord(query[i]));
			}
			if (ps.getSize() == 0)
			{
				System.out.println("No webpage contains all of these words");
				return;
			}
			MyLinkedList<PageEntry> l = ps.list();
			MyLinkedList<PageEntry>.Node n = l.getHead();
			MySet<SearchResult> res = new MySet<>();
			while (n != null)
			{
				PageEntry pe = n.data();
				float rel = pe.getRelevanceOfPage(query,false);
				SearchResult sr = new SearchResult(pe,rel);
				try
				{
					res.addElement(sr);
				}
				catch (Exception e)
				{}
				n = n.next();
			}
			MySort sort = new MySort();
			ArrayList<SearchResult> arr = sort.sortThisList(res);
			String ans = "";
			for(int i=0;i<arr.size()-1;i++)
			{
				ans += arr.get(i).getPageEntry().getName() + ", ";
			}
			ans += arr.get(arr.size()-1).getPageEntry().getName();
			if (ans.equals(""))
				System.out.println("No webpage contains all of these words");
			else
				System.out.println(ans);
		}
			break;
		case "queryFindPagesWhichContainAnyOfTheseWords":
		{
			MySet<PageEntry> ps = index.getPagesWhichContainWord(query[0]);
			for(int i=1;i<query.length;i++)
			{
				ps = ps.union(index.getPagesWhichContainWord(query[i]));
			}
			if (ps.getSize() == 0)
			{
				System.out.println("No webpage contains any of these words");
				return;
			}
			MyLinkedList<PageEntry> l = ps.list();
			MyLinkedList<PageEntry>.Node n = l.getHead();
			MySet<SearchResult> res = new MySet<>();
			while (n != null)
			{
				PageEntry pe = n.data();
				float rel = pe.getRelevanceOfPage(query,false);
				SearchResult sr = new SearchResult(pe,rel);
				try
				{
					res.addElement(sr);
				}
				catch (Exception e)
				{}
				n = n.next();
			}
			MySort sort = new MySort();
			ArrayList<SearchResult> arr = sort.sortThisList(res);
			String ans = "";
			for(int i=0;i<arr.size()-1;i++)
			{
				ans += arr.get(i).getPageEntry().getName() + ", ";
			}
			ans += arr.get(arr.size()-1).getPageEntry().getName();
			if (ans.equals(""))
				System.out.println("No webpage contains any of these words");
			else
				System.out.println(ans);
		}
			break;
		case "queryFindPagesWhichContainPhrase":
		{
			MySet<PageEntry> ps = index.getPagesWhichContainPhrase(query);
			if (ps.getSize() == 0)
			{
				System.out.println("No webpage contains this phrase");
				return;
			}
			MySet<SearchResult> res = new MySet<>();
			MyLinkedList<PageEntry> l = ps.list();
			MyLinkedList<PageEntry>.Node n = l.getHead();
			while (n != null)
			{
				PageEntry pe = n.data();
				float rel = pe.getRelevanceOfPage(query,true);
				SearchResult sr = new SearchResult(pe,rel);
				try
				{
					res.addElement(sr);
				}
				catch (Exception e)
				{}
				n = n.next();
			}
			MySort sort = new MySort();
			ArrayList<SearchResult> arr = sort.sortThisList(res);
			String ans = "";
			for(int i=0;i<arr.size()-1;i++)
			{
				ans += arr.get(i).getPageEntry().getName() + ", ";
			}
			ans += arr.get(arr.size()-1).getPageEntry().getName();
			if (ans.equals(""))
				System.out.println("No webpage contains this phrase");
			else
				System.out.println(ans);
		}
		break;
		default: System.out.println("Invalid action message");
		}
	}
}
