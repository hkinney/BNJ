package edu.ksu.cis.bnj.ver3.core.values;

import edu.ksu.cis.bnj.ver3.core.Value;

/*!
 * \file ValueFloat.java
 * \author Jeffrey M. Barber
 */
public class ValueFloat implements Value
{
	private float	_Value;
	/*! A box over float
	 * \param[in] s  the double to be boxed up
	 */
	public ValueFloat(float s)
	{
		_Value = s;
	}
	/*! Get the string value of this value
	 * \see edu.ksu.cis.bnj.ver3.core.Value::getExpr()
	 * \return the string expression
	 */
	public String getExpr()
	{
		return "" + _Value;
	}
	/*! helper, extract the double in the box
	 * @return the value of this cell
	 */
	public float getValue()
	{
		return _Value;
	}	
}
