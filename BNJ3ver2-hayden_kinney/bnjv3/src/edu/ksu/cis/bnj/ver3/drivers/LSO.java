package edu.ksu.cis.bnj.ver3.drivers;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.inference.exact.LSonline;
import edu.ksu.cis.util.driver.Options;

public class LSO
{
	public static void onbn(Options opt, BeliefNetwork bn)
	{
		LSonline lsr = new LSonline();
		int trials = opt.getInteger("trials", 1);
		if (trials == 1)
		{
			trials = opt.getInteger("t", 1);
		}
		Options.outputln("running " + trials + " trials");
		for (int i = 0; i < trials; i++)
		{
			lsr.run(bn);
		}
		Options.outputln(opt.renderInferenceResult(bn, lsr));
	}
	public static void exec(Options opt)
	{
		Options.outputln("LSrealtime");
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