package edu.ksu.cis.bnj.ver3.core.values;
import edu.ksu.cis.bnj.ver3.core.Value;
/*!
 * \file ExprAdd.java
 * \author Jeffrey M. Barber
 */
public class ExprAdd implements Value
{
	private Value	_Left;
	private Value	_Right;
	/*! (a + b)
	 * \param[in] a the left hand side
	 * \param[in] b the right hand side
	 */
	public ExprAdd(Value a, Value b)
	{
		_Left = a;
		_Right = b;
	}
	/*! Get the string value of this value
	 * \see edu.ksu.cis.bnj.ver3.core.Value::getExpr()
	 * \return the string expression
	 */
	public String getExpr()
	{
		return "( " + _Left.getExpr() + " + " + _Right.getExpr() + " )";
	}
}