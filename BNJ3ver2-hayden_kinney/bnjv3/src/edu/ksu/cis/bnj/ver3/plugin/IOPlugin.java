package edu.ksu.cis.bnj.ver3.plugin;
/*!
 * \file IOPlugin.java
 * \author Jeffrey M. Barber
 */
public interface IOPlugin
{
	public int getType(); // 0 = BN:Import, 1 = BN:Export, 3 = 1+2 
	public String getClassName(int mode);
}
