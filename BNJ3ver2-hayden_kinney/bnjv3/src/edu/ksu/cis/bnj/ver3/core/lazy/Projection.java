package edu.ksu.cis.bnj.ver3.core.lazy;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.core.Value;
import edu.ksu.cis.bnj.ver3.core.values.Field;
import edu.ksu.cis.bnj.ver3.core.values.ValueZero;
/*!
 * \file Projection.java
 * \author Jeffrey M. Barber
 */
public class Projection extends CPF
{
	private CPF				_CPF;
	private BeliefNode[]	_Set;
	private int[]			_Projection;
	private int[]			_CacheAddress;
	private int[]			_SubSizeBuffer;
	/*! query the projection
	 * \see edu.ksu.cis.bnj.ver3.core.CPF#get(int[])
	 */
	public Value get(int[] query)
	{
		CPF.applyProjectionMapping(query, _Projection, _CacheAddress);
		return getZ(_CacheAddress);
	}
	/*! query the sum of the projection
	 * \see edu.ksu.cis.bnj.ver3.core.CPF#get(int[])
	 */
	public Value getZ(int[] query)
	{
		for (int k = 0; k < query.length; k++)
		{
			if (query[k] == -1)
			{
				Value s = ValueZero.SingletonZero;
				for (int j = 0; j < _SubSizeBuffer[k]; j++)
				{
					query[k] = j;
					Value p = getZ(query);
					s = Field.add(s, p);
				}
				query[k] = -1;
				return s;
			}
		}
		return _CPF.get(query);
	}
	/*! Projectiion from an CPF (original) to a new set
	 * \param[in] original CPF
	 * \param[in] set the new domain product
	 */
	public Projection(CPF original, BeliefNode[] set)
	{
		_DomainProduct = set;
		_SizeBuffer = new int[_DomainProduct.length];
		for (int i = 0; i < _SizeBuffer.length; i++)
		{
			_SizeBuffer[i] = _DomainProduct[i].getDomain().getOrder();
		}
		_SubSizeBuffer = new int[original.getDomainProduct().length];
		for (int i = 0; i < original.getDomainProduct().length; i++)
		{
			_SubSizeBuffer[i] = original.getDomainProduct()[i].getDomain().getOrder();
		}
		_CPF = original;
		_Set = set;
		_Projection = CPF.getProjectionMapping(set, original.getDomainProduct());
		_CacheAddress = original.realaddr2addr(0);
	}
	/*! this class is a sub class
	 * \see edu.ksu.cis.bnj.ver3.core.CPF::isSubClass()
	 */
	public boolean isSubClass()
	{
		return true;
	}
}