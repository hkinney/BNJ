package edu.ksu.cis.bnj.ver3.streams;
/*!
 * \file CPFStream.java
 * \author Jeffrey M. Barber
 */
public interface CPFStream
{
	public void Begin();
	public void Write(int addr, String expr);
	public void End();
}
