package edu.ksu.cis.bnj.gui.tools;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
public class LanguageUnicodeParser
{
	static LanguageUnicodeParser	instance	= new LanguageUnicodeParser();
	public boolean					debugmode	= true;
	static public LanguageUnicodeParser getInstance()
	{
		return instance;
	}
	public LanguageUnicodeParser()
	{
		try
		{
			_mapping = new HashMap();
			parse(new FileInputStream("default.ini"));
		}
		catch (Exception e)
		{
			// eh, don't care?
		}
	}
	private HashMap	_mapping;
	private void mapvalue(String key, String val)
	{
		_mapping.put(key, val);
	}
	public String get(String key)
	{
		String v = (String) _mapping.get(key);
		if (v == null)
		{
			if (debugmode) System.out.println(key + " = ?");
			return ".ini bad" + key;
		}
		return v;
	}
	private void parseline(String x)
	{
		if (x.length() <= 1) return;
		if (x.charAt(0) == '!')
		{
			//System.out.println("comment: " + x);
			return;
		}
		try
		{
			int k = x.indexOf("=");
			String key = x.substring(0, k).trim();
			//			System.out.println("got key:" + key);
			String val = x.substring(k + 1).trim();
			//			System.out.println("got val:" + val);
			mapvalue(key, val);
		}
		catch (Exception e)
		{
			// report error on line X
		}
	}
	public void parse(String filename)
	{
		try
		{
			parse(new FileInputStream(filename));
		}
		catch (Exception e)
		{
			// eh, don't care?
		}
	}
	public void parse(InputStream IS)
	{
		try
		{
			int h = IS.read();
			int l = IS.read();
			boolean isunicode = false;
			boolean swap = false;
			if (l == 254 && h == 255)
			{
				isunicode = true;
			}
			if (l == 255 && h == 254)
			{
				isunicode = true;
				swap = true;
			}
			if (!isunicode)
			{
				return;
			}
			String buffer = "";
			while (IS.available() > 0)
			{
				if (swap)
				{
					h = IS.read();
					l = IS.read();
				}
				else
				{
					l = IS.read();
					h = IS.read();
				}
				char code = (char) (short) (h * 256 + l);
				//System.out.println(" " + h + " : " + l);
				if (code == 10)
				{
					parseline(buffer.trim());
					buffer = "";
				}
				if (code >= 30)
				{
					buffer += code;
				}
			}
			parseline(buffer.trim());
			IS.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("failure?");
		}
	}
}