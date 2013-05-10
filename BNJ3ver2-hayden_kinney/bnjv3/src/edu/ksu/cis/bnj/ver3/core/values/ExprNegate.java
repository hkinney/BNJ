package edu.ksu.cis.bnj.ver3.core.values;
import edu.ksu.cis.bnj.ver3.core.Value;
/*!
 * \file ExprNegate.java
 * \author Jeffrey M. Barber
 */
public class ExprNegate implements Value
{
	private Value	_Val;
	/*! -a
	 * \param[in] a the value
	 */
	public ExprNegate(Value a)
	{
		_Val = a;
	}
	/*! Get the string value of this value
	 * \see edu.ksu.cis.bnj.ver3.core.Value::getExpr()
	 * \return the string expression
	 */
	public String getExpr()
	{
		return "(-(" + _Val.getExpr() + "))";
	}
}