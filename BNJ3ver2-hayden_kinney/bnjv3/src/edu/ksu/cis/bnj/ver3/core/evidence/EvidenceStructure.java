package edu.ksu.cis.bnj.ver3.core.evidence;
import java.util.Iterator;
import java.util.TreeMap;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.Discrete;
import edu.ksu.cis.bnj.ver3.core.Domain;
/*!
 * \file EvidenceStructure.java
 * \author Jeffrey M. Barber
 */
public class EvidenceStructure
{
	private BeliefNode[]	_Nodes;
	/*! construct by infering the eivdence structure of this list
	 * \param[in] el the evidence list
	 */
	public EvidenceStructure(EvidenceList el)
	{
		TreeMap Attributes = new TreeMap();
		for (int i = 0; i < el.size(); i++)
		{
			EvidenceSample es = el.getSample(i);
			for (Iterator it = es.getKeyIterator(); it.hasNext();)
			{
				String key = es.getKey(it);
				String value = es.getValue(key);
				if (Attributes.keySet().contains(key))
				{
					Discrete D = new Discrete();
					D.addName(value);
					Attributes.put(key, D);
				}
				else
				{
					Discrete D = (Discrete) Attributes.get(key);
					if (D.findName(value) < 0)
					{
						D.addName(value);
					}
				}
			}
		}
		_Nodes = new BeliefNode[Attributes.keySet().size()];
		int idx = 0;
		for (Iterator it = Attributes.keySet().iterator(); it.hasNext();)
		{
			String name = (String) it.next();
			Domain D = (Domain) Attributes.get(name);
			_Nodes[idx] = new BeliefNode(name, D);
			idx++;
		}
	}
	/*! construct from a set of names, and domains
	 * \param[in] Names
	 * \param[in] Domains
	 */
	public EvidenceStructure(String[] Names, Domain[] Domains)
	{
		_Nodes = new BeliefNode[Names.length];
		for (int i = 0; i < Names.length; i++)
		{
			_Nodes[i] = new BeliefNode(Names[i], Domains[i]);
		}
	}
	/*! get the nodes associated to this structure
	 * \return
	 */
	public BeliefNode[] getNodes()
	{
		return _Nodes;
	}
}