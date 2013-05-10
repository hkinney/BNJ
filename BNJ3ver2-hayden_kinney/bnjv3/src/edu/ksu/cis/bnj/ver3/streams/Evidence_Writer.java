package edu.ksu.cis.bnj.ver3.streams;

import java.util.Iterator;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.evidence.EvidenceList;
import edu.ksu.cis.bnj.ver3.core.evidence.EvidenceSample;

/*!
 * \file Evidence_Writer.java
 * \author Jeffrey M. Barber
 */
public class Evidence_Writer
{
	public void BeginCollectSamples(EvidenceStream out)
	{
		out.BeginEvidence();
	}
	
	public void WriteSample(BeliefNetwork bn, EvidenceStream out, int time)
	{
		out.BeginSample(time);
		BeliefNode[] nodes = bn.getNodes();
		for(int i = 0; i < nodes.length; i++)
		{
			if(nodes[i].hasEvidence())
			{
				out.Witness(nodes[i].getName(), nodes[i].getEvidence().getName(nodes[i].getDomain()));
			}
		}
		out.EndSample();
	}
	
	public void EndCollectSamples(EvidenceStream out)
	{
		out.EndEvidence();
	}
	
	public void WriteEvidence(BeliefNetwork bn, EvidenceStream out)
	{
		out.Start();
		out.BeginEvidence();
		out.BeginSample(0);
		BeliefNode[] nodes = bn.getNodes();
		for(int i = 0; i < nodes.length; i++)
		{
			if(nodes[i].hasEvidence())
			{
				out.Witness(nodes[i].getName(), nodes[i].getEvidence().getName(nodes[i].getDomain()));
			}
		}
		out.EndSample();
		out.EndEvidence();
		out.End();
	}
	
	public void WriteEvidenceList(EvidenceList el, EvidenceStream out)
	{
		out.Start();
		out.BeginEvidence();
		for(int i = 0; i < el.size(); i++)
		{
			EvidenceSample es = el.getSample(i);
			out.BeginSample(i);
			for(Iterator it = es.getKeyIterator(); it.hasNext(); )
			{
				String key = es.getKey(it);
				String value = es.getValue(key);
				out.Witness(key,value);
			}
			out.EndSample();
		}
		out.EndEvidence();
		out.End();
	}
}
