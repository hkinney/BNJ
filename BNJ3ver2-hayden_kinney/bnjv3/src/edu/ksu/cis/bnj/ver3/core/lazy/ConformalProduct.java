package edu.ksu.cis.bnj.ver3.core.lazy;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.core.DiscreteEvidence;
import edu.ksu.cis.bnj.ver3.core.Value;
import edu.ksu.cis.bnj.ver3.core.values.Field;
import edu.ksu.cis.bnj.ver3.core.values.ValueUnity;
import edu.ksu.cis.bnj.ver3.core.values.ValueZero;
/*!
 * \file ConformalProduct.java
 * \author Jeffrey M. Barber
 */
public class ConformalProduct extends CPF
{
	public BeliefNode[]	_Base;
	protected int[]			zero;
	public int[][]		baseQueryProjectionCache;
	public int[][]		baseQueryAddressCache;
	public int[]			_SubsetCache;
	protected Value			unity;
	/*! query the actual cpfs of
	 * \param[in] query the query to be projected onto each node
	 * \return a value at that cell virtually
	 */
	private Value getZenRocks(int[] query)
	{
		Value result = unity;
		for (int j = 0; j < _Base.length; j++)
		{
			CPF.applyProjectionMapping(query, baseQueryProjectionCache[j], baseQueryAddressCache[j]);
			Value temp;
			if (_Base[j].hasEvidence()
					&& _Base[j].getEvidence().getEvidenceValue(query[_SubsetCache[j]]) instanceof ValueUnity)
			{
				temp = _Base[j].getCPF().get(baseQueryAddressCache[j]);
			}
			else
			{
				temp = _Base[j].query(baseQueryAddressCache[j]);
			}
			result = Field.mult(result, temp);
		}
		return result;
	}
	/*! query the conformal product
	 * \see edu.ksu.cis.bnj.ver3.core.CPF::get(int[])
	 */
	public Value get(int[] query)
	{
		if (zero != null)
		{
			boolean keep = true;
			for (int j = 0; j < zero.length && keep; j++)
			{
				keep = keep && (zero[j] == -1 || zero[j] == query[j]);
			}
			if (!keep) return ValueZero.SingletonZero;
		}
		return getZenRocks(query);
	}
	/*! construct a conformal product using a domain product as a filter, and the sources Nodes
	 * \param[in] DomainProduct the product domain of this virtual CPF
	 * \param[in] Nodes the underlying nodes for querying
	 */
	public ConformalProduct(BeliefNode[] DomainProduct, BeliefNode[] Nodes)
	{
		_Base = Nodes;
		zero = null;
		_DomainProduct = DomainProduct;
		_SizeBuffer = new int[_DomainProduct.length];
		for (int i = 0; i < _SizeBuffer.length; i++)
		{
			_SizeBuffer[i] = _DomainProduct[i].getDomain().getOrder();
		}
		baseQueryProjectionCache = new int[_Base.length][];
		baseQueryAddressCache = new int[_Base.length][];
		_SubsetCache = new int[_Base.length];
		unity = ValueUnity.SingletonUnity;
		for (int j = 0; j < _Base.length; j++)
		{
			baseQueryProjectionCache[j] = CPF
					.getProjectionMapping(_DomainProduct, _Base[j].getCPF().getDomainProduct());
			baseQueryAddressCache[j] = _Base[j].getCPF().realaddr2addr(0);
			for (int k = 0; k < _DomainProduct.length; k++)
			{
				if (_DomainProduct[k] == _Base[j])
				{
					_SubsetCache[j] = k;
				}
			}
		}
	}
	/*! this class is a sub class
	 * \see edu.ksu.cis.bnj.ver3.core.CPF::isSubClass()
	 */
	public boolean isSubClass()
	{
		return true;
	}
	/*! set the zero vector
	 * \see edu.ksu.cis.bnj.ver3.core.CPF::zeroExceptForNodeEvidence(edu.ksu.cis.bnj.ver3.core.BeliefNode[])
	 */
	public void zeroExceptForNodeEvidence(BeliefNode[] evNodes)
	{
		if (evNodes == null || evNodes.length == 0)
		{
			zero = null;
			return;
		}
		zero = realaddr2addr(0);
		for (int i = 0; i < zero.length; i++)
		{
			zero[i] = -1;
			for (int j = 0; j < evNodes.length; j++)
			{
				if (evNodes[j] == _DomainProduct[i])
				{
					if (evNodes[j].getEvidence() instanceof DiscreteEvidence)
					{
						DiscreteEvidence DE = (DiscreteEvidence) evNodes[j].getEvidence();
						zero[i] = DE.getDirectValue();
						//zero[i] = evNodes[j].getEvidence().getEvidenceValue();
					}
				}
			}
		}
	}
}