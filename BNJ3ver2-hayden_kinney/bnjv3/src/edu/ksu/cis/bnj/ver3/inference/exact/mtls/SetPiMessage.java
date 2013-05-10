package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
import edu.ksu.cis.bnj.ver3.core.CPF;
/*!
 * \file SetPiMessage.java
 * \author Jeffrey M. Barber
 */
public class SetPiMessage extends Message
{
	private CPF					_Message;
	public MessageCoordinator	_MessageCoordinator;
	/*! Set Pi Message
	 * \param[in] C the clique
	 * \param[in] Message the pi message
	 * \param[in] MC the coordinator of messages
	 */
	public SetPiMessage(MClique C, CPF Message, MessageCoordinator MC)
	{
		_MessageCoordinator = MC;
		_Clique = C;
		_Message = Message;
	}
	/*! Run this Message
	 * \see edu.ksu.cis.bnj.ver3.inference.exact.mtls.Message::run()
	 */
	public void run()
	{
		_Clique.localSetPiMessage(_Message, _MessageCoordinator);
	}
}