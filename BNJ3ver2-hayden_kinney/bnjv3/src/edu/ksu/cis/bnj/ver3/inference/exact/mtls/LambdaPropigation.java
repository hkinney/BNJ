package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
/*!
 * \file LambdaPropigation.java
 * \author Jeffrey M. Barber
 */
public class LambdaPropigation extends Message
{
	MessageCoordinator	_MessageCoordinator;
	/*! Lambda Propigation
	 * \param[in] C the clique
	 * \param[in] MC the coordinator of messages
	 */
	public LambdaPropigation(MClique C, MessageCoordinator MC)
	{
		_Clique = C;
		_MessageCoordinator = MC;
	}
	/*! Run this Message
	 * \see edu.ksu.cis.bnj.ver3.inference.exact.mtls.Message::run()
	 */
	public void run()
	{
		_Clique.localLambdaPropigation(_MessageCoordinator);
	}
}