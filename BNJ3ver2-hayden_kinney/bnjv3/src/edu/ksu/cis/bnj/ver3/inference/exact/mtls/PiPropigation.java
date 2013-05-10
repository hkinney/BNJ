package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
/*!
 * \file PiPropigation.java
 * \author Jeffrey M. Barber
 */
public class PiPropigation extends Message
{
	MessageCoordinator	_MessageCoordinator;
	/*! Pi Propigation
	 * \param[in] C the clique
	 * \param[in] MC the coordinator of messages
	 */
	public PiPropigation(MClique C, MessageCoordinator MC)
	{
		_Clique = C;
		_MessageCoordinator = MC;
	}
	/*! Run this Message
	 * \see edu.ksu.cis.bnj.ver3.inference.exact.mtls.Message::run()
	 */
	public void run()
	{
		_Clique.localPiPropigation(_MessageCoordinator);
	}
}