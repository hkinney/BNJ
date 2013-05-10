package edu.ksu.cis.bnj.ver3.core.values;
import edu.ksu.cis.bnj.ver3.core.Value;
/*!
 * \file ExprVar.java
 * \author Jeffrey M. Barber
 */
public class ExprVar implements Value
{
	private String	_Var;
	/*! An unknown
	 * \param[in] var the unknown
	 */
	public ExprVar(String var)
	{
		_Var = var;
	}
	/*! Get the string value of this value
	 * \see edu.ksu.cis.bnj.ver3.core.Value::getExpr()
	 * \return the string expression
	 */
	public String getExpr()
	{
		return "(" + _Var + ")";
	}
}