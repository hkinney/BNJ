package edu.ksu.cis.bnj.ver3.learning.data;

import edu.ksu.cis.bnj.ver3.core.Discrete;

/*!
 * \file DataSource.java
 * \author Jeffrey M. Barber
 */
public interface IDiscreteDataSource
{
	public String getColumnName(int col);
	public String getValueName(int col, int val);

	public int findColumn(String name);
	public Discrete getDiscrete(int col); 
	
	public int getNumberColumns();
	public int getNumberRows();
	
	public void reset(int[] q);
	public int query(int[] q);
	public int[] getQueryRegister();
}
