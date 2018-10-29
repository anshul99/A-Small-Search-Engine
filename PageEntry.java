import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PageEntry
{
	private PageIndex pind;
	private String name;
	private int size;
	private static int total = 0;
	private AVLTree treeArr[];
	private PageIndex pind2;
	public PageEntry(String PageName) throws Exception
	{
		name = PageName;
		size = 0;
		pind = new PageIndex();
		pind2 = new PageIndex();
		FileInputStream fstream;
		try
		{
			total += 1;
			fstream = new FileInputStream("webpages/"+name);
			Scanner s = new Scanner(fstream);
			String[] connectors = {"a","an","the","they","these","this","for","is","are","was","of","or","and","does","will","whose"};
			String[] punc = {"{","}","[","]","<",">","=","(",")",".",",",";","'","\"","?","#","!","-",":"};
			int ind = 0;
			int ind2 = 0;
			while (s.hasNextLine())
			{
				String str = s.nextLine();
				for(int i=0;i<punc.length;i++)
					str = str.replace(punc[i]," ");
				str = str.replace("  "," ");
				String[] arr = str.split("\\s{1,}");
				for(int i=0;i<arr.length;i++)
				{
					String word = arr[i].toLowerCase();
					if (!word.equals(""))
					{
						ind += 1;
						ind2 += 1;
					}
					if (word.equals("stacks"))
						word = "stack";
					if (word.equals("structures"))
						word = "structure";
					if (word.equals("applications"))
						word = "application";
					int f = 1;
					for(int j=0;j<connectors.length;j++)
					{
						if (word.equals(connectors[j]))
						{
							ind2 -= 1;
						}
						if (word.equals(connectors[j]) || word.equals(""))
						{
							f = 0;
							break;
						}
					}
					if (f == 1)
					{
						Position pos = new Position(this,ind);
						Position pos2 = new Position(this,ind2);
						pind.addPositionForWord(word,pos);
						pind2.addPositionForWord(word,pos2);
						size += 1;
					}
				}
			}
			s.close();
			makeTreeArr();
		}
		catch (FileNotFoundException e)
		{
			throw new Exception("File not found");
		}
		
	}
	public PageIndex getPageIndex()
	{
		return pind;
	}
	public int getSize()
	{
		return size;
	}
	public String getName()
	{
		return name;
	}
	public void makeTreeArr()
	{
		MyLinkedList<WordEntry> we = pind2.getWordEntries();
		treeArr = new AVLTree[we.getSize()];
		MyLinkedList<WordEntry>.Node n = we.getHead();
		for(int i=0;i<we.getSize();i++)
		{
			treeArr[i] = n.data().getTree();
			n = n.next();
		}
	}
	public int countPhrase(String[] str)
	{
		WordEntry we = pind2.searchByWord(str[0]);
		int index = pind2.getWordEntries().index(we);
		if (index == -1)
			return 0;
		AVLTree t = treeArr[index];
		ArrayList<Position> poslist = new ArrayList<>();
		poslist = t.getInorder(poslist,t.root());
		int cnt = 0;
		for(int i=0;i<poslist.size();i++)
		{
			boolean f = true;
			for(int j=1;j<str.length;j++)
			{
				WordEntry we2 = pind2.searchByWord(str[j]);
				int ind = pind2.getWordEntries().index(we2);
				if (ind == -1)
				{
					f = false;
					break;
				}
				AVLTree t2 = treeArr[ind];
				if (!t2.find(new Position(this,poslist.get(i).getWordIndex()+j)))
				{
					f = false;
					break;
				}
			}
			if (f)
				cnt += 1;
		}
		return cnt;
	}
	public float getRelevanceOfPage(String str[], boolean doTheseWordsRepresentAPhrase)
	{
		float rel = 0;
		if (!doTheseWordsRepresentAPhrase)
		{
			for(int i=0;i<str.length;i++)
			{
				int occurInPage = 0;
				WordEntry we = pind.searchByWord(str[i]);
				if (we == null)
				{
					continue;
				}
				MyLinkedList<Position> l = we.getAllPositionsForThisWord();
				MyLinkedList<Position>.Node n = l.getHead();
				while (n != null)
				{
					PageEntry pe = n.data().getPageEntry();
					if (pe.equals(this))
						occurInPage += 1;
					n = n.next();
				}
				int pageContaining = InvertedPageIndex.getPagesWhichContainWord(str[i]).getSize();
				float termfreq = (float) occurInPage/size;
				float invdocfreq = (float) Math.log((float) total/pageContaining);
				rel += (termfreq*invdocfreq);
			}
		}
		else
		{
			int cnt = countPhrase(str);
			if (cnt == 0)
				return 0;
			int pageContaining = InvertedPageIndex.getPagesWhichContainPhrase(str).getSize();
			float invdocfreq = (float) Math.log((float) total/pageContaining);
			rel = ((float)cnt / (size - (str.length - 1)*cnt ))*invdocfreq;
		}
		return rel;
	}
}