package edu.ksu.cis.bnj.ver3.drivers;
import java.io.FileInputStream;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.Discrete;
import edu.ksu.cis.bnj.ver3.core.DiscreteEvidence;
import edu.ksu.cis.bnj.ver3.core.evidence.EvidenceList;
import edu.ksu.cis.bnj.ver3.core.evidence.EvidenceSample;
import edu.ksu.cis.bnj.ver3.streams.Evidence_Reader;
import edu.ksu.cis.bnj.ver3.streams.xml.Converter_xmlevidence;
import edu.ksu.cis.util.driver.Options;
/**
 * file: LS.java
 * 
 * @author Jeffrey M. Barber
 */
public class LS
{
	public static void onbn(Options opt, BeliefNetwork bn, EvidenceList evidence)
	{
		if (evidence == null)
		{
			edu.ksu.cis.bnj.ver3.inference.exact.LS ls = new edu.ksu.cis.bnj.ver3.inference.exact.LS();
			int trials = opt.getInteger("trials", 1);
			if (trials == 1)
			{
				trials = opt.getInteger("t", 1);
			}
			Options.outputln("running " + trials + " trials");
			for (int i = 0; i < trials; i++)
			{
				ls.run(bn);
			}
			Options.outputln(opt.renderInferenceResult(bn, ls));
		}
		else
		{
			edu.ksu.cis.bnj.ver3.inference.exact.LS ls = new edu.ksu.cis.bnj.ver3.inference.exact.LS();
			BeliefNode[] bnodes = bn.getNodes();
			for(int k = 0; k < evidence.size(); k++)
			{
				EvidenceSample es = evidence.getSample(k);
				for(int s = 0; s < bnodes.length; s++)
				{
					String observe = es.getValue( bnodes[s].getName() );
					System.out.println("settting evidence for " + bnodes[s].getName());
					if( observe == null || observe.equals(""))
					{
						System.out.print(" to hidden\n");
						bnodes[s].setEvidence(null);
					}
					else
					{
						Discrete D = (Discrete) bnodes[s].getDomain();
						int V = D.findName(observe);
						if(V >= 0)
						{
							bnodes[s].setEvidence(new DiscreteEvidence(V));
							System.out.print(" to " + observe + "\n");
							
						}
						else
						{
							System.out.print(" to hidden\n");
							bnodes[s].setEvidence(null);
						}
					}
				}
				ls.run(bn);
				Options.outputln(opt.renderInferenceResult(bn, ls));
			}
		}
	}
	public static void exec(Options opt)
	{
		Options.outputln("LS");
		opt.begin();
		String EvidenceFile = opt.get("evidence");
		EvidenceList evidence = null;
		
		if(EvidenceFile != null)
		{
			if(EvidenceFile.length() >= 3)
			{
				try
				{
						Converter_xmlevidence evidenceLoad = new Converter_xmlevidence();
						Evidence_Reader ER = new Evidence_Reader();
						evidenceLoad.load(new FileInputStream(EvidenceFile), ER);
						evidence = ER.get(0);
				}
				catch (Exception e)
				{
					
				}
			}
		}
		
		
		String f;
		while ((f = opt.file()) != null)
		{
			BeliefNetwork bn = Options.load(f);
			if (bn != null)
			{
				opt.BeginPerfMeasure();
				onbn(opt, bn, evidence);
				opt.EndPerfMeasure();
				opt.outputPerformanceReport(bn.getName());
			}
		}
	}
}