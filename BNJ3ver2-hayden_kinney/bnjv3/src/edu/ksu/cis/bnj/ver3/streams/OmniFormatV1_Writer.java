package edu.ksu.cis.bnj.ver3.streams;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.Domain;
/**
 * file: OmniFormatV1_Reader.java
 * 
 * @author Jeffrey M. Barber
 */
public class OmniFormatV1_Writer
{
	/**
	 * @param bn
	 * @param out
	 */
	public static void Write(BeliefNetwork bn, OmniFormatV1 out)
	{
		BeliefNode[] nodes = bn.getNodes();
		out.Start();
		out.CreateBeliefNetwork(0);
		out.SetBeliefNetworkName(0, bn.getName());
		for (int i = 0; i < nodes.length; i++)
		{
			//System.out.println("writing out:" + nodes[i].getName());
			out.BeginBeliefNode(nodes[i].loc());
			if(nodes[i].getType() == BeliefNode.NODE_CHANCE) out.SetType("chance");
			if(nodes[i].getType() == BeliefNode.NODE_DECISION) out.SetType("decision");
			if(nodes[i].getType() == BeliefNode.NODE_UTILITY) out.SetType("utility");
			out.SetBeliefNodeName(nodes[i].getName());
			Domain D = nodes[i].getDomain();
			for (int j = 0; j < D.getOrder(); j++)
			{
				out.BeliefNodeOutcome(D.getName(j));
			}
			out.SetBeliefNodePosition(nodes[i].getOwner().getx(), nodes[i].getOwner().gety());
			out.EndBeliefNode();
		}
		for (int i = 0; i < nodes.length; i++)
		{
			int j;
			BeliefNode[] children = bn.getChildren(nodes[i]);
			for (j = 0; j < children.length; j++)
			{
				//System.out.println("attempt connect:" + nodes[i].getName() + " to " + children[j].getName());
				out.Connect(i,children[j].loc());
			}
			//.out.println(" name: " + nodes[i].getName() + ":>" + nodes[i].getCPF().size());
		}
		for (int i = 0; i < nodes.length; i++)
		{
			int j;
			out.BeginCPF(nodes[i].loc());
			int max = nodes[i].getCPF().size();
			int tab = nodes[i].getCPF().getDomainProduct()[0].getDomain().getOrder();
			for (j = 0; j < max / tab; j++)
			{
				for (int k = 0; k < tab; k++)
				{
					out.ForwardFlat_CPFWriteValue(nodes[i].getCPF().get(k * max / tab + j).getExpr());
				}
			}
			out.EndCPF();
		}
		out.Finish();
	}
}