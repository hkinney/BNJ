package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
/*!
 * \file MessageProcessor.java
 * \author Jeffrey M. Barber
 */
public class MessageProcessor extends Thread
{
	private MessageCoordinator	_MessageCoordinator;
	private int					_Processor;
	private int					_Counter;
	/*! The Message Processor!
	 * @param[in] MC the Message Controller
	 * @param[in] Proc the proc ident
	 */
	public MessageProcessor(MessageCoordinator MC, int Proc)
	{
		_MessageCoordinator = MC;
		_Processor = Proc;
	}
	/*! Run this Processor
	 */
	public void run()
	{
		Message M = _MessageCoordinator.get(_Processor);
		while (M != null)
		{
			M.run();
			M = _MessageCoordinator.get(_Processor);
		}
	}
}