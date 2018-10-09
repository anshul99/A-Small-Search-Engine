public class SearchEngine
{
	private InvertedPageIndex index;
	public SearchEngine()
	{
		index = new InvertedPageIndex();
	}
	public void performAction(String actionMessage)
	{
		String[] str = actionMessage.split(" ");
		String action = "", x = "" , y = "";
		if (str.length == 3)
		{
			action = str[0];
			x = str[1];
			x = x.toLowerCase();
			y = str[2];
		}
		else if (str.length == 2)
		{
			action = str[0];
			x = str[1];
			x = x.toLowerCase();
		}
		else
		{
			System.out.println("Invalid action message");
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
				System.out.println(e.getMessage());
			}
		}
			break;
		case "queryFindPagesWhichContainWord":
		{
			MySet<PageEntry> ps = index.getPagesWhichContainWord(x);
			if (ps.getSize() == 0)
			{
				System.out.println("No webpage contains word " + x);
			}
			else
			{
				MyLinkedList<PageEntry> l = ps.list();
				MyLinkedList<PageEntry>.Node n = l.getHead();
				String ans = "";
				for(int i=0;i<l.getSize()-1;i++)
				{
					ans += n.data().getName() + ", ";
					n = n.next();
				}
				ans += n.data().getName();
				System.out.println(ans);
			}
		}
			break;
		case "queryFindPositionsOfWordInAPage":
		{
			try 
			{
				PageEntry p = new PageEntry(y);
				PageIndex pi = p.getPageIndex();
				WordEntry w = pi.searchByWord(x);
				if (w == null)
				{
					System.out.println("Webpage " + y + " does not contain word " + x);
				}
				else
				{
					MyLinkedList<Position> l = w.getAllPositionsForThisWord();
					MyLinkedList<Position>.Node n = l.getHead();
					String ans = "";
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
					}
					System.out.println(ans);
				}
			} 
			catch (Exception e)
			{
				System.out.println("No webpage " + y + " found");
			}
		}
			break;
		default: System.out.println("Invalid action message");
		}
	}
}
