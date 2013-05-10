package edu.ksu.cis.util.data;

import java.util.ArrayList;

/**
 * file: OrderedList.java
 * @author Jeffrey M. Barber
 */
public class OrderedList
{
	private ArrayList _data;
	public OrderedList()
	{
		_data = new ArrayList();
	}
	
	public void add(Object o)
	{
		_data.add(o);
	}
	
	public void remove(Object o)
	{
		_data.remove(o);
	}
	
	public int size()
	{
		return _data.size();
	}
	
	public Object get(int x)
	{
		return _data.get(x);
	}
}
