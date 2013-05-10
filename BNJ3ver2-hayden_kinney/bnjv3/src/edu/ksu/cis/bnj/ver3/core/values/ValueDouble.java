package edu.ksu.cis.bnj.ver3.core.values;
import edu.ksu.cis.bnj.ver3.core.Value;
/*!
 * \file ValueDouble.java
 * \author Jeffrey M. Barber
 */
public class ValueDouble implements Value
{
	private double	_Value;
	/*! A box over double
	 * \param[in] s  the double to be boxed up
	 */
	public ValueDouble(double s)
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
	public double getValue()
	{
		return _Value;
	}
}