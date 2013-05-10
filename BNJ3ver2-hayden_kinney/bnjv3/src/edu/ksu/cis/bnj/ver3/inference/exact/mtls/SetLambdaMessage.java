package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
import edu.ksu.cis.bnj.ver3.core.CPF;
/*!
 * \file SetLambdaMessage.java
 * \author Jeffrey M. Barber
 */
public class SetLambdaMessage extends Message
{
	public MessageCoordinator	_MessageCoordinator;
	public CPF					_Lambda;
	/*! Set Lambda Message
	 * \param[in] C the clique
	 * \param[in] MC the coordinator of messages
	 * \param[in] Lambda the lambda message
	 */
	public SetLambdaMessage(MClique C, MessageCoordinator MC, CPF Lambda)
	{
		_Clique = C;
		_MessageCoordinator = MC;
		_Lambda = Lambda;
	}
	/*! Run this Message
	 * \see edu.ksu.cis.bnj.ver3.inference.exact.mtls.Message::run()
	 */
	public void run()
	{
		_Clique.localSetLambdaMessage(_Lambda, _MessageCoordinator);
	}
}