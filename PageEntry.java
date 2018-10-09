import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PageEntry
{
	private PageIndex pind;
	private String name;
	private int size;
	public PageEntry(String PageName) throws Exception
	{
		name = PageName;
		size = 0;
		pind = new PageIndex();
		FileInputStream fstream;
		try
		{
			fstream = new FileInputStream("webpages/"+name);
			Scanner s = new Scanner(fstream);
			String[] connectors = {"a","an","the","they","these","this","for","is","are","was","of","or","and","does","will","whose"};
			String[] punc = {"{","}","[","]","<",">","=","(",")",".",",",";","’","”","?","#","!","-",":"};
			int ind = 0;
			while (s.hasNextLine())
			{
				String str = s.nextLine();
				for(int i=0;i<punc.length;i++)
					str = str.replace(punc[i]," ");
				str = str.replace("  "," ");
				String[] arr = str.split(" ");
				size += arr.length;
				for(int i=0;i<arr.length;i++)
				{
					ind += 1;
					String word = arr[i].toLowerCase();
					if (word.equals("stacks"))
						word = "stack";
					if (word.equals("structures"))
						word = "structure";
					if (word.equals("applications"))
						word = "application";
					int f = 1;
					for(int j=0;j<connectors.length;j++)
					{
						if (word.equals(connectors[j]) || word.equals(""))
						{
							f = 0;
							break;
						}
					}
					if (f == 1)
					{
						Position pos = new Position(this,ind);
						pind.addPositionForWord(word,pos);
					}
				}
			}
			s.close();
		}
		catch (FileNotFoundException e)
		{
			throw new Exception();
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
}