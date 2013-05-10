package edu.ksu.cis.bnj.ver3.drivers;

import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.inference.exact.mtls.MTLS;
import edu.ksu.cis.util.driver.Options;

/*!
 * \file MTLS.java
 * \author Jeffrey M. Barber
 */
public class MTLSd
{
	public static void onbn(Options opt, BeliefNetwork bn)
	{
		MTLS ls = new  MTLS();
		int numProc = opt.getInteger("proc", 2);
		
		ls.run(bn,numProc);
		ls.waitUntilRecieved();
		Options.outputln(opt.renderInferenceResult(bn, ls));
	}
	
	public static void exec(Options opt)
	{
		Options.outputln("LS Message Passing - Multi Threaded");
		Options.outputln("-----------------------------------");
		String f;
		opt.begin();
		while ((f = opt.file()) != null)
		{
			
			BeliefNetwork bn = Options.load(f);
			if (bn != null)
			{
				opt.BeginPerfMeasure();
				onbn(opt, bn);
				opt.EndPerfMeasure();
				opt.outputPerformanceReport(bn.getName());
			}
		}
	}

}
