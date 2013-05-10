package edu.ksu.cis.util.graph.visualization.operators;
import edu.ksu.cis.util.graph.visualization.CodePage;
import edu.ksu.cis.util.graph.visualization.CodePageOperator;
/*!
 * \file CodePageSelect.java
 * \author Jeffrey M. Barber
 */
public class CodePageSelect implements CodePageOperator
{
	private int	_newindex;
	private int	_oldindex;
	private int	_currentPage;
	/*! select the newIdx page
	 * \param[in] newIdx
	 */
	public CodePageSelect(int newIdx)
	{
		_newindex = newIdx;
		_oldindex = -1;
	}
	/*! apply this code page operator
	 * \see edu.ksu.cis.util.graph.visualization.CodePageOperator::apply(edu.ksu.cis.util.graph.visualization.CodePage)
	 */
	public void apply(CodePage CP)
	{
		_currentPage = _newindex;
		if (CP != null) _oldindex = CP.getIndex();
	}
	/*! apply the inverse of this code page operator
	 * \see edu.ksu.cis.util.graph.visualization.CodePageOperator::applyInverse(edu.ksu.cis.util.graph.visualization.CodePage)
	 */
	public void applyInverse(CodePage CP)
	{
		_currentPage = _oldindex;
	}
	/*! (non-Javadoc)
	 * \see edu.ksu.cis.util.graph.visualization.CodePageOperator::getCodePage()
	 */
	public int getCodePage()
	{
		return _currentPage;
	}
}