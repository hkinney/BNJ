package edu.ksu.cis.util.data;

import java.util.TreeMap;

/**
 * file: IntTriplet.java
 * @author Jeffrey M. Barber
 */
public class IntTriplet
{
	public int x;
	public int y;
	public int z;
	public static TreeMap _html2colorname = null;
	
	public String strReg()
	{
		return "<" + x + "," + y + "," + z + ">";
	}
	public char v2hex(int v)
	{
		switch(v)
		{
			case 0: return '0';
			case 1: return '1';
			case 2: return '2';
			case 3: return '3';
			case 4: return '4';
			case 5: return '5';
			case 6: return '6';
			case 7: return '7';
			case 8: return '8';
			case 9: return '9';
			case 10: return 'A';
			case 11: return 'B';
			case 12: return 'C';
			case 13: return 'D';
			case 14: return 'E';
			case 15: return 'F';
		}
		return '0';
	}
	public String strHTML()
	{
		if(x<=255 && y <=255 && z <= 255 && x>=0 && y>=0 && z>=0)
		{
			return "#" + v2hex(x/16) + v2hex(x%16) + v2hex(y/16) + v2hex(y%16) + v2hex(z/16) + v2hex(z%16); 
		}
		else
		{
			return strReg();
		}
	}
	
	public String toString()
	{
		String key = strHTML(); 
		if(_html2colorname != null)
		{
			if(_html2colorname.get(key)!=null)
			{
				return (String) _html2colorname.get(key);
			}
		}
		return key;
	}
}
