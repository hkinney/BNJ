package edu.ksu.cis.bnj.ver3.inference.exact.mtls;
import java.util.LinkedList;
import edu.ksu.cis.bnj.ver3.core.CPF;
/*!
 * \file MessageCoordinator.java
 * \author Jeffrey M. Barber
 */
public class MessageCoordinator
{
	private int				NUMBER_PROCESSORS;
	private CPF[]			_Marginals;
	private LSMarginalHook	_MarginalHook;
	private int				_MarginalCounter;
//	private ArrayList		_Messages;
	private MessageQueue	_Messages;
	private MClique[]		_CliqueCheckout;
	/*! Construct the Message Coordinator
	 * \param numMarginals the numober of marginals
	 * \param lsmh the hook for knowing when marginals are done 
	 * \param MarginalCounter how many marginals from cliques are to be processed
	 * \param NumProc the number of message
	 */
	public MessageCoordinator(int numMarginals, LSMarginalHook lsmh, int MarginalCounter, int NumProc)
	{
		//NUMBER_PROCESSORS = GlobalOptions.getInstance().getInteger("number_processors", 2);
		NUMBER_PROCESSORS = NumProc;
		_Marginals = new CPF[numMarginals];
		_MarginalHook = lsmh;
		_MarginalCounter = MarginalCounter;
		_CliqueCheckout = new MClique[NUMBER_PROCESSORS];
		_Messages = new MessageQueue();
	}
	/*! Launch the processes in the background (Workes)
	 */
	private void RunBackground()
	{
		for (int i = NUMBER_PROCESSORS - 1; i >= 1; i--)
		{
			MessageProcessor MP = new MessageProcessor(this, i);
			MP.start();
		}
	}
	/*! place a message on the message queue
	 * \param[in] M the new message
	 */
	private synchronized void put(Message M)
	{
		_Messages.add(M);
		if (M instanceof BuildMarginals)
		{
			_MarginalCounter--;
			if (_MarginalCounter == 0)
			{
				_Messages.add(new SetHook(M.getClique(), _MarginalHook, _Marginals));
			}
		}
	}
	/*! get a message from the queue
	 * \param[in] Proc
	 * \return a message, or null if none exist of all cliques are busy
	 */
	public synchronized Message get(int Proc)
	{
		if (_CliqueCheckout[Proc] != null)
		{
			_CliqueCheckout[Proc].assignProcessor(-1);
			_CliqueCheckout[Proc] = null;
		}
		return _Messages.get(_CliqueCheckout, Proc);
	}
	/*! Run the background and foreground processors
	 */
	public void run()
	{
		RunBackground();
		MessageProcessor MP = new MessageProcessor(this, 0);
		MP.run();
	}
	/*! Message Routing for \see BuildMarginals */
	public void queueBuildMarginal(MClique C)
	{	put(new BuildMarginals(C, _Marginals));}
	/*! Message Routing for \see BuildCPF */
	public void queueBuildCPF(MClique C, LinkedList evNodes)
	{	put(new BuildCPF(C, evNodes));		 }
	/*! Message Routing for \see LambdaPropigation */
	public void queueLambdaPropigation(MClique C)
	{	put(new LambdaPropigation(C, this));	 }
	/*! Message Routing for \see SetLambdaMessage */
	public void queueSetLambdaMessage(MClique C, CPF lambda)
	{ put(new SetLambdaMessage(C,this,lambda)); }	
	/*! Message Routing for \see PiPropigation */
	public void queuePiPropigation(MClique C)
	{	put(new PiPropigation(C, this)); 		 }
	/*! Message Routing for \see SetPiMessage */
	public void queueSetPiMessage(MClique C, CPF Message)
	{	put(new SetPiMessage(C,Message,this)); }	
}