package edu.ksu.cis.util.graph.visualization.operators;
import edu.ksu.cis.util.graph.visualization.CodePage;
import edu.ksu.cis.util.graph.visualization.CodePageOperator;
/*!
 * \file CodePageUpdateGlobalEnvironment.java
 * \author Jeffrey M. Barber
 */
public class CodePageUpdateGlobalEnvironment implements CodePageOperator
{
	private int		_currentPage;
	private String	_key;
	private String	_value;
	private String	_oldvalue;
	/*! update the global environment, map key to value
	 * \param[in] key the key
	 * \param[in] value the value
	 */
	public CodePageUpdateGlobalEnvironment(String key, String value)
	{
		_key = key;
		_value = value;
	}
	/*! apply this code page operator
	 * \see edu.ksu.cis.util.graph.visualization.CodePageOperator::apply(edu.ksu.cis.util.graph.visualization.CodePage)
	 */
	public void apply(CodePage CP)
	{
		_currentPage = CP.getIndex();
		_oldvalue = CP.getGlobal(_key);
		CP.setGlobal(_key, _value);
	}
	/*! apply the inverse of this code page operator
	 * \see edu.ksu.cis.util.graph.visualization.CodePageOperator::applyInverse(edu.ksu.cis.util.graph.visualization.CodePage)
	 */
	public void applyInverse(CodePage CP)
	{
		if (_oldvalue != null) CP.setGlobal(_key, _oldvalue);
	}
	/*! (non-Javadoc)
	 * \see edu.ksu.cis.util.graph.visualization.CodePageOperator::getCodePage()
	 */
	public int getCodePage()
	{
		return _currentPage;
	}
}