package edu.ksu.cis.bnj.ver3.core.lazy;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.core.Value;
import edu.ksu.cis.bnj.ver3.core.values.Field;
/*!
 * \file Multiply.java
 * \author Jeffrey M. Barber
 */
public class Multiply extends CPF
{
	private CPF	_Left;
	private CPF	_Right;
	/*! query the multiplication
	 * \see edu.ksu.cis.bnj.ver3.core.CPF#get(int[])
	 */
	public Value get(int[] query)
	{
		Value L = _Left.get(query);
		Value R = _Right.get(query);
		return Field.mult(L, R);
	}
	/*! multiply two CPFS in an inner manner
	 * \param[in] left the left hand side of the multiplication (top)
	 * \param[in] right the right hand side of the multiplication (bottom)
	 */
	public Multiply(CPF left, CPF right)
	{
		_DomainProduct = left.getDomainProduct();
		_SizeBuffer = new int[_DomainProduct.length];
		for (int i = 0; i < _SizeBuffer.length; i++)
		{
			_SizeBuffer[i] = _DomainProduct[i].getDomain().getOrder();
		}
		_Left = left;
		_Right = new Projection(right, left.getDomainProduct());
	}
	/*! this class is a sub class
	 * \see edu.ksu.cis.bnj.ver3.core.CPF::isSubClass()
	 */
	public boolean isSubClass()
	{
		return true;
	}
}