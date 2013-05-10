package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
/*!
 * \file Message.java
 * \author Jeffrey M. Barber
 */
public abstract class Message
{
	protected MClique	_Clique;
	/*! Get the clique that this message belongs too
	 * @return the clique
	 */
	public MClique getClique()
	{
		return _Clique;
	}
	/*! Run this message
	 */
	public abstract void run();
}