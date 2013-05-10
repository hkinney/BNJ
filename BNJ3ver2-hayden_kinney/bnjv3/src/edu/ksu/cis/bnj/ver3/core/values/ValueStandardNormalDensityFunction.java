package edu.ksu.cis.bnj.ver3.core.values;
import edu.ksu.cis.bnj.ver3.core.Value;
/*!
 * \file ValueStandardNormalDensityFunction.java
 * \author Jeffrey M. Barber
 */
public class ValueStandardNormalDensityFunction implements Value
{
	private String	_Var;
	private double	_Mean;
	private double	_StdDev2;
	/*!
	 * N(var;Mean,StdDev2)
	 * @param var - the variable of which the distribution is focused around
	 * @param Mean - the mean of the normal density function
	 * @param StdDev2 - the standard deviation squared
	 */
	public ValueStandardNormalDensityFunction(String var, double Mean, double StdDev2)
	{
		_Var = var;
		_Mean = Mean;
		_StdDev2 = StdDev2;
	}
	/*! Get the string value of this value
	 * \see edu.ksu.cis.bnj.ver3.core.Value::getExpr()
	 * \return the string expression
	 */
	public String getExpr()
	{
		return "N(" + _Var + ";" + _Mean + "," + _StdDev2 + ")";
	}
}