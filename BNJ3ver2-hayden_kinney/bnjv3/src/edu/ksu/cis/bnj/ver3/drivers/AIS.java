/*
 * Created on Jul 29, 2004
 *
 * 
 */
package edu.ksu.cis.bnj.ver3.drivers;

import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.util.driver.Options;

/**
 * @author Andrew King
 *
 * This is the driver for the AIS code
 * TODO: need to get an interval and samples option in Options
 * then run AIS according to those parameters
 */
public class AIS {
	
	public static void onbn(Options opt, BeliefNetwork bn)
	{
		edu.ksu.cis.bnj.ver3.inference.approximate.sampling.AIS ais = new edu.ksu.cis.bnj.ver3.inference.approximate.sampling.AIS();
		int trials = opt.getInteger("trials", 1);
		int samples = opt.getInteger("samples", 2000);
		int interval = opt.getInteger("interval", 100);
		ais.setNumSamples(samples);
		ais.setInterval(interval);
		System.out.println("Samples: " + samples);
		System.out.println("Interval: " + interval);
		if (trials == 1)
		{
			trials = opt.getInteger("t", 1);
		}
		Options.outputln("running " + trials + " trials");
		for (int i = 0; i < trials; i++)
		{
			ais.run(bn);
		}
		Options.outputln(opt.renderInferenceResult(bn, ais));
	}
	public static void exec(Options opt)
	{
		Options.outputln("AIS");
		opt.begin();
		String f;
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
