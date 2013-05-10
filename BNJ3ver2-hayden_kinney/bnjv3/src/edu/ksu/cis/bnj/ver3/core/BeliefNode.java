package edu.ksu.cis.bnj.ver3.core;
import edu.ksu.cis.util.graph.core.Vertex;
/*!
 * \file BeliefNode.java
 * \author Jeffrey M. Barber
 */
public class BeliefNode
{
	public static final int NODE_CHANCE = 0;
	public static final int NODE_DECISION = 1;
	public static final int NODE_UTILITY = 2;
	
	private Vertex		_Owner;
	private CPF			_ConditionalProbabilityFunction;
	private Domain		_Domain;
	private String		_Name;
	private Evidence	_Evidence;
	private int			_Type;
	/*!
	 * Construct a node from a name and a domain
	 * \param[in] name 	the name of the node
	 * \param[in] d		the Domain of the node (i.e. Discrete / Continuous)
	 */
	public BeliefNode(String name, Domain d)
	{
		_Owner = null;
		_Name = name;
		_Domain = d;
		BeliefNode[] init = new BeliefNode[1];
		init[0] = this;
		_ConditionalProbabilityFunction = new CPF(init);
		_Evidence = null;
		_Type = BeliefNode.NODE_CHANCE;
	}
	/*! set the type of this node
	 * \param[in] type - the new type of this node, either it be CHANCE, DECISION, UTILITY
	 */
	public void setType(int type)
	{
		if(0 <= type && type <= 2)
		{
			_Type = type;
		}
	}
	/*! get the node type
	 * \return the type of this node, be it either CHANCE, DECISION, UTILITY
	 */
	public int getType()
	{
		return _Type;
	}
	/*! Set the domain of the node
	 * \note
	 * \par
	 * 		DO NOT USE unless you plan to change it back
	 * \param[in] D		the new domain, but the CPF associated to this are now broken
	 */
	public void setDomain(Domain D)
	{
		_Domain = D;
	}
	/*! Set the name of the node
	 * \note
	 * \par(for visual/reporting only, not use except for that)
	 * \param[in] name the new name
	 */
	public void setName(String name)
	{
		_Name = name;
	}
	/*! Set the evidence of this node
	 * \param[in] e 	the evidence value
	 */
	public void setEvidence(Evidence e)
	{
		_Evidence = e;
	}
	/*! Get the evidence of this node
	 * \return the evidence associate to this node (no evidence -> null)
	 */
	public Evidence getEvidence()
	{
		return _Evidence;
	}
	/*! Does this node have evidence?
	 * \return the answer to this question
	 */
	public boolean hasEvidence()
	{
		return _Evidence != null;
	}
	/*! Get the name of the node
	 * \note
	 * \par(for visual/reporting only, not use except for that)
	 * \return the name of the node
	 */
	public String getName()
	{
		return _Name;
	}
	/*! Get the CPF of this node
	 * \return the cpf of this node
	 */
	public CPF getCPF()
	{
		return _ConditionalProbabilityFunction;
	}
	/*! Set the CPF of this node
	 * \note
	 * \par
	 * 		DO NOT USE unless you plan to update the network
	 * \param[in] cpf 	the new cpf
	 */
	public void setCPF(CPF cpf)
	{
		_ConditionalProbabilityFunction = cpf;
	}
	/*! Get the domain of the cpf
	 * \return the domain
	 */
	public Domain getDomain()
	{
		return _Domain;
	}
	/*! set the Owner (a Vertex)
	 * \note
	 * \par
	 * 		DO NOT USE!!!!!!!!!!
	 * \param v
	 */
	public void setOwner(Vertex v)
	{
		_Owner = v;
	}
	/*! Get the Owner (a Vertex)
	 * \note
	 * \par
	 * 		TRY NOT TO USE THIS!!!
	 * \return the owner vertex
	 */
	public Vertex getOwner()
	{
		return _Owner;
	}
	/*!
	 * The optimization requirement
	 * \return the location index
	 */
	public int loc()
	{
		return _Owner.loc();
	}
	/*! Query a value from this node
	 * \param[in] query the query to the CPF or Evidence
	 * \return the value or the sum of values or the evidence
	 */
	public Value query(int[] query)
	{
		if (_Evidence != null)
		{
			return _Evidence.getEvidenceValue(query[0]);
		}
		return getCPF().get(query);
	}
	/*! Set the node's position
	 * \note
	 * \par(for visual/reporting only, not use except for that)
	 * @param[in] x 	the x component
	 * @param[in] y		the y component
	 */
	public void setPosition(int x, int y)
	{
		if (_Owner != null)
		{
			_Owner.translate(x - _Owner.getx(), y - _Owner.gety());
		}
	}
	
	public String toString() {
		return _Name;
	}
}