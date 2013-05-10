package edu.ksu.cis.bnj.ver3.core.sparse;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.core.Value;
import edu.ksu.cis.bnj.ver3.core.values.Field;
import edu.ksu.cis.bnj.ver3.core.values.ValueUnity;
import edu.ksu.cis.bnj.ver3.core.values.ValueZero;
/*!
 * \file Multiply.java
 * \author Jeffrey M. Barber
 */
public class Sparse extends CPF
{
	/*! query the multiplication
	 * \see edu.ksu.cis.bnj.ver3.core.CPF#get(int[])
	 */
	public Value get(int[] query)
	{
	    return new ValueZero();
	    /*
		Value L = _Left.get(query);
		Value R = _Right.get(query);
		return Field.mult(L, R);
		*/
	}
	/*! multiply two CPFS in an inner manner
	 * \param[in] left the left hand side of the multiplication (top)
	 * \param[in] right the right hand side of the multiplication (bottom)
	 */
	public Sparse(CPF original)
	{
		_DomainProduct = original.getDomainProduct();
		_SizeBuffer = new int[_DomainProduct.length];
		for (int i = 0; i < _SizeBuffer.length; i++)
		{
			_SizeBuffer[i] = _DomainProduct[i].getDomain().getOrder();
		}
	}
	/*! this class is a sub class
	 * \see edu.ksu.cis.bnj.ver3.core.CPF::isSubClass()
	 */
	public boolean isSubClass()
	{
		return true;
	}
}