package edu.ksu.cis.util.graph.visualization.operators;
import java.util.ArrayList;
import edu.ksu.cis.util.graph.core.Edge;
import edu.ksu.cis.util.graph.visualization.Markup;
/*!
 * \file MarkupOperator.java
 * \author Jeffrey M. Barber
 */
public class MarkupOperator
{
	private Markup	_M;
	private Edge	_edgeTrack;
	/*! a marking at the midpoint of the edge E
	 * @param[in] E the edge to track
	 * @param[in] msg the message
	 */
	public MarkupOperator(Edge E, String msg)
	{
		_M = new Markup();
		_edgeTrack = E;
		_M.msg = msg;
	}
	/*! a marking at (x,y) of msg
	 * \param[in] x the x comp
	 * \param[in] y the y comp
	 * \param[in] msg the message
	 */
	public MarkupOperator(int x, int y, String msg)
	{
		_edgeTrack = null;
		_M = new Markup();
		_M.x = x;
		_M.y = y;
		_M.msg = msg;
	}
	/*! apply this markup (add)
	 * @param al
	 */
	public void apply(ArrayList al)
	{
		if (_edgeTrack != null)
		{
			_M.x = (_edgeTrack.s().getx() + _edgeTrack.d().getx()) / 2;
			_M.y = (_edgeTrack.s().gety() + _edgeTrack.d().gety()) / 2;
		}
		al.add(_M);
	}
	/*! apply the inverse of this markup (remove)
	 * @param al
	 */
	public void applyInverse(ArrayList al)
	{
		al.remove(_M);
	}
}